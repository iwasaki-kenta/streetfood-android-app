package hkust.com.bitwise.utils;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import hkust.com.bitwise.R;

public class FragmentUtil {
    public static void gotoFragment(FragmentManager manager, Fragment fragment, boolean backstack, Bundle params) {
        if (params != null) fragment.setArguments(params);

        FragmentTransaction transaction = manager.beginTransaction().replace(R.id.currentFragment, fragment)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);

        if (backstack) transaction = transaction.addToBackStack("");
        else transaction = transaction.disallowAddToBackStack();

        transaction.commit();
    }
}
