package com.sparky.Price;

import com.sparky.Price.Price.model.Price;
import com.sparky.Price.Product.model.Product;
import com.sparky.Price.Website.model.Website;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Spy;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class ProductTest {
    @Spy
    Product product = new Product();

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
        when(product.getDate()).thenReturn(date);
        product.setName("Test product");
        product.setActivate(true);

        List<Website> websites = Arrays.asList(
                new Website("https://test.com"),
                new Website("https://test2.com"),
                new Website("https://test3.com")
                );
        product.setWebsite(websites);
    }

    @Test
    public void ShouldReturnProduct() throws Exception {
        assertThat(product.getWebsite().size()).isEqualTo(3);
        assertThat(product.getName()).isEqualTo("Test product");
    }
    @Test
    public void ShouldReturnEmpty() throws Exception {
        List<Price> prices = product.retrieveWebsitePrices();
        assertThat(prices.size()).isEqualTo(0);
    }
    @Test
    public void ShouldReturnHtmlBasicString() throws Exception {
        //todo: should be 1
        String html = product.toHtml();
        assertThat(html).isEqualTo("<tr>\n" +
                "<td rowspan=\"4\">Test product</td>\n" +
                "</tr>");
    }
}
