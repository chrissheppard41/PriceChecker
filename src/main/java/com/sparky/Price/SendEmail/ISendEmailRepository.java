package com.sparky.Price.SendEmail;

import com.sparky.Price.Price.model.Price;
import com.sparky.Price.SendEmail.model.SendEmail;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 *
 * @author chris.sheppard
 * Created by chris.sheppard on 23/08/2017.
 */
public interface ISendEmailRepository extends CrudRepository<SendEmail, Long> {

    Price findByEmail(String email);

    List<SendEmail> findAll();

}