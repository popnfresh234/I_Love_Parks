package com.dmtaiwan.alexander.parks.Views;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dmtaiwan.alexander.parks.Bus.EventBus;
import com.dmtaiwan.alexander.parks.Bus.ParkListEvent;
import com.dmtaiwan.alexander.parks.Bus.SettingsEvent;
import com.dmtaiwan.alexander.parks.Models.Park;
import com.dmtaiwan.alexander.parks.R;
import com.dmtaiwan.alexander.parks.Utilities.AdapterParkList;
import com.dmtaiwan.alexander.parks.Utilities.Utilities;
import com.squareup.otto.Subscribe;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by lenovo on 11/26/2015.
 */
public class ParkListFragment extends Fragment implements AdapterParkList.RecyclerClickListener{

    private static final String LOG_TAG = ParkListFragment.class.getSimpleName();

    @Bind(R.id.recycler_view_parks_list)
    RecyclerView mRecyclerView;

    @Bind(R.id.empty_view)
    TextView mEmptyView;

    private AdapterParkList mAdapter;
    private List<Park> mParksList;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getInstance().register(this);


    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_park_list, container, false);
        ButterKnife.bind(this, rootView);
        //Set Layout Manager
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(llm);
        mAdapter = new AdapterParkList(getActivity(), mEmptyView, this);
        mRecyclerView.setAdapter(mAdapter);

        return rootView;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getInstance().unregister(this);
    }

    @Subscribe
    public void onNewParkList(ParkListEvent event) {
        mParksList = event.getParkList();
        if (mAdapter != null) {
            mAdapter.udpateData(mParksList);
        }
    }

    @Subscribe
    public void onSettingsEvent(SettingsEvent event) {
        String key = event.getSettingKey();
        if (key.equals(getString(R.string.pref_sort_order_key))) {
            if (mAdapter != null) {
                mAdapter.udpateData(mParksList);
            }
        }
    }

    @Override
    public void onRecyclerClick(Park park) {
        Intent intent = new Intent(getActivity(), ParkDetailActivity.class);
        intent.putExtra(Utilities.EXTRA_PARK, park);
        startActivity(intent);
    }
}
