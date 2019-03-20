package com.android4dev.navigationview;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by KUNAL on 9/9/2016.
 */
public class HomeFragment extends FragAb {

    ListView lv;
    static ArrayList<String> l1, l2, l3, rooms;
    View v;
    String readValue = "patients";
    Comm comm;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Vars.key = "docid";
        Vars.currentActivity = 3;
        v = inflater.inflate(
                R.layout.frament_home, container, false);
        comm = new Comm(this.getActivity(), this);
        comm.read(readValue, Vars.key, Vars.val);

        final Context c = this.getActivity();



        return v;
    }

    @Override
    int kind() {
        return 3;
    }

    @Override
    void update(ArrayList<String> s1, ArrayList<String> s2, ArrayList<String> s3) {
        l1 = s1;
        l2 = s2;
        l3 = s3;

        lv = (ListView) v.findViewById(R.id.listview_home);

        final CustomAdapter listAdapter = new CustomAdapter(this.getActivity(), l1, l2, l3);
        lv.setAdapter(listAdapter);

        if (Vars.patients.isEmpty() && l1.size() > 0) {
            ArrayList<String> names = new ArrayList<>();

            for (int i=0; i<l1.size(); i++) {
                //names.set(i, l1.get(i) + " " + l2.get(i));
                names.add(l1.get(i) + " " + l2.get(i));
                Vars.patients.add(new Patient());
                Vars.patients.get(i).setP_name(names.get(i));
                Log.d("TAG", "Added patient " + names.get(i));
            }
        }
    }

    @Override
    void setAllRooms(ArrayList<String> r) {

    }
}