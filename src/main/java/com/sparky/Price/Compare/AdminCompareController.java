package com.sparky.Price.Compare;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by chrissheppard on 20/08/2017.
 */
@Controller
@RequestMapping("/compare")
public class AdminCompareController {
    private static final Logger log = LoggerFactory.getLogger(AdminCompareController.class);

    @RequestMapping(path = "/admin/", method = RequestMethod.GET)
    public String compare(Model model) {
        model.addAttribute("name", "chris");
        return "compare";
    }
}
