package vitusapotek.utilities;

import com.sun.mail.util.MailSSLSocketFactory;
import org.dom4j.DocumentException;
import org.jdom.JDOMException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.xml.sax.SAXException;
import us.codecraft.xsoup.Xsoup;

import javax.mail.*;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Properties;

public class EmailUtilities {
    private static Message[] messages;

    public static Message[] getEmailMessages(String user, String pass) throws MessagingException, IOException, GeneralSecurityException {
        String host = "pop.gmail.com";
        Auth auth = new Auth(user, pass);
        Properties props = new Properties();
        props.put("mail.pop3.host", host);
        props.put("mail.pop3.socketFactory", 995);
        props.put("mail.pop3.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.pop3.port", 995);
        props.put("mail.pop3.starttls.enable", "true");

        props.put("mail.smtp.ssl.trust", "*");
        MailSSLSocketFactory socketFactory= new MailSSLSocketFactory();
        socketFactory.setTrustAllHosts(true);
        socketFactory.setTrustedHosts(new String[] { "my-server" });
        props.put("mail.pop3s.ssl.socketFactory", socketFactory);
        props.put("mail.smtp.ssl.enable", "true");
        props.put("mail.smtp.ssl.checkserveridentity", "true");
        props.put("mail.smtp.ssl.socketFactory", socketFactory);

        Session emailSession = Session.getInstance(props);
        Store store = emailSession.getStore("pop3s");
        store.connect(host, user, pass);
        Folder inbox = store.getFolder("inbox");
        inbox.open(Folder.READ_ONLY);
        messages = inbox.getMessages();

//        Properties props = new Properties();
//        props.put("mail.store.protocol","imaps");
//
//        Session session;
//
//        session = Session.getDefaultInstance(props, null);
//        Store store = session.getStore("imaps");
//        store.connect("imap.gmail.com",user,pass);
//
//        IMAPFolder folder = (IMAPFolder) store.getFolder("inbox");
//        folder.open(Folder.READ_ONLY);
//
//        Flags seen = new Flags(Flags.Flag.SEEN);
//        FlagTerm unseenFlagTerm = new FlagTerm(seen,false);
//        Message messages[] = folder.search(unseenFlagTerm);
        return messages;
    }

    public static Message getEmailMessage(String email, String password, String fromAddress, String subject) throws IOException, MessagingException {
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

        for (Message msg : messages) {
            if (msg.getFrom()[0].toString().contains(fromAddress) && (msg.getSubject().toString().contains(subject))) {
                return msg;
            }
        }
        return null;
    }

    public static String getOrderTotalFromEmail(Message msg) throws IOException, SAXException, MessagingException, JDOMException, DocumentException {
        String s;
        s = ((Multipart) msg.getContent()).getBodyPart(0).getContent().toString();
        Document document = Jsoup.parse(s);
        return Xsoup.compile("//table/tfoot/tr[2]/td[2]/p/strong").evaluate(document).getElements().get(0).ownText();
    }
}
