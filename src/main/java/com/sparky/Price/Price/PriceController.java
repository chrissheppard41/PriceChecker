package com.sparky.Price.Price;

import com.sparky.Price.Compare.model.Response;
import com.sparky.Price.Price.model.Price;
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
@RequestMapping("/price")
public class PriceController {
    private static final Logger log = LoggerFactory.getLogger(PriceController.class);

    @Autowired
    IPriceRepository priceRepository;

    @RequestMapping(path = "/", method = RequestMethod.GET)
    public ResponseEntity<Response> get() throws Exception {

        Response response = new Response(HttpStatus.OK, "");
        response.setData(priceRepository.findAll());

        return new ResponseEntity<>(
                response,
                response.getStatus());
    }


    @RequestMapping(path = "/", method = RequestMethod.POST)
    public ResponseEntity<Response> post(@RequestBody @Valid Price price) throws Exception {
        Response response = new Response(HttpStatus.OK, "");

        try {
            log.info("Price :: Save :: Preparing to save");
            priceRepository.save(price);
            log.info("Price :: Save :: Successful");
        } catch(Exception e) {
            log.error("Price :: Save :: General Exception: " + e);
            response.setStatus(HttpStatus.SERVICE_UNAVAILABLE);
            response.setError("General Exception");
        }

        return new ResponseEntity<>(
                response,
                response.getStatus());
    }
}
