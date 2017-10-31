package com.dynadevs.interfaces;

import com.dynadevs.fragments.AboutFragment;
import com.dynadevs.fragments.BooksFragment;
import com.dynadevs.fragments.FinesFragment;
import com.dynadevs.fragments.HelpFragment;
import com.dynadevs.fragments.LoansFragment;
import com.dynadevs.fragments.MainFragment;

/**
 * Created by beto_ on 01/10/2017.
 */

public interface FragmentsListenerInterface extends MainFragment.OnFragmentInteractionListener,
        BooksFragment.OnFragmentInteractionListener,
        LoansFragment.OnFragmentInteractionListener,
        FinesFragment.OnFragmentInteractionListener,
        HelpFragment.OnFragmentInteractionListener,
        AboutFragment.OnFragmentInteractionListener {
}
