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

import py.gov.csj.poi.errores.AppException;
import py.gov.csj.poi.service.ConfiguracionesServices;

import java.security.Security;
import java.util.Properties;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

@Stateless
public class EmailSender {
	
	@Inject
	ConfiguracionesServices config;
	
	@SuppressWarnings("restriction")
    public void send(String recipientEmail, String ccEmail, String title, String message) 
    		throws AddressException, MessagingException, AppException {

        Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
        final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";
        
        String host = config.get("MAIL_SMTPS_HOST");
        String port = config.get("MAIL_SMTPS_PORT");
        String user = config.get("MAIL_SMTPS_USER");
        String pass = config.get("MAIL_SMTPS_PASS");

        // Get a Properties object
        Properties props = System.getProperties();
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.starttls.enable", "false");
        props.put("mail.smtp.port", port);
        props.put("mail.smtp.user", user);
        props.put("mail.smtp.auth", "true");

        props.put("mail.smtps.quitwait", "false");

        Session session = Session.getInstance(props, null);

        // -- Create a new message --
        final MimeMessage msg = new MimeMessage(session);

        // -- Set the FROM and TO fields --
        msg.setFrom(new InternetAddress(user));
        msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail, false));

        if (ccEmail.length() > 0) {
            msg.setRecipients(Message.RecipientType.CC, InternetAddress.parse(ccEmail, false));
        }

        msg.setSubject(title);
        //msg.setText(message, "utf-8");
        msg.setContent(message, "text/html; charset=utf-8");
        msg.setSentDate(new Date());

        SMTPTransport t = (SMTPTransport) session.getTransport("smtp");

        t.connect(host, user, pass);
        t.sendMessage(msg, msg.getAllRecipients());
        t.close();
    }

}
