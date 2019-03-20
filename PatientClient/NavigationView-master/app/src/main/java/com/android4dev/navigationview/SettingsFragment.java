package com.android4dev.navigationview;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by KUNAL on 9/13/2016.
 */
public class SettingsFragment extends FragAb {

    String s = "";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate
                (R.layout.fragment_settings, container, false);


        return v;
    }

    @Override
    int kind() {
        return 3;
    }

    @Override
    void update(ArrayList<String> s1, ArrayList<String> s2, ArrayList<String> s3) {

    }

    @Override
    void setAllRooms(ArrayList<String> r) {


    }
}
