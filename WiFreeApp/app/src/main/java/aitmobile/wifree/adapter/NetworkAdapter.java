package aitmobile.wifree.adapter;

import android.content.Context;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import com.orm.SugarDb;

import android.widget.ImageButton;
import android.widget.TextView;

import java.util.List;

import aitmobile.wifree.MainActivity;
import aitmobile.wifree.R;
import aitmobile.wifree.data.Network;

/**
 * Created by C_lo on 12/9/2016.
 */

public class NetworkAdapter extends RecyclerView.Adapter<NetworkAdapter.ViewHolder> {

    private List<Network> netwList;
    private Context context;
    private int lastPosition = -1;

    public NetworkAdapter(Context context) {
        netwList = Network.listAll(Network.class);
        this.context = context;
        addNetwork(new Network("Sivilotti", "carloandali"));
        addNetwork(new Network("UPC0777948","VOQAKOZE"));
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView tvName;

        public ImageButton btnDelete;
        public ImageButton btnDownload;


        public ViewHolder(View itemView) {
            super(itemView);
            btnDelete = (ImageButton) itemView.findViewById(R.id.btnDelete);
            tvName = (TextView) itemView.findViewById(R.id.tvName);
            btnDownload = (ImageButton) itemView.findViewById(R.id.btnDownload);

        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.netw_row, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        viewHolder.tvName.setText(netwList.get(position).getSSID());
        viewHolder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeNetwork(position);
            }
        });

        viewHolder.btnDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) context).downloadNetwork(netwList.get(position).getSSID(),netwList.get(position).getKey());
            }
        });
    }

    @Override
    public int getItemCount() {
        return netwList.size();
    }

    public void addNetwork(Network netw) {
        netw.save();
        netwList.add(0, netw);
        notifyDataSetChanged();
    }


    public void removeNetwork(int index) {
        // remove it from the DB
        netwList.get(index).delete();
        // remove it from the list
        netwList.remove(index);
        notifyItemRemoved(index);
    }

    public Network getNetwork(int i) {
        return netwList.get(i);
    }

}