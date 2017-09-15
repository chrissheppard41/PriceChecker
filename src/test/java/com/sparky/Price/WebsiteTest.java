package com.sparky.Price;

import com.sparky.Price.Price.model.Price;
import com.sparky.Price.Product.model.Product;
import com.sparky.Price.Provider.model.Provider;
import com.sparky.Price.Website.model.Website;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Spy;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class WebsiteTest {
    @Spy
    Website website = new Website();

    @Spy
    Provider provider = new Provider("test", "#test");

    /**
     * Globally set the LocalDateTime
     */
    LocalDateTime date;

    /**
     * setup
     * Set up the datetime and the spy value when the function is called
     */
    @Before
    public void setup() {
        date = LocalDateTime.of(1970, 1, 1, 0, 0);
        when(website.getDate()).thenReturn(date);
        website.setUrl("https://test.com");

        List<Price> websites = Arrays.asList(
                new Price(1f),
                new Price(2f),
                new Price(3f)
        );
        website.setPriceList(websites);

        provider.setColour("#000000");


        try {
            when(provider.getBestWebsitePrice(website.getUrl())).thenReturn(10f);
        } catch (IOException e) {
            e.printStackTrace();
        }

        website.setProvider(provider);
        Product product = new Product("Test product");
        product.setActivate(true);
        website.setProduct(product);
    }

    @Test
    public void ShouldReturnWebsiteHtml() throws Exception {
        String html = website.toHtml();

        assertThat(html).isEqualTo("<tr><td><a href=\"https://test.com\">test</a></td>\n" +
                "<td style=\"color: #880000\">£3.0</td>\n" +
                "<td>£1.0</td>\n" +
                "<td>£3.0</td>\n" +
                "</tr>\n" +
                "");
    }

    @Test
    public void ShouldReturnWebsiteSetPrice() throws Exception {
        Optional<Price> price = website.getHtmlPrice();

        assertThat(price.get().getPrice()).isEqualTo(10f);
        assertThat(price.get().getWebsite().getProduct().getName()).isEqualTo("Test product");
        assertThat(price.get().getWebsite().getUrl()).isEqualTo("https://test.com");
    }

}
