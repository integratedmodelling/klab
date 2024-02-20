package org.integratedmodelling.klab.hub.users.controllers;


import javax.mail.MessagingException;

import org.integratedmodelling.klab.hub.exception.ResponseEntityAdapter;
import org.integratedmodelling.klab.hub.users.exceptions.DeletedUserNotFoundException;
import org.integratedmodelling.klab.hub.users.exceptions.LoginFailedExcepetion;
import org.integratedmodelling.klab.hub.users.exceptions.UserByEmailDoesNotExistException;
import org.integratedmodelling.klab.hub.users.exceptions.UserDoesNotExistException;
import org.integratedmodelling.klab.hub.users.exceptions.UserEmailExistsException;
import org.integratedmodelling.klab.hub.users.exceptions.UserExistsException;
import org.integratedmodelling.klab.hub.users.exceptions.UserNameOrEmailExistsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice(basePackages= {"org.integratedmodelling.klab.hub.users.controllers"})
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
    
    @ExceptionHandler(UserNameOrEmailExistsException.class)
    public ResponseEntity<Object> handleUserNameOrEmailExistsException(
            UserNameOrEmailExistsException ex, WebRequest request) {
        return new ResponseEntityAdapter<UserNameOrEmailExistsException>
            (HttpStatus.CONFLICT, ex).getResponse();
    }
    
    @ExceptionHandler(DeletedUserNotFoundException.class)
    public ResponseEntity<Object> handleUserEmailExistsException(
    		DeletedUserNotFoundException ex, WebRequest request) {
    	return new ResponseEntityAdapter<DeletedUserNotFoundException>
    		(HttpStatus.NOT_FOUND, ex).getResponse();
    }
    
    @ExceptionHandler(UserDoesNotExistException.class)
    public ResponseEntity<Object> handleUserDoesNotExistError(
    		UserDoesNotExistException ex, WebRequest request) {
    	return new ResponseEntityAdapter<UserDoesNotExistException>
    		(HttpStatus.NOT_FOUND, ex).getResponse();
    }
    
    @ExceptionHandler(UserByEmailDoesNotExistException.class)
    public ResponseEntity<Object> handleUserDoesNotExistError(
    		UserByEmailDoesNotExistException ex, WebRequest request) {
    	return new ResponseEntityAdapter<UserByEmailDoesNotExistException>
    		(HttpStatus.NOT_FOUND, ex).getResponse();
    }
    
    @ExceptionHandler(MessagingException.class)
    public ResponseEntity<Object> handleEmailManagerError(
    		MessagingException ex, WebRequest request) {
    	return new ResponseEntityAdapter<MessagingException>
    		(HttpStatus.INTERNAL_SERVER_ERROR, ex).getResponse();
    }

}
