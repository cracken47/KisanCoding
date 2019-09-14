package android.dev.com.karan.android.kisancodingchallenge;

import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import android.dev.com.karan.android.kisancodingchallenge.serviceinterface.ApiNetwork;
import android.dev.com.karan.android.kisancodingchallenge.utilities.Constants;
import android.dev.com.karan.android.kisancodingchallenge.utilities.Util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class UnitTest {

    @Test
    public void checkDateFormat() throws Exception {
        String date_updated = "Mon, 19 Oct 2015 07:07:03 +0000";
        SimpleDateFormat dateFormatIn = new SimpleDateFormat("E, d MMM yyyy HH:mm:ss Z", Locale.getDefault());
        SimpleDateFormat dateFormatOut = new SimpleDateFormat("E, d MMM yyyy", Locale.getDefault());
        Date date = dateFormatIn.parse(date_updated);
        assertEquals("Mon, 19 Oct 2015", dateFormatOut.format(date));
    }

    @Test
    public void test_NumberFilter() throws Exception {
        String phoneNumber = BuildConfig.ACCOUNT_SENDER;
        assertFalse(Util.filterPhoneNumber(phoneNumber).matches("/^[- +()]]*/"));
    }

    @Test
    public void test_NetworkClass() throws Exception{
        ApiNetwork apiNetwork = new ApiNetwork();
        assertNotNull(apiNetwork.getNetworkService(null, Constants.TWILIO_API_END));
    }

    @Test
    public void checkClass_Exists() throws  Exception{
        Class clazz = Class.forName("android.dev.com.karan.android.kisancodingchallenge.javaviews.MainActivity");
        assertNotNull(clazz);
    }
}