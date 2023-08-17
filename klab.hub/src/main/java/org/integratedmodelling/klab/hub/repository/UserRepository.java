package org.integratedmodelling.klab.hub.repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.integratedmodelling.klab.auth.Role;
import org.integratedmodelling.klab.hub.api.User;
import org.integratedmodelling.klab.hub.stats.controllers.GroupUsersByDate;
import org.springframework.data.mongodb.repository.Aggregation;
import org.integratedmodelling.klab.hub.api.User.AccountStatus;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<User, ObjectId>{		
	
	Optional<User> findById(String id);
	
	Optional<User> findByName(String username); // need exactly the username
	
	Optional<User> findByNameIgnoreCase(String username);
	
	Optional<User> findByNameIgnoreCaseOrderByNameAsc(String username);
	
	Optional<User> findByEmailIgnoreCase(String email);
	
	Optional<User> findByNameIgnoreCaseOrEmailIgnoreCase(String username, String email);
	
	Collection<User> findByNameInIgnoreCase(Collection<String> username);
	
    Boolean existsByNameIgnoreCase(String username);

    Boolean existsByEmailIgnoreCase(String email);
    
    
    @Aggregation(pipeline = {
    	  "{"
   		+ "    $group: {"
   		+ "      _id: {"
		+ "      	month: {"
		+ "       	 $month: $registrationDate"
		+ "      	},"
		+ "     	 year: {"
		+ "      	  $year: $registrationDate"
		+ "    	  	}"
		+ "      },"
   		+ "      count: {"
   		+ "        $sum: 1"
   		+ "      },"
   		+ "		month:{"
   		+ "			$first: {$month: $registrationDate}"
   		+ "		},"
   		+ "		year:{"
   		+ "			$first: {$year: $registrationDate}"
   		+ "    },"
		+ "    dateString: {"
   		+ "			$first: {"
   		+ "				 $dateToString: {"
   		+ "    				date: $registrationDate,"
   		+ "    				format: '%Y-%m'"
   		+ "				}"
   		+ "			}"
		+ "    	  }"
   		+ "    }"
   		+ "  }"
	})
    
    public List<GroupUsersByDate> groupByMonthYear();
    
    @Aggregation(pipeline = {
    		"{"
   		+ "    $group: {"
   		+ "      _id: {"
		+ "     	 year: {"
		+ "      	  $year: $registrationDate"
		+ "    	  	}"
		+ "      },"
   		+ "      count: {"
   		+ "        $sum: 1"
   		+ "      },"
   		+ "		year:{"
   		+ "			$first: {$year: $registrationDate}"
   		+ "    },"
		+ "    dateString: {"
   		+ "			'$first': {"
   		+ "				 '$dateToString': {"
   		+ "    				'date': '$registrationDate',"
   		+ "    				'format': '%Y'"
   		+ "				}"
   		+ "			}"
		+ "    	  }"
   		+ "    }"
   		+ "  }"
	})
    
    public List<GroupUsersByDate> groupByYear();
    
    @Query("{'groupEntries.group.$id' : ?0}")
	List<User> getUsersByGroupEntriesWithGroupId(ObjectId id);

    @Query("{'roles' : ?0}")
    List<User> getUsersByRole(Role role);

    @Query("{'accountStatus' : ?0}")
	List<User> getUsersByAccountStatus(AccountStatus accountStatus);
    
}