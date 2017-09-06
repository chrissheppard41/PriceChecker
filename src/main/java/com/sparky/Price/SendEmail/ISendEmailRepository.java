package com.sparky.Price.SendEmail;

import com.sparky.Price.SendEmail.model.SendEmail;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 *
 * @author chris.sheppard
 * Created by chris.sheppard on 23/08/2017.
 */
public interface ISendEmailRepository extends CrudRepository<SendEmail, Long> {

    SendEmail findByEmail(String email);

    SendEmail findById(long id);

    List<SendEmail> findAll();

    List<SendEmail> findAllByActivate(boolean activate);

}