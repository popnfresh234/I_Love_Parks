package alexander.dmtaiwan.com.parks.Interactors;

import android.content.Context;
import android.util.Log;

import alexander.dmtaiwan.com.parks.Models.HttpResponse;
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

    private Context mContext;
    private MainListener mListener;

    public MainInteractorImpl(Context context, MainListener listener) {
        this.mContext = context;
        this.mListener = listener;
    }


    @Override
    public void requestData() {
        if (Utilities.isNetworkAvailable(mContext)) {
            Service service = new Service();
            Observable<HttpResponse> httpResponseObservable = service.requestParks();
            Subscriber<HttpResponse> subscriber = new Subscriber<HttpResponse>() {
                @Override
                public void onCompleted() {

                }

                @Override
                public void onError(Throwable e) {
                    Log.i("RX ERROR", e.toString());
                }

                @Override
                public void onNext(HttpResponse httpResponse) {
                    Log.i("GOT RESPONSE", httpResponse.getResponse());
                }
            };

            httpResponseObservable.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(subscriber);
        } else {
            Log.i("NO NETWORK", "NO NETWORK");
        }
    }

    public interface MainListener{
        void onDataReturned();
    }
}
