package com.sparky.Price.Compare;

import com.sparky.Price.Price.IPriceRepository;
import com.sparky.Price.Price.model.Price;
import com.sparky.Price.Product.IProductRepository;
import com.sparky.Price.Product.model.Product;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chrissheppard on 20/08/2017.
 */
@RestController
@RequestMapping("/compare")
public class CompareController {
    private static final Logger log = LoggerFactory.getLogger(CompareController.class);

    @Autowired
    IProductRepository productRepository;

    @Autowired
    IPriceRepository priceRepository;

    //todo: Take a look at this code, see if you can clean up the way it gets the price

    @RequestMapping(path = "/", method = RequestMethod.GET)
    public String get() throws Exception {

        List<Product> products = productRepository.findAll();
        List<Price> retrievedPrices = new ArrayList<>();
        products.stream()
                .filter(item -> item.isActivate() == true)
                .forEach(item -> {
                    item.retrieveWebsitePrices();
                });

        if(retrievedPrices.size() != 0) {
            priceRepository.save(retrievedPrices);
        }

        return "";
    }
}
