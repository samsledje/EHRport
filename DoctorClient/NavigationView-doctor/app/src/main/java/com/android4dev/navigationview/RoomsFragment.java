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

import java.util.ArrayList;

/**
 * Created by KUNAL on 9/13/2016.
 */
public class RoomsFragment extends FragAb {

    ListView lv;
    static ArrayList<String> l1, l2, l3;
    View v;
    String readValue = "rooms";


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Vars.key = "patid";
        Vars.currentActivity = 1;
        v = inflater.inflate(R.layout.fragment_rooms, container, false);

        Comm comm = new Comm(this.getActivity(), this);
        comm.read(readValue, Vars.key, Vars.val);

        l1 = new ArrayList<>();
        l2 = new ArrayList<>();
        l3 = new ArrayList<>();

        return v;
    }

    @Override
    int kind() {
        return 1;
    }

    @Override
    void update(ArrayList<String> s1, ArrayList<String> s2, ArrayList<String> s3) {

        l1 = s1;
        l2 = s2;
        l3 = s3;

        for (int i=0; i<l2.size(); i++) {
            if (l2.get(i).equals("0")) {
                l2.set(i, "Open");
            } else if (l2.get(i).equals("1")) {
                l2.set(i, "Closed");
            } else if (l2.get(i).equals("2")) {
                l2.set(i, "Recurring");
            }
        }

        lv = (ListView) v.findViewById(R.id.listview_rooms);
        final CustomAdapter listAdapter = new CustomAdapter(this.getActivity(), l1, l2, l3);
        lv.setAdapter(listAdapter);


    }

    @Override
    void setAllRooms(ArrayList<String> r) {

    }
}
