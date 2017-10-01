package com.albertocaro.interfaces;

import com.albertocaro.fragments.AboutFragment;
import com.albertocaro.fragments.AdminFragment;
import com.albertocaro.fragments.BooksFragment;
import com.albertocaro.fragments.FinesFragment;
import com.albertocaro.fragments.HelpFragment;
import com.albertocaro.fragments.LoansFragment;
import com.albertocaro.fragments.MainFragment;

/**
 * Created by beto_ on 01/10/2017.
 */

public interface FragmentsListenerInterface extends MainFragment.OnFragmentInteractionListener,
        BooksFragment.OnFragmentInteractionListener,
        LoansFragment.OnFragmentInteractionListener,
        FinesFragment.OnFragmentInteractionListener,
        HelpFragment.OnFragmentInteractionListener,
        AboutFragment.OnFragmentInteractionListener,
        AdminFragment.OnFragmentInteractionListener {
}
