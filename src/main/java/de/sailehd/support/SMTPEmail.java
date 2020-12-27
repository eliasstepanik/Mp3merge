package de.sailehd.support;

import com.sun.mail.smtp.SMTPTransport;

import javax.mail.*;
import javax.mail.internet.*;
import java.util.Date;
import java.util.Properties;

public class SMTPEmail {
    private String SMTP_SERVER = "sailehd.de";
    private String USERNAME = "email";
    private String PASSWORD = "pufC784!";

    Session session;

    public SMTPEmail(String SMTP_SERVER, String USERNAME, String PASSWORD){
        this.SMTP_SERVER = SMTP_SERVER;
        this.USERNAME = USERNAME;
        this.PASSWORD = PASSWORD;
    }

    public void connect(){
        Properties prop = System.getProperties();
        prop.put("mail.smtp.host", SMTP_SERVER); //optional, defined in SMTPTransport
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.port", "25"); // default port 25
        //prop.put("mail.smtp.starttls.enable", "true");

        session = Session.getInstance(prop, null);
    }

    public void sendMail(String subject, String massage, String from, String to, String cc) throws MessagingException {
        Message msg = new MimeMessage(session);

        try {

            // from
            msg.setFrom(new InternetAddress(from));

            // to
            msg.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(to, false));

            // cc
            msg.setRecipients(Message.RecipientType.CC,
                    InternetAddress.parse(cc, false));

            // subject
            msg.setSubject(subject);

            // content
            msg.setText(massage);

            msg.setSentDate(new Date());

            // Get SMTPTransport
            SMTPTransport t = (SMTPTransport) session.getTransport("smtp");

            // connect
            t.connect(SMTP_SERVER, USERNAME, PASSWORD);

            // send
            t.sendMessage(msg, msg.getAllRecipients());

            System.out.println("Response: " + t.getLastServerResponse());

            t.close();

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
