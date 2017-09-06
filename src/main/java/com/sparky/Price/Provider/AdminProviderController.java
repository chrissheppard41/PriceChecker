package com.sparky.Price.Provider;

import com.sparky.Price.Provider.model.Provider;
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
 * Created by chrissheppard on 06/09/2017.
 */
@Controller
@RequestMapping("/provider")
public class AdminProviderController {
    private static final Logger log = LoggerFactory.getLogger(AdminProviderController.class);

    @Autowired
    private IProviderRepository repository;

    @RequestMapping(path = "/admin/", method = RequestMethod.GET)
    public String get(Model model) {
        model.addAttribute("posts", repository.findAll());
        return "provider/list";
    }

    @RequestMapping(path = "/admin/add", method = RequestMethod.GET)
    public String add(Model model) {
        model.addAttribute("provider", new Provider());
        return "provider/add";
    }

    @RequestMapping(path = "/admin/add", method = RequestMethod.POST)
    public ModelAndView addPost(@Valid @ModelAttribute("provider")Provider provider,
                                BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ModelAndView("redirect:/price/api/provider/admin/add/");
        }
        repository.save(provider);
        return new ModelAndView("redirect:/price/api/provider/admin/");
    }

    @RequestMapping(path = "/admin/edit/{id}", method = RequestMethod.GET)
    public ModelAndView edit(@Valid @NotNull final @PathVariable long id) {
        return new ModelAndView("provider/edit", "provider", repository.findById(id));
    }


    @RequestMapping(path = "/admin/edit/{id}", method = RequestMethod.POST)
    public ModelAndView editPost(@Valid @ModelAttribute("provider")Provider provider,
                                 BindingResult bindingResult,
                                 @RequestParam("id") long id) {

        if (bindingResult.hasErrors()) {
            return new ModelAndView("redirect:/price/api/provider/admin/edit/" + id);
        }
        repository.save(provider);
        return new ModelAndView("redirect:/price/api/provider/admin/");
    }

    @RequestMapping(path = "/admin/delete/{id}", method = RequestMethod.POST)
    public ModelAndView deletePost(@PathVariable("id") long id) {
        repository.delete(id);
        return new ModelAndView("redirect:/price/api/provider/admin/");
    }
}
