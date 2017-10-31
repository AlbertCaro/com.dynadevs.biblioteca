package com.dynadevs.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.dynadevs.biblioteca.BookDetailActivity;
import com.dynadevs.biblioteca.R;
import com.dynadevs.classes.Book;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

/**
 * Created by beto_ on 29/09/2017.
 */

public class BooksAdapter extends RecyclerView.Adapter<BooksAdapter.ViewHolderBooks> {
    private ArrayList<Book> BookList;
    private Context context;
    private View.OnClickListener Listener;

    public BooksAdapter(ArrayList<Book> bookList, Context context) {
        BookList = bookList;
        this.context = context;
    }

    @Override
    public ViewHolderBooks onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_books, parent, false);
        return new ViewHolderBooks(view);
    }

    @Override
    public void onBindViewHolder(ViewHolderBooks holder, final int position) {
        Glide.with(context).load(BookList.get(position).getPhoto()).centerCrop().into(holder.IvPhoto);
        holder.TvTitle.setText(BookList.get(position).getTitle());
        holder.BtnDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("Object", BookList.get(position));
                Intent intent = new Intent(context, BookDetailActivity.class);
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return BookList.size();
    }


    public class ViewHolderBooks extends RecyclerView.ViewHolder {
        ImageView IvPhoto;
        TextView TvTitle;
        Button BtnDetails;
        public ViewHolderBooks(View itemView) {
            super(itemView);
            IvPhoto = itemView.findViewById(R.id.ivPhoto);
            TvTitle = itemView.findViewById(R.id.tvTitleBook);
            BtnDetails = itemView.findViewById(R.id.btnDetails);
        }
    }


}
