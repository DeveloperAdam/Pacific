package com.techease.pacific.Fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.techease.pacific.Activities.NavigationActivity;
import com.techease.pacific.R;
import com.techease.pacific.Utils.AlertsUtils;
import com.techease.pacific.Utils.Api;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class SettingFragment extends Fragment {

    EditText etCurrentPass,etNewPass,etCNewPass;
    Button btnUpdate;
    String strCurrent,strNew,strConfirm,getCurrentPass,userId;
    android.support.v7.app.AlertDialog alertDialog;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_setting, container, false);

        sharedPreferences = getActivity().getSharedPreferences("abc", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        etCurrentPass=view.findViewById(R.id.etCurrentPass);
        etNewPass=view.findViewById(R.id.etNewPass);
        etCNewPass=view.findViewById(R.id.etConfirmPassNewPass);
        btnUpdate=view.findViewById(R.id.btnUpdatePass);

        getCurrentPass=sharedPreferences.getString("current","");
        userId=sharedPreferences.getString("id","");


        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                check();
            }
        });
        return view;
    }

    private void check() {
        strCurrent=etCurrentPass.getText().toString();
        strNew=etNewPass.getText().toString();
        strConfirm=etCurrentPass.getText().toString();

        if (!strCurrent.equals(getCurrentPass) && strCurrent.equals(""))
        {
            etCurrentPass.setError("Please enter valid password");
        }
        else
            if (strNew.equals(""))
            {
                etNewPass.setError("Please fill this field");
            }
            else
                if (strConfirm.equals("") && strConfirm!=strNew)
                {
                    etCNewPass.setError("Password does not match");
                }
                else
                {
                    if (alertDialog==null)
                    {
                        alertDialog= AlertsUtils.createProgressDialog(getActivity());
                        alertDialog.show();
                    }
                    apicall();
                }

    }
    private void apicall() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Api.passChange, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.contains("true"))
                {
                    if (alertDialog!=null)
                        alertDialog.dismiss();
                    try {
                        JSONObject jsonObject=new JSONObject(response);
                        String message=jsonObject.getString("message");
                        editor.putString("current",strConfirm).commit();
                        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
                else
                {

                    if (alertDialog!=null)
                        alertDialog.dismiss();
                    try {
                        JSONObject object=new JSONObject(response);
                        String message=object.getString("message");
                        AlertsUtils.showErrorDialog(getActivity(),message);
                        etNewPass.setText("");
                        etCNewPass.setText("");
                        etCurrentPass.setText("");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }



            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (alertDialog!=null)
                    alertDialog.dismiss();
                AlertsUtils.showErrorDialog(getActivity(),error.getMessage().toString());
                Log.d("zma error", String.valueOf(error.getCause()));
            }
        }) {
            @Override
            public String getBodyContentType() {
                return "application/x-www-form-urlencoded;charset=UTF-8";
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("userid",userId);
                params.put("current_password",strCurrent);
                params.put("new_password",strConfirm);
                return params;
            }
        };

        RequestQueue mRequestQueue = Volley.newRequestQueue(getActivity());
        stringRequest.setRetryPolicy(new
                DefaultRetryPolicy(200000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        mRequestQueue.add(stringRequest);
    }

}
