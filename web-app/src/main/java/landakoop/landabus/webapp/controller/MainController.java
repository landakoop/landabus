package landakoop.landabus.webapp.controller;

import java.security.Principal;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class MainController {

    @RequestMapping("/")
    public ModelAndView hasiera() {
    	
    	return new ModelAndView("site.hasiera");
    }
    
    @RequestMapping("/ibilbideak")
    public ModelAndView eskaerak() {
    	
    	return new ModelAndView("site.ibilbideak");
    }
    
    @RequestMapping("/kaixo")
    public String email(Principal principal) {
    	
        return "Kaixo " + principal.getName();
    }
}
