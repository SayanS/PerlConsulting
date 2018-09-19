package vitusapotek.utilities;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

public class Auth extends Authenticator {
    private String user;
    private String pass;

    public Auth(String user, String pass){
        this.user=user;
        this.pass=pass;
    }

    @Override
    protected PasswordAuthentication getPasswordAuthentication(){
        return new PasswordAuthentication(user,pass);
    }
}
