package com.sparky.Price.Compare;

import com.sparky.Price.Product.ProductController;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by chrissheppard on 20/08/2017.
 */
@RestController
@RequestMapping("/compare")
public class CompareController {
    private static final Logger log = LoggerFactory.getLogger(CompareController.class);

    @RequestMapping(path = "/", method = RequestMethod.GET)
    public String get() throws Exception {

        String url = "http://www.cdkeys.com/pc/games/total-war-warhammer-2-pc";
        String class_name = ".special-price";


        Document doc = Jsoup.connect(url).get();
        String link = doc.select(class_name).first().text();

        return "";
    }
}
