package com.sparky.Price.Product.model;

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

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Website> website;





    public List<Price> retrieveWebsitePrices() {
        List<Price> prices = new ArrayList<>();
        website.stream()
                .forEach(item -> {
                    try {
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
        //reorganise this method, get the website count returned, then get the row count, maybe think about outputing -1 values to hint it's broken
        int row_span = (website.size() == 0)?2:website.size() + 1;
        output += "<tr>\n" +
                "<td rowspan=\"" + row_span + "\">" + this.name + "</td>\n";
        output += "</tr>";

        output += website.stream()
                .filter(item -> item.getPriceList() != null)
                .filter(item -> item.getProvider() != null)
                .map(Website::toHtml)
                .collect(Collectors.joining(" "));

        return output;
    }

}
