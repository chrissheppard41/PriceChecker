package com.sparky.Price.SendEmail;

import com.sparky.Price.Compare.model.Response;
import com.sparky.Price.Price.model.Price;
import com.sparky.Price.SendEmail.model.SendEmail;
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
@RequestMapping("/sendEmail")
public class SendEmailController {
    private static final Logger log = LoggerFactory.getLogger(SendEmailController.class);

    @Autowired
    ISendEmailRepository sendEmailRepository;

    @RequestMapping(path = "/", method = RequestMethod.GET)
    public ResponseEntity<Response> get() throws Exception {

        Response response = new Response(HttpStatus.OK, "");
        response.setData(sendEmailRepository.findAll());

        return new ResponseEntity<>(
                response,
                response.getStatus());
    }


    @RequestMapping(path = "/", method = RequestMethod.POST)
    public ResponseEntity<Response> post(@RequestBody @Valid SendEmail sendEmail) throws Exception {
        Response response = new Response(HttpStatus.OK, "");

        try {
            log.info("Send Email :: Save :: Preparing to save");
            sendEmailRepository.save(sendEmail);
            log.info("Send Email :: Save :: Successful");
        } catch(Exception e) {
            log.error("Send Email :: Save :: General Exception: " + e);
            response.setStatus(HttpStatus.SERVICE_UNAVAILABLE);
            response.setError("General Exception");
        }

        return new ResponseEntity<>(
                response,
                response.getStatus());
    }
}
