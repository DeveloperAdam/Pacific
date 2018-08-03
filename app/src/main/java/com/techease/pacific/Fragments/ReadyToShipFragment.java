package com.techease.pacific.Fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.techease.pacific.Adapters.ShipOrderAdpater;
import com.techease.pacific.Models.ActiveModel;
import com.techease.pacific.Models.ShipOrderModel;
import com.techease.pacific.R;
import com.techease.pacific.Utils.AlertsUtils;
import com.techease.pacific.Utils.Api;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReadyToShipFragment extends Fragment {

    RecyclerView recyclerView;
    List<ShipOrderModel> shipOrderModelList;
    ShipOrderAdpater adpater;
    android.support.v7.app.AlertDialog alertDialog;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    String useriD;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_ready_to_ship, container, false);

        sharedPreferences = getActivity().getSharedPreferences("abc", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        getActivity().setTitle("READY TO SHIP");
        useriD=sharedPreferences.getString("id","");
        recyclerView=view.findViewById(R.id.rvReadyToShip);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        shipOrderModelList=new ArrayList<>();
        if (alertDialog==null)
        {
            alertDialog= AlertsUtils.createProgressDialog(getActivity());
            alertDialog.show();
        }
        apicall();
        adpater=new ShipOrderAdpater(getActivity(),shipOrderModelList);
        recyclerView.setAdapter(adpater);
        return view;
    }

    private void apicall() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Api.readyToShip, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("zmaCOmpleteReturn",response);
                if (response.contains("true"))
                {
                    if (alertDialog!=null)
                        alertDialog.dismiss();
                    try {
                        JSONObject jsonObject=new JSONObject(response);
                        JSONArray jsonArray=jsonObject.getJSONArray("data");
                        for (int i=0; i<jsonArray.length(); i++)
                        {
                            JSONObject object=jsonArray.getJSONObject(i);
                            ShipOrderModel model=new ShipOrderModel();
                            model.setId(object.getString("id"));
                            model.setArrived_date(object.getString("arrived_date"));
                            model.setCustomer(object.getString("customer"));
                            model.setShipment_date(object.getString("shipment_date"));
                            model.setOrder_no(object.getString("order_no"));
                            model.setNotesProduction(object.getString("notes_production"));
                            model.setNotesShipping(object.getString("notes_shipping"));
                            model.setMetrialDescription(object.getString("description"));
                            shipOrderModelList.add(model);
                        }
                        adpater.notifyDataSetChanged();
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
                params.put("userid",useriD);

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
