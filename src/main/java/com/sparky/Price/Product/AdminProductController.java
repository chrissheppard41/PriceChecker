package com.sparky.Price.Product;

import com.sparky.Price.Product.model.Product;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

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
        return "list";
    }


    /*
    / get: list
    /add get: configure new item
    /add post: post new item
    /{id} get: get item
    /{id} post: update item
    /{id} delete: delete item
     */



    //todo: try and map out all the functions needed then populate it with values

    @RequestMapping(path = "/admin/add", method = RequestMethod.GET)
    public ModelAndView add() {
        ModelAndView model = new ModelAndView();
        model.addObject("product", new Product());
        model.setViewName("add");
        return model;
    }

    @RequestMapping(path = "/admin/add", method = RequestMethod.POST)
    public ModelAndView addPost(@RequestParam("name") String name) {
        repository.save(new Product(name));
        return new ModelAndView("redirect:/price/api/product/admin/");
    }

    @RequestMapping(path = "/admin/{id}", method = RequestMethod.GET)
    public ModelAndView edit(@Valid @NotNull final @PathVariable long id) {
        ModelAndView model = new ModelAndView();
        model.addObject("product", repository.findById(id));
        model.setViewName("edit");
        return model;
    }


    @RequestMapping(path = "/admin/{id}", method = RequestMethod.POST)
    public ModelAndView editPost(@Valid Product product) {
        repository.save(product);
        return new ModelAndView("redirect:/price/api/product/admin/");
    }
}
