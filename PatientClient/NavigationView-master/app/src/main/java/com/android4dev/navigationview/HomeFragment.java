package com.android4dev.navigationview;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;

/**
 * Created by KUNAL on 9/9/2016.
 */
public class HomeFragment extends FragAb {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(
                R.layout.frament_home, container, false);
        Comm comm = new Comm(this.getActivity(), this);
        //comm.read("rooms");

        return v;
    }

    @Override
    int kind() {
        return 1;
    }

    @Override
    void update(ArrayList<String> s1, ArrayList<String> s2, ArrayList<String> s3) {

    }

    @Override
    void setAllRooms(ArrayList<String> r) {

    }
/*
    @Override
    void update(String str) {

    }*/
}