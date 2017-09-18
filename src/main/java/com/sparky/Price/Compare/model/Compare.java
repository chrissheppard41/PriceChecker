package com.sparky.Price.Compare.model;

import com.sparky.Price.Product.model.Product;
import com.sparky.Price.SendEmail.model.SmtpMailSender;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.slf4j.Logger;

import javax.mail.MessagingException;
import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor(force = true)
@Getter
@Setter
public class Compare {
    private boolean sendEmailToday = false;
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
                .map(item -> {
                    String html = item.toHtml();
                    if(item.isSendEmail()) {
                        this.sendEmailToday = true;
                    }
                    return html;
                })
                .collect(Collectors.joining(" "));

        output += "</tbody>\n" +
                "</table>";

        return output;
    }

    public void sendErrorMail(SmtpMailSender smtpMailSender, Logger log) {
        try {
            smtpMailSender.preSend("Mail unable to send", "Daily product report failed to send", new String[]{"cshepoth+daily@gmail.com"});
        } catch (MessagingException e) {
            log.error("Unable to send email :: " + e.toString());
        }
    }
}
