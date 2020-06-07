package sova.another_try.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import sova.another_try.service.StudentGroupService;

@Controller
public class IndexController {

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String helloAll() {
        return "index";
    }

    @RequestMapping(value = {"/admin/", "/admin/index", "/admin"}, method = RequestMethod.GET)
    public String helloAdmin() {
        return "admin/index";
    }

    @RequestMapping(value = {"/student/", "/student/index", "/student"}, method = RequestMethod.GET)
    public String helloUser() {
        return "user/index";
    }

}
