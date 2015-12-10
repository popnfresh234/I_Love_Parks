package alexander.dmtaiwan.com.parks.Utilities;

/**
 * Created by lenovo on 12/10/2015.
 */

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import alexander.dmtaiwan.com.parks.Models.Park;
import alexander.dmtaiwan.com.parks.R;
import butterknife.Bind;
import butterknife.ButterKnife;
/**
 * Created by Alexander on 11/13/2015.
 */
public class AdapterParkDetails extends RecyclerView.Adapter<AdapterParkDetails.ViewHolder> {

    private Context mContext;
    private Park mPark;


    public AdapterParkDetails(Context context, Park park) {
        this.mContext = context;
        this.mPark = park;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_detail, parent, false);
        return new ViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
            holder.mTitle.setText(Utilities.getParkDetailHeader(position, mContext));
            holder.mData.setText(Utilities.getParkData(position, mPark));
    }

    @Override
    public int getItemCount() {
        return 6;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        @Nullable
        @Bind(R.id.aq_detail_title)
        TextView mTitle;

        @Nullable
        @Bind(R.id.aq_detail_data)
        TextView mData;


        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
