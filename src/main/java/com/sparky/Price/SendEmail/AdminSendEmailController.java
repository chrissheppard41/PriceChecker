package com.sparky.Price.SendEmail;

import com.sparky.Price.SendEmail.model.SendEmail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * Created by chrissheppard on 20/08/2017.
 */
@Controller
@RequestMapping("/sendEmail")
public class AdminSendEmailController {
    private static final Logger log = LoggerFactory.getLogger(AdminSendEmailController.class);

    @Autowired
    private ISendEmailRepository repository;

    @RequestMapping(path = "/admin/", method = RequestMethod.GET)
    public String get(Model model) {
        model.addAttribute("posts", repository.findAll());
        return "email/list";
    }

    @RequestMapping(path = "/admin/add", method = RequestMethod.GET)
    public String add(Model model) {
        model.addAttribute("SendEmail", new SendEmail());
        return "email/add";
    }

    @RequestMapping(path = "/admin/add", method = RequestMethod.POST)
    public ModelAndView addPost(@Valid @ModelAttribute("SendEmail")SendEmail email,
                                BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ModelAndView("redirect:/price/api/sendEmail/admin/add/");
        }
        repository.save(email);
        return new ModelAndView("redirect:/price/api/sendEmail/admin/");
    }

    @RequestMapping(path = "/admin/edit/{id}", method = RequestMethod.GET)
    public ModelAndView edit(@Valid @NotNull final @PathVariable long id) {
        return new ModelAndView("email/edit", "SendEmail", repository.findById(id));
    }


    @RequestMapping(path = "/admin/edit/{id}", method = RequestMethod.POST)
    public ModelAndView editPost(@Valid @ModelAttribute("SendEmail")SendEmail email,
                                 BindingResult bindingResult,
                                 @RequestParam("id") long id) {

        if (bindingResult.hasErrors()) {
            return new ModelAndView("redirect:/price/api/sendEmail/admin/edit/" + id);
        }
        repository.save(email);
        return new ModelAndView("redirect:/price/api/sendEmail/admin/");
    }

    @RequestMapping(path = "/admin/delete/{id}", method = RequestMethod.POST)
    public ModelAndView deletePost(@PathVariable("id") long id) {
        repository.delete(id);
        return new ModelAndView("redirect:/price/api/sendEmail/admin/");
    }
}
