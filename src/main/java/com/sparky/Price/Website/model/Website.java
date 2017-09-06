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
//todo: clean up this method by moving some of the methods about
//todo: make sure it handles the null pointers
//todo: ???

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private @NotNull Product product;
    @ManyToOne
    @JoinColumn(name = "provider_id")
    private @NotNull Provider provider;
    private @NotEmpty @NotNull @NonNull String url;
    private LocalDateTime date = LocalDateTime.now();


    @JsonIgnore
    @OneToMany(mappedBy = "website", cascade = CascadeType.DETACH)
    private List<Price> priceList;

    private String[] getTargetNames() {
        return provider.getTargetName().split(",");
    }

    public Optional<Price> getHtmlPrice() throws IOException {
        return Optional.ofNullable(this.setPrice());
    }

    private Price setPrice() throws IOException {
        Float websitePrice = this.getHtmlElement();
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

    private Float getHtmlElement() throws IOException {
        List<Float> elementText = new ArrayList<>();
        Document doc = Jsoup.connect(url).get();

        for(String searchFor : this.getTargetNames()) {
            Elements element = doc.select(searchFor);
            if(element != null && element.size() != 0) {

                elementText = Stream.concat(elementText.stream(), element.stream()
                    .map(item -> this.getPrice(item.text())))
                    .collect(Collectors.toList());

            }
        }

        if(elementText.size() != 0) {
            return elementText.stream().min(Comparator.comparing(Float::floatValue)).get();
        } else {
            return 0f;
        }

    }

    private Float getPrice(String input) {
        Float output = 0f;

        Pattern pattern = Pattern.compile("\\d{1,3}[,\\.]?(\\d{1,2})?");
        Matcher matcher = pattern.matcher(input);
        while (matcher.find()) {
            Float value = Float.parseFloat(matcher.group(0));

            if(output == 0f || output > value) {
                output = value;
            }
        }

        return output;
    }

    public String toHtml() {
        PriceValue priceValue = new PriceValue(priceList);
        return "<td><a href=\"" + url + "\">" + provider.getName() + "</a></td>\n" +
                "<td style=\"color: " + priceValue.getPriceColour() + "\">" + priceValue.getCurrentPrice() + "</td>\n" +
                "<td>" + priceValue.getLowestPrice() + "</td>\n" +
                "<td>" + priceValue.getHighestPrice() + "</td>\n" +
                "</tr>\n";
    }

}
