package com.sparky.Price.Website.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sparky.Price.Price.model.Price;
import com.sparky.Price.Price.model.PriceValue;
import com.sparky.Price.Product.model.Product;
import com.sparky.Price.Provider.model.Provider;
import lombok.*;
import org.hibernate.validator.constraints.NotEmpty;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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


    @OneToMany(mappedBy = "website", cascade = CascadeType.DETACH)
    private List<Price> priceList;


    public Optional<Price> getHtmlPrice() throws IOException {
        return Optional.ofNullable(this.setPrice());
    }

    private Price setPrice() throws IOException {
        Float websitePrice = provider.getBestWebsitePrice(url);
        Price p = new Price();
        if(websitePrice != 0f) {
            if(this.anyChangeInPrice(websitePrice)) {
                p.setPrice(websitePrice);
                p.setWebsite(this);
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
        PriceValue priceValue = new PriceValue(priceList);
        return "<tr><td><a href=\"" + url + "\">" + provider.getName() + "</a></td>\n" +
                "<td style=\"color: " + priceValue.getPriceColour() + "\">" + provider.getCurrency() + priceValue.getCurrentPrice() + "</td>\n" +
                "<td>" + provider.getCurrency() + priceValue.getLowestPrice() + "</td>\n" +
                "<td>" + provider.getCurrency() + priceValue.getHighestPrice() + "</td>\n" +
                "</tr>\n";
    }

}
