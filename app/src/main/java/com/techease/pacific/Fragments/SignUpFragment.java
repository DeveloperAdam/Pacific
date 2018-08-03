package com.techease.pacific.Fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

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


public class SignUpFragment extends Fragment {

    EditText etFname,etLname,etEmail,etSetPass,etConfirmPass;
    Button btnSignup;
    LinearLayout tvLogin;
    String strEmail,strSetPass,strConfirmPass,strLname,strFname,id;
    android.support.v7.app.AlertDialog alertDialog;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_sign_up, container, false);


        sharedPreferences = getActivity().getSharedPreferences("abc", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
        etFname=view.findViewById(R.id.etFname);
        etLname=view.findViewById(R.id.etLname);
        etEmail=view.findViewById(R.id.etEmailSignUp);
        etSetPass=view.findViewById(R.id.etSetPass);
        etConfirmPass=view.findViewById(R.id.etConfirmPass);
        btnSignup=view.findViewById(R.id.btnSignup);
        tvLogin=view.findViewById(R.id.txtLogin);
        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                check();
            }
        });

        tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment=new LoginFragment();
                getFragmentManager().beginTransaction().replace(R.id.container,fragment).commit();

            }
        });
        return view;
    }

    private void check() {
        strEmail=etEmail.getText().toString();
        strConfirmPass=etConfirmPass.getText().toString();
        strSetPass=etSetPass.getText().toString();
        strLname=etLname.getText().toString();
        strFname=etFname.getText().toString();

        if (strEmail.equals("") && !strEmail.contains("@"))
        {
            etEmail.setError("Please enter the valid email");
        }
        else
            if (strFname.equals(""))
            {
                etFname.setError("Please fill this field");
            }
            else
            if (strLname.equals(""))
            {
                etLname.setError("Please fill this field");
            }
            else
            if (strSetPass.equals(""))
            {
                etSetPass.setError("Please fill this field");
            }
            else
            if (strConfirmPass.equals("") && strConfirmPass!=strSetPass)
            {
                etConfirmPass.setError("Password does not match");
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
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Api.singup, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.contains("true"))
                {
                    if (alertDialog!=null)
                        alertDialog.dismiss();
                    try {
                        JSONObject jsonObject=new JSONObject(response);
                        JSONObject object=jsonObject.getJSONObject("data");
                        id=object.getString("id");
                        strFname=object.getString("firstname");
                        strLname=object.getString("lastname");
                        strEmail=object.getString("email");
                        editor.putString("id",id).commit();
                        editor.putString("token","login").commit();
                        editor.putString("current",strConfirmPass).commit();
                        editor.putString("name",strFname+" "+strLname).commit();
                        startActivity(new Intent(getActivity(), NavigationActivity.class));
                        getActivity().finish();

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
                        etEmail.setText("");
                        etConfirmPass.setText("");
                        etLname.setText("");
                        etFname.setText("");
                        etSetPass.setText("");
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
                params.put("email",strEmail);
                params.put("password",strConfirmPass);
                params.put("first_name",strFname);
                params.put("last_name",strLname);
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
