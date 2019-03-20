package com.android4dev.navigationview;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class RoomActivity extends Act {

    private Toolbar toolbar;
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    ListView lv;
    static ArrayList<String> l1, l2, l3, rooms;
    Comm comm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rooms);


        // Initializing Toolbar and setting it as the actionbar
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        l1 = new ArrayList<>();
        l2 = new ArrayList<>();
        l3 = new ArrayList<>();


        comm = new Comm(this, this);
        comm.readAct("rooms", Vars.key, Vars.val);
        final Context current = this;

        FloatingActionButton fab = (FloatingActionButton)findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(current);
                builder.setTitle("Add event");
                final EditText input = new EditText(current);
                input.setHint("Event description");
                input.setSingleLine(false);  //add this
                input.setLines(4);
                input.setMaxLines(5);
                input.setHorizontalScrollBarEnabled(false); //this
               builder.setView(input);

                builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        l1.add("David Mills");
                        l2.add("Client sneezing");
                        l3.add("Conditions");
                        activityUpdate(l1, l2, l3);
                    }
                });

                builder.setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();

                AlertDialog.Builder dialog = new AlertDialog.Builder(current);
                dialog.setTitle("Set Target Title & Description");
                dialog.setMessage("Title: ");




            }
        });

    }

    @Override
    void activityUpdate(ArrayList<String> s1, ArrayList<String> s2, ArrayList<String> s3) {
        Log.e("CALL", "Called activity update " + s1.size());

        l1 = s1;
        l2 = s2;
        l3 = s3;

        for (int i=0; i<l1.size(); i++) {
            if (l1.get(i).equals("1")) {
                l1.set(i, "David Mills");
            }
        }

        lv = (ListView) findViewById(R.id.listview_ra);

        final CustomAdapter listAdapter = new CustomAdapter(getApplicationContext(), l1, l2, l3);
        lv.setAdapter(listAdapter);
    }
}
