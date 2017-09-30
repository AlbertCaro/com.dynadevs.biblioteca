package com.albertocaro.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.albertocaro.biblioteca.R;
import com.albertocaro.classes.Loan;

import java.util.ArrayList;

/**
 * Created by beto_ on 29/09/2017.
 */

public class LoansAdapter extends RecyclerView.Adapter<LoansAdapter.ViewHolderLoans> {

    private ArrayList<Loan> LoanList;

    public LoansAdapter (ArrayList<Loan> LoansList) {
        this.LoanList = LoansList;
    }
    @Override
    public LoansAdapter.ViewHolderLoans onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_loans,null,false);
        return new ViewHolderLoans(view);
    }

    @Override
    public void onBindViewHolder(LoansAdapter.ViewHolderLoans holder, int position) {
        holder.TvTitle.setText(LoanList.get(position).getTitle());
        holder.TvDate.setText(LoanList.get(position).getDate());
    }

    @Override
    public int getItemCount() {
        return LoanList.size();
    }

    class ViewHolderLoans extends RecyclerView.ViewHolder {
        TextView TvTitle, TvDate;
        public ViewHolderLoans(View itemView) {
            super(itemView);
            TvTitle = itemView.findViewById(R.id.tvTitleLoan);
            TvDate = itemView.findViewById(R.id.tvDate);
        }
    }
}
