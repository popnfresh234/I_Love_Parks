package com.dmtaiwan.alexander.parks.Presenters;

import android.content.Context;

import com.dmtaiwan.alexander.parks.Interactors.MainInteractor;
import com.dmtaiwan.alexander.parks.Interactors.MainInteractorImpl;
import com.dmtaiwan.alexander.parks.Models.HttpResponse;
import com.dmtaiwan.alexander.parks.Models.Park;
import com.dmtaiwan.alexander.parks.R;
import com.dmtaiwan.alexander.parks.Views.MainView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lenovo on 11/19/2015.
 */
public class MainPresenterImpl implements MainPresenter, MainInteractorImpl.MainListener {

    private Context mContext;
    private MainView mView;
    private MainInteractor mInteractor;

    public MainPresenterImpl(Context context, MainView mainView) {
        this.mContext = context;
        this.mView = mainView;
        this.mInteractor = new MainInteractorImpl(mContext, this);
    }

    @Override
    public void requestData() {
        mInteractor.requestData();
    }

    @Override
    public void onDataReturned(HttpResponse httpResponse) {
        int responseCode = httpResponse.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            List<Park> parks = parseJson(httpResponse.getResponse());
            if (parks != null) {
                mView.onDataReturned(parks);
            }
        }
    }



    @Override
    public void onError(String error) {
        mView.onError(error);
    }

    @Override
    public void showProgress() {
        mView.showProgress();
    }

    private List<Park> parseJson(String response) {
        List<Park> parks = new ArrayList<>();
        try {
            JSONObject top = new JSONObject(response);
            JSONObject result = top.getJSONObject("result");
            JSONArray results = result.getJSONArray("results");
            for (int i = 0; i < results.length(); i++) {
                JSONObject jsonPark = results.getJSONObject(i);
                Park park = new Park();
                park.set_id(jsonPark.getString("_id"));
                park.setParkName(jsonPark.getString("ParkName"));
                park.setAdministrativeArea(jsonPark.getString("AdministrativeArea"));
                park.setLocation(jsonPark.getString("Location"));
                park.setParkType(jsonPark.getString("ParkType"));
                park.setIntroduction(jsonPark.getString("Introduction"));
                park.setLatitude(jsonPark.getString("Latitude"));
                park.setLongitude(jsonPark.getString("Longitude"));
                park.setArea(jsonPark.getString("Area"));
                park.setYearBuilt(jsonPark.getString("YearBuilt"));
                park.setImage(jsonPark.getString("Image"));
                park.setManagementName(jsonPark.getString("ManagementName"));
                park.setManageTelephone(jsonPark.getString("ManageTelephone"));
                parks.add(park);
            }
            return parks;

        } catch (JSONException e) {
            onError(mContext.getString(R.string.error_json));
            return parks;
        }
    }
}
