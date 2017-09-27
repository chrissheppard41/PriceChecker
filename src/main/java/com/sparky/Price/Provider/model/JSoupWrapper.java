package com.sparky.Price.Provider.model;

import lombok.*;
import org.hibernate.validator.constraints.NotEmpty;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@NoArgsConstructor(force = true)
@RequiredArgsConstructor
@Getter
@Setter
public class JSoupWrapper {

    private @NotEmpty @NotNull @NonNull String targetName;
    private @NotEmpty @NotNull @NonNull String currency;

    private String[] getTargetNames() {
        return targetName.split(",");
    }

    public Float getBestWebsitePrice(String url) throws IOException {
        if(url != null || url.isEmpty()) {
            List<Float> listOfPrices = this.getAllPrices(Jsoup.connect(url).get());

            if(listOfPrices.size() != 0) {
                return listOfPrices.stream().min(Comparator.comparing(Float::floatValue)).get();
            }
        }
        return 0f;

    }

    private List<Float> getAllPrices(Document doc) {
        List<Float> elementText = new ArrayList<>();

        for(String searchFor : this.getTargetNames()) {
            Elements element = doc.select(searchFor);
            if(element != null && element.size() != 0) {

                elementText = Stream.concat(elementText.stream(), element.stream()
                        .filter(item -> !item.text().equals(""))
                        .map(item -> this.getElementBestPrice(item.text())))
                        .collect(Collectors.toList());

            }
        }

        return elementText;
    }

    private Float getElementBestPrice(String input) {
        Float output = 0f;

        input = input.replaceAll(",", "");
        Pattern pattern = Pattern.compile("([$£€]?)(\\d{1,7}[,\\.]?(\\d{1,2})?)");
        Matcher matcher = pattern.matcher(input);
        while (matcher.find()) {
            Float value = Float.parseFloat(matcher.group(2));

            if(value != 0f) {
                if(output == 0f || output > value) {
                    if(!matcher.group(1).isEmpty()) {
                        currency = matcher.group(1);
                    }
                    output = value;
                }
            }
        }

        return output;
    }
}
