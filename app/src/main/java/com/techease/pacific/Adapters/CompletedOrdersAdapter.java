package com.techease.pacific.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.techease.pacific.Activities.NavigationActivity;
import com.techease.pacific.Fragments.CompletedOrdersFragment;
import com.techease.pacific.Models.CompletedOrdersModel;
import com.techease.pacific.Models.imageMode;
import com.techease.pacific.R;
import com.techease.pacific.Utils.AlertsUtils;
import com.techease.pacific.Utils.Api;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.microedition.khronos.opengles.GL;

/**
 * Created by Adamnoor on 6/25/2018.
 */

public class CompletedOrdersAdapter extends RecyclerView.Adapter<CompletedOrdersAdapter.ViewHolder> {
    Activity context;
    List<CompletedOrdersModel> modelList;
    String id;
    RecyclerView recyclerView;
    List<imageMode> imageModeList;
    ImageAdapter imageAdapter;
    String ariveDate,shipDate;
    android.support.v7.app.AlertDialog alertDialog;
    TextView tvOrderDetails,tvCustomerDetails,tvNotesProDetails,tvNotesShipDetails,tvM_Des,tvArriveDateDetails,
            tvShipDateDetails,tvDaysLeftDetails;

    public CompletedOrdersAdapter(Activity context, List<CompletedOrdersModel> completedOrdersModelList) {
        this.context=context;
        this.modelList=completedOrdersModelList;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_completedorders,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        final CompletedOrdersModel model=modelList.get(position);
        ariveDate =   changeDateFormat("dd/mm/yyyy","mm/dd/yyyy",model.getArrivDate());
        shipDate =   changeDateFormat("dd/mm/yyyy","mm/dd/yyyy",model.getShipDate());
        holder.tvOrderNo.setText(model.getOrderNo());
        holder.tvArriDate.setText(ariveDate);
        holder.tvShipdate.setText(shipDate);
        holder.tvUser.setText(model.getCustomerName());

        holder.tvSHow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                id=model.getId();
                final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
                LayoutInflater inflater = context.getLayoutInflater();
                View dialogView = inflater.inflate(R.layout.custom_image, null);
                dialogBuilder.setView(dialogView);

                recyclerView=dialogView.findViewById(R.id.rvShowImages);
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
                imageModeList=new ArrayList<>();
                if (alertDialog==null)
                {
                    alertDialog= AlertsUtils.createProgressDialog(context);
                    alertDialog.show();
                }
                apicall();
                imageAdapter=new ImageAdapter(context,imageModeList);
                recyclerView.setAdapter(imageAdapter);
                AlertDialog alertDialogs = dialogBuilder.create();
                alertDialogs.show();
            }
        });


        holder.tvOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
                LayoutInflater inflater = context.getLayoutInflater();
                View dialogView = inflater.inflate(R.layout.custom_active_details, null);
                dialogBuilder.setView(dialogView);

                tvArriveDateDetails=dialogView.findViewById(R.id.tvArrieDateDetails);
                tvShipDateDetails=dialogView.findViewById(R.id.tvShipdateDetails);
                tvCustomerDetails=dialogView.findViewById(R.id.tvCustomerDetails);
                tvNotesProDetails=dialogView.findViewById(R.id.tvNotesProductionDetails);
                tvNotesShipDetails=dialogView.findViewById(R.id.tvNotesShipDetails);
                tvM_Des=dialogView.findViewById(R.id.tvM_Des);
                tvDaysLeftDetails=dialogView.findViewById(R.id.tvDaysLeftDetails);
                tvOrderDetails=dialogView.findViewById(R.id.tvOrderNoDetails);


                tvArriveDateDetails.setText(model.getArrivDate());
                tvShipDateDetails.setText(model.getShipDate());
                tvCustomerDetails.setText(model.getCustomerName());
                tvNotesShipDetails.setText(model.getNotesShippping());
                tvM_Des.setText(model.getMeterialDes());
                tvNotesProDetails.setText(model.getNotesProduction());
                tvOrderDetails.setText(model.getOrderNo());

                dialogBuilder.setNegativeButton("OK", new DialogInterface.OnClickListener() { // define the 'Cancel' button
                    public void onClick(DialogInterface dialog, int which) {
                        //Either of the following two lines should work.
                        dialog.cancel();
                        //dialog.dismiss();
                    }
                });
                AlertDialog alertDialog = dialogBuilder.create();
                alertDialog.show();

            }
        });

        holder.tvOrderNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
                LayoutInflater inflater = context.getLayoutInflater();
                View dialogView = inflater.inflate(R.layout.custom_active_details, null);
                dialogBuilder.setView(dialogView);

                tvArriveDateDetails=dialogView.findViewById(R.id.tvArrieDateDetails);
                tvShipDateDetails=dialogView.findViewById(R.id.tvShipdateDetails);
                tvCustomerDetails=dialogView.findViewById(R.id.tvCustomerDetails);
                tvNotesProDetails=dialogView.findViewById(R.id.tvNotesProductionDetails);
                tvNotesShipDetails=dialogView.findViewById(R.id.tvNotesShipDetails);
                tvM_Des=dialogView.findViewById(R.id.tvM_Des);
                tvDaysLeftDetails=dialogView.findViewById(R.id.tvDaysLeftDetails);
                tvOrderDetails=dialogView.findViewById(R.id.tvOrderNoDetails);


                tvArriveDateDetails.setText(model.getArrivDate());
                tvShipDateDetails.setText(model.getShipDate());
                tvCustomerDetails.setText(model.getCustomerName());
                tvNotesShipDetails.setText(model.getNotesShippping());
                tvM_Des.setText(model.getMeterialDes());
                tvNotesProDetails.setText(model.getNotesProduction());
                tvOrderDetails.setText(model.getOrderNo());

                dialogBuilder.setNegativeButton("OK", new DialogInterface.OnClickListener() { // define the 'Cancel' button
                    public void onClick(DialogInterface dialog, int which) {
                        //Either of the following two lines should work.
                        dialog.cancel();
                        //dialog.dismiss();
                    }
                });
                AlertDialog alertDialog = dialogBuilder.create();
                alertDialog.show();
            }
        });

    }
    private String changeDateFormat(String currentFormat,String requiredFormat,String dateString){
        String result="";
        SimpleDateFormat formatterOld = new SimpleDateFormat(currentFormat, Locale.getDefault());
        SimpleDateFormat formatterNew = new SimpleDateFormat(requiredFormat, Locale.getDefault());
        Date date=null;
        try {
            date = formatterOld.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (date != null) {
            result = formatterNew.format(date);
        }
        return result;
    }

    private void apicall() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Api.getImages, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("zmaShowComplete",response);
                if (response.contains("true"))
                {
                    if (alertDialog!=null)
                        alertDialog.dismiss();
                    try {
                        JSONObject jsonObject=new JSONObject(response);
                        JSONArray array=jsonObject.getJSONArray("data");
                        for (int i=0; i<array.length();i++)
                        {
                            JSONObject object=array.getJSONObject(i);
                            imageMode model=new imageMode();
                            model.setImage(object.getString("file"));
                            imageModeList.add(model);
                        }
                        imageAdapter.notifyDataSetChanged();
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
                        AlertsUtils.showErrorDialog(context,message);

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
                AlertsUtils.showErrorDialog(context,error.getMessage().toString());
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
                params.put("id",id);
                return params;
            }
        };

        RequestQueue mRequestQueue = Volley.newRequestQueue(context);
        stringRequest.setRetryPolicy(new
                DefaultRetryPolicy(200000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        mRequestQueue.add(stringRequest);
    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvOrderNo,tvOrder,tvShipdate,tvArriDate,tvUser,tvSHow;
        SharedPreferences sharedPreferences;
        SharedPreferences.Editor editor;
        public ViewHolder(View itemView) {
            super(itemView);
            sharedPreferences = context.getSharedPreferences("abc", Context.MODE_PRIVATE);
            editor = sharedPreferences.edit();
            tvOrderNo=itemView.findViewById(R.id.tvOrderNoCompletedOrder);
            tvOrder=itemView.findViewById(R.id.tvOrderComplete);
            tvArriDate=itemView.findViewById(R.id.tvArivedDateComOrder);
            tvShipdate=itemView.findViewById(R.id.tvShipDateCompOrder);
            tvUser=itemView.findViewById(R.id.tvUserCompOrder);
            tvSHow=itemView.findViewById(R.id.tvShowImages);
        }
    }
}
