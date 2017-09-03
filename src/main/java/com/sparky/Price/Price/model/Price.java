package com.sparky.Price.Price.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sparky.Price.Website.model.Website;
import lombok.*;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * Created by chrissheppard on 23/08/2017.
 */
@Entity
@NoArgsConstructor(force = true)
@RequiredArgsConstructor
@Table(name = "price")
@Getter
@Setter
public class Price {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne
    @JoinColumn(name = "website_id")
    private Website website;
    private @NotEmpty @NotNull @NonNull String price;
    private LocalDateTime date = LocalDateTime.now();
}
