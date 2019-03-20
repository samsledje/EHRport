package com.android4dev.navigationview;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Admin on 04-06-2015.
 */
public class DoctorsFragment extends FragAb {

    ListView lv;
    static ArrayList<String> l1, l2, l3, rooms;
    View v;
    String readValue = "doctors";
    Comm comm;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        Vars.currentActivity = 0;

        v = inflater.inflate(R.layout.fragment_doctors,container,false);

        comm = new Comm(this.getActivity(), this);
        comm.read(readValue, Vars.key, Vars.val);


        l1 = new ArrayList<>();
        l2 = new ArrayList<>();
        l3 = new ArrayList<>();

        return v;
    }

    @Override
    int kind() {
        return 0;
    }

    @Override
    void update(ArrayList<String> s1, ArrayList<String> s2, ArrayList<String> s3) {
        /*
        if (Vars.doctors.isEmpty()) {
            for (int i=0; i<l1.size(); i++) {
                for (int j=0; j<3; j++) {
                    switch (j) {
                        case 0:
                            Vars.doctors.get(i).setRooms();
                            break;
                        case 1:
                            Vars.doctors.get(i).setName(l2.get(i));
                            break;

                        case 2:
                            Vars.doctors.get(i).setEmail(l3.get(i));
                            break;
                        default:
                            break;
                    }
                }
            }
        }
        */

        l1 = s1;
        l2 = s2;
        l3 = s3;

        lv = (ListView) v.findViewById(R.id.listview_doctors);

        final CustomAdapter listAdapter = new CustomAdapter(this.getActivity(), l1, l2, l3);
        lv.setAdapter(listAdapter);
    }

    @Override
    void setAllRooms(ArrayList<String> r) {
        rooms = r;
        Log.d("ROOMS", "ROOOOOOMS " + rooms.size());
    }

    public int getNumRooms() {
        return rooms.size();
    }
}
