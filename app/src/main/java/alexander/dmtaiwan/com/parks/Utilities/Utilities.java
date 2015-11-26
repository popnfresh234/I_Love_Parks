package alexander.dmtaiwan.com.parks.Utilities;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.net.HttpURLConnection;

import alexander.dmtaiwan.com.parks.Models.HttpResponse;

/**
 * Created by lenovo on 11/19/2015.
 */
public class Utilities {

    public static final String API_URL = "http://data.taipei/opendata/datalist/apiAccess?scope=resourceAquire&rid=8f6fcb24-290b-461d-9d34-72ed1b3f51f0";

    //Outstate strings
    public static final String OUTSTATE_NAV_POSITION = "com.dmtaiwan.alexander.nav_position";

    static public boolean isNetworkAvailable(Context c) {
        ConnectivityManager cm =
                (ConnectivityManager) c.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
    }

    public static boolean httpSuccess(HttpResponse httpResponse) {
        if (httpResponse.getResponseCode() == HttpURLConnection.HTTP_OK) {
            return true;
        }else {
            return false;
        }
    }
}
