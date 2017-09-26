package com.sparky.Price.Price;

import com.sparky.Price.Price.model.Price;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

/**
 *
 * @author chris.sheppard
 * Created by chris.sheppard on 23/08/2017.
 */
public interface IPriceRepository extends CrudRepository<Price, Long> {

    Price findByPrice(String price);

    List<Price> findAll();

    @Transactional
    @Modifying
    @Query("DELETE FROM Price p WHERE p.id = :id")
    void remove(@Param("id") long id);
}