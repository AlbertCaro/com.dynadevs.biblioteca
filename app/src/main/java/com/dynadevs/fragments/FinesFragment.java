package com.dynadevs.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.dynadevs.activities.R;
import com.dynadevs.adapters.FinesAdapter;
import com.dynadevs.classes.Fine;
import com.dynadevs.classes.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.dynadevs.classes.Utilities.isNetAvailible;
import static com.dynadevs.classes.Utilities.loadSesion;
import static com.dynadevs.classes.Utilities.md5;
import static com.dynadevs.classes.Utilities.setCurrentAccent;
import static com.dynadevs.classes.Utilities.setMessage;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FinesFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FinesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FinesFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private RecyclerView recyclerView;
    private ArrayList<Fine> FineList = new ArrayList<>();
    private ArrayList<Fine> FineQuery = new ArrayList<>();
    private FinesAdapter Adapter;
    private FloatingActionButton fab;
    private LinearLayout linearLayout;
    private ImageView IvMessage;
    private TextView TvMessage;

    public FinesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FinesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FinesFragment newInstance(String param1, String param2) {
        FinesFragment fragment = new FinesFragment();
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
        final View view = inflater.inflate(R.layout.fragment_fines, container, false);
        if (isAdded()) {
            IvMessage = view.findViewById(R.id.ivEmptyFines);
            TvMessage = view.findViewById(R.id.tvEmptyFines);
            recyclerView = view.findViewById(R.id.rvFines);
            recyclerView.setLayoutManager(new GridLayoutManager(container.getContext(),1));
            linearLayout = view.findViewById(R.id.emptyListFines);
            SearchView searchView = getActivity().findViewById(R.id.search);
            searchView.setVisibility(View.VISIBLE);
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    FineQuery.clear();
                    for(int i = 0; i < FineList.size(); i++) {
                        if(FineList.get(i).getTitle().toLowerCase().contains(query.toLowerCase())) {
                            FineQuery.add(FineList.get(i));
                        }
                    }
                    Adapter.setFineList(FineQuery);
                    Adapter.notifyDataSetChanged();
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    FineQuery.clear();
                    for(int i = 0; i < FineList.size(); i++) {
                        if(FineList.get(i).getTitle().toLowerCase().contains(newText.toLowerCase())) {
                            FineQuery.add(FineList.get(i));
                        }
                    }
                    Adapter.setFineList(FineQuery);
                    Adapter.notifyDataSetChanged();
                    return false;
                }
            });
            Bundle bundle = getArguments();
            final User user = (User) bundle.getSerializable("user");
            final SwipeRefreshLayout swipeRefreshLayout = view.findViewById(R.id.srFines);
            setCurrentAccent(swipeRefreshLayout, getActivity(), user);
            swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    if (isNetAvailible(getActivity())){
                        doRequest(user.getCode());
                    } else {
                        IvMessage.setImageResource(R.drawable.ic_not_signal);
                        setMessage(getString(R.string.unavalible_internet), TvMessage, linearLayout, recyclerView);
                    }
                    swipeRefreshLayout.setRefreshing(false);
                }
            });
            fab = getActivity().findViewById(R.id.fab);
            fab.show();
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (isNetAvailible(getActivity())) {
                        Snackbar.make(view, getString(R.string.update_list), Snackbar.LENGTH_SHORT).show();
                        doRequest(user != null ? user.getCode() : loadSesion(getActivity()).getCode());
                    } else {
                        IvMessage.setImageResource(R.drawable.ic_not_signal);
                        setMessage(getString(R.string.unavalible_internet), TvMessage, linearLayout, recyclerView);
                    }
                }
            });
            recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);
                    if (dy < 1)
                        fab.show();
                    else
                        fab.hide();
                }
            });
            if (isNetAvailible(getActivity()))
                doRequest(user != null ? user.getCode() : loadSesion(getActivity()).getCode());
            else {
                IvMessage.setImageResource(R.drawable.ic_not_signal);
                setMessage(getString(R.string.unavalible_internet), TvMessage, linearLayout, recyclerView);
            }
            recyclerView.setHasFixedSize(true);
            Adapter = new FinesAdapter(FineList);
            recyclerView.setAdapter(Adapter);
        }
        return view;
    }

    public void doRequest (String Code) {
        FineList.clear();
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        String url = getString(R.string.server_url)+"biblioteca/rest/multas.php?id="+md5(Code);
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    if (jsonArray.length() != 0) {
                        recyclerView.setVisibility(View.VISIBLE);
                        linearLayout.setVisibility(View.GONE);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            FineList.add(new Fine(
                                    jsonObject.getString("Titulo"),
                                    "$"+jsonObject.getDouble("Monto")));
                        }
                        Adapter.notifyDataSetChanged();
                    } else {
                        IvMessage.setImageResource(R.drawable.ic_empty_fines);
                        setMessage(getString(R.string.empty_fineslist), TvMessage, linearLayout, recyclerView);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error instanceof TimeoutError) {
                    IvMessage.setImageResource(R.drawable.ic_not_signal);
                    setMessage(getString(R.string.unavalible_connection), TvMessage, linearLayout, recyclerView);
                } else {
                    Toast.makeText(getContext(), "VolleyError: "+error, Toast.LENGTH_LONG).show();
                }
            }
        });
        int socketTimeout = 800;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        request.setRetryPolicy(policy);
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
