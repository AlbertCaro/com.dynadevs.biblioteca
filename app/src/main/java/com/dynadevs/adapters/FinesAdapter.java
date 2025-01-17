package com.dynadevs.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dynadevs.activities.R;
import com.dynadevs.classes.Fine;

import java.util.ArrayList;

/**
 * Created by Alberto Caro Navarro on 29/09/2017.
 * albertcaronava@gmail.com
 */

public class FinesAdapter extends RecyclerView.Adapter<FinesAdapter.ViewHolderFines> {
    private ArrayList<Fine> FineList;

    public FinesAdapter(ArrayList<Fine> fineList) {
        FineList = fineList;
    }

    @Override
    public FinesAdapter.ViewHolderFines onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_fines, parent,false);
        return new ViewHolderFines(view);
    }

    @Override
    public void onBindViewHolder(FinesAdapter.ViewHolderFines holder, int position) {
        holder.TvTitle.setText(FineList.get(position).getTitle());
        holder.TvSanction.setText(FineList.get(position).getSanction());
    }

    @Override
    public int getItemCount() {
        return FineList.size();
    }

    public void setFineList(ArrayList<Fine> fineList) {
        FineList = fineList;
    }

    class ViewHolderFines extends RecyclerView.ViewHolder {
        TextView TvTitle, TvSanction;
        ViewHolderFines(View itemView) {
            super(itemView);
            TvTitle = itemView.findViewById(R.id.tvTitleFine);
            TvSanction = itemView.findViewById(R.id.tvSanction);
        }
    }
}
