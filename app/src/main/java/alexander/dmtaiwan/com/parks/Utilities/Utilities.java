package alexander.dmtaiwan.com.parks.Utilities;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;

import alexander.dmtaiwan.com.parks.Models.HttpResponse;

/**
 * Created by lenovo on 11/19/2015.
 */
public class Utilities {

    public static final String LOG_TAG = Utilities.class.getSimpleName();

    public static final String API_URL = "http://data.taipei/opendata/datalist/apiAccess?scope=resourceAquire&rid=8f6fcb24-290b-461d-9d34-72ed1b3f51f0";
    public static final String FILE_NAME = "parkData.json";
    public static final Double TAIPEI_LAT = 25.033611;
    public static final Double TAIPEI_LONG = 121.565;

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

    static public boolean doesFileExist(Context context) {
        File file = context.getFileStreamPath(FILE_NAME);
        return file.exists();
    }

    public static String readFromFile(Context context) {
        Log.i(LOG_TAG, "Reading from SD card");
        String json = "";
        try {
            InputStream inputStream = context.openFileInput(Utilities.FILE_NAME);
            if (inputStream != null) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();
                while ((receiveString = bufferedReader.readLine()) != null) {
                    stringBuilder.append(receiveString);
                }
                inputStream.close();
                json = stringBuilder.toString();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return json;
    }

    public static void writeToFile(String json, Context context) {
        Log.i(LOG_TAG, "Writing to SD card");
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput(Utilities.FILE_NAME, Context.MODE_PRIVATE));
            outputStreamWriter.write(json);
            outputStreamWriter.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
