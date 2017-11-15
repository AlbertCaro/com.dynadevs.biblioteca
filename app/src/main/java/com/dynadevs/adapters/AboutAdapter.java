package com.dynadevs.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dynadevs.activities.R;
import com.dynadevs.classes.Person;

import java.util.ArrayList;

/**
 * Created by Alberto Caro Navarro on 11/11/2017.
 * albertcaronava@gmail.com
 */

public class AboutAdapter extends RecyclerView.Adapter<AboutAdapter.ViewHolder> {
    private ArrayList<Person> PersonList;

    public AboutAdapter(ArrayList<Person> personList) {
        PersonList = personList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_about, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.TvNombre.setText(PersonList.get(position).getName());
        holder.TvEmail.setText(PersonList.get(position).getEmail());
        holder.TvCareer.setText(PersonList.get(position).getCareer());
        holder.IvPhoto.setImageResource(PersonList.get(position).getPhoto());
    }

    @Override
    public int getItemCount() {
        return PersonList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView TvNombre, TvEmail, TvCareer;
        ImageView IvPhoto;

        ViewHolder(View itemView) {
            super(itemView);
            TvNombre = itemView.findViewById(R.id.tvName);
            TvEmail = itemView.findViewById(R.id.tvEmail);
            TvCareer = itemView.findViewById(R.id.tvCareer);
            IvPhoto = itemView.findViewById(R.id.ivPhoto);
        }
    }
}
