package com.dmtaiwan.alexander.parks.Views;

import java.util.List;

import com.dmtaiwan.alexander.parks.Models.Park;

/**
 * Created by lenovo on 11/19/2015.
 */
public interface MainView {
    void onDataReturned(List<Park> parksList);

    void onError(String error);

    void showProgress();

    void hideProgress();
}
