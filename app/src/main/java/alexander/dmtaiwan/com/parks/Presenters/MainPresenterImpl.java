package alexander.dmtaiwan.com.parks.Presenters;

import android.content.Context;

import alexander.dmtaiwan.com.parks.Interactors.MainInteractor;
import alexander.dmtaiwan.com.parks.Interactors.MainInteractorImpl;
import alexander.dmtaiwan.com.parks.Views.MainView;

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
    public void onDataReturned() {

    }
}
