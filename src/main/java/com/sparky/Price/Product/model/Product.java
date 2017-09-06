package com.sparky.Price.Product.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sparky.Price.Price.model.Price;
import com.sparky.Price.Website.model.Website;
import lombok.*;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created by chrissheppard on 23/08/2017.
 */
@Entity
@NoArgsConstructor(force = true)
@RequiredArgsConstructor
@Table(name = "product")
@Getter
@Setter
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private @NotEmpty @NotNull @NonNull String name;
    private @NonNull boolean activate = false;
    private LocalDateTime date = LocalDateTime.now();

    //@todo: Take a look at stopping the stackoverflow issue by only returning itself :: https://stackoverflow.com/questions/37362676/spring-boot-onetomany-with-jpa
    @JsonIgnore
    @OneToMany(mappedBy = "product", cascade = CascadeType.DETACH)
    private List<Website> website;





    public List<Price> retrieveWebsitePrices() {
        List<Price> prices = new ArrayList<>();
        website.stream()
                .forEach(item -> {
                    try {
                        //@todo: check to see if this works, test this
                        Optional<Price> price = item.getHtmlPrice();
                        if(price.isPresent()) {
                            if(price.get().getPrice() != null) {
                                prices.add(price.get());
                            }
                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });

        return prices;
    }



    public String toHtml() {
        String output = "";

        int row_span = (website.size() == 0)?1:website.size();
        output += "<tr>\n" +
                "<td rowspan=\"" + row_span + "\">" + this.name + "</td>\n";

        output += website.stream()
                .map(Website::toHtml)
                .collect(Collectors.joining(" "));
        output += "</tr>";

        return output;
    }

}
