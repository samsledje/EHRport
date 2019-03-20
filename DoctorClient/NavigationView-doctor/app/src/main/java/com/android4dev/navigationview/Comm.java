package com.android4dev.navigationview;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Comm {

    //String url = "http://50.28.167.71:8080/";
    String url = "http://67.221.89.61:8080/";
    RequestQueue queue;
    StringRequest stringRequest;
    String returnData = "NONE";
    String TAG = "COMM%";
    Context context;
    FragAb fragment;
    Act activity;
    int kind;

    public Comm(Context c, FragAb f) {
        context = c;
        fragment = f;
        kind = fragment.kind();
    }

    public Comm(Context c, Act a) {
        context = c;
        activity = a;
        kind = 4;
    }

    public void readAct(String data, String k, String v) {
        Log.d("Parse", "" + data + ",\t" + k + ", " + v);

        final String key = k;
        final String value = v;

        queue = Volley.newRequestQueue(context);

        String extra = "";
        if (kind == 4) {
            extra = "?id=1";
        }
        final String fullUrl = url + data + extra;

        Log.d(TAG, "Reading at: " + fullUrl);

        stringRequest = new StringRequest(Request.Method.GET, fullUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        int responseLength = response.length();
                        returnData = response.substring(0,responseLength);
                        Log.d(TAG, "data: "+returnData);

                        readRooms(returnData);

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "UNABLE TO READ");
            }
        })

        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put(key, value);
                return params;
            }
        };
        /*
        try {
            returnData = stringRequest.getHeaders().toString();
        } catch (AuthFailureError authFailureError) {
            authFailureError.printStackTrace();
        }*/

        queue.add(stringRequest);
    }



    public void read(String data, String k, String v) {

        Log.d("Parse", "" + data + ",\t" + k + ", " + v);

        final String key = k;
        final String value = v;

        queue = Volley.newRequestQueue(context);

        final String fullUrl = url + data;

        Log.d(TAG, "Reading at: " + fullUrl);

        stringRequest = new StringRequest(Request.Method.GET, fullUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        int responseLength = response.length();
                        returnData = response.substring(0,responseLength);
                        Log.d(TAG, "data: "+returnData);
                        if (fullUrl.contains("?")) {
                            specialJSONdoctors(returnData);
                        } else {
                            doJSONarray(returnData);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "UNABLE TO READ");
            }
        })

        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put(key, value);
                return params;
            }
        };
        /*
        try {
            returnData = stringRequest.getHeaders().toString();
        } catch (AuthFailureError authFailureError) {
            authFailureError.printStackTrace();
        }*/

        queue.add(stringRequest);
    }

    public void write(String data) {

    }

    public void specialJSONdoctors(String s) {
        ArrayList<String> rooms = new ArrayList<>();
        try {
            //JSONObject obj = new JSONObject(s);
            //String pageName = obj.getJSONObject("id").getString("pageName");
            Log.d("JSON","Starting parse");
            JSONArray arr = new JSONArray(s);
            for (int i = 0; i < arr.length(); i++)
            {
                String str = arr.getString(i);
                //String str = arr.getJSONObject(i).getString("");
                Log.d("JSON","Parsed: "+str);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        fragment.setAllRooms(rooms);
    }

    public void readRooms(String s) {
        doJSONarray(s);
    }

    public void doJSONarray(String s) {
        String[] field = new String[3];
        switch(kind) {
            case 0: // doctors
                field[0] = "title";
                field[1] = "first";
                field[2] = "email";
                break;
            case 1: // rooms
                field[0] = "title";
                field[1] = "activity";
                field[2] = "create_time";
                break;
            case 2: // boards
                field[0] = "tag";
                field[1] = "tag";
                field[2] = "tag";
                break;
            case 3: // doc
                field[0] = "firstname";
                field[1] = "lastname";
                field[2] = "email";
                break;
            case 4: // events
                field[0] = "doctor";
                field[1] = "contents";
                field[2] = "tags";
                break;
            default:
                break;
        }

        ArrayList<String> r1 = new ArrayList<>();
        ArrayList<String> r2 = new ArrayList<>();
        ArrayList<String> r3 = new ArrayList<>();

        Log.d("TAG", "READING THE ROOMS FOR LIST");

        try {
            //JSONObject obj = new JSONObject(s);
            //String pageName = obj.getJSONObject("id").getString("pageName");
            Log.d("JSON","Starting parse");
            JSONArray arr = new JSONArray(s);
            for (int l=0; l<3; l++) {
                if (field[l].equals("")) {
                    //break;
                }
                for (int i = 0; i < arr.length(); i++)
                {
                    String str = arr.getJSONObject(i).getString(""+field[l]);
                    if (kind == 0 && l == 1) {
                        str += " " + arr.getJSONObject(i).getString("last");
                    }
                    Log.d("JSON","Parsed: "+str);
                    switch (l) {
                        case 0:
                            r1.add(str);
                            break;
                        case 1:
                            r2.add(str);
                            break;
                        case 2:
                            r3.add(str);
                            break;
                        default:
                            break;
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if (kind == 4) {
            activity.activityUpdate(r1, r2, r3);
        } else {
            fragment.update(r1, r2, r3);
        }
    }


}
