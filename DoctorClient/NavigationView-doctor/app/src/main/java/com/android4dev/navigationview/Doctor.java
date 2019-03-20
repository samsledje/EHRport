package com.android4dev.navigationview;

import java.util.ArrayList;

public class Doctor {
    String name, email;
    ArrayList<String> rooms;

    public Doctor(String n, String e, ArrayList<String> r) {
        name = n;
        rooms = r;
        email = e;
    }

    public int numRooms() {
        return rooms.size();
    }

    public String getName() {
        return "";
    }

    public void setName(String n) {
        name = n;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public ArrayList<String> getRooms() {
        return rooms;
    }

    public void setRooms(ArrayList<String> rooms) {
        this.rooms = rooms;
    }
}
