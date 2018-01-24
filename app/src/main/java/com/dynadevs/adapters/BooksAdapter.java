package com.dynadevs.adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.dynadevs.activities.BookDetailActivity;
import com.dynadevs.activities.R;
import com.dynadevs.classes.Book;
import com.bumptech.glide.Glide;
import com.dynadevs.classes.User;

import java.util.ArrayList;

/**
 * Created by Alberto Caro Navarro on 29/09/2017.
 * albertcaronava@gmail.com
 */

public class BooksAdapter extends RecyclerView.Adapter<BooksAdapter.ViewHolderBooks> {
    private ArrayList<Book> BookList;
    private User user;
    private Context context;
    private Activity activity;

    public BooksAdapter(ArrayList<Book> bookList, User user, Context context, Activity activity) {
        BookList = bookList;
        this.user = user;
        this.context = context;
        this.activity = activity;
    }

    public BooksAdapter(ArrayList<Book> bookList, Context context, Activity activity) {
        BookList = bookList;
        this.context = context;
        this.activity = activity;
    }

    @Override
    public ViewHolderBooks onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_books, parent, false);
        return new ViewHolderBooks(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(ViewHolderBooks holder, @SuppressLint("RecyclerView") final int position) {
        Glide.with(context).load(R.drawable.generic_book).centerCrop().into(holder.IvPhoto);
        holder.TvTitle.setText(BookList.get(position).getTitle());
        holder.TvAutor.setText(BookList.get(position).getAutor()+" - "+BookList.get(position).getEdition());
        holder.BtnDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                if (user != null)
                    bundle.putSerializable("user", user);
                bundle.putSerializable("book", BookList.get(position));
                Intent intent = new Intent(context, BookDetailActivity.class);
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });
        if(getTheme() == 0) {
            if (user != null) {
                if (user.getUniversity().toLowerCase().equals("centro universitario de ciencias exactas e ingenierias"))
                    holder.BtnDetails.setTextColor(activity.getResources().getColor(R.color.CUCEI));
                else if (user.getUniversity().toLowerCase().equals("centro universitario de arte, arquitectura y diseño"))
                    holder.BtnDetails.setTextColor(activity.getResources().getColor(R.color.CUAAD));
                else if (user.getUniversity().toLowerCase().equals("centro universitario de los altos"))
                    holder.BtnDetails.setTextColor(activity.getResources().getColor(R.color.CUAltos));
                else if (user.getUniversity().toLowerCase().equals("centro universitario de ciencias biologicas y agropecuarias"))
                    holder.BtnDetails.setTextColor(activity.getResources().getColor(R.color.CUCBA));
                else if (user.getUniversity().toLowerCase().equals("centro universitario de ciencias economico administrativas"))
                    holder.BtnDetails.setTextColor(activity.getResources().getColor(R.color.CUCEA));
                else if (user.getUniversity().toLowerCase().equals("centro universitario de la cienega"))
                    holder.BtnDetails.setTextColor(activity.getResources().getColor(R.color.CUCienega));
                else if (user.getUniversity().toLowerCase().equals("centro universitario de la costa"))
                    holder.BtnDetails.setTextColor(activity.getResources().getColor(R.color.CUCosta));
                else if (user.getUniversity().toLowerCase().equals("centro universitario de ciencias de la salud"))
                    holder.BtnDetails.setTextColor(activity.getResources().getColor(R.color.CUCS));
                else if (user.getUniversity().toLowerCase().equals("centro universitario de ciencias sociales y humanidades"))
                    holder.BtnDetails.setTextColor(activity.getResources().getColor(R.color.CUCSH));
                else if (user.getUniversity().toLowerCase().equals("centro universitario de la costa sur"))
                    holder.BtnDetails.setTextColor(activity.getResources().getColor(R.color.CUCSur));
                else if (user.getUniversity().toLowerCase().equals("centro universitario de los lagos"))
                    holder.BtnDetails.setTextColor(activity.getResources().getColor(R.color.CULagos));
                else if (user.getUniversity().toLowerCase().equals("centro universitario del norte"))
                    holder.BtnDetails.setTextColor(activity.getResources().getColor(R.color.CUNorte));
                else if (user.getUniversity().toLowerCase().equals("centro universitario del sur"))
                    holder.BtnDetails.setTextColor(activity.getResources().getColor(R.color.CUSur));
                else if (user.getUniversity().toLowerCase().equals("centro universitario de tonala"))
                    holder.BtnDetails.setTextColor(activity.getResources().getColor(R.color.CUTonala));
            }
        } else
            holder.BtnDetails.setTextColor(activity.getResources().getColor(R.color.colorAccentAndroid));
    }

    @Override
    public int getItemCount() {
        return BookList.size();
    }

    public void setBookList(ArrayList<Book> bookList) {
        BookList = bookList;
    }

    static class ViewHolderBooks extends RecyclerView.ViewHolder {
        ImageView IvPhoto;
        TextView TvTitle;
        TextView TvAutor;
        Button BtnDetails;
        ViewHolderBooks(View itemView) {
            super(itemView);
            IvPhoto = itemView.findViewById(R.id.ivPhoto);
            TvTitle = itemView.findViewById(R.id.tvTitleBook);
            TvAutor = itemView.findViewById(R.id.tvAutor);
            BtnDetails = itemView.findViewById(R.id.btnDetails);
        }
    }

    private int getTheme() {
        SharedPreferences preferences = context.getSharedPreferences("settings", Context.MODE_PRIVATE);
        return preferences.getInt("theme", 0);
    }
}
