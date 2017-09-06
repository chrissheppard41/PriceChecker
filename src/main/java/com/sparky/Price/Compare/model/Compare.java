package com.sparky.Price.Compare.model;

import com.sparky.Price.Product.model.Product;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor(force = true)
//@RequiredArgsConstructor
@Getter
@Setter
public class Compare {
    public String formatEmail(List<Product> emailData) {
        String output = "";

        output += "<p>The current price trends for the following products:</p>\n" +
                "<table width=\"100%\">\n" +
                "<thead>\n" +
                "<tr>\n" +
                "<th style=\"text-align: left;\">Product</th>\n" +
                "<th style=\"text-align: left;\">Website</th>\n" +
                "<th style=\"width: 75px;text-align: left;\">Current Price</th>\n" +
                "<th style=\"width: 75px;text-align: left;\">Lowest Price</th>\n" +
                "<th style=\"width: 75px;text-align: left;\">Highest Price</th>\n" +
                "</tr>\n" +
                "</thead>\n" +
                "<tbody>\n";

        output += emailData.stream()
                .filter(Product::isActivate)
                .map(Product::toHtml)
                .collect(Collectors.joining(" "));

        output += "</tbody>\n" +
                "</table>";

        return output;
    }
}
