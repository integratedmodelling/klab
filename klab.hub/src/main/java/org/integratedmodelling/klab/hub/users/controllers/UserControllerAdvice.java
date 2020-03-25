package org.integratedmodelling.klab.hub.users.controllers;


import org.integratedmodelling.klab.hub.exception.LoginFailedExcepetion;
import org.integratedmodelling.klab.hub.exception.ResponseEntityAdapter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class UserControllerAdvice extends ResponseEntityExceptionHandler {
	
    @ExceptionHandler(LoginFailedExcepetion.class)
    public ResponseEntity<Object> handleLoginFailedException(
        LoginFailedExcepetion ex, WebRequest request) {
    	return new ResponseEntityAdapter<LoginFailedExcepetion>
    		(HttpStatus.UNAUTHORIZED, ex).getResponse();
    }

}
