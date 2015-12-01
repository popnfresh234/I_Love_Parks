package alexander.dmtaiwan.com.parks.Views;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import java.util.List;

import alexander.dmtaiwan.com.parks.Bus.EventBus;
import alexander.dmtaiwan.com.parks.Bus.ParkListEvent;
import alexander.dmtaiwan.com.parks.Models.Park;
import alexander.dmtaiwan.com.parks.Presenters.MainPresenter;
import alexander.dmtaiwan.com.parks.Presenters.MainPresenterImpl;
import alexander.dmtaiwan.com.parks.R;
import alexander.dmtaiwan.com.parks.Utilities.LocationProvider;
import alexander.dmtaiwan.com.parks.Utilities.Utilities;
import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements MainView, NavigationView.OnNavigationItemSelectedListener, LocationProvider.LocationCallback {

    @Bind(R.id.toolbar)
    Toolbar mToolbar;

    @Bind(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;

    @Bind(R.id.navigation_view)
    NavigationView mNavigationView;

    @Bind(R.id.main_content_container)
    FrameLayout mContentContainer;

    @Bind(R.id.toolbar_progress)
    ProgressBar mProgressBar;

    private final static String LOG_TAG = MainActivity.class.getSimpleName();

    private MainPresenter mPresenter;
    private int mNavPositoin;
    private List<Park> mParksList;
    private LocationProvider mLocationProvider;


    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mNavPositoin = savedInstanceState.getInt(Utilities.OUTSTATE_NAV_POSITION, 0);
        Menu menu = mNavigationView.getMenu();
        menu.getItem(mNavPositoin).setChecked(true);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(mToolbar);

        //create location provider
        if (mLocationProvider == null) {
            mLocationProvider = new LocationProvider(this, this);
        }

        if (savedInstanceState != null) {
            mNavPositoin = savedInstanceState.getInt(Utilities.OUTSTATE_NAV_POSITION);
        }else {
            Log.i(LOG_TAG, "inflating frag");
            getSupportFragmentManager().beginTransaction().replace(R.id.main_content_container, new ParkListFragment()).commit();
        }

        setupToolbar();
        setupNavDrawer();

        mPresenter = new MainPresenterImpl(this, this);

    }

    @Override
    protected void onResume() {
        super.onResume();
        mLocationProvider.connect();

        if (mPresenter != null) {
            mPresenter.requestData();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        mLocationProvider.disconnect();
    }

    private void setupNavDrawer() {
        if (mToolbar != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            mToolbar.setNavigationIcon(android.R.drawable.ic_media_play);
            mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mDrawerLayout.openDrawer(GravityCompat.START);
                }
            });
            mNavigationView.setNavigationItemSelectedListener(this);
        }
    }

    private void setupToolbar() {
        if (mToolbar != null) {
            setSupportActionBar(mToolbar);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(Utilities.OUTSTATE_NAV_POSITION, mNavPositoin);
    }

    @Override
    public void onDataReturned(List<Park> parksList) {
        mParksList = parksList;
        ParkListEvent event = new ParkListEvent(parksList);
        EventBus.getInstance().post(event);
        mProgressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onError(String error) {
        Log.i(LOG_TAG, error);
    }

    @Override
    public void showProgress() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        mProgressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        item.setChecked(true);
        switch (item.getItemId()) {
            case R.id.drawer_home:
                mNavPositoin = 0;
                Log.i(LOG_TAG, "Home");
                return true;
            case R.id.drawer_map:
                mNavPositoin = 1;
                Log.i(LOG_TAG, "Map");
                return true;
            default:
                return true;
        }
    }

    @Override
    public void handleNewLocation(Location location) {
        Utilities.setUserLocation(location, this);
    }
}
