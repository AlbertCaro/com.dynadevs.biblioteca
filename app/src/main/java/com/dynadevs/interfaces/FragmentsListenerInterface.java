package com.dynadevs.interfaces;

import com.dynadevs.fragments.BookmarksFragment;
import com.dynadevs.fragments.BooksFragment;
import com.dynadevs.fragments.FinesFragment;
import com.dynadevs.fragments.LoansFragment;
import com.dynadevs.fragments.MainFragment;

/**
 * Created by Alberto Caro Navarro on 01/10/2017.
 * albertcaronava@gmail.com
 */

public interface FragmentsListenerInterface extends MainFragment.OnFragmentInteractionListener,
        BooksFragment.OnFragmentInteractionListener,
        BookmarksFragment.OnFragmentInteractionListener,
        LoansFragment.OnFragmentInteractionListener,
        FinesFragment.OnFragmentInteractionListener {
}
