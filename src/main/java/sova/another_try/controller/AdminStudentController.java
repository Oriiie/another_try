package sova.another_try.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import sova.another_try.entity.Student;
import sova.another_try.service.StudentGroupService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Path;
import javax.validation.Valid;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Controller
@RequestMapping(value = "/admin/students")
public class AdminStudentController {

    private StudentGroupService service;

    @Autowired
    public void setService(StudentGroupService service) {
        this.service = service;
    }

    @RequestMapping(value = {"/", ""}, method = RequestMethod.GET)
    public String getAllStudents(Model model) {
        model.addAttribute("students", service.getAllStudents());
        return "admin/all_students";
    }

    @RequestMapping(value = {"/{group_id}/", "/{group_id}"}, method = RequestMethod.GET)
    public String getGroupOfStudents(Model model, @PathVariable(name = "group_id") int group_id) {
        model
                .addAttribute("students", service.getGroupStudents(group_id))
                .addAttribute("group", service.getGroupById(group_id));
        return "admin/group/group_students/group_students";
    }

    //Добавление студента, проверочная форма

    @RequestMapping(value = "/{group_id}/add_student", method = RequestMethod.POST)
    public String processAddStudentToGroupForm(@Valid Student student,
                                               BindingResult bindingResult,
                                               @PathVariable(name = "group_id") int group_id,
                                               Model model) {

        if(bindingResult.hasErrors()) {
            System.out.println("Валидация не прошла успешно");
            System.out.println("Student ID:" + student.getId());
            model.addAttribute("group", service.getGroupById(group_id));
            return "admin/group/group_students/add_student";
        }
        System.out.println(student);
        service.addStudent(student, group_id);
        return "redirect:/admin/students/{group_id}";
    }

    // Добавление студента

    @RequestMapping(value = "/{group_id}/add_student", method = RequestMethod.GET)
    public String addStudent(Model model,
                             @PathVariable(name = "group_id") int group_id) {
        model
                .addAttribute("group", service.getGroupById(group_id))
                .addAttribute("student", new Student());
        System.out.println("Создание студента, id:" + new Student().getId());
        return "admin/group/group_students/add_student";
    }


    // Удаление студента

    @RequestMapping(value = "/{group_id}/delete_student/{student_id}", method = RequestMethod.GET)
    public String deleteStudent(@PathVariable(name = "student_id") int student_id,
                                @PathVariable(name = "group_id") int group_id) {
        service.deleteStudent(student_id);
        return "redirect:/admin/students/{group_id}";
    }


    @RequestMapping(value = "/{group_id}/update_student/{student_id}", method = RequestMethod.GET)
    public String updateStudent(Model model,
                                @PathVariable(name = "student_id") int student_id,
                                @PathVariable(name = "group_id") int group_id) {
        model
                .addAttribute("student", service.getStudentById(student_id))
                .addAttribute("group", service.getGroupById(group_id));
        System.out.println(student_id);
        return "admin/group/group_students/update_student";
    }

    @RequestMapping(value = "/{group_id}/update_student/{id}", method = RequestMethod.POST)
    public String processAddStudentUpdateForm(@Valid Student student,
                                              BindingResult bindingResult,
                                              @PathVariable(name = "group_id") int group_id) {

        if(bindingResult.hasErrors()) {
            System.out.println("Валидация не прошла успешно");
            return "admin/group/group_students/update_student";
        }

        service.addStudent(student, group_id);
        return "redirect:/admin/students/{group_id}";
    }

    @RequestMapping(value = "/{group_id}/get_data", method = RequestMethod.GET)
    public void getStudentsDataFile(@PathVariable(name = "group_id") int group_id,
                                  HttpServletRequest request,
                                  HttpServletResponse response) {
        File file = service.getStudentsData(group_id);
        response.setHeader("Content-Disposition", "attachment; filename="+file.getName());
        response.setHeader("Content-Transfer-Encoding", "binary");
        try {
            BufferedOutputStream bos = new BufferedOutputStream(response.getOutputStream());
            FileInputStream fis = new FileInputStream(file.getPath());
            byte[] buf = new byte[1024];
            bos.write(buf, 0, fis.read(buf));
            bos.close();
            response.flushBuffer();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
