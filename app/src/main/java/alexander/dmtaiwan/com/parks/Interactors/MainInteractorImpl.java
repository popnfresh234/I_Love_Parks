package alexander.dmtaiwan.com.parks.Interactors;

import android.content.Context;
import android.util.Log;

import java.net.HttpURLConnection;

import alexander.dmtaiwan.com.parks.Models.HttpResponse;
import alexander.dmtaiwan.com.parks.R;
import alexander.dmtaiwan.com.parks.Service.Service;
import alexander.dmtaiwan.com.parks.Utilities.Utilities;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by lenovo on 11/19/2015.
 */
public class MainInteractorImpl implements MainInteractor{

    private static String LOG_TAG = MainInteractor.class.getSimpleName();

    private Context mContext;
    private MainListener mListener;

    public MainInteractorImpl(Context context, MainListener listener) {
        this.mContext = context;
        this.mListener = listener;
    }


    @Override
    public void requestData() {

        if(Utilities.doesFileExist(mContext)) {
            Log.i(LOG_TAG, "reading from SD card");
            //TODO show loading
            String response = Utilities.readFromFile(mContext);

            //Create a mock http response
            HttpResponse httpResponse = new HttpResponse();
            httpResponse.setResponseCode(HttpURLConnection.HTTP_OK);
            httpResponse.setResponse(response);
            mListener.onDataReturned(httpResponse);
        }

        if (Utilities.isNetworkAvailable(mContext)) {
            Log.i(LOG_TAG, "Network Request");
            Service service = new Service();
            Observable<HttpResponse> httpResponseObservable = service.requestParks();
            Subscriber<HttpResponse> subscriber = new Subscriber<HttpResponse>() {
                @Override
                public void onCompleted() {

                }

                @Override
                public void onError(Throwable e) {
                    Log.i(LOG_TAG, "RX Error");
                    mListener.onError(e.toString());
                }

                @Override
                public void onNext(HttpResponse httpResponse) {
                    if (Utilities.httpSuccess(httpResponse)) {
                        Log.i(LOG_TAG, "HTTP success");
                        Utilities.writeToFile(httpResponse.getResponse(), mContext);
                        mListener.onDataReturned(httpResponse);
                    } else {
                        Log.i(LOG_TAG, "HTTP error: " + String.valueOf(httpResponse.getResponseCode()));
                        mListener.onError(mContext.getString(R.string.error_http));
                    }

                }
            };

            httpResponseObservable.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(subscriber);
        } else {
            Log.i(LOG_TAG, "Network error");
            mListener.onError(mContext.getString(R.string.error_network));
        }
    }

    public interface MainListener{
        void onDataReturned(HttpResponse httpResponse);

        void onError(String error);
    }
}
