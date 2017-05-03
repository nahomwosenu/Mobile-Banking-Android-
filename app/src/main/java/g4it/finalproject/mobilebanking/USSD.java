package g4it.finalproject.mobilebanking;

import android.net.Uri;
public class USSD {
    static final String NUMBER="996*1*";
    static final String OPTION_CHECK_BALANCE="5";
    static final String OPTION_DEPOSITE="2";
    static final String OPTION_WITHDRAW="3";
    static final String OPTION_TRANSFER="1";
    static final String OPTION_CHANGE_PIN="5";
    static final String SERVER="http://10.0.3.2/mobilebanking";
    static Uri ussdToCallableUri(String ussd) {
        String uriString = "";
        if(!ussd.startsWith("tel:"))
            uriString += "tel:";

        for(char c : ussd.toCharArray()) {
            if(c == '#')
                uriString += Uri.encode("#");
            else
                uriString += c;
        }
        return Uri.parse(uriString);
    }
}
