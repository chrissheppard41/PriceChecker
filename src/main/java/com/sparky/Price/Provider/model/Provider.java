package com.sparky.Price.Provider.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sparky.Price.Price.model.Price;
import com.sparky.Price.Website.model.Website;
import lombok.*;
import org.hibernate.validator.constraints.NotEmpty;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

    @JsonIgnore
    @OneToMany(mappedBy = "provider", cascade = CascadeType.DETACH)
    private List<Website> website;




    private String[] getTargetNames() {
        return targetName.split(",");
    }



    public Float getBestWebsitePrice(String url) throws IOException {
        List<Float> listOfPrices = this.getAllPrices(Jsoup.connect(url).get());

        if(listOfPrices.size() != 0) {
            return listOfPrices.stream().min(Comparator.comparing(Float::floatValue)).get();
        } else {
            return 0f;
        }

    }

    private List<Float> getAllPrices(Document doc) {
        List<Float> elementText = new ArrayList<>();

        for(String searchFor : this.getTargetNames()) {
            Elements element = doc.select(searchFor);
            if(element != null && element.size() != 0) {

                elementText = Stream.concat(elementText.stream(), element.stream()
                        .map(item -> this.getElementBestPrice(item.text())))
                        .collect(Collectors.toList());

            }
        }

        return elementText;
    }

    private Float getElementBestPrice(String input) {
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
}
