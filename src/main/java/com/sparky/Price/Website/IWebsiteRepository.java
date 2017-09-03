package com.sparky.Price.Website;

import com.sparky.Price.Product.model.Product;
import com.sparky.Price.Website.model.Website;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 *
 * @author chris.sheppard
 * Created by chris.sheppard on 23/08/2017.
 */
public interface IWebsiteRepository extends CrudRepository<Website, Long> {

    Website findByName(String name);

    Website findById(long id);

    List<Website> findAll();

}