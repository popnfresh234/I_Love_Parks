package alexander.dmtaiwan.com.parks.Utilities;

import android.content.Context;
import android.content.SharedPreferences;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.preference.PreferenceManager;
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
import alexander.dmtaiwan.com.parks.Models.Park;
import alexander.dmtaiwan.com.parks.R;

/**
 * Created by lenovo on 11/19/2015.
 */
public class Utilities {

    public static final String LOG_TAG = Utilities.class.getSimpleName();

    public static final String API_URL = "http://data.taipei/opendata/datalist/apiAccess?scope=resourceAquire&rid=8f6fcb24-290b-461d-9d34-72ed1b3f51f0";
    public static final String FILE_NAME = "parkData.json";
    public static final Double TAIPEI_LAT = 25.033611;
    public static final Double TAIPEI_LONG = 121.565;

    //Intent Extras
    public static final String EXTRA_PARK = "com.dmtaiwan.alexander.park";

    //Outstate strings
    public static final String OUTSTATE_NAV_POSITION = "com.dmtaiwan.alexander.nav_position";

    //Shard prefs keys
    public static final String SHARED_PREFS_LOCATION_LAT_KEY = "com.dmtaiwan.alexander.key.location.lat";
    public static final String SHARED_PREFS_LOCATION_LONG_KEY = "com.dmtaiwan.alexander.key.location.long";
    public static final String SHARED_PREFS_SORT_KEY = "com.dmtaiwan.alexander.key.sort";

    //Sort codes
    public static final int SORT_DEFAULT =2222;
    public static final int SORT_PROXIMITY = 3333;


    //Networking utilities
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


    //File system utilites
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


    //Location utilities

    public static float calculateDistance(double targetLat, double targetLong, Location userLocation) {
        Location stationLocation = new Location("");

        stationLocation.setLatitude(targetLat);
        stationLocation.setLongitude(targetLong);

        return userLocation.distanceTo(stationLocation);
    }

    public static String formatDistance(float meters) {
        if (meters < 1000) {
            return ((int) meters) + "m";
        } else if (meters < 10000) {
            return formatDec(meters / 1000f, 1) + "km";
        } else {
            return ((int) (meters / 1000f)) + "km";
        }
    }

    private static String formatDec(float val, int dec) {
        int factor = (int) Math.pow(10, dec);

        int front = (int) (val);
        int back = (int) Math.abs(val * (factor)) % factor;

        return front + "." + back;
    }

    public static void setUserLocation(Location location, Context context) {
        if (location != null) {
            SharedPreferences settings;
            SharedPreferences.Editor spe;
            double lat = location.getLatitude();
            double longitude = location.getLongitude();
            settings = PreferenceManager.getDefaultSharedPreferences(context);
            spe = settings.edit();
            spe.putLong(SHARED_PREFS_LOCATION_LAT_KEY, Double.doubleToRawLongBits(lat));
            spe.putLong(SHARED_PREFS_LOCATION_LONG_KEY, Double.doubleToRawLongBits(longitude));
            spe.apply();
        }
    }

    public static Location getUserLocation(Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        double lat = Double.longBitsToDouble(prefs.getLong(Utilities.SHARED_PREFS_LOCATION_LAT_KEY, 0));
        double longitude = Double.longBitsToDouble(prefs.getLong(Utilities.SHARED_PREFS_LOCATION_LONG_KEY, 0));
        //If activity_station_detai; location has been stored in shared prefs, retrieve it and set the lat/long coordinates for the query
        if (lat != 0 && longitude != 0) {
            Location userLocation = new Location("newLocation");
            userLocation.setLatitude(lat);
            userLocation.setLongitude(longitude);
            return userLocation;
        } else {
            return null;
        }
    }

    public static String getSortCode(Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        String sortKey = prefs.getString(context.getString(R.string.pref_sort_order_key), context.getString(R.string.pref_sort_default_value));
        return sortKey;
    }

    public static String getParkData(int position, Park mPark) {
        switch (position){
            case 0: return mPark.getParkName();
            case 1: return mPark.getLocation();
            case 2: return mPark.getParkType();
            case 3: return mPark.getYearBuilt();
            case 4: return mPark.getManagementName();
            case 5: return mPark.getManageTelephone();
            default:return null;
        }
    }

    public static String getParkDetailHeader(int position, Context mContext) {
        switch (position) {
            case 0: return mContext.getString(R.string.detail_header_name);
            case 1: return mContext.getString(R.string.detail_header_location);
            case 2: return mContext.getString(R.string.detail_header_type);
            case 3: return mContext.getString(R.string.detail_header_year);
            case 4: return mContext.getString(R.string.detail_header_management_name);
            case 5: return mContext.getString(R.string.detail_header_management_number);
         default:return null;
        }
    }
}
