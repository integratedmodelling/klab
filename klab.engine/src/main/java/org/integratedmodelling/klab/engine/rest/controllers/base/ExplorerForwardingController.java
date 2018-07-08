package org.integratedmodelling.klab.engine.rest.controllers.base;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Custom controller needed to forward all GET request of klab.explorer
 * static content to ui/index.html.
 * All klab.explorer static content is stored in klab.engine/src/main/resources/static/ui 
 * Is needed to use the 'history' method of vue-route
 * @see <a href="https://router.vuejs.org/en/essentials/history-mode.html">HTML5 History Mode</a>
 * @author Enrico Girotto
 *
 */
@Controller
public class ExplorerForwardingController {
    
	@RequestMapping("/ui/{path:[^\\.]+}") // Everithing without point (something.smt is served as is) 
    public String forward() {
        return "forward:/ui/index.html";
    }
	
	
}