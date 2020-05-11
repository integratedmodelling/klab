package org.integratedmodelling.klab.hub.tests;

import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertEquals;
import static org.hamcrest.Matchers.equalTo;

import java.util.HashMap;
import java.util.Map;

import javax.validation.ConstraintViolationException;

import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.integratedmodelling.klab.hub.api.MongoGroup;
import org.integratedmodelling.klab.hub.config.dev.DevMongoModelsConfig;
import org.integratedmodelling.klab.hub.config.dev.MongoConfigDev;
import org.integratedmodelling.klab.hub.groups.services.GroupServiceImpl;
import org.integratedmodelling.klab.hub.repository.MongoGroupRepository;
import org.integratedmodelling.klab.utils.FileCatalog;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.annotation.Order;
import org.springframework.dao.DuplicateKeyException;

@SpringBootTest(classes = {MongoConfigDev.class})
@RunWith(SpringRunner.class)
@ActiveProfiles(profiles = "development")
public class GroupServiceTests {
	
	@Autowired
	private MongoGroupRepository groupRepo;
	private GroupServiceImpl groupService;
	
	@Before
	public void setuo() {
		groupService = new GroupServiceImpl(groupRepo);
	}

	@Test(expected = ConstraintViolationException.class)
	@Order(1)
	public void fail_create_without_name() {
		MongoGroup group = new MongoGroup();
		group.setWorldview(false);
		groupService.create(group);
	}
	
	@Test(expected = DuplicateKeyException.class)
	@Order(2)
	public void fail_create_group_with_same_name() {
		MongoGroup group = new MongoGroup();
		group.setName("Test");
		group.setWorldview(false);
		groupService.create(group);
		groupService.create(group);
	}
	
	@Test
	@Order(3)
	public void pass_create_groups() {
		groupRepo.deleteAll();
		Map<String, MongoGroup> groups = new HashMap<>();
		groups = FileCatalog.create(DevMongoModelsConfig.class.getClassLoader().getResource("initial-groups.json"), MongoGroup.class);
		groups.forEach((k,v)-> groupService.create(v));
		assertThat(groupRepo.findAll().size(),equalTo(groups.size()));
	}
	
	@Test(expected = NullPointerException.class)
	@Order(4)
	public void fail_getGroup_not_in_db() {
		groupService.getByName("neverAdded");	
	}
	
	@Test
	@Order(5)
	public void pass_updateGroup() {
		Map<String, MongoGroup> groups = new HashMap<>();
		groups = FileCatalog.create(DevMongoModelsConfig.class.getClassLoader().getResource("initial-groups.json"), MongoGroup.class);
		MongoGroup update = groupService.getByName(groups.keySet().iterator().next());
		update.setDescription("I have made an update");
		MongoGroup updated = groupService.update(update);
		assertEquals(updated, update);
	}

}