package org.integratedmodelling.klab.hub.stats.services;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.integratedmodelling.klab.hub.api.User;
import org.integratedmodelling.klab.hub.api.ProfileResource;
import org.integratedmodelling.klab.hub.repository.UserRepository;
import org.integratedmodelling.klab.hub.stats.controllers.GroupUsersByDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.DateOperators;
import org.springframework.data.mongodb.core.aggregation.ProjectionOperation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.stereotype.Service;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.internal.connection.QueryResult;

@Service
public class HubStatsService {

	@Autowired
	private UserRepository userRepository;
	
	private ObjectMapper objectMapper;
	
	@Autowired
	private MongoTemplate mongoTemplate;
	
	public HubStatsService(UserRepository userRepository, ObjectMapper objectMapper) {
		super();
		this.userRepository = userRepository;
		this.objectMapper = objectMapper;
	}	
	
	public List<GroupUsersByDate> registeredUsers(String groupBy) {
		List<GroupUsersByDate> userList = null;
		switch(groupBy) {
		case "year":
			userList = userRepository.groupByYear();
			if (userList.size() > 0) {
				  Collections.sort(userList, new Comparator<GroupUsersByDate>() {
				      @Override
				      public int compare(final GroupUsersByDate object1, final GroupUsersByDate object2) {
				          return object1.getDateString().compareTo(object2.getDateString());
				      }
				  });
			}
			break;
		default:
			userList = userRepository.groupByMonthYear();
			if (userList.size() > 0) {
				  Collections.sort(userList, new Comparator<GroupUsersByDate>() {
				      @Override
				      public int compare(final GroupUsersByDate object1, final GroupUsersByDate object2) {
				          return object1.getDateString().compareTo(object2.getDateString());
				      }
				  });
				}
		}
		return userList;
	}
}
