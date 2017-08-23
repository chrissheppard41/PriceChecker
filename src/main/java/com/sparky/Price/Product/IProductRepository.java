package com.sparky.Price.Product;

import com.sparky.Price.Product.model.Product;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Set;

/**
 *
 * @author chris.sheppard
 * Created by chris.sheppard on 23/08/2017.
 */
public interface IProductRepository extends CrudRepository<Product, Long> {

    Product findByName(String name);

    List<Product> findAll();

}