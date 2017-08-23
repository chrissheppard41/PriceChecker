package com.sparky.Price.Website;

import com.sparky.Price.Compare.model.Response;
import com.sparky.Price.Product.model.Product;
import com.sparky.Price.Website.model.Website;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * Created by chrissheppard on 20/08/2017.
 */
@RestController
@RequestMapping("/website")
public class WebsiteController {
    private static final Logger log = LoggerFactory.getLogger(WebsiteController.class);

    @Autowired
    IWebsiteRepository websiteRepository;

    @RequestMapping(path = "/", method = RequestMethod.GET)
    public ResponseEntity<Response> get() throws Exception {

        Response response = new Response(HttpStatus.OK, "");
        response.setData(websiteRepository.findAll());

        return new ResponseEntity<>(
                response,
                response.getStatus());
    }


    @RequestMapping(path = "/", method = RequestMethod.POST)
    public ResponseEntity<Response> post(@RequestBody @Valid Website website) throws Exception {
        Response response = new Response(HttpStatus.OK, "");

        try {
            log.info("Website :: Save :: Preparing to save");
            websiteRepository.save(website);
            log.info("Website :: Save :: Successful");
        } catch(Exception e) {
            log.error("Website :: Save :: General Exception: " + e);
            response.setStatus(HttpStatus.SERVICE_UNAVAILABLE);
            response.setError("General Exception");
        }

        return new ResponseEntity<>(
                response,
                response.getStatus());
    }
}
