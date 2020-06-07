package sova.another_try.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Role;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import sova.another_try.entity.Group;
import sova.another_try.service.StudentGroupService;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;

@Controller
@RequestMapping(value = "/admin/groups")
public class AdminGroupController {

    private StudentGroupService service;

    @Autowired
    public void setService(StudentGroupService service) {
        this.service = service;
    }

    @RequestMapping(value = {"/", ""}, method = RequestMethod.GET)
    public String getAllGroups(Model model) {
       // System.out.println("Группы:" + service.getAllGroups());
        model.addAttribute("groups", service.getAllGroups());
        return "admin/group/groups";
    }

    @RequestMapping(value = "/add_group", method = RequestMethod.POST)
    public String processAddGroupForm(@Valid Group group, BindingResult bindingResult) {

        if(bindingResult.hasErrors()) {
            System.out.println("Валидация не прошла успешно");
            return "admin/group/add_group";
        }

       // System.out.println(group);
        service.addGroup(group);
        return "redirect:/admin/groups";
    }

    @RequestMapping(value = "/add_group", method = RequestMethod.GET)
    public String addGroup(Model model) {
        model.addAttribute("group", new Group());
        return "admin/group/add_group";
    }

    @RequestMapping(value = "/delete_group/{id}", method = RequestMethod.GET)
    public String deleteGroup(@PathVariable int id) {
        service.deleteGroup(id);
        return "redirect:/admin/groups";
    }

    @RequestMapping(value = "/update_group/{group_id}", method = RequestMethod.GET)
    public String updateGroup(Model model, @PathVariable(name = "group_id") int group_id) {
        model.addAttribute("group",service.getGroupById(group_id));
        System.out.println("Редактирование группы с id:" + group_id);
        return "admin/group/update_group";
    }

    // Обновление группы, логика как у добавления, но при вызове формы там уже есть данные

    @RequestMapping(value = "/update_group/{id}", method = RequestMethod.POST)
    public String processUpdateGroupForm(@Valid Group group, BindingResult bindingResult, Model model) {

        if(bindingResult.hasErrors()) {
            System.out.println("Валидация не прошла успешно");
            return "admin/group/update_group";
        }

       // System.out.println("Группа после изменения:" + group);
        service.addGroup(group);
        return "redirect:/admin/groups";
    }
}
