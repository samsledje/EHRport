package com.android4dev.navigationview;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/*
 * Custom list adapter to display all messages
 */
class CustomAdapter extends BaseAdapter {

    Context context;
    ArrayList<String> data;
    ArrayList<String> data2;
    ArrayList<String> data3;
    private static LayoutInflater inflater = null;

    public CustomAdapter(Context context, ArrayList<String> d, ArrayList<String> d2, ArrayList<String> d3) {
        this.context = context;
        data = d;
        data2 = d2;
        data3 = d3;
        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        View vi = convertView;
        if (vi == null)
            vi = inflater.inflate(R.layout.row, null);

        // first
        TextView firstText = (TextView) vi.findViewById(R.id.tv_one);
        firstText.setText(data.get(position));
        // second
        TextView secondText = (TextView) vi.findViewById(R.id.tv_two);
        secondText.setText(data2.get(position));
        // third
        if (data3 != null && data3.size() >= position + 1) {
            TextView thirdText = (TextView) vi.findViewById(R.id.tv_three);
            thirdText.setText(data3.get(position));
        }
        final String s = vi.toString();
        final Button button = (Button) vi.findViewById(R.id.button);
        switch (Vars.currentActivity) {
            case 0: // doctors
                button.setText("Edit");
                break;
            case 1: // rooms
                button.setText("View");
                break;
            case 2: // boards
                button.setText("View");
                break;
            default:
                button.setVisibility(View.INVISIBLE);
                break;
        }

        final Context c = this.context;

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Perform action
                Log.d("---", "Button pressed: " + s);
                switch (Vars.currentActivity) {
                    case 0: // doctors
                        // num rooms

                        AlertDialog.Builder b = new AlertDialog.Builder(context);
                        b.setTitle("Remove access from room:");
                        // All the messages
                        final String[] messages = {"Earache", "Toothache"};

                        String[] rooms;
                        DoctorsFragment f = new DoctorsFragment();
                        Comm comm = new Comm(c, f);
                        int pos = position+1;
                        comm.read("doctors?id="+pos, Vars.key, Vars.val);
                        Log.d("PARSING", "PARSING NOW");

                        b.setItems(messages, new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();

                                // Use current time in ms to create a unique message header that
                                // can also be used for sorting.
                                Toast.makeText(context, "Preferences changed", Toast.LENGTH_SHORT).show();
                            }

                        });

                        b.show();
                        break;
                    case 1: // rooms
                        // view
                        break;
                    case 2: // boards
                        //
                        break;
                    default:
                        break;
                }
            }
        });

        return vi;
    }
}