package lloydsPharmaProject.utils;

import com.sun.mail.imap.IMAPFolder;
import org.dom4j.DocumentException;
import org.jdom.JDOMException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.xml.sax.SAXException;
import us.codecraft.xsoup.Xsoup;

import javax.mail.*;
import javax.mail.search.FlagTerm;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Properties;


public class EmailUtilities {
    private static Message[] messages;

    public static Message[] getEmailMessages(String user, String pass) throws MessagingException, IOException, GeneralSecurityException {
//        String host="pop.gmail.com";
//        Auth auth = new Auth(user, pass);
//        Properties props = new Properties();
//        props.put("mail.pop3.host", host);
//        props.put("mail.pop3.socketFactory", 995);
//        props.put("mail.pop3.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
//        props.put("mail.pop3.port", 995);
//        props.put("mail.pop3.starttls.enable", "true");

//        props.put("mail.smtp.ssl.trust", "*");
//        MailSSLSocketFactory socketFactory= new MailSSLSocketFactory();
//        socketFactory.setTrustAllHosts(true);
//        socketFactory.setTrustedHosts(new String[] { "my-server" });
//        props.put("mail.pop3s.ssl.socketFactory", socketFactory);
//        props.put("mail.smtp.ssl.enable", "true");
//        props.put("mail.smtp.ssl.checkserveridentity", "true");
//        props.put("mail.smtp.ssl.socketFactory", socketFactory);
//
//        Session emailSession = Session.getInstance(props);
//        Store store = emailSession.getStore("pop3s");
//        store.connect(host, user, pass);
//        Folder inbox = store.getFolder("Inbox");
//        inbox.open(Folder.READ_ONLY);
//        messages = inbox.getMessages();

        Properties props = new Properties();
        props.put("mail.store.protocol", "imaps");

        Session session;

        session = Session.getDefaultInstance(props, null);
        Store store = session.getStore("imaps");
        store.connect("imap.gmail.com", user, pass);

        IMAPFolder folder = (IMAPFolder) store.getFolder("inbox");
        folder.open(Folder.READ_ONLY);

        Flags seen = new Flags(Flags.Flag.SEEN);
        FlagTerm unseenFlagTerm = new FlagTerm(seen, false);
        Message messages[] = folder.search(unseenFlagTerm);
        return messages;
    }

    public static Message getEmailMessage(String email, String password, String fromAddress, String subject) throws IOException, MessagingException, GeneralSecurityException {
        for (int i = 1; i <= 60; i++) {
            try {
                Thread.sleep(1000);
                messages = getEmailMessages(email, password);
                if (messages[messages.length - 1].getSubject().contains(subject)) {
                    break;
                }
            } catch (Exception e) {
            }
        }
        messages = getEmailMessages(email, password);
        for (Message msg : messages) {
            if (msg.getFrom()[0].toString().contains(fromAddress) && (msg.getSubject().toString().contains(subject))) {
//                msg.setFlag(Flags.Flag.DELETED, true); //delete
                return msg;
            }
        }
        return null;
    }

    public static void deleteAllInboxMessages(String user, String pass) throws MessagingException {
        Properties props = new Properties();
        props.put("mail.store.protocol", "imaps");
        Session session;
        session = Session.getDefaultInstance(props, null);
        Store store = session.getStore("imaps");
        store.connect("imap.gmail.com", user, pass);
        IMAPFolder folder = (IMAPFolder) store.getFolder("inbox");
        folder.open(Folder.READ_WRITE);
        Message[] messages = folder.getMessages();
        for (int i = 0; i < messages.length; i++) {
            Message message = messages[i];
            message.setFlag(Flags.Flag.DELETED, true);
        }
        boolean expunge = true;
        folder.close(expunge);
        store.close();
    }

    public static String getConfirmRegistrationLinkFromEmail(Message msg) throws IOException, SAXException, MessagingException, JDOMException, DocumentException {
        String s;
        s = msg.getContent().toString();
        Document document = Jsoup.parse(s);
        return Xsoup.compile("//tbody/tr[4]/td/span/a").evaluate(document).getElements().get(0).attr("href");
    }

    public static String getOrderTotalFromEmail(Message msg) throws IOException, SAXException, MessagingException, JDOMException, DocumentException {
        String s;
        s = ((Multipart) msg.getContent()).getBodyPart(0).getContent().toString();
        Document document = Jsoup.parse(s);
        return Xsoup.compile("//table/tfoot/tr[2]/td[2]/p/strong").evaluate(document).getElements().get(0).ownText();
    }

    public static String getPasswordRecoveryLink(Message msg) throws IOException, MessagingException {
        String s;
        s = ((Multipart) msg.getContent()).getBodyPart(0).getContent().toString();
        Document document = Jsoup.parse(s);
        return Xsoup.compile("//table//td/p//a[contains(@href,\"login/pw/change?token\")]").evaluate(document).getElements().get(0).attr("href");
    }

    public static Message getEmailMessageByReceiver(String email, String password, String fromAddress, String receiver) throws MessagingException {
        for (int i = 1; i <= 60; i++) {
            try {
                Thread.sleep(1000);
                messages = getEmailMessages(email, password);
                if (messages[messages.length - 1].getHeader("to")[0].contains(receiver)) {
                    deleteAllInboxMessages(email,password);
                    return messages[messages.length - 1];
                }
            } catch (Exception e) {
            }
        }
        return null;
    }
}

