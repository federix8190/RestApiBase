package py.gov.csj.poi.utils;

import java.security.SecureRandom;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.apache.shiro.SecurityUtils;

import com.sun.mail.smtp.SMTPTransport;

import java.security.Security;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class EmailSender {
	
	@SuppressWarnings("restriction")
    public static void send(String recipientEmail,
            String ccEmail, String title, String message) throws AddressException, MessagingException {

        Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
        final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";

        // Get a Properties object
        Properties props = System.getProperties();
        /*props.setProperty("mail.smtps.host", Constantes.MAIL_SMTPS_HOST);
        props.setProperty("mail.smtp.socketFactory.class", SSL_FACTORY);
        props.setProperty("mail.smtp.socketFactory.fallback", "false");
        props.setProperty("mail.smtp.port", Constantes.MAIL_SMTPS_PORT);
        props.setProperty("mail.smtp.socketFactory.port", Constantes.MAIL_SMTPS_PORT);
        props.setProperty("mail.smtps.auth", "true");*/

        props.put("mail.smtp.host", Constantes.MAIL_SMTPS_HOST);
        props.put("mail.smtp.starttls.enable", "false");
        props.put("mail.smtp.port", 25);
        props.put("mail.smtp.user", Constantes.USERN_EMAIL);
        props.put("mail.smtp.auth", "true");

        props.put("mail.smtps.quitwait", "false");

        Session session = Session.getInstance(props, null);

        // -- Create a new message --
        final MimeMessage msg = new MimeMessage(session);

        // -- Set the FROM and TO fields --
        msg.setFrom(new InternetAddress(Constantes.USERN_EMAIL));
        msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail, false));

        if (ccEmail.length() > 0) {
            msg.setRecipients(Message.RecipientType.CC, InternetAddress.parse(ccEmail, false));
        }

        msg.setSubject(title);
        //msg.setText(message, "utf-8");
        msg.setContent(message, "text/html; charset=utf-8");
        msg.setSentDate(new Date());

        SMTPTransport t = (SMTPTransport) session.getTransport("smtp");

        t.connect(Constantes.MAIL_SMTPS_HOST, Constantes.USERN_EMAIL, Constantes.PASSWORD_EMAIL);
        t.sendMessage(msg, msg.getAllRecipients());
        t.close();
    }

}
