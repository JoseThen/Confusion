package notifiers;

import java.util.*;
import play.libs.mailer.Email;
import play.libs.mailer.MailerPlugin;

/**
 * Created by Maria on 5/8/16.
 */

public class Mails {

    public static void sendEmail(String subject, List<String> recipients, String htmlBody) {
        Email email = new Email();
        email.setSubject(subject);
        email.setFrom("gamego.reports@gmail.com");
        email.setTo(recipients);
        email.setBodyHtml(htmlBody);
        MailerPlugin.send(email);
    }
}

