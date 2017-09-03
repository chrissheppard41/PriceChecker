package com.sparky.Price.Website.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sparky.Price.Price.model.Price;
import com.sparky.Price.Product.model.Product;
import lombok.*;
import org.hibernate.validator.constraints.NotEmpty;
import org.jsoup.Jsoup;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    @ManyToOne
    @JoinColumn(name = "product_id")
    private @NotNull Product product;
    private @NotEmpty @NotNull @NonNull String name;
    private @NotEmpty @NotNull @NonNull String url;
    private @NotEmpty @NotNull @NonNull String targetName;
    private LocalDateTime date = LocalDateTime.now();

    @JsonIgnore
    @OneToMany(mappedBy = "website", cascade = CascadeType.DETACH)
    private List<Price> priceList;



    public Optional<Price> getHtmlPrice() throws IOException {
        return Optional.ofNullable(this.setPrice());
    }

    private Price setPrice() throws IOException {
        String websitePrice = this.getPrice(this.getHtmlElement());
        Price p = new Price();
        if(!websitePrice.equals("")) {
            if(this.anyChangeInPrice(websitePrice)) {
                p.setPrice(websitePrice);
                p.setWebsite(this);
            }
        }
        return p;
    }

    //todo: fix the way website gets prices
    private boolean anyChangeInPrice(String price) {
        if(priceList.size() != 0) {
            Optional<Price> priceOptional = Optional.ofNullable(priceList.get(priceList.size()));
            return priceOptional.map(price1 -> !Objects.equals(price1.getPrice(), price)).orElse(true);
        }

        return true;
    }

    private String getHtmlElement() throws IOException {
        return Jsoup.connect(url).get().select(targetName).first().text();
    }

    private String getPrice(String input) {
        String output = "";

        Pattern pattern = Pattern.compile("\\d{1,3}[,\\.]?(\\d{1,2})?");
        Matcher matcher = pattern.matcher(input);
        if (matcher.find()) {
            output = matcher.group(0);
        }

        return output;
    }
}
