package org.integratedmodelling.klab.components.runtime.graphql;

import org.integratedmodelling.klab.rest.ObservationReference;

//import graphql.GraphQL;
//import graphql.schema.GraphQLSchema;
//import io.leangen.graphql.GraphQLSchemaGenerator;
//import io.leangen.graphql.annotations.GraphQLArgument;
//import io.leangen.graphql.annotations.GraphQLQuery;

public class GraphQLInterface {
//
//    public class User {
//
//        private String name;
//        private Integer id;
//        private Date registrationDate;
//
//        @GraphQLQuery(name = "name", description = "A person's name")
//        public String getName() {
//            return name;
//        }
//
//        @GraphQLQuery
//        public Integer getId() {
//            return id;
//        }
//
//        @GraphQLQuery(name = "regDate", description = "Date of registration")
//        public Date getRegistrationDate() {
//            return registrationDate;
//        }
//    }
    
    static class UserService {

//        @GraphQLQuery(name = "context")
//        public ObservationReference getById(@GraphQLArgument(name = "id") Integer id) {
//            return null;
//        }
    }
    
    public static void main(String[] args) {
   
//        UserService userService = new UserService(); //instantiate the service (or inject by Spring or another framework)
//        GraphQLSchema schema = new GraphQLSchemaGenerator()
//            .withBasePackages("org.integratedmodelling.klab.rest") //not mandatory but strongly recommended to set your "root" packages
//            .withOperationsFromSingleton(userService) //register the service
//            .generate(); //done ;)
//        
//        GraphQL graphQL = new GraphQL.Builder(schema)
//            .build();

        //keep the reference to GraphQL instance and execute queries against it.
        //this operation selects a user by ID and requests name, regDate and twitterProfile fields only
//        ExecutionResult result = graphQL.execute(   
//            "{ user (id: 123) {
//                 name,
//                 regDate,
//                 twitterProfile {
//                   handle
//                   numberOfTweets
//                 }
//            }}");
        
    }
    
}
