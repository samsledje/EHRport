package com.android4dev.navigationview;

import android.app.Fragment;

import java.util.ArrayList;

public abstract class FragAb extends android.support.v4.app.Fragment {

    ArrayList<String> l1;
    ArrayList<String> l2;
    ArrayList<String> l3;

    abstract int kind();

    abstract void update(ArrayList<String> s1, ArrayList<String> s2, ArrayList<String> s3);

    abstract void setAllRooms(ArrayList<String> r);
}
