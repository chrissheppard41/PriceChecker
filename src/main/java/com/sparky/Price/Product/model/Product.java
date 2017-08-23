package com.sparky.Price.Product.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sparky.Price.Website.model.Website;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by chrissheppard on 23/08/2017.
 */
@Entity
@NoArgsConstructor(force = true)
@RequiredArgsConstructor
@Table(name = "product")
@Getter
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private @NotEmpty @NotNull final String name;
    private LocalDateTime date = LocalDateTime.now();

    //@todo: Take a look at stopping the stackoverflow issue by only returning itself :: https://stackoverflow.com/questions/37362676/spring-boot-onetomany-with-jpa
    @JsonIgnore
    @OneToMany(mappedBy = "product", cascade = CascadeType.DETACH)
    private List<Website> website;
}
