package com.albertocaro.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.albertocaro.biblioteca.R;
import com.albertocaro.classes.Book;

import java.util.ArrayList;

/**
 * Created by beto_ on 29/09/2017.
 */

public class BooksAdapter extends RecyclerView.Adapter<BooksAdapter.ViewHolderBooks> implements View.OnClickListener {
    private ArrayList<Book> BookList;
    private View.OnClickListener Listener;

    public BooksAdapter(ArrayList<Book> bookList) {
        this.BookList = bookList;
    }

    @Override
    public ViewHolderBooks onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_books, null, false);
        view.setOnClickListener(this);
        return new ViewHolderBooks(view);
    }

    @Override
    public void onBindViewHolder(ViewHolderBooks holder, int position) {
        holder.TvTitle.setText(BookList.get(position).getTitle());
    }

    @Override
    public int getItemCount() {
        return BookList.size();
    }

    public void setOnClickListener (View.OnClickListener Listener) {
        this.Listener = Listener;
    }


    @Override
    public void onClick(View view) {
        if (Listener != null)
            Listener.onClick(view);
    }

    public class ViewHolderBooks extends RecyclerView.ViewHolder {
        TextView TvTitle;
        public ViewHolderBooks(View itemView) {
            super(itemView);
            TvTitle = itemView.findViewById(R.id.tvTitleBook);
        }
    }


}
