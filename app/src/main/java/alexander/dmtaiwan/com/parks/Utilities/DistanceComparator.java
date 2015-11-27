package alexander.dmtaiwan.com.parks.Utilities;

import android.location.Location;


import java.util.Comparator;

import alexander.dmtaiwan.com.parks.Models.Park;


/**
 * Created by Alexander on 10/20/2015.
 */
public class DistanceComparator implements Comparator<Park> {
    private Location mLocation;

    public DistanceComparator(Location location) {
        this.mLocation = location;
    }


    private Double distanceFromMe(Park park) {

        if (mLocation == null) {
            double theta = Double.valueOf(park.getLongitude()) - Utilities.TAIPEI_LONG;
            double dist = Math.sin(deg2rad(Double.valueOf(park.getLatitude()))) * Math.sin(deg2rad(Utilities.TAIPEI_LAT))
                    + Math.cos(deg2rad(Double.valueOf(park.getLatitude()))) * Math.cos(deg2rad(Utilities.TAIPEI_LAT))
                    * Math.cos(deg2rad(theta));
            dist = Math.acos(dist);
            dist = rad2deg(dist);
            return dist;
        }else{
            double theta = Double.valueOf(park.getLongitude()) - mLocation.getLongitude();
            double dist = Math.sin(deg2rad(Double.valueOf(park.getLatitude()))) * Math.sin(deg2rad(mLocation.getLatitude()))
                    + Math.cos(deg2rad(Double.valueOf(park.getLatitude()))) * Math.cos(deg2rad(mLocation.getLatitude()))
                    * Math.cos(deg2rad(theta));
            dist = Math.acos(dist);
            dist = rad2deg(dist);
            return dist;
        }
    }

    private double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    private double rad2deg(double rad) {
        return (rad * 180.0 / Math.PI);
    }

    @Override
    public int compare(Park lhs, Park rhs) {
        return distanceFromMe(lhs).compareTo(distanceFromMe(rhs));
    }
}
