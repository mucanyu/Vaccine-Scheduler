package com.sourcey.materiallogindemo.Fragments;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.sourcey.materiallogindemo.DatabaseHelper.DAO;
import com.sourcey.materiallogindemo.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ShowSelectedVaccinesInfoFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_show_selected_vaccines_info, container, false);

        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar_vacInfo);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);

        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFragment(new VaccineScheduleFragment());
            }
        });

        final int child_id = getArguments().getInt("child_id");
        String childName = getArguments().getString("child_name");
        String childGender = getArguments().getString("child_gender");
        int asiGunu = getArguments().getInt("gun");
        String asiAdi = getArguments().getString("asi_adi");
        String hastalikAciklamasi = getArguments().getString("aciklama");
        final int asi_id = getArguments().getInt("asi_id");

        ImageView selectedChildGenderImg = (ImageView) view.findViewById(R.id.imageViewSelectedVacPage_ChildImg);
        TextView selectedChildName = (TextView) view.findViewById(R.id.textViewSelctdVacPage_ChildName);
        TextView selectedVaccineDate = (TextView) view.findViewById(R.id.textViewSelectedPage_vaccineDate);
        Switch selectedSwitch = (Switch) view.findViewById(R.id.switch1);

        TextView selectedVaccineName = (TextView) view.findViewById(R.id.textViewSelctdVacPage_VaccineName);
        TextView selectedVaccineInfo = (TextView) view.findViewById(R.id.textViewSelctdVacPage_VaccineInfo);

        selectedChildName.setText(childName);
        if(childGender.equals("Erkek")) {
            selectedChildGenderImg.setImageResource(R.drawable.boy);

        } else {
            selectedChildGenderImg.setImageResource(R.drawable.girl);
        }
        selectedVaccineName.setText(asiAdi);
        selectedVaccineInfo.setText(hastalikAciklamasi);

        selectedVaccineDate.setText(addDaysToDate(asiGunu));

        if (checkIsVaccinated(child_id, asi_id)) {
            selectedSwitch.setChecked(true);
            selectedSwitch.setText("Yapıldı");
        } else {
            selectedSwitch.setChecked(false);
            selectedSwitch.setText("Yapılmadı");
        }

        selectedSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // do something, the isChecked will be
                // true if the switch is in the On position
                if(isChecked) {
                    Log.w("Switch", "****** switch position was changed to ON " + String.valueOf(child_id) + " - " + String.valueOf(asi_id));
                    buttonView.setText("Yapıldı");
                    changeSwitchStatus(1, asi_id, child_id);
                } else {
                    Log.w("Switch", "****** switch position was changed to OFF! " + String.valueOf(child_id) + " - " + String.valueOf(asi_id));
                    buttonView.setText("Yapılmadı");
                    changeSwitchStatus(0, asi_id, child_id);
                }
            }
        });

        return view;
    }

    private boolean checkIsVaccinated(int child_id, int asi_id) {
        DAO dao = new DAO(getActivity());
        dao.openDB();

        boolean isVaccinated = dao.isVaccinated(child_id, asi_id);

        dao.closeDB();

        return isVaccinated;
    }

    public String addDaysToDate(int amount) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy ");
        Calendar c = Calendar.getInstance();
        try {
            //Setting the date to the given date
            c.setTime(simpleDateFormat.parse(simpleDateFormat.format(c.getTime())));
        } catch(ParseException e){
            e.printStackTrace();
        }
        String strDate = "Current Date : " + simpleDateFormat.format(c.getTime());
        Log.w("Date", " **** Date: " + strDate);

        //Number of Days to add
        c.add(Calendar.DAY_OF_MONTH, amount);
        //Date after adding the days to the given date
        String newDate = simpleDateFormat.format(c.getTime());
        //Displaying the new Date after addition of Days
        Log.w("Date", "Date after Addition: " + newDate);

        return newDate;
    }

    public void changeSwitchStatus(int val, int asi_id, int child_id) {
        DAO dao = new DAO(getActivity());
        dao.openDB();

        dao.changeSwitchCheckedStatus(val, asi_id, child_id);

        dao.closeDB();
    }

    public void setFragment(Fragment fr) {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.fragmentContainer, fr);
        fragmentTransaction.commit();
    }
}
