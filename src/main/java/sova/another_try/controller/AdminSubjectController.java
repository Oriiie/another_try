package sova.another_try.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import sova.another_try.entity.Student;
import sova.another_try.entity.Subject;
import sova.another_try.service.StudentGroupService;
import sova.another_try.service.SubjectStudentService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

@Controller
@RequestMapping(value = "/admin/subjects")
public class AdminSubjectController {
    private SubjectStudentService service;

    @Autowired
    public void setService(SubjectStudentService service) {
        this.service = service;
    }

    @RequestMapping(value = {"/", ""}, method = RequestMethod.GET)
    public String getAllSubjects(Model model) {
        model.addAttribute("subjects", service.getAllSubjects());
        return "admin/all_subjects";
    }

    @RequestMapping(value = {"/{student_id}/", "/{subject_id}"}, method = RequestMethod.GET)
    public String getSubjectsOfStudents(Model model, @PathVariable(name = "student_id") int student_id,
                                        @PathVariable(name = "subject_id") int subject_id) {
        model
                .addAttribute("students", service.getStudentById(student_id))
                .addAttribute("subjects", service.getSubjectStudents(subject_id));
        return "admin/student_subjects";
    }

    //Добавление студента, проверочная форма

    @RequestMapping(value = "/{student_id}/add_subject", method = RequestMethod.POST)
    public String processAddSubjectsToStudentForm(@Valid Subject subject,
                                               BindingResult bindingResult,
                                               @PathVariable(name = "student_id") int student_id,
                                               Model model) {

        if(bindingResult.hasErrors()) {
            System.out.println("Валидация не прошла успешно");
            System.out.println("Subject ID:" + subject.getId());
            model.addAttribute("student", service.getStudentById(student_id));
            return "admin/group/group_students/add_student";
        }
        System.out.println(subject);
        service.addSubject(subject, student_id);
        return "redirect:/admin/subjects/";
    }

    // Добавление студента

    @RequestMapping(value = "/{student_id}/add_subject", method = RequestMethod.GET)
    public String addSubject(Model model,
                             @PathVariable(name = "student_id") int student_id) {
        model
                .addAttribute("student", service.getStudentById(student_id))
                .addAttribute("subject", new Subject());
        System.out.println("Создание предмета, id:" + new Subject().getId());
        return "admin/add_subject";
    }


    // Удаление студента

    @RequestMapping(value = "/{student_id}/delete_subject/{subject_id}", method = RequestMethod.GET)
    public String deleteStudent(@PathVariable(name = "student_id") int student_id,
                                @PathVariable(name = "subject_id") int subject_id) {
        service.deleteSubject(subject_id);
        return "redirect:/admin/subjects/{student_id}";
    }


    @RequestMapping(value = "/{student_id}/update_subject/{subject_id}", method = RequestMethod.GET)
    public String updateStudent(Model model,
                                @PathVariable(name = "student_id") int student_id,
                                @PathVariable(name = "subject_id") int subject_id) {
        model
                .addAttribute("student", service.getStudentById(student_id))
                .addAttribute("subject", service.getSubjectById(subject_id));
        System.out.println(subject_id);
        return "admin/update_subject";
    }

    @RequestMapping(value = "/{student_id}/update_subject/{id}", method = RequestMethod.POST)
    public String processAddStudentUpdateForm(@Valid Subject subject,
                                              BindingResult bindingResult,
                                              @PathVariable(name = "student_id") int student_id) {

        if(bindingResult.hasErrors()) {
            System.out.println("Валидация не прошла успешно");
            return "admin/update_subject";
        }

        service.addSubject(subject, student_id);
        return "redirect:/admin/subjects/{student_id}";
    }


}
