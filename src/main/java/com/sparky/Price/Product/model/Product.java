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

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Website> website;

    @Transient
    private boolean sendEmail = false;



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

        List<String> ceils = website.stream()
                .filter(item -> item.getPriceList() != null)
                .filter(item -> item.getProvider() != null)
                .map(Website::toHtml)
                .collect(Collectors.toList());

        int row_span = (ceils.size() == 0)?1:ceils.size() + 1;
        output += "<tr>\n" +
                "<td rowspan=\"" + row_span + "\" style=\"border-bottom: 1px solid #000000;\">" + this.name + "</td>\n";
        output += "</tr>";

        output += ceils.stream()
                .collect(Collectors.joining(" "));

        return output;
    }

}
