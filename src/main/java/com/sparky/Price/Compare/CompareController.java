package com.sparky.Price.Compare;

import com.sparky.Price.Compare.model.Compare;
import com.sparky.Price.Price.IPriceRepository;
import com.sparky.Price.Price.model.Price;
import com.sparky.Price.Product.IProductRepository;
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

import javax.mail.MessagingException;
import java.util.List;

/**
 * Created by chrissheppard on 20/08/2017.
 */
@RestController
@RequestMapping("/compare")
@EnableScheduling
public class CompareController {
    private static final Logger log = LoggerFactory.getLogger(CompareController.class);

    @Autowired
    IProductRepository productRepository;

    @Autowired
    IPriceRepository priceRepository;

    @Autowired
    ISendEmailRepository sendEmailRepository;


    @Autowired
    private SmtpMailSender smtpMailSender;

    //todo: Take a look at this code, see if you can clean up the way it gets the price
    //todo: Add provider to the list, use that to set the name of the provider and the class to effect, assign it to the website code
    //todo: Stop possible memory leaking
    @RequestMapping(path = "/", method = RequestMethod.GET)
    @Scheduled(cron = "0 0 9 * * *")
    public String get() throws Exception {
        List<Product> products = productRepository.findAll();
        products.stream()
                .filter(Product::isActivate)
                .forEach(item -> {
                    List<Price> retrievedPrices = item.retrieveWebsitePrices();
                    if(retrievedPrices.size() != 0) {
                        priceRepository.save(retrievedPrices);
                    }
                });
        return "";
    }

    @RequestMapping(path = "/sendData/", method = RequestMethod.GET)
    @Scheduled(cron = "0 30 9 * * *")
    public String sendData() {
        Compare c = new Compare();
        List<Product> products = productRepository.findAll();
        System.out.println(sendEmailRepository.findAllByActivate(true).get(0).getEmail());

        /*try {
            smtpMailSender.send(c.formatEmail(products), "Daily product report", new String[]{"cshepoth+daily@gmail.com"});
        } catch (MessagingException e) {
            e.printStackTrace();
        }*/
        return c.formatEmail(products);
    }
}
