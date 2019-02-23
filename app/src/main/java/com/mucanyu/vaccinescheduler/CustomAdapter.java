package com.sourcey.materiallogindemo;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class CustomAdapter extends BaseAdapter {
    private LayoutInflater childInflater;
    private List<Child> childList;

    public CustomAdapter(Activity activity, List<Child> childList) {
        childInflater = (LayoutInflater) activity.getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);
        this.childList = childList;
    }

    @Override
    public int getCount() {
        return childList.size();
    }

    @Override
    public Object getItem(int i) {
        return childList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View lineView;
        lineView = childInflater.inflate(R.layout.custom_viewgroup, null);
        TextView textViewUserName = (TextView) lineView.findViewById(R.id.textViewUserName);
        ImageView imageViewUserPicture = (ImageView) lineView.findViewById(R.id.imageViewUserPicture);

        Child child = childList.get(i);
        textViewUserName.setText(child.getName());

        if (child.getGender().equals("Erkek")) {
            imageViewUserPicture.setImageResource(R.drawable.boy);
        } else {
            imageViewUserPicture.setImageResource(R.drawable.girl);
        }

        return lineView;
    }
}
