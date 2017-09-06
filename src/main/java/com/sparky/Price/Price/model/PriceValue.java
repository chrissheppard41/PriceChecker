package com.sparky.Price.Price.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Comparator;
import java.util.List;

@Getter
@Setter
public class PriceValue {
    private Float currentPrice;
    private Float highestPrice;
    private Float lowestPrice;
    private String priceColour = "#000000";

    public PriceValue(List<Price> priceList) {

        //get the latest price
        if(priceList.size() != 0) {
            currentPrice = priceList.get(priceList.size() - 1).getPrice();
            if(priceList.size() > 1) {
                //if the price has more than one, we can do checks on the previous price
                priceColour = getPriceColour(priceList.get(priceList.size() - 2).getPrice());
            }

            highestPrice = priceList.stream().max(Comparator.comparing(Price::getPrice)).get().getPrice();
            lowestPrice = priceList.stream().min(Comparator.comparing(Price::getPrice)).get().getPrice();
        }
    }


    /**
     * if the price has more than one, we can do checks on the previous price
     * if the previous item to the current price is less than
     * if not then the price is higher because we don't store the same price twice
     * @param prevPrice f
     * @return String
     */
    private String getPriceColour(Float prevPrice) {
        return (prevPrice > currentPrice)?"1a8800":"#880000";
    }
}
