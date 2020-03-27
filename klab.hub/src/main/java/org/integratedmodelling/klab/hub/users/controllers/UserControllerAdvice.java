package org.integratedmodelling.klab.hub.users.controllers;


import org.integratedmodelling.klab.hub.exception.LoginFailedExcepetion;
import org.integratedmodelling.klab.hub.exception.ResponseEntityAdapter;
import org.integratedmodelling.klab.hub.exception.UserEmailExistsException;
import org.integratedmodelling.klab.hub.exception.UserExistsException;
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
    
    @ExceptionHandler(UserEmailExistsException.class)
    public ResponseEntity<Object> handleUserEmailExistsException(
    		UserEmailExistsException ex, WebRequest request) {
    	return new ResponseEntityAdapter<UserEmailExistsException>
    		(HttpStatus.CONFLICT, ex).getResponse();
    }
    
    @ExceptionHandler(UserExistsException.class)
    public ResponseEntity<Object> handleUserEmailExistsException(
    		UserExistsException ex, WebRequest request) {
    	return new ResponseEntityAdapter<UserExistsException>
    		(HttpStatus.CONFLICT, ex).getResponse();
    }

}
