package com.sparky.Price.Product;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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

    @RequestMapping(path = "/admin/", method = RequestMethod.GET)
    public String get(Model model) {
        model.addAttribute("name", "chris");
        return "get";
    }

    //todo: try and map out all the functions needed then populate it with values
//@todo: fix this as this is broken
    @RequestMapping(path = "/admin/{id}", method = RequestMethod.GET)
    public ModelAndView ModelAndView(Model model,
                                     @Valid @NotNull final @PathVariable long id) {
        model.addAttribute("id", id);
        ModelAndView model2 = new ModelAndView();
        model2.setViewName("post");
        return model2;
    }
}
