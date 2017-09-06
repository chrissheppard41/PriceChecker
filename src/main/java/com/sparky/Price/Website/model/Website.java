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

    private Float currentPrice;
    private Float highestPrice;
    private Float lowestPrice;
    private String priceColour = "#000000";


    public Optional<Price> getHtmlPrice() throws IOException {
        return Optional.ofNullable(this.setPrice());
    }

    //todo: take a look at looking for more dominate values, so if a item is on sale, return that price, or display the backup: elementid|elementclass2|elementOther ... maybe it's own class to handle this
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
            Optional<Price> priceOptional = Optional.ofNullable(priceList.get(priceList.size() - 1));
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





    public String toHtml() {
        this.setPriceValues();
        return "<td>" + name + "</td>\n" +
                "<td style=\"" + priceColour + "\">" + this.getCurrentPrice() + "</td>\n" +
                "<td>" + this.getHighestPrice() + "</td>\n" +
                "<td>" + this.getLowestPrice() + "</td>\n" +
                "</tr>\n";
    }

    private void setPriceValues() {
        if(priceList.size() != 0) {
            currentPrice = Float.parseFloat(priceList.get(priceList.size() - 1).getPrice());

            if(priceList.size() > 1) {
                //if the price has more than one, we can do checks on the previous price
                priceColour = getPriceColour(Float.parseFloat(priceList.get(priceList.size() - 2).getPrice()));
            }

            priceList.stream()
                    .forEach(item -> {
                        Float cost = Float.parseFloat(item.getPrice());

                        if(highestPrice == null || highestPrice < cost) {
                            highestPrice = cost;
                        }

                        if(lowestPrice == null || lowestPrice < cost) {
                            lowestPrice = cost;
                        }

                    });
        }

    }

    private String getPriceColour(Float prevPrice) {
        String colour = "#000000";
        //if the price has more than one, we can do checks on the previous price
        if(prevPrice < currentPrice) {
            //if the previous item to the current price is less than
            colour = "1a8800";
        } else {
            //if not then the price is higher because we don't store the same price twice
            colour = "#880000";
        }
        return colour;
    }

}
