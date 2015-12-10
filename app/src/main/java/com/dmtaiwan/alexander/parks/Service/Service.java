package com.dmtaiwan.alexander.parks.Service;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.net.HttpURLConnection;
import java.net.URL;

import com.dmtaiwan.alexander.parks.Models.HttpResponse;
import com.dmtaiwan.alexander.parks.Utilities.Utilities;
import rx.Observable;
import rx.Subscriber;

/**
 * Created by lenovo on 11/19/2015.
 */
public class Service {
    public Observable<HttpResponse> requestParks() {
        Observable<HttpResponse> ParksObservable = Observable.create(new Observable.OnSubscribe<HttpResponse>() {
            @Override
            public void call(Subscriber<? super HttpResponse> subscriber) {
                String apiUrl = Utilities.API_URL;
                HttpResponse httpResponse = new HttpResponse();
                int resultCode;
                try {
                    URL url = new URL(apiUrl);
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder()
                            .url(url)
                            .build();

                    Response response = client.newCall(request).execute();
                    resultCode = response.code();
                    httpResponse.setResponseCode(resultCode);
                    if (resultCode == HttpURLConnection.HTTP_OK) {
                        httpResponse.setResponse(response.body().string());
                        subscriber.onNext(httpResponse);
                    } else {
                        subscriber.onNext(httpResponse);
                    }
                } catch (Exception e) {
                    httpResponse.setResponseCode(0);
                    httpResponse.setResponse(e.toString());
                    subscriber.onNext(httpResponse);
                }
            }
        });
        return ParksObservable;
    }
}
