package com.dmtaiwan.alexander.parks.Bus;

import java.util.List;

import com.dmtaiwan.alexander.parks.Models.Park;

/**
 * Created by lenovo on 11/26/2015.
 */
public class ParkListEvent {
    private List<Park> mParkList;

    public ParkListEvent(List<Park> parkList) {
        this.mParkList = parkList;
    }

    public List<Park> getParkList() {
        return mParkList;
    }
}
