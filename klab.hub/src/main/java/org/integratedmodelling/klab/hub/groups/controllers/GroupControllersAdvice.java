package org.integratedmodelling.klab.hub.groups.controllers;

import org.integratedmodelling.klab.hub.exception.ResponseEntityAdapter;
import org.integratedmodelling.klab.hub.users.exceptions.GroupDoesNotExistException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GroupControllersAdvice extends ResponseEntityExceptionHandler {
	
    @ExceptionHandler(GroupDoesNotExistException.class)
    public ResponseEntity<Object> handleUserDoesNotExistError(
    		GroupDoesNotExistException ex, WebRequest request) {
    	return new ResponseEntityAdapter<GroupDoesNotExistException>
    		(HttpStatus.NOT_FOUND, ex).getResponse();
    }
}
