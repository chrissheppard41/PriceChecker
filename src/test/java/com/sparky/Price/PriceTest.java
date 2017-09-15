package com.sparky.Price;

import com.sparky.Price.Price.model.Price;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Spy;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class PriceTest {

    @Spy
    Price price = new Price();

    /**
     * setup
     * Set up the datetime and the spy value when the function is called
     */
    @Before
    public void setup() {
        price.setPrice(1f);
    }

    @Test
    public void ShouldReturnPrice() throws Exception {
        assertThat(price.getPrice()).isEqualTo(1f);
    }

    @Test
    public void ShouldReturnPriceX() throws Exception {
        price.setDate(LocalDateTime.of(1970, 1, 1, 0, 0));
        assertThat(price.getX()).isEqualTo("1-1-1970");
    }
}
