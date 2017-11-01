package com.dynadevs.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dynadevs.activities.R;
import com.dynadevs.classes.Book;

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

    TextView TvISBN, TvAutor, TvEditorial, TvDescription, TvEdition, TvPages, TvCopies;

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
    // TODO: Rename and change types and number of parameters
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_book_detail_content, container, false);
        TvISBN = view.findViewById(R.id.tvISBN);
        TvAutor = view.findViewById(R.id.tvAutor);
        TvEditorial = view.findViewById(R.id.tvEditorial);
        TvEdition = view.findViewById(R.id.tvEdition);
        TvDescription = view.findViewById(R.id.tvDescription);
        TvPages = view.findViewById(R.id.tvPages);
        TvCopies = view.findViewById(R.id.tvCopies);

        Bundle ObjectBook = getArguments();
        Book book;

        if (getArguments() != null) {
            book = (Book) ObjectBook.getSerializable("Object");

            TvISBN.setText(getString(R.string.detail_ISBN)+" "+book.getISBN());
            TvAutor.setText(book.getAutor());
            TvEditorial.setText(getString(R.string.detail_editorial)+" "+book.getEditorial());
            TvEdition.setText(getString(R.string.detail_edition)+" "+book.getEdition());
            TvDescription.setText(book.getDescription());
            TvPages.setText(getString(R.string.detail_pages)+" "+book.getPages());
            TvCopies.setText(getString(R.string.detail_copies)+" "+book.getCopies());
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        getActivity().finish();
    }
}