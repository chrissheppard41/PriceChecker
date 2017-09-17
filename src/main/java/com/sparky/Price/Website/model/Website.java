package com.sparky.Price.Website.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sparky.Price.Price.model.Price;
import com.sparky.Price.Price.model.PriceValue;
import com.sparky.Price.Product.model.Product;
import com.sparky.Price.Provider.model.Provider;
import lombok.*;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Created by chrissheppard on 20/08/2017.
 */
@Entity
@NoArgsConstructor(force = true)
@RequiredArgsConstructor
@Table(name = "website")
@Getter
@Setter
public class Website {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "product_id")
    private @NotNull Product product;
    @ManyToOne
    @JoinColumn(name = "provider_id")
    private @NotNull Provider provider;
    private @NotEmpty @NotNull @NonNull String url;
    private LocalDateTime date = LocalDateTime.now();


    @OneToMany(mappedBy = "website", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Price> priceList;


    public Optional<Price> getHtmlPrice() throws IOException {
        return Optional.ofNullable(this.setPrice());
    }

    private Price setPrice() throws IOException {
        Price p = new Price();
        if(provider != null) {
            Float websitePrice = provider.getBestWebsitePrice(url);
            if(websitePrice != 0f) {
                if(this.anyChangeInPrice(websitePrice)) {
                    p.setPrice(websitePrice);
                    p.setWebsite(this);
                }
            }
        }
        return p;
    }

    private boolean anyChangeInPrice(Float price) {
        if(priceList.size() != 0) {
            Optional<Price> priceOptional = Optional.ofNullable(priceList.get(priceList.size() - 1));
            return priceOptional.map(price1 -> !Objects.equals(price1.getPrice(), price)).orElse(true);
        }

        return true;
    }


    public String toHtml() {
        String html = "";
        if(priceList != null || provider != null) {
            PriceValue priceValue = new PriceValue(priceList);
            html = "<tr><td><a href=\"" + url + "\">" + provider.getName() + "</a></td>\n" +
                    "<td style=\"color: " + priceValue.getPriceColour() + "\">" + provider.getCurrency() + priceValue.getCurrentPrice() + "</td>\n" +
                    "<td>" + provider.getCurrency() + priceValue.getLowestPrice() + "</td>\n" +
                    "<td>" + provider.getCurrency() + priceValue.getHighestPrice() + "</td>\n" +
                    "</tr>\n";
        }
        return html;
    }

}
