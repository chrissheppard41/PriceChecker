package com.sparky.Price.Price;

import com.sparky.Price.Price.model.Price;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 *
 * @author chris.sheppard
 * Created by chris.sheppard on 23/08/2017.
 */
public interface IPriceRepository extends CrudRepository<Price, Long> {

    Price findByPrice(String price);

    List<Price> findAll();

}