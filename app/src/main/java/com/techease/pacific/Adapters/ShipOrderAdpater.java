package com.techease.pacific.Adapters;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
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
import com.techease.pacific.Fragments.ActiveOrdersFragment;
import com.techease.pacific.Fragments.CompletedOrdersFragment;
import com.techease.pacific.Fragments.ReadyToShipFragment;
import com.techease.pacific.Fragments.UploadFragment;
import com.techease.pacific.Models.ShipOrderModel;
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

/**
 * Created by Adamnoor on 7/9/2018.
 */

public class ShipOrderAdpater extends RecyclerView.Adapter <ShipOrderAdpater.ViewHolder>{
    List<ShipOrderModel> shipOrderModelList;
    String strNotesProduction="",id;
    Activity activity;
    RecyclerView recyclerView;
    ImageAdapter imageAdapter;
    EditText editText;
    List<imageMode> imageModeList;
    String ariveDate,shipDate;
    android.support.v7.app.AlertDialog alertDialogs;
    TextView tvOrderDetails,tvCustomerDetails,tvNotesProDetails,tvNotesShipDetails,tvM_Des,tvArriveDateDetails,
            tvShipDateDetails,tvDaysLeftDetails;

    public ShipOrderAdpater(Activity context, List<ShipOrderModel> shipOrderModelList) {
        this.activity=context;
        this.shipOrderModelList=shipOrderModelList;
    }


