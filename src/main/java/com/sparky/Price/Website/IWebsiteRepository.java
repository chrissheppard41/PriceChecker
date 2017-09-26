package com.sparky.Price.Website;

import com.sparky.Price.Website.model.Website;
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
public interface IWebsiteRepository extends CrudRepository<Website, Long> {

    Website findById(long id);

    List<Website> findAll();

    @Transactional
    @Modifying
    @Query("DELETE FROM Website w WHERE w.id = :id")
    void remove(@Param("id") long id);
}
