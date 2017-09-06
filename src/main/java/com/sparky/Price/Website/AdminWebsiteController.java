package com.sparky.Price.Website;

import com.sparky.Price.Product.IProductRepository;
import com.sparky.Price.Provider.IProviderRepository;
import com.sparky.Price.Website.model.Website;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Controller
@RequestMapping("/website")
public class AdminWebsiteController {
    @Autowired
    private IWebsiteRepository repository;
    @Autowired
    private IProductRepository productRepository;
    @Autowired
    private IProviderRepository providerRepository;

    @RequestMapping(path = "/admin/", method = RequestMethod.GET)
    public String get(Model model) {
        model.addAttribute("posts", repository.findAll());
        return "website/list";
    }

    @RequestMapping(path = "/admin/add", method = RequestMethod.GET)
    public String add(Model model) {
        model.addAttribute("website", new Website());
        model.addAttribute("productList", productRepository.findAll());
        model.addAttribute("providerList", providerRepository.findAll());
        return "website/add";
    }

    @RequestMapping(path = "/admin/add", method = RequestMethod.POST)
    public ModelAndView addPost(@Valid @ModelAttribute("website")Website website,
                                BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ModelAndView("redirect:/price/api/website/admin/add/");
        }
        repository.save(website);
        return new ModelAndView("redirect:/price/api/website/admin/");
    }

    @RequestMapping(path = "/admin/edit/{id}", method = RequestMethod.GET)
    public ModelAndView edit(@Valid @NotNull final @PathVariable long id) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("website/edit");
        modelAndView.addObject("website", repository.findById(id));
        modelAndView.addObject("productList", productRepository.findAll());
        modelAndView.addObject("providerList", providerRepository.findAll());
        return modelAndView;
    }


    @RequestMapping(path = "/admin/edit/{id}", method = RequestMethod.POST)
    public ModelAndView editPost(@Valid @ModelAttribute("website")Website website,
                                 BindingResult bindingResult,
                                 @RequestParam("id") long id) {

        if (bindingResult.hasErrors()) {
            return new ModelAndView("redirect:/price/api/website/admin/edit/" + id);
        }
        repository.save(website);
        return new ModelAndView("redirect:/price/api/website/admin/");
    }

    @RequestMapping(path = "/admin/delete/{id}", method = RequestMethod.POST)
    public ModelAndView deletePost(@PathVariable("id") long id) {
        repository.delete(id);
        return new ModelAndView("redirect:/price/api/website/admin/");
    }
}
