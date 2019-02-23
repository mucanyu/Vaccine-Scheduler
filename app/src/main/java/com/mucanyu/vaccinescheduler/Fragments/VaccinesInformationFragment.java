package com.sourcey.materiallogindemo.Fragments;

import android.app.ActionBar;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v7.widget.Toolbar;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.sourcey.materiallogindemo.AsiTablosuDAO;
import com.sourcey.materiallogindemo.BilgilerDAO;
import com.sourcey.materiallogindemo.Child;
import com.sourcey.materiallogindemo.DatabaseHelper.DAO;
import com.sourcey.materiallogindemo.R;
import com.sourcey.materiallogindemo.VacsInfoListViewAdapter;

import java.util.ArrayList;
import java.util.List;

public class VaccinesInformationFragment extends Fragment {
    Toolbar toolbar;
    List<BilgilerDAO> bilgiler = new ArrayList<BilgilerDAO>();
    public Fragment myFragment;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_vaccines_information, container, false);

        final int child_id = getArguments().getInt("child_id");
        final String child_name = getArguments().getString("child_name");
        final String child_gender = getArguments().getString("child_gender");

        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar_vacInfo);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);

        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFragment(new VaccineScheduleFragment());
            }
        });
//        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Merhaba!");

        ListView listView = (ListView) view.findViewById(R.id.infoListView);
        final VacsInfoListViewAdapter adapter;

        bilgiler = getBilgiler();

        if (bilgiler != null) {
            Log.w("BilgilerListView", "*** Bilgilerin ListView dolu. Ekli bilgiler tespit edildi!");
            adapter = new VacsInfoListViewAdapter(this.getActivity(), bilgiler, child_id);
            listView.setAdapter(adapter);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    BilgilerDAO selectedVaccine = (BilgilerDAO) parent.getItemAtPosition(position);

                    ShowSelectedVaccinesInfoFragment ssVacInfoFrag = new ShowSelectedVaccinesInfoFragment();
                    Bundle args = new Bundle();
                    args.putInt("child_id", child_id);
                    args.putString("child_name", child_name);
                    args.putString("child_gender", child_gender);
                    args.putString("asi_adi", selectedVaccine.getAsi_ismi());
                    args.putString("aciklama", selectedVaccine.getAciklama());
                    args.putInt("gun", selectedVaccine.getAsi_Gunu());
                    args.putInt("asi_id", selectedVaccine.getBilgiler_id());
                    ssVacInfoFrag.setArguments(args);

                    setFragment(ssVacInfoFrag);
                }
            });

            return view;
        }
        Log.w("listView", "ListView boş! Eklenmiş çocuk yok");

        // Eğer eklenmiş çocuk YOKSA empty_list fragmentini ekrana getir
        setFragment(new EmptyListFragment());
        return view;
    }

    private boolean checkIsVaccinated(int child_id, int asi_id) {
        DAO dao = new DAO(getActivity());
        dao.openDB();

        boolean isVaccinated = dao.isVaccinated(child_id, asi_id);

        dao.closeDB();

        return isVaccinated;
    }

    private List<BilgilerDAO> getBilgiler() {
        List<BilgilerDAO> bilgilerList;
        DAO dao = new DAO(getActivity());
        dao.openDB();

        bilgilerList = dao.getBilgilerList();

        if (bilgilerList.size() > 0) {
            Log.w("x", "*********** bilgilerList Size > 0 ");
            dao.closeDB();
            return bilgilerList;
        } else {
            Log.w("x", "*********** bilgilerList Size <<< 0 ");
            dao.closeDB();
            return null;
        }
    }

    public List<AsiTablosuDAO> getAsiTablosu(int child_id) {
//        List<AsiTablosuDAO> asiTablosu;
//        DAO dao = new DAO(getActivity());
//        dao.openDB();
//
//        asiTablosu = dao.getAsiTablosuList(child_id);
//
//        if (asiTablosu.size() > 0) {
//            Log.w("x", "*********** Size > 0 ");
//            dao.closeDB();
//            return asiTablosu;
//        } else {
//            Log.w("x", "*********** Size <<< 0 ");
//            dao.closeDB();
//            return null;
//        }

        // ----- SİL -----
        return null;
    }

    public void setFragment(Fragment fr) {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.fragmentContainer, fr);
        fragmentTransaction.commit();
    }
}
