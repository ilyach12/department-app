package controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/sign")
public class SignInController {

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView signIn(){
        return new ModelAndView("signin");
    }
}
