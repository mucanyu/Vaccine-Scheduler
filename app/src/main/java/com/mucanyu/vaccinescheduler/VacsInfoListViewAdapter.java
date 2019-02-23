package com.sourcey.materiallogindemo;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.sourcey.materiallogindemo.DatabaseHelper.DAO;

import java.util.List;

public class VacsInfoListViewAdapter extends BaseAdapter {

    private LayoutInflater bilgilerInflater;
    private List<BilgilerDAO> bilgilerDAOList;
    private int child_id;

    public VacsInfoListViewAdapter(Activity activity, List<BilgilerDAO> bilgilerDAOList, int child_id) {
        bilgilerInflater = (LayoutInflater) activity.getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);
        this.bilgilerDAOList = bilgilerDAOList;
        this.child_id = child_id;
    }

    @Override
    public int getCount() {
        return bilgilerDAOList.size();
    }

    @Override
    public Object getItem(int i) {
        return bilgilerDAOList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View lineView;
        lineView = bilgilerInflater.inflate(R.layout.custom_info_view, null);

        ImageView isVaccinatedImg = (ImageView) lineView.findViewById(R.id.ImageViewisVaccinated);
        TextView vaccineName = (TextView) lineView.findViewById(R.id.textViewVaccineName);

        BilgilerDAO myBilgilerList = bilgilerDAOList.get(i);
        vaccineName.setText(myBilgilerList.getAsi_ismi());
//        isVaccinatedImg.setImageResource(R.drawable.vaccine_icon);

        // Object'ten veri çekmek için
        // myBilgilerList.isAsiYapildiMi();

        DAO dao = new DAO(viewGroup.getContext());
        dao.openDB();
        if (dao.isVaccinated(child_id, myBilgilerList.getBilgiler_id())) {
            isVaccinatedImg.setImageResource(R.drawable.tick_mark);
        } else {
            isVaccinatedImg.setImageResource(R.drawable.x_mark);
        }
        dao.closeDB();

        return lineView;
    }
}
