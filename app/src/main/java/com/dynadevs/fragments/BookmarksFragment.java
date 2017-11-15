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
import com.dynadevs.adapters.BooksAdapter;
import com.dynadevs.classes.Book;
import com.dynadevs.classes.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.dynadevs.classes.Utilities.isNetAvailible;
import static com.dynadevs.classes.Utilities.loadSesion;
import static com.dynadevs.classes.Utilities.md5;
import static com.dynadevs.classes.Utilities.setMessage;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link BookmarksFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link BookmarksFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BookmarksFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private ArrayList<Book> BookList = new ArrayList<>();
    private ArrayList<Book> BookQuery = new ArrayList<>();
    private User user;
    private RecyclerView recyclerView;
    private FloatingActionButton fab;
    private LinearLayout linearLayout;
    private TextView TvMessage;
    private ImageView IvMessage;
    BooksAdapter Adapter;

    public BookmarksFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BookmarksFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BookmarksFragment newInstance(String param1, String param2) {
        BookmarksFragment fragment = new BookmarksFragment();
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
        final View view = inflater.inflate(R.layout.fragment_bookmarks, container, false);
        if (isAdded()) {
            IvMessage = view.findViewById(R.id.ivEmptyBookmarks);
            Bundle bundle = getArguments();
            user = (User) bundle.getSerializable("user");
            fab = getActivity().findViewById(R.id.fab);
            fab.setImageResource(R.drawable.ic_refresh);
            fab.show();
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (isNetAvailible(getActivity())){
                        Snackbar.make(view, getString(R.string.update_list), Snackbar.LENGTH_SHORT).show();
                        doRequest(user != null ? user.getCode() : loadSesion(getActivity()).getCode());
                    } else {
                        IvMessage.setImageResource(R.drawable.ic_not_signal);
                        setMessage(getString(R.string.unavalible_internet), TvMessage, linearLayout, recyclerView);
                    }
                }
            });
            TvMessage = view.findViewById(R.id.tvEmptyBookmarks);
            recyclerView = view.findViewById(R.id.rvBookmarks);
            recyclerView.setLayoutManager(new GridLayoutManager(container.getContext(),1));
            linearLayout = view.findViewById(R.id.emptyListBookmark);
            SearchView searchView = getActivity().findViewById(R.id.search);
            searchView.setVisibility(View.VISIBLE);
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    BookQuery.clear();
                    for(int i = 0; i < BookList.size(); i++) {
                        if(BookList.get(i).getTitle().toLowerCase().contains(query.toLowerCase()) ||
                                BookList.get(i).getAutor().toLowerCase().contains(query.toLowerCase()) ||
                                BookList.get(i).getISBN().toLowerCase().contains(query.toLowerCase()) ||
                                BookList.get(i).getEditorial().toLowerCase().contains(query.toLowerCase()))  {
                            BookQuery.add(BookList.get(i));
                        }
                    }
                    Adapter.setBookList(BookQuery);
                    Adapter.notifyDataSetChanged();
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    BookQuery.clear();
                    for(int i = 0; i < BookList.size(); i++) {
                        if(BookList.get(i).getTitle().toLowerCase().contains(newText.toLowerCase()) ||
                                BookList.get(i).getAutor().toLowerCase().contains(newText.toLowerCase()) ||
                                BookList.get(i).getISBN().toLowerCase().contains(newText.toLowerCase()) ||
                                BookList.get(i).getEditorial().toLowerCase().contains(newText.toLowerCase()))  {
                            BookQuery.add(BookList.get(i));
                        }
                    }
                    Adapter.setBookList(BookQuery);
                    Adapter.notifyDataSetChanged();
                    return false;
                }
            });
            Adapter = new BooksAdapter(BookList, user, getContext(), getActivity());
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
            recyclerView.setHasFixedSize(true);
            recyclerView.setAdapter(Adapter);
        }
        return view;
    }

    public void doRequest(String Code) {
        BookList.clear();
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        String url = getString(R.string.server_url)+"biblioteca/rest/marcadores.php?id="+md5(Code);
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray  = new JSONArray(response);
                    if (jsonArray.length() != 0) {
                        recyclerView.setVisibility(View.VISIBLE);
                        linearLayout.setVisibility(View.GONE);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            BookList.add(new Book(
                                    jsonObject.getString("ISBN"),
                                    jsonObject.getString("Titulo"),
                                    jsonObject.getString("Autor"),
                                    jsonObject.getString("Editorial"),
                                    jsonObject.getString("Edicion"),
                                    jsonObject.getString("Descripcion"),
                                    jsonObject.getString("Portada"),
                                    Integer.parseInt(jsonObject.getString("Paginas")),
                                    Integer.parseInt(jsonObject.getString("Ejemplares"))));
                        }
                        Adapter.notifyDataSetChanged();
                    } else {
                        IvMessage.setImageResource(R.drawable.ic_empty_bookmarks);
                        setMessage(getString(R.string.empty_bookmarklist), TvMessage, linearLayout, recyclerView);
                    }
                } catch (JSONException e) {
                    Toast.makeText(getContext(), "Error: "+e, Toast.LENGTH_SHORT).show();
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
    public void onResume() {
        super.onResume();
        if (isNetAvailible(getActivity()))
            doRequest(user != null ? user.getCode() : loadSesion(getActivity()).getCode());
        else {
            IvMessage.setImageResource(R.drawable.ic_not_signal);
            setMessage(getString(R.string.unavalible_internet), TvMessage, linearLayout, recyclerView);
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
