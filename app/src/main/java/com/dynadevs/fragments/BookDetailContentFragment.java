package com.dynadevs.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.dynadevs.activities.R;
import com.dynadevs.classes.Book;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link BookDetailContentFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link BookDetailContentFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BookDetailContentFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private TextView TvCopies;

    public BookDetailContentFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BookDetailContentFragment.
     */
    public static BookDetailContentFragment newInstance(String param1, String param2) {
        BookDetailContentFragment fragment = new BookDetailContentFragment();
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

    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_book_detail_content, container, false);
        TextView tvISBN = view.findViewById(R.id.tvISBN);
        TextView tvAutor = view.findViewById(R.id.tvAutor);
        TextView tvEditorial = view.findViewById(R.id.tvEditorial);
        TextView tvEdition = view.findViewById(R.id.tvEdition);
        TextView tvDescription = view.findViewById(R.id.tvDescription);
        TextView tvPages = view.findViewById(R.id.tvPages);
        TvCopies = view.findViewById(R.id.tvCopies);

        Bundle ObjectBook = getArguments();
        final Book book;

        if (getArguments() != null) {
            book = (Book) ObjectBook.getSerializable("book");

            tvISBN.setText(getString(R.string.detail_ISBN)+" "+book.getISBN());
            tvAutor.setText(book.getAutor());
            tvEditorial.setText(getString(R.string.detail_editorial)+" "+book.getEditorial());
            tvEdition.setText(getString(R.string.detail_edition)+" "+book.getEdition());
            tvDescription.setText(book.getDescription());
            tvPages.setText(getString(R.string.detail_pages)+" "+book.getPages());

            RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
            String Url = getString(R.string.server_url)+"biblioteca/rest/ejemplares_disp.php?id="+book.getISBN();

            StringRequest request = new StringRequest(Request.Method.GET, Url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        for(int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            TvCopies.setText(getString(R.string.detail_copies)+" "+jsonObject.getString("Disponibles")+"/"+book.getCopies());
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

        return view;
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
