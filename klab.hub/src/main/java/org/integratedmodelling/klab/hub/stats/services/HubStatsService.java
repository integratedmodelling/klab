package org.integratedmodelling.klab.hub.stats.services;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.integratedmodelling.klab.hub.repository.UserRepository;
import org.integratedmodelling.klab.hub.stats.controllers.GroupUsersByDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

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
		List<GroupUsersByDate> groupList = null;
		
		/* switch case, in case more options need to be added */
		switch(groupBy) {
		case "year":
			groupList = userRepository.groupByYear();
			break;
		case "monthAccumulation":
			groupList = userRepository.groupByMonthYear();
			break;
		case "yearAccumulation":
			groupList = userRepository.groupByYear();
			break;		
		/*by default users are grouped by month and year */
		default:
			groupList = userRepository.groupByMonthYear();
			if (groupList.size() > 0) {
				/* code to sort date strings */
				  Collections.sort(groupList, new Comparator<GroupUsersByDate>() {
				      @Override
				      public int compare(final GroupUsersByDate object1, final GroupUsersByDate object2) {
				          return object1.getDateString().compareTo(object2.getDateString());
				      }
				  });
				  
				  /* get rid of entries where registrationDate is null */
				  groupList.removeIf(o -> o.getDateString() == "NaN");
				  
				  /* formatting return list so that it also contains "empty" months */
				  int currentMonth = 0, currentYear = 0;
				  int previousMonth = 0, previousYear = groupList.get(0).getYear();
				  int monthDifference = 0;
				  GroupUsersByDate group = null;
				  for(int groupIndex = 0; groupIndex < groupList.size(); groupIndex++) {
					  group = groupList.get(groupIndex);
					  currentYear = group.getYear();
					  
					  if(previousYear != currentYear && previousMonth != 12 && previousYear != 0) {
						  monthDifference = 12 - previousMonth;
						  for(int i = 0; i < monthDifference; i++) {
							  GroupUsersByDate emptyGroup = new GroupUsersByDate();
							  emptyGroup.setYear(previousYear);
							  emptyGroup.setMonth(previousMonth + i + 1);
							  emptyGroup.setCount(0);
							  emptyGroup.setDateString(Integer.toString(currentYear) + "-" + String.format("%02d", emptyGroup.getMonth()));
							  groupList.add(groupIndex, emptyGroup);
							  groupIndex++;
						  }
						  previousMonth = 0;
					  } else if (previousYear != currentYear)	{
						  previousMonth = 0;
					  }
					  
					  currentMonth = group.getMonth();

					  if(currentMonth > previousMonth + 1) {
						  monthDifference = currentMonth - previousMonth - 1;
						  for(int i = 0; i < monthDifference; i++) {
							  GroupUsersByDate emptyGroup = new GroupUsersByDate();
							  emptyGroup.setYear(currentYear);
							  emptyGroup.setMonth(previousMonth + i + 1);
							  emptyGroup.setCount(0);
							  emptyGroup.setDateString(Integer.toString(currentYear) + "-" + String.format("%02d", emptyGroup.getMonth()));
							  groupList.add(groupIndex, emptyGroup);
							  groupIndex++;
						  }
					  }
					  previousMonth = currentMonth;
					  previousYear = currentYear;
				  }
				}
		}
		
		if (groupList.size() > 0) {
			  /* code to sort date strings */
			  Collections.sort(groupList, new Comparator<GroupUsersByDate>() {
			      @Override
			      public int compare(final GroupUsersByDate object1, final GroupUsersByDate object2) {
			          return object1.getDateString().compareTo(object2.getDateString());
			      }
			  });
			  
			  if(groupBy.equals("monthAccumulation")) {
				  /* get rid of entries where registrationDate is null */
				  groupList.removeIf(o -> o.getDateString() == "NaN");
				  
				  int accumulatedCounter = 0;
				  for(GroupUsersByDate group : groupList) {
					  accumulatedCounter += group.getCount();
					  group.setCount(accumulatedCounter);
				  }
				  
				  /* formatting return list so that it also contains "empty" months */
				  int currentMonth = 0, currentYear = 0, currentCount = 0;
				  int previousMonth = 0, previousYear = groupList.get(0).getYear(), previousCount = 0;
				  int monthDifference = 0;
				  GroupUsersByDate group = null;
				  for(int groupIndex = 0; groupIndex < groupList.size(); groupIndex++) {
					  group = groupList.get(groupIndex);
					  currentYear = group.getYear();
					  currentCount = group.getCount();
					  
					  if(previousYear != currentYear && previousMonth != 12) {
						  monthDifference = 12 - previousMonth;
						  for(int i = 0; i < monthDifference; i++) {
							  GroupUsersByDate emptyGroup = new GroupUsersByDate();
							  emptyGroup.setYear(previousYear);
							  emptyGroup.setMonth(previousMonth + i + 1);
							  emptyGroup.setCount(previousCount);
							  emptyGroup.setDateString(Integer.toString(currentYear) + "-" + String.format("%02d", emptyGroup.getMonth()));
							  groupList.add(groupIndex, emptyGroup);
							  groupIndex++;
						  }
						  previousMonth = 0;
					  } else if (previousYear != currentYear)	{
						  previousMonth = 0;
					  }
					  
					  currentMonth = group.getMonth();

					  if(currentMonth > previousMonth + 1) {
						  monthDifference = currentMonth - previousMonth - 1;
						  for(int i = 0; i < monthDifference; i++) {
							  GroupUsersByDate emptyGroup = new GroupUsersByDate();
							  emptyGroup.setYear(currentYear);
							  emptyGroup.setMonth(previousMonth + i + 1);
							  emptyGroup.setCount(previousCount);
							  emptyGroup.setDateString(Integer.toString(currentYear) + "-" + String.format("%02d", emptyGroup.getMonth()));
							  groupList.add(groupIndex, emptyGroup);
							  groupIndex++;
						  }
					  }
					  previousMonth = currentMonth;
					  previousYear = currentYear;
					  previousCount = currentCount;
				  }
			  }
			  
			  if(groupBy.equals("yearAccumulation")) {
					if (groupList.size() > 0) {
						int accumulatedCounter = 0;
						for(GroupUsersByDate group : groupList) {
							accumulatedCounter += group.getCount();
							group.setCount(accumulatedCounter);
						}
					}
			  }
		}
		return groupList;
	}
}
