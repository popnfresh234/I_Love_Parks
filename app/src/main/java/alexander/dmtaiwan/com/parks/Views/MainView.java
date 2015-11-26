package alexander.dmtaiwan.com.parks.Views;

import java.util.List;

import alexander.dmtaiwan.com.parks.Models.Park;

/**
 * Created by lenovo on 11/19/2015.
 */
public interface MainView {
    void onDataReturned(List<Park> parksList);

    void onError(String error);
}
