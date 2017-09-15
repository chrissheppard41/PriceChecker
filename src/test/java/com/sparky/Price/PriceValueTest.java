package com.sparky.Price;

import com.sparky.Price.Price.model.Price;
import com.sparky.Price.Price.model.PriceValue;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class PriceValueTest {

    /**
     * setup
     * Set up the datetime and the spy value when the function is called
     */
    @Before
    public void setup() {

    }


    @Test
    public void ShouldReturnPriceTest1() throws Exception {
        List<Price> priceList = Arrays.asList(
                new Price(1f),
                new Price(2f),
                new Price(3f)
        );


        PriceValue priceValue = new PriceValue(priceList);


        assertThat(priceValue.getCurrentPrice()).isEqualTo(3f);
        assertThat(priceValue.getHighestPrice()).isEqualTo(3f);
        assertThat(priceValue.getLowestPrice()).isEqualTo(1f);
        assertThat(priceValue.getPriceColour()).isEqualTo("#880000");


    }

    @Test
    public void ShouldReturnPriceTest2() throws Exception {
        List<Price> priceList = Arrays.asList(
                new Price(10f),
                new Price(5f),
                new Price(15f),
                new Price(9f)
        );


        PriceValue priceValue = new PriceValue(priceList);


        assertThat(priceValue.getCurrentPrice()).isEqualTo(9f);
        assertThat(priceValue.getHighestPrice()).isEqualTo(15f);
        assertThat(priceValue.getLowestPrice()).isEqualTo(5f);
        assertThat(priceValue.getPriceColour()).isEqualTo("#1a8800");


    }
}
