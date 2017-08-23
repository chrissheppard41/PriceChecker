package com.sparky.Price.Product;

import com.sparky.Price.Compare.model.Response;
import com.sparky.Price.Product.model.Product;
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
@RequestMapping("/product")
public class ProductController {
    private static final Logger log = LoggerFactory.getLogger(ProductController.class);

    @Autowired
    IProductRepository productRepository;

    @RequestMapping(path = "/", method = RequestMethod.GET)
    public ResponseEntity<Response> get() throws Exception {

        Response response = new Response(HttpStatus.OK, "");
        response.setData(productRepository.findAll());

        return new ResponseEntity<>(
                response,
                response.getStatus());
    }


    @RequestMapping(path = "/", method = RequestMethod.POST)
    public ResponseEntity<Response> post(@RequestBody @Valid Product product) throws Exception {
        Response response = new Response(HttpStatus.OK, "");

        try {
            log.info("Product :: Save :: Preparing to save");
            productRepository.save(product);
            log.info("Product :: Save :: Successful");
        } catch(Exception e) {
            log.error("Product :: Save :: General Exception: " + e);
            response.setStatus(HttpStatus.SERVICE_UNAVAILABLE);
            response.setError("General Exception");
        }

        return new ResponseEntity<>(
                response,
                response.getStatus());
    }

    //@todo: test this delete function to see if it deletes the saved entry with a long id
    @RequestMapping(path = "/", method = RequestMethod.DELETE)
    public ResponseEntity<Response> delete(@RequestBody Long id) throws Exception {
        Response response = new Response(HttpStatus.OK, "");

        try {
            log.info("Product :: Delete :: Preparing to delete");
            productRepository.delete(id);
            log.info("Product :: Delete :: Successful");
        } catch(Exception e) {
            log.error("Product :: Delete :: General Exception: " + e);
            response.setStatus(HttpStatus.SERVICE_UNAVAILABLE);
            response.setError("General Exception");
        }

        return new ResponseEntity<>(
                response,
                response.getStatus());
    }

    //@todo: test this, make sure the id is part of the request
    @RequestMapping(path = "/", method = RequestMethod.PUT)
    public ResponseEntity<Response> delete(@RequestBody @Valid Product product) throws Exception {
        Response response = new Response(HttpStatus.OK, "");

        try {
            log.info("Product :: Updating :: Preparing to delete");
            productRepository.save(product);
            log.info("Product :: Updating :: Successful");
        } catch(Exception e) {
            log.error("Product :: Updating :: General Exception: " + e);
            response.setStatus(HttpStatus.SERVICE_UNAVAILABLE);
            response.setError("General Exception");
        }

        return new ResponseEntity<>(
                response,
                response.getStatus());
    }
}
