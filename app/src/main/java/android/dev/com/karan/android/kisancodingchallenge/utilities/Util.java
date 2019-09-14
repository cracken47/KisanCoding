package android.dev.com.karan.android.kisancodingchallenge.utilities;


public class Util {

    public static String filterPhoneNumber(String phoneNo) {
        StringBuilder stringBuilder = new StringBuilder("+");
        stringBuilder.append(phoneNo.replace("(", "").replace(")","").replace(" ", "").replace("-", ""));
        return stringBuilder.toString();
    }
}
