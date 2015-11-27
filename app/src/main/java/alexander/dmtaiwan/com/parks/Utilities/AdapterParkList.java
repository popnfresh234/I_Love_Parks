package alexander.dmtaiwan.com.parks.Utilities;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import alexander.dmtaiwan.com.parks.Models.Park;
import alexander.dmtaiwan.com.parks.R;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by lenovo on 11/26/2015.
 */
public class AdapterParkList extends RecyclerView.Adapter<AdapterParkList.ViewHolder> {

    private static final String LOG_TAG = AdapterParkList.class.getSimpleName();
    private Context mContext;
    final private View mEmptyView;
    private RecyclerClickListener mListener;
    private List<Park> mParksList;

    public AdapterParkList(Context context, View emptyView, RecyclerClickListener listener) {
        this.mContext = context;
        this.mEmptyView = emptyView;
        this.mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_park, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        Park park = mParksList.get(position);
        holder.mNameText.setText(park.getParkName());
        holder.mDistanceText.setText("110");

    }

    @Override
    public int getItemCount() {
        if (mParksList != null) {
            return mParksList.size();
        }else {
            return 0;
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @Bind(R.id.list_item_park_name)
        TextView mNameText;

        @Bind(R.id.list_item_park_distance)
        TextView mDistanceText;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            Park park = mParksList.get(position);
            mListener.onRecyclerClick(park);
        }
    }

    public void udpateData(List<Park> parksList) {
        Log.i(LOG_TAG, "updating data");
        mParksList = parksList;
        notifyDataSetChanged();
        mEmptyView.setVisibility(mParksList.size() == 0 ? View.VISIBLE : View.GONE);
    }

    public interface RecyclerClickListener {
        void onRecyclerClick(Park park);
    }
}

