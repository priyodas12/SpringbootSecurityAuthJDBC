package io.springlab.SpringbootSecurityAuthJDBC.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class GenericController {

    @RequestMapping(method = RequestMethod.GET ,value = "/home")
    public String gotoHomePage(){
        return "HomePage";
    }

    @RequestMapping(method = RequestMethod.GET ,value ="/welcome")
    public String gotoWelcomePage(){
        return "WelcomePage";
    }

    @RequestMapping(method = RequestMethod.GET ,value ="/admin")
    public String gotoAdminPage(){
        return "AdminPage";
    }

    @RequestMapping(method = RequestMethod.GET ,value ="/emp")
    public String gotoEmpPage(){
        return "EmpPage";
    }

    @RequestMapping(method = RequestMethod.GET ,value ="/clerk")
    public String gotoClerkPage(){
        return "ClerkPage";
    }

    @RequestMapping(method = RequestMethod.GET ,value ="/no-access")
    public String accessDenied(){
        return "AccessDenied";
    }
}
