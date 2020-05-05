//package org.integratedmodelling.klab.hub.security;
//
//import javax.servlet.Filter;
//import javax.servlet.FilterChain;
//import javax.servlet.FilterConfig;
//import javax.servlet.ServletException;
//import javax.servlet.ServletRequest;
//import javax.servlet.ServletResponse;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import org.springframework.stereotype.Component;
//
//
//@Component
//public class NotSoSimpleCorsFilter implements Filter {
//	
//	@Override
//	public void init(FilterConfig filterConfig) throws ServletException {
//		// TODO Auto-generated method stub
//		
//	}
//	
//
//    @Override
//    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) {
//      HttpServletResponse response = (HttpServletResponse) res;
//      HttpServletRequest request = (HttpServletRequest) req;
//      System.out.println("WebConfig; "+request.getRequestURI());
//      response.setHeader("Access-Control-Allow-Origin", "*");
//      response.setHeader("Access-Control-Allow-Methods", "POST, PUT, GET, OPTIONS, DELETE");
//      response.setHeader("Access-Control-Allow-Headers", "Content-Type, Access-Control-Allow-Headers, Authorization, X-Requested-With,observe");
//      response.setHeader("Access-Control-Max-Age", "3600");
//      response.setHeader("Access-Control-Allow-Credentials", "true");
//      response.setHeader("Access-Control-Expose-Headers", "Authorization");
//      response.addHeader("Access-Control-Expose-Headers", "USERID");
//      response.addHeader("Access-Control-Expose-Headers", "ROLE");
//      response.addHeader("Access-Control-Expose-Headers", "responseType");
//      response.addHeader("Access-Control-Expose-Headers", "observe");
//      System.out.println("Request Method: "+request.getMethod());
//      if (!(request.getMethod().equalsIgnoreCase("OPTIONS"))) {
//          try {
//              chain.doFilter(req, res);
//          } catch(Exception e) {
//              e.printStackTrace();
//          }
//      } else {
//          System.out.println("Pre-flight");
//          response.setHeader("Access-Control-Allow-Origin", "*");
//          response.setHeader("Access-Control-Allow-Methods", "POST,GET,DELETE,PUT");
//          response.setHeader("Access-Control-Max-Age", "3600");
//          response.setHeader("Access-Control-Allow-Headers", "Access-Control-Expose-Headers"+"Authorization, content-type," +
//          "USERID"+"ROLE"+
//                  "access-control-request-headers,access-control-request-method,accept,origin,authorization,x-requested-with,responseType,observe");
//          response.setStatus(HttpServletResponse.SC_OK);
//      }
//
//    }
//
//	@Override
//	public void destroy() {
//		// TODO Auto-generated method stub
//		
//	}
//
//}
