package com.sourcey.materiallogindemo.Fragments;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
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
import com.sourcey.materiallogindemo.MainActivity;
import com.sourcey.materiallogindemo.R;

import java.util.ArrayList;
import java.util.List;

public class myChildrenFragment extends Fragment {
    List<Child> children = new ArrayList<Child>();
    public Fragment myFragment;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        final CustomAdapter adapter;
        View view = inflater.inflate(R.layout.fragment_vaccine_schedule, container, false);

        ListView listView = (ListView) view.findViewById(R.id.listView);
        FloatingActionButton plusActionButton = (FloatingActionButton) view.findViewById(R.id.plusActionButton);

        // <--- Listeleme işlemleri --->

        children = getChildren();

        if (children != null) {
            Log.w("listView", "ListView DOLU. Eklenmiş çocuklar var");
            adapter = new CustomAdapter(this.getActivity(), children);
            listView.setAdapter(adapter);

            plusActionButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.w("fab", "(+) butonuna basildi...");
                    myFragment = new AddChildFragment();
                    setFragment(myFragment);
                }
            });

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Child selectedChild = (Child) parent.getItemAtPosition(position);
//                Toast.makeText(getActivity(), "ID: " + String.valueOf(selectedChild.getId()), Toast.LENGTH_SHORT).show();

                    UpdateChildFragment updateChildFragment = new UpdateChildFragment();
                    Bundle args = new Bundle();
                    args.putInt("id", selectedChild.getId());
                    args.putString("name", selectedChild.getName());
                    args.putString("birthDate", selectedChild.getBirthDate());
                    args.putString("gender", selectedChild.getGender());
                    updateChildFragment.setArguments(args);

                    setFragment(updateChildFragment);
                }
            });

            return view;
        }
        Log.w("listView", "ListView boş! Eklenmiş çocuk yok");

        // Eğer eklenmiş çocuk YOKSA empty_list fragmentini ekrana getir
        setFragment(new EmptyListFragment());
        return view;
//        return inflater.inflate(R.layout.fragment_empty_list, container, false);
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
