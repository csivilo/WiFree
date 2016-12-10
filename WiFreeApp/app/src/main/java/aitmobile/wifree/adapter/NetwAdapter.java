package aitmobile.wifree.adapter;

import android.content.Context;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
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
import aitmobile.wifree.data.Netw;

/**
 * Created by C_lo on 12/9/2016.
 */

public class NetwAdapter extends RecyclerView.Adapter<NetwAdapter.ViewHolder> {

    private List<Netw> netwList;
    private Context context;
    private int lastPosition = -1;

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView tvName;

        public ImageButton btnDelete;


        public ViewHolder(View itemView) {
            super(itemView);
            btnDelete = (ImageButton) itemView.findViewById(R.id.btnDelete);
            tvName = (TextView) itemView.findViewById(R.id.tvName);

        }
    }



    public NetwAdapter(Context context) {
        netwList = Netw.listAll(Netw.class);
        this.context = context;
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
                removeNetw(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return netwList.size();
    }

    public void addNetw(Netw netw) {
        netw.save();
        netwList.add(netw);
        notifyDataSetChanged();
    }


    public void removeNetw(int index) {
        // remove it from the DB
        netwList.get(index).delete();
        // remove it from the list
        netwList.remove(index);
        notifyItemRemoved(index);
    }

    public Netw getNetw(int i) {
        return netwList.get(i);
    }

}