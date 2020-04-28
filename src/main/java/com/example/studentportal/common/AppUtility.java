package com.example.studentportal.common;

import com.example.studentportal.model.User;
import org.springframework.stereotype.Component;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.net.IDN;
import java.util.Properties;

@Component
public class AppUtility {
    public void sentMail(String email, String resetPassword){
        final String username = "webapp.fptgreenwich@gmail.com";
        final String password = "1234Abcd";

        Properties prop = new Properties();
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "587");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.starttls.enable", "true"); //TLS

        Session session = Session.getInstance(prop,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });
        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("webapp.fptgreenwich@gmail.com"));
            message.setRecipients(
                    Message.RecipientType.TO,
                    InternetAddress.parse(email)
            );
            message.setSubject("Your new password");
            message.setText("Hi. \n Please use your new password to login: " + resetPassword);

            Transport.send(message);

            System.out.println("Done");

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    public void sentConfirmMail(User student, User tutor){
        final String username = "webapp.fptgreenwich@gmail.com";
        final String password = "1234Abcd";

        Properties prop = new Properties();
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "587");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.starttls.enable", "true"); //TLS

        Session session = Session.getInstance(prop,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });
        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("webapp.fptgreenwich@gmail.com"));
            message.setRecipients(
                    Message.RecipientType.TO,
                    InternetAddress.parse(student.getEmail())
            );
            message.setSubject("Introduce tutor");
            message.setText("Hi " + student.getFirstName() +
                    "\n" +
                    "Your new tutor is " + tutor.getFirstName() + "." +
                    "\nWish you study good.");

            Transport.send(message);

            System.out.println("Done");

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    public static String toIdnAddress(String mail) {
        if (mail == null) {
            return null;
        }
        int idx = mail.indexOf('@');
        if (idx < 0) {
            return mail;
        }
        return localPart(mail, idx) + "@" + IDN.toASCII(domain(mail, idx));
    }

    private static String localPart(String mail, int idx) {
        return mail.substring(0, idx);
    }

    private static String domain(String mail, int idx) {
        return mail.substring(idx + 1);
    }
}
