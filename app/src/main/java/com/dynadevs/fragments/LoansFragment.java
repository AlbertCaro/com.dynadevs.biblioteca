package com.dynadevs.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.dynadevs.activities.R;
import com.dynadevs.adapters.LoansAdapter;
import com.dynadevs.classes.Loan;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link LoansFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link LoansFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LoansFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private RecyclerView recyclerView;
    private ArrayList<Loan> LoanList = new ArrayList<>();
    private LoansAdapter Adapter;
    private FloatingActionButton fab;
    private LinearLayout linearLayout;
    private SearchView searchView;

    public LoansFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LoansFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LoansFragment newInstance(String param1, String param2) {
        LoansFragment fragment = new LoansFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view =  inflater.inflate(R.layout.fragment_loans, container, false);
        recyclerView = view.findViewById(R.id.rvLoans);
        recyclerView.setLayoutManager(new GridLayoutManager(container.getContext(),1));
        linearLayout = view.findViewById(R.id.emptyListLoan);
        searchView = getActivity().findViewById(R.id.search);
        searchView.setVisibility(View.VISIBLE);
        fab = getActivity().findViewById(R.id.fab);
        fab.setImageResource(R.drawable.ic_refrescar);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(view, "Refrescando la lista...", Snackbar.LENGTH_SHORT).show();
                doRequest();
            }
        });
        doRequest();
        recyclerView.setHasFixedSize(true);
        Adapter = new LoansAdapter(LoanList);
        recyclerView.setAdapter(Adapter);
        return view;
    }

    public void doRequest() {
        LoanList.clear();
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        String Url = "http://albertocaro.000webhostapp.com/biblioteca/rest/prestamos.php?id=215818158";
        StringRequest request = new StringRequest(Request.Method.GET, Url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    if (jsonArray.length() != 0) {
                        recyclerView.setVisibility(View.VISIBLE);
                        linearLayout.setVisibility(View.GONE);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            LoanList.add(new Loan(
                                    jsonObject.getString("Titulo"),
                                    jsonObject.getString("FechaLimite")));
                        }
                        Adapter.notifyDataSetChanged();
                    } else {
                        recyclerView.setVisibility(View.GONE);
                        linearLayout.setVisibility(View.VISIBLE);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        requestQueue.add(request);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
