package com.dmtaiwan.alexander.parks.Models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by lenovo on 11/22/2015.
 */
public class Park implements Parcelable {

    public Park() {
    }

    private String ManageTelephone;

    private String AdministrativeArea;

    private String Area;

    private String ParkType;

    private String Location;

    private String YearBuilt;

    private String _id;

    private String Introduction;

    private String Image;

    private String ManagementName;

    private String ParkName;

    private String Latitude;

    private String Longitude;

    public String getManageTelephone ()
    {
        return ManageTelephone;
    }

    public void setManageTelephone (String ManageTelephone)
    {
        this.ManageTelephone = ManageTelephone;
    }

    public String getAdministrativeArea ()
    {
        return AdministrativeArea;
    }

    public void setAdministrativeArea (String AdministrativeArea)
    {
        this.AdministrativeArea = AdministrativeArea;
    }

    public String getArea ()
    {
        return Area;
    }

    public void setArea (String Area)
    {
        this.Area = Area;
    }

    public String getParkType ()
    {
        return ParkType;
    }

    public void setParkType (String ParkType)
    {
        this.ParkType = ParkType;
    }

    public String getLocation ()
    {
        return Location;
    }

    public void setLocation (String Location)
    {
        this.Location = Location;
    }





    public String getYearBuilt ()
    {
        return YearBuilt;
    }

    public void setYearBuilt (String YearBuilt)
    {
        this.YearBuilt = YearBuilt;
    }

    public String get_id ()
    {
        return _id;
    }

    public void set_id (String _id)
    {
        this._id = _id;
    }

    public String getIntroduction ()
    {
        return Introduction;
    }

    public void setIntroduction (String Introduction)
    {
        this.Introduction = Introduction;
    }

    public String getImage ()
    {
        return Image;
    }

    public void setImage (String Image)
    {
        this.Image = Image;
    }

    public String getManagementName ()
    {
        return ManagementName;
    }

    public void setManagementName (String ManagementName)
    {
        this.ManagementName = ManagementName;
    }

    public String getParkName ()
    {
        return ParkName;
    }

    public void setParkName (String ParkName)
    {
        this.ParkName = ParkName;
    }

    public String getLatitude ()
    {
        return Latitude;
    }

    public void setLatitude (String Latitude)
    {
        this.Latitude = Latitude;
    }

    public String getLongitude ()
    {
        return Longitude;
    }

    public void setLongitude (String Longitude)
    {
        this.Longitude = Longitude;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [ManageTelephone = "+ManageTelephone+", AdministrativeArea = "+AdministrativeArea+", Area = "+Area+", ParkType = "+ParkType+", Location = "+Location+", YearBuilt = "+YearBuilt+", _id = "+_id+", Introduction = "+Introduction+", Image = "+Image+", ManagementName = "+ManagementName+", ParkName = "+ParkName+", Latitude = "+Latitude+", Longitude = "+Longitude+"]";
    }

    protected Park(Parcel in) {
        ManageTelephone = in.readString();
        AdministrativeArea = in.readString();
        Area = in.readString();
        ParkType = in.readString();
        Location = in.readString();
        YearBuilt = in.readString();
        _id = in.readString();
        Introduction = in.readString();
        Image = in.readString();
        ManagementName = in.readString();
        ParkName = in.readString();
        Latitude = in.readString();
        Longitude = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(ManageTelephone);
        dest.writeString(AdministrativeArea);
        dest.writeString(Area);
        dest.writeString(ParkType);
        dest.writeString(Location);
        dest.writeString(YearBuilt);
        dest.writeString(_id);
        dest.writeString(Introduction);
        dest.writeString(Image);
        dest.writeString(ManagementName);
        dest.writeString(ParkName);
        dest.writeString(Latitude);
        dest.writeString(Longitude);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Park> CREATOR = new Parcelable.Creator<Park>() {
        @Override
        public Park createFromParcel(Parcel in) {
            return new Park(in);
        }

        @Override
        public Park[] newArray(int size) {
            return new Park[size];
        }
    };
}