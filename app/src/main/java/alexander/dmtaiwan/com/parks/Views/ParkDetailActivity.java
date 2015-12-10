package alexander.dmtaiwan.com.parks.Views;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import alexander.dmtaiwan.com.parks.Models.Park;
import alexander.dmtaiwan.com.parks.R;
import alexander.dmtaiwan.com.parks.Utilities.AdapterParkDetails;
import alexander.dmtaiwan.com.parks.Utilities.DividerItemDecoration;
import alexander.dmtaiwan.com.parks.Utilities.Utilities;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by lenovo on 12/10/2015.
 */
public class ParkDetailActivity extends AppCompatActivity {

    private static final String LOG_TAG = ParkDetailActivity.class.getSimpleName();


    @Bind(R.id.toolbar)
    Toolbar mToolbar;

    @Bind(R.id.collapsing_toolbar)
    CollapsingToolbarLayout mCollapsingToolbar;

    @Bind(R.id.collapsing_image)
    ImageView mCollapsingImage;

    @Bind(R.id.aq_detail_recycler_view)
    RecyclerView mRecyclerView;

    private Park mPark;
    private AdapterParkDetails mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);

        if (getIntent() != null) {
            mPark = getIntent().getParcelableExtra(Utilities.EXTRA_PARK);
        }

        setSupportActionBar(mToolbar);
        mCollapsingToolbar.setTitle(mPark.getParkName());
        mCollapsingToolbar.setExpandedTitleColor(getResources().getColor(android.R.color.transparent));
        Picasso.with(this).load(mPark.getImage()).into(mCollapsingImage);

        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(llm);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));
        mAdapter = new AdapterParkDetails(this, mPark);
        mRecyclerView.setAdapter(mAdapter);
    }
}
