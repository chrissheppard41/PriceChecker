package com.sparky.Price.Product;

import com.sparky.Price.Product.model.Product;
import com.sparky.Price.Website.model.Website;
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
import java.util.List;

/**
 * Created by chrissheppard on 20/08/2017.
 */
@Controller
@RequestMapping("/product")
public class AdminProductController {
    private static final Logger log = LoggerFactory.getLogger(AdminProductController.class);

    @Autowired
    private IProductRepository repository;

    @RequestMapping(path = "/admin/", method = RequestMethod.GET)
    public String get(Model model) {
        model.addAttribute("posts", repository.findAll());
        return "product/list";
    }

    @RequestMapping(path = "/admin/add", method = RequestMethod.GET)
    public String add(Model model) {
        model.addAttribute("product", new Product());
        return "product/add";
    }

    @RequestMapping(path = "/admin/add", method = RequestMethod.POST)
    public ModelAndView addPost(@Valid @ModelAttribute("product")Product product,
                                BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ModelAndView("redirect:/price/api/product/admin/add/");
        }
        List<Website> websiteList = product.getWebsite();
        product.getWebsite().clear();
        product.setWebsite(websiteList);
        repository.save(product);

        return new ModelAndView("redirect:/price/api/product/admin/");
    }

    @RequestMapping(path = "/admin/edit/{id}", method = RequestMethod.GET)
    public ModelAndView edit(@Valid @NotNull final @PathVariable long id) {
        return new ModelAndView("product/edit", "product", repository.findById(id));
    }


    @RequestMapping(path = "/admin/edit/{id}", method = RequestMethod.POST)
    public ModelAndView editPost(@Valid @ModelAttribute("product")Product product,
                                 BindingResult bindingResult,
                                 @RequestParam("id") long id) {

        if (bindingResult.hasErrors()) {
            return new ModelAndView("redirect:/price/api/product/admin/edit/" + id);
        }
        repository.save(product);
        return new ModelAndView("redirect:/price/api/product/admin/");
    }

    @RequestMapping(path = "/admin/delete/{id}", method = RequestMethod.POST)
    public ModelAndView deletePost(@PathVariable("id") long id) {
        repository.delete(id);
        return new ModelAndView("redirect:/price/api/product/admin/");
    }
}
