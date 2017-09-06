package com.sparky.Price.Provider;

import com.sparky.Price.Provider.model.Provider;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 *
 * @author chris.sheppard
 * Created by chris.sheppard on 06/09/2017.
 */
public interface IProviderRepository extends CrudRepository<Provider, Long> {

    Provider findByName(String name);

    Provider findById(long id);

    List<Provider> findAll();

}