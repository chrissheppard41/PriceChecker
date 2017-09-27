package com.sparky.Price.Provider.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sparky.Price.Website.model.Website;
import lombok.*;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by chrissheppard on 06/09/2017.
 */
@Entity
@NoArgsConstructor(force = true)
@RequiredArgsConstructor
@Table(name = "provider")
@Getter
@Setter
public class Provider {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private @NotEmpty @NotNull @NonNull String name;
    private @NotEmpty @NotNull @NonNull String targetName;
    private LocalDateTime date = LocalDateTime.now();
    private String colour;

    @Transient
    private String currency = "£";

    @JsonIgnore
    @OneToMany(mappedBy = "provider", fetch = FetchType.EAGER)
    private List<Website> website;

    public Float getProviderPrice(String url) throws IOException {
        JSoupWrapper jSoupWrapper = new JSoupWrapper(targetName, currency);
        currency = jSoupWrapper.getCurrency();
        return jSoupWrapper.getBestWebsitePrice(url);
    }
}