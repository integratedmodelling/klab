package org.integratedmodelling.klab.hub.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class FrontEndController {

    @GetMapping({"/ui","/ui/{path:[^\\.]+}", "/ui/**/{path:[^\\.]+}"}) // Everything without point (something.smt is served as is)
    public String forward() {
        return "forward:/ui/index.html";
    }
}
