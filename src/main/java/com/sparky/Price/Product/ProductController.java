package com.sparky.Price.Product;

import com.sparky.Price.Compare.model.Compare;
import com.sparky.Price.Price.IPriceRepository;
import com.sparky.Price.Price.model.Price;
import com.sparky.Price.Product.model.Product;
import com.sparky.Price.SendEmail.ISendEmailRepository;
import com.sparky.Price.SendEmail.model.SmtpMailSender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by chrissheppard on 09/09/2017.
 */
@RestController
@RequestMapping("/product")
@EnableScheduling
public class ProductController {
    private static final Logger log = LoggerFactory.getLogger(ProductController.class);

    @Autowired
    IProductRepository productRepository;


    @RequestMapping(path = "/", method = RequestMethod.GET)
    public List<Product> get() throws Exception {
        return productRepository.findAll();
    }


}
