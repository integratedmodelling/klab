package org.integratedmodelling.klab.engine.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/")
public class FrontEndController {

    @GetMapping("/")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ModelAndView home() {
    	ModelAndView home = new ModelAndView();
    	home.setViewName("/index.html");
    	return home;
    }
}
