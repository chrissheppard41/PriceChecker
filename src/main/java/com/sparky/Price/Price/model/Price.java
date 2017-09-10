package com.sparky.Price.Price.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
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
    //JsonProperty("x")
    @JsonIgnore
    private long id;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "website_id")
    private Website website;
    @JsonProperty("y")
    private @NotNull @NonNull Float price;

    @JsonIgnore
    private LocalDateTime date = LocalDateTime.now();

    @JsonProperty("x")
    @Transient
    private String x;

    public String getX() {
        return this.date.getDayOfMonth() + "-" + this.date.getMonthValue() + "-" + this.date.getYear();
    }
}
