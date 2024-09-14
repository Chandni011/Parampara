package com.ambitious.parampara.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.ambitious.parampara.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class Registration_mainLayout extends Fragment {
    View view;
    private static FragmentManager fm;


    public Registration_mainLayout() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_registration_main_layout, container, false);
        fm = getFragmentManager();
        addFragment(new RegistrationFragment(), false);
        return view;
    }
    public static void addFragment(Fragment fragment, boolean addToBackStack) {
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.box, fragment, "");
        if (addToBackStack)
            transaction.addToBackStack(null);
        transaction.commit();
    }
}