    @NonNull
    @Override
    public ShipOrderAdpater.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_shiporder,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ShipOrderAdpater.ViewHolder holder, int position) {
        final  ShipOrderModel model=shipOrderModelList.get(position);
        holder.tvOrderNo.setText(model.getOrder_no());
        ariveDate =   changeDateFormat("dd/mm/yyyy","mm/dd/yyyy",model.getArrived_date());
        shipDate =   changeDateFormat("dd/mm/yyyy","mm/dd/yyyy",model.getShipment_date());
        holder.tvArriDate.setText(ariveDate);
        strNotesProduction=model.getNotesProduction();
        if (strNotesProduction.equals(""))
        {
            holder.tvNotesProduction.setText("Write Here");
        }
        else
        {
            holder.tvNotesProduction.setText(model.getNotesProduction());
        }
        holder.tvShipdate.setText(shipDate);
        holder.tvUser.setText(model.getCustomer());
        holder.uploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String orderId=model.getId();
                String orderNo=model.getOrder_no();
                Bundle bundle=new Bundle();
                bundle.putString("orderNo",orderNo);
                holder.editor.putString("orderid",orderId).commit();
                Fragment fragment=new UploadFragment();
                fragment.setArguments(bundle);
                ((AppCompatActivity)activity).getFragmentManager().beginTransaction().replace(R.id.navContainer,fragment).commit();
            }
        });

        holder.btnComplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment=new CompletedOrdersFragment();
                ((AppCompatActivity)activity).getFragmentManager().beginTransaction().replace(R.id.navContainer,fragment).addToBackStack("abc").commit();
            }
        });

        holder.tvNotesProduction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(activity);
                LayoutInflater inflater = activity.getLayoutInflater();
                View dialogView = inflater.inflate(R.layout.custom_notes, null);
                dialogBuilder.setView(dialogView);

                TextView textView =dialogView.findViewById(R.id.tvNotesINDetails);
                textView.setText(holder.tvNotesProduction.getText().toString());
                AlertDialog alertDialog = dialogBuilder.create();
                alertDialog.show();
            }
        });
        holder.ivEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                id=model.getId();
                editCall();
                holder.tvNotesProduction.setText(strNotesProduction);
            }
        });

        holder.tvOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(activity);
                LayoutInflater inflater = activity.getLayoutInflater();
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


                tvArriveDateDetails.setText(model.getArrived_date());
                tvShipDateDetails.setText(model.getShipment_date());
                tvCustomerDetails.setText(model.getCustomer());
                tvNotesShipDetails.setText(model.getNotesShipping());
                tvM_Des.setText(model.getMetrialDescription());
                tvNotesProDetails.setText(model.getNotesProduction());
                tvOrderDetails.setText(model.getOrder_no());

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

                final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(activity);
                LayoutInflater inflater = activity.getLayoutInflater();
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



                tvArriveDateDetails.setText(model.getArrived_date());
                tvShipDateDetails.setText(model.getShipment_date());
                tvCustomerDetails.setText(model.getCustomer());
                tvNotesShipDetails.setText(model.getNotesShipping());
                tvM_Des.setText(model.getMetrialDescription());
                tvNotesProDetails.setText(model.getNotesProduction());
                tvOrderDetails.setText(model.getOrder_no());

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

        holder.tvShowImages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                id=model.getId();
                final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(activity);
                LayoutInflater inflater = activity.getLayoutInflater();
                View dialogView = inflater.inflate(R.layout.custom_image, null);
                dialogBuilder.setView(dialogView);

                recyclerView=dialogView.findViewById(R.id.rvShowImages);
                recyclerView.setLayoutManager(new LinearLayoutManager(activity));
                imageModeList=new ArrayList<>();
                if (alertDialogs==null)
                {
                    alertDialogs= AlertsUtils.createProgressDialog(activity);
                    alertDialogs.show();
                }
                apicall();
                imageAdapter=new ImageAdapter(activity,imageModeList);
                recyclerView.setAdapter(imageAdapter);
                AlertDialog alertDialog = dialogBuilder.create();
                alertDialog.show();
            }
        });
    }
    private void apicall() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Api.getImages, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.contains("true"))
                {
                    if (alertDialogs!=null)
                        alertDialogs.dismiss();
                    alertDialogs=null;
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

                    if (alertDialogs!=null)
                        alertDialogs.dismiss();
                    alertDialogs=null;
                    try {
                        JSONObject object=new JSONObject(response);
                        String message=object.getString("message");
                        AlertsUtils.showErrorDialog(activity,message);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }



            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (alertDialogs!=null)
                    alertDialogs.dismiss();
                alertDialogs=null;
                AlertsUtils.showErrorDialog(activity,error.getMessage().toString());
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

        RequestQueue mRequestQueue = Volley.newRequestQueue(activity);
        stringRequest.setRetryPolicy(new
                DefaultRetryPolicy(200000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        mRequestQueue.add(stringRequest);
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
    private void editCall() {
        AlertDialog.Builder pictureDialog = new AlertDialog.Builder(activity);
        pictureDialog.setTitle("PACIFIC GALVANZING");
        String[] pictureDialogItems = {
                "\tEdit"};
        pictureDialog.setItems(pictureDialogItems,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(final DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(activity);
                                LayoutInflater inflater = activity.getLayoutInflater();
                                View dialogView = inflater.inflate(R.layout.alert_label_editor, null);
                                dialogBuilder.setView(dialogView);

                                editText =dialogView.findViewById(R.id.etNotesProduction);
                                Button btnSubmit=dialogView.findViewById(R.id.btnSubmit);

                                btnSubmit.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        strNotesProduction=editText.getText().toString();
                                        if (alertDialogs==null)
                                        {
                                            alertDialogs= AlertsUtils.createProgressDialog(activity);
                                            alertDialogs.show();
                                        }
                                        updateApiCall();

                                    }
                                });
                                AlertDialog alertDialog = dialogBuilder.create();
                                alertDialog.show();
                                break;
                        }
                    }
                });
        pictureDialog.show();

    }
    private void updateApiCall() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Api.updateNotes, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("zmaUpdate",response);
                if (response.contains("true"))
                {
                    if (alertDialogs!=null)
                        alertDialogs.dismiss();
                         alertDialogs=null;
                    Toast.makeText(activity, "Updated Successfully", Toast.LENGTH_SHORT).show();
                    Fragment fragment=new ReadyToShipFragment();
                    ((AppCompatActivity)activity).getFragmentManager().beginTransaction().replace(R.id.navContainer,fragment).commit();


                }
                else
                {

                    if (alertDialogs!=null)
                        alertDialogs.dismiss();
                    alertDialogs=null;
                    try {
                        JSONObject object=new JSONObject(response);
                        String message=object.getString("message");
                        AlertsUtils.showErrorDialog(activity,message);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }



            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (alertDialogs!=null)
                    alertDialogs.dismiss();
                alertDialogs=null;
                AlertsUtils.showErrorDialog(activity,error.getMessage().toString());
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
                params.put("shipment_note",strNotesProduction);
                return params;
            }
        };

        RequestQueue mRequestQueue = Volley.newRequestQueue(activity);
        stringRequest.setRetryPolicy(new
                DefaultRetryPolicy(200000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        mRequestQueue.add(stringRequest);
    }
    @Override
    public int getItemCount() {
        return shipOrderModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvOrderNo,tvOrder,tvNotesProduction,tvShipdate,tvArriDate,tvUser;
        LinearLayout uploadImage;
        SharedPreferences sharedPreferences;
        SharedPreferences.Editor editor;
        Button btnComplete;
        TextView tvShowImages;
        ImageView ivEdit;
        public ViewHolder(View itemView) {
            super(itemView);

            sharedPreferences = activity.getSharedPreferences("abc", Context.MODE_PRIVATE);
            editor = sharedPreferences.edit();
            tvOrderNo=itemView.findViewById(R.id.tvOrderNoShip);
            tvShowImages=itemView.findViewById(R.id.tvShowImagesReady);
            tvOrder=itemView.findViewById(R.id.tvOrderShip);
            tvArriDate=itemView.findViewById(R.id.tvArivedDateShip);
            tvShipdate=itemView.findViewById(R.id.tvShipDateShip);
//            tvWOrdDaysLeft=itemView.findViewById(R.id.tvDaysleftship);
            tvUser=itemView.findViewById(R.id.tvCustomer);
            ivEdit=itemView.findViewById(R.id.ivEditShip);
            tvNotesProduction=itemView.findViewById(R.id.tvNotesProductionShip);
            uploadImage=itemView.findViewById(R.id.ivUploadImageShip);
            btnComplete=itemView.findViewById(R.id.btnCompleteOrders);
        }
    }
}
