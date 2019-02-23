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
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.sourcey.materiallogindemo.Child;
import com.sourcey.materiallogindemo.CustomAdapter;
import com.sourcey.materiallogindemo.DatabaseHelper.DAO;
import com.sourcey.materiallogindemo.LoggedUser.SharedPref;
import com.sourcey.materiallogindemo.R;

import java.util.ArrayList;
import java.util.List;

public class VaccineScheduleFragment extends Fragment {
    List<Child> children = new ArrayList<Child>();
    public Fragment myFragment;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_vaccine_schedule, container, false);

        ListView listView = (ListView) view.findViewById(R.id.listView);
        FloatingActionButton plusActionButton = (FloatingActionButton) view.findViewById(R.id.plusActionButton);
        final CustomAdapter adapter;

        children = getChildren();
        if (children != null) {
            Log.w("listView", "ListView DOLU. Eklenmiş çocuklar var");

            plusActionButton.hide();
            adapter = new CustomAdapter(this.getActivity(), children);
            listView.setAdapter(adapter);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Child selectedChild = (Child) parent.getItemAtPosition(position);

                    VaccinesInformationFragment vaccinesInformationFragment = new VaccinesInformationFragment();
                    Bundle args = new Bundle();
                    args.putInt("child_id", selectedChild.getId());
                    args.putString("child_name", selectedChild.getName());
                    args.putString("child_gender", selectedChild.getGender());
                    vaccinesInformationFragment.setArguments(args);

                    setFragment(vaccinesInformationFragment);
                }
            });

            return view;
        }
        Log.w("listView", "ListView boş! Eklenmiş çocuk yok");

        EmptyListFragment emptyListFragment = new EmptyListFragment();
        Bundle args = new Bundle();
        args.putString("who", "VaccineScheduleFragment");
        emptyListFragment.setArguments(args);

        setFragment(emptyListFragment);
//        return inflater.inflate(R.layout.fragment_empty_list, container, false);
        return view;
    }

    public List<Child> getChildren() {
        List<Child> children;
        DAO dao = new DAO(getActivity());
        dao.openDB();

        SharedPref.context = getActivity();
        int parent_id = SharedPref.getMyInstance().getId();

        children = dao.getChildrenList(parent_id);

        if (children.size() > 0) {
            Log.w("x", "*********** Size > 0 ");
            dao.closeDB();
            return children;
        } else {
            Log.w("x", "*********** Size <<< 0 ");
            dao.closeDB();
            return null;
        }
    }

    public void setFragment(Fragment fr) {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.fragmentContainer, fr);
        fragmentTransaction.commit();
    }
}
