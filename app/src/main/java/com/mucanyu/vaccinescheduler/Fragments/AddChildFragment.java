package com.sourcey.materiallogindemo.Fragments;

import android.app.DatePickerDialog;
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
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.sourcey.materiallogindemo.Child;
import com.sourcey.materiallogindemo.DatabaseHelper.DAO;
import com.sourcey.materiallogindemo.LoggedUser.SharedPref;
import com.sourcey.materiallogindemo.R;

import java.util.Calendar;

public class AddChildFragment extends Fragment {
    TextView childName;
    TextView dogumTarihi;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_add_child, container, false);

        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar_vacInfo);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);

        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFragment(new myChildrenFragment());
            }
        });

        childName = (TextView) view.findViewById(R.id.textViewAddName);
        dogumTarihi = (TextView) view.findViewById(R.id.textViewAddDateText);

        // Tarih seçme ve butonları
        Button tarihSec = (Button) view.findViewById(R.id.btnAddSelectDate);

        // RadioButtonlar
        final RadioGroup radioGroup = (RadioGroup) view.findViewById(R.id.radioGrpAdd);
        RadioButton checkedRadioButton = (RadioButton) radioGroup.findViewById(radioGroup.getCheckedRadioButtonId());
        final ImageView babyImage = (ImageView) view.findViewById(R.id.imageViewAddUserPicture);

        // Kaydet button
        Button btnKaydet = (Button) view.findViewById(R.id.btnAddKaydet);

        btnKaydet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Database transactions...
                String gender;
                int selectedItemId = radioGroup.getCheckedRadioButtonId();
                Log.w("Kaydet", "**** selectedItemId: " + String.valueOf(selectedItemId));

                if (selectedItemId != -1 && validate()) {
                    Log.w("radioButton", "Basariyla itemi aldi!");
                    RadioButton selectedRadioButton = (RadioButton) view.findViewById(radioGroup.getCheckedRadioButtonId());

                    gender = selectedRadioButton.getText().toString();

                    SharedPref.context = getActivity().getApplicationContext();
                    int parentId = SharedPref.getMyInstance().getId();

                    Child child = new Child(childName.getText().toString(), dogumTarihi.getText().toString(), gender, parentId);
                    insertChild(child);
                    Log.w("Yeni_cocuk", "********* Yeni cocuk eklendi ve IDsi:" + String.valueOf(child.getId()));

                    addVaccinesForChild(child.getId());
                    setFragment(new myChildrenFragment());
                } else {
                    Toast.makeText(getActivity(), "Tüm alanları doldurmalısınız!" ,Toast.LENGTH_SHORT).show();
                    Log.w("radioButton", "HATA: Secili radio buttonun ID'si -1");
                }
            }
        });

        tarihSec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                                if (year > calendar.get(Calendar.YEAR) ) {
                                    dogumTarihi.setError("Geçerli bir tarih giriniz");
                                }
                                dogumTarihi.setText(day + "/" + (month + 1) + "/" + year);
                            }
                        }, year, month, dayOfMonth);

                // Seçebileceği en ileri tarih bugünden ötesi olamayacak
                datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
                datePickerDialog.show();
            }
        });

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton checkedRadioButton = (RadioButton) group.findViewById(checkedId);
                boolean isChecked = checkedRadioButton.isChecked();

                if (isChecked) {
                    if (checkedRadioButton.getText().equals("Erkek")) {
                        babyImage.setImageResource(R.drawable.add_boy);
                    } else {
                        babyImage.setImageResource(R.drawable.add_girl);
                    }
                }
            }
        });

        return  view;
    }

    private void addVaccinesForChild(int child_id) {
        DAO dao = new DAO(getActivity());
        dao.openDB();

        dao.addVaccinesForChild(child_id);

        dao.closeDB();
    }

    public void insertChild(Child child) {
        DAO dao = new DAO(getActivity());
        dao.openDB();

        int childId = dao.addChild(child.getName(), child.getBirthDate(), child.getGender(), child.getParent_id());
        child.setId(childId);

        dao.closeDB();
    }

    // Validate checks whether name and birth date field is empty
    public boolean validate() {
        if (childName.getText().toString().equals("")) {
            return false;
        } else if (dogumTarihi.getText().toString().equals("")) {
            return  false;
        } else {
            return true;
        }
    }

    public void setFragment(Fragment fr) {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.fragmentContainer, fr);
        fragmentTransaction.commit();
    }
}
