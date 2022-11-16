package org.integratedmodelling.opencpu.temp;

import java.net.URISyntaxException;

public class Main {
    public static void main(String[] args) throws URISyntaxException, OCPUException {
        OCPURuntimeEnvironment runtime = new OCPURuntimeEnvironment("http://192.168.0.74/ocpu");

        String msg = "package=Rsymphony";
        runtime.localPackageFunctionCall("steve","base","library", msg, OCPURuntimeEnvironment.InputType.R);

        // create data frame to pass as polygons
        String polygons = runtime.jsonIORPC("base","data.frame","{\"id\": [0,1,2,3], \"cost\": [1,2,3,4]}");
        System.out.println(polygons);

        String features = runtime.jsonIORPC("base","data.frame","{\"id\": [0,1], \"name\": [\"birds\",\"fish\"]}");
        System.out.println(features);
        String rij = runtime.jsonIORPC("base","data.frame","{\"pu\": [0,1,2,3,0,1,2,3], \"species\": [0,0,0,0,1,1,1,1], \"amount\": [1,2,4,1,2,2,3,3]}");
        System.out.println(rij);

        msg = "{\"x\": " + polygons + ", \"features\":" + features + ", \"cost_column\": \"cost\" " + ", \"rij\":" + rij + "}";
        String dfp = runtime.rGlobalPackageFunctionCall("prioritizr","problem", msg, OCPURuntimeEnvironment.InputType.JSON);
        System.out.println(dfp);
        String problem_key = runtime.getSessionKey(dfp);

        System.out.println(problem_key);

        msg =  "x="+problem_key ;
        dfp = runtime.rGlobalPackageFunctionCall("prioritizr","add_min_set_objective", msg, OCPURuntimeEnvironment.InputType.R);
        problem_key = runtime.getSessionKey(dfp);

        msg = "x=" + problem_key + "&targets=0.15";
        dfp = runtime.rGlobalPackageFunctionCall("prioritizr","add_relative_targets", msg, OCPURuntimeEnvironment.InputType.R);
        problem_key = runtime.getSessionKey(dfp);

        msg = "x=" + problem_key;
        dfp = runtime.rGlobalPackageFunctionCall("prioritizr","add_binary_decisions", msg, OCPURuntimeEnvironment.InputType.R);
        problem_key = runtime.getSessionKey(dfp);



        msg = "x=" + problem_key;
        dfp = runtime.rGlobalPackageFunctionCall("prioritizr","add_rsymphony_solver", msg, OCPURuntimeEnvironment.InputType.R);
        problem_key = runtime.getSessionKey(dfp);

        msg = "a=" + problem_key;
        String ret = runtime.rGlobalPackageFunctionCall("prioritizr","solve", msg, OCPURuntimeEnvironment.InputType.R);
        System.out.println(ret);
        String solution = runtime.getRObjectFromSession(ret);
        System.out.println(solution);
    }

}