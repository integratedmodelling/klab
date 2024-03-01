package org.integratedmodelling.klab.hub.tests;


import static org.junit.Assert.assertEquals;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import java.util.HashMap;
import java.util.Map;

import javax.validation.ConstraintViolationException;

import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.integratedmodelling.klab.hub.config.dev.DevMongoModelsConfig;
import org.integratedmodelling.klab.hub.config.dev.MongoConfigDev;
import org.integratedmodelling.klab.hub.groups.services.GroupServiceImpl;
import org.integratedmodelling.klab.hub.listeners.HubEventPublisher;
import org.integratedmodelling.klab.hub.repository.MongoGroupRepository;
import org.integratedmodelling.klab.hub.users.commands.CreateMongoGroup;
import org.integratedmodelling.klab.hub.users.dto.MongoGroup;
import org.integratedmodelling.klab.hub.users.exceptions.GroupDoesNotExistException;
import org.integratedmodelling.klab.hub.users.exceptions.GroupExistException;
import org.integratedmodelling.klab.hub.users.listeners.RemoveGroup;
import org.integratedmodelling.klab.utils.FileCatalog;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DuplicateKeyException;

@SpringBootTest(classes = {MongoConfigDev.class, HubEventPublisher.class})
@RunWith(SpringRunner.class)
@ActiveProfiles(profiles = "development")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class GroupServiceTests {
	
	@Autowired
	private MongoGroupRepository groupRepo;
	
	@Autowired
	private HubEventPublisher<RemoveGroup> publisher;
	
	private GroupServiceImpl groupService;
	
	@Before
	public void setup() {
		groupService = new GroupServiceImpl(groupRepo, publisher);
	}

	@Test(expected = ConstraintViolationException.class)
	public void test_01_fail_create_without_name() {
		MongoGroup group = new MongoGroup();
		group.setWorldview(false);
		new CreateMongoGroup(group, groupRepo).execute();
	}
	
	@Test
	public void test_02_pass_create_group() {
		MongoGroup group = new MongoGroup();
		group.setName("Test");
		group.setWorldview(false);
		MongoGroup newGroup = groupService.create(group);
		newGroup.getName().equals("Test");
		assertEquals(newGroup.getName().equals(group.getName()), true);
	}
	
	@Test(expected = GroupExistException.class)
	public void test_03_fail_create_group_with_same_name() {
		MongoGroup group = new MongoGroup();
		group.setName("Test");
		group.setWorldview(false);
		groupService.create(group);
	}
	
	@Test
	public void test_04_pass_create_groups() {
		groupRepo.deleteAll();
		Map<String, MongoGroup> groups = new HashMap<>();
		groups = FileCatalog.create(DevMongoModelsConfig.class.getClassLoader().getResource("initial-groups.json"), MongoGroup.class);
		groups.forEach((k,v)-> groupService.create(v));
		assertThat(groupRepo.findAll().size(),equalTo(groups.size()));
	}
	
	@Test(expected = GroupDoesNotExistException.class)
	public void test_05_fail_getGroup_not_in_db() {
		groupService.getByName("neverAdded");	
	}
	
	@Test
	public void test_06_pass_updateGroup() {
		Map<String, MongoGroup> groups = new HashMap<>();
		groups = FileCatalog.create(DevMongoModelsConfig.class.getClassLoader().getResource("initial-groups.json"), MongoGroup.class);
		MongoGroup update = groupService.getByName(groups.keySet().iterator().next());
		update.setDescription("I have made an update");
		MongoGroup updated = groupService.update(update);
		assertEquals(updated, update);
	}
	
	@Test
	public void test_07_pass_deleteGroup() {
		Map<String, MongoGroup> groups = new HashMap<>();
		groups = FileCatalog.create(DevMongoModelsConfig.class.getClassLoader().getResource("initial-groups.json"), MongoGroup.class);
		MongoGroup group = groupService.getByName(groups.keySet().iterator().next());
		groupService.delete(group.getName());
	}

}