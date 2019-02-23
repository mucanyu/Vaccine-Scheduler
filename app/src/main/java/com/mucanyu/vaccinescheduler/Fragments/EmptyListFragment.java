package com.sourcey.materiallogindemo.Fragments;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sourcey.materiallogindemo.R;

public class EmptyListFragment extends Fragment {
    public Fragment myFragment;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_empty_list, container, false);

        FloatingActionButton plusActionButton = (FloatingActionButton) view.findViewById(R.id.plusActionButton);
        if (getArguments() != null) {
            plusActionButton.hide();
        }

        plusActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.w("fab", "(+) butonuna basildi...");
                myFragment = new AddChildFragment();
                setFragment(myFragment);
            }
        });

        return view;
    }

    public void setFragment(Fragment fr) {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.fragmentContainer, fr);
        fragmentTransaction.commit();
    }
}

