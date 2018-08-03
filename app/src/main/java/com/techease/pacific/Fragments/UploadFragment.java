package com.techease.pacific.Fragments;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.provider.MediaStore;
import android.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
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
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.techease.pacific.Activities.FullscreenActivity;
import com.techease.pacific.Activities.MainActivity;
import com.techease.pacific.Activities.NavigationActivity;
import com.techease.pacific.R;
import com.techease.pacific.Utils.AlertsUtils;
import com.techease.pacific.Utils.Api;
import com.techease.pacific.Utils.HTTPMultiPartEntity;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.app.Activity.RESULT_OK;


public class UploadFragment extends Fragment implements View.OnClickListener {

    TextView tvOrderNo;
    ImageView ivUpload1,ivUpload2,ivUpload3,ivUpload4,ivUpload5,ivGallery,ivSend,ivTakePic,
            ivCross1,ivCross2,ivCross3,ivCross4,ivCross5,ivUploadPic1,ivUploadPic2,ivUploadPic3,ivUploadPic4,ivUploadPic5,ivComplete;
    final int CAMERA_CAPTURE = 1;
    final int RESULT_LOAD_IMAGE = 2;
    File file1,file2,file3,file4,file5;
    RelativeLayout rl1,rl2,rl3,rl4,rl5;
    String responseMessage,orderNo;
    boolean flag=false;
    View view123;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    LinearLayout linearLayoutComplete;
    android.support.v7.app.AlertDialog alertDialog;
    String orderId;
    FrameLayout frameLayout1,frameLayout2,frameLayout3,frameLayout4,frameLayout5;
    int a,b,c,d,e,i;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_upload, container, false);


        sharedPreferences = getActivity().getSharedPreferences("abc", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        orderNo=getArguments().getString("orderNo");
        getActivity().setTitle("UPLOAD IMAGE");
        orderId=sharedPreferences.getString("orderid","");
        tvOrderNo=view.findViewById(R.id.tvOrderNoUploadImage);
        ivUpload1=view.findViewById(R.id.ivUploadImage1);
        ivUpload2=view.findViewById(R.id.ivUploadImage2);
        ivUpload3=view.findViewById(R.id.ivUploadImage3);
        ivUpload4=view.findViewById(R.id.ivUploadImage4);
        ivUpload5=view.findViewById(R.id.ivUploadImage5);
        ivGallery=view.findViewById(R.id.ivGallery);
        view123=view.findViewById(R.id.view);
        linearLayoutComplete=view.findViewById(R.id.llUploadComplete);
        ivSend=view.findViewById(R.id.ivSend);
        ivTakePic=view.findViewById(R.id.ivTakePic);
        rl1=view.findViewById(R.id.rl1);
        rl2=view.findViewById(R.id.rl2);
        rl3=view.findViewById(R.id.rl3);
        rl4=view.findViewById(R.id.rl4);
        rl5=view.findViewById(R.id.rl5);
        ivCross1=view.findViewById(R.id.ivCross1);
        ivCross2=view.findViewById(R.id.ivCross2);
        ivCross3=view.findViewById(R.id.ivCross3);
        ivCross4=view.findViewById(R.id.ivCross4);
        ivCross5=view.findViewById(R.id.ivCross5);
        ivUploadPic1=view.findViewById(R.id.ivUpload1);
        ivUploadPic2=view.findViewById(R.id.ivUpload2);
        ivUploadPic3=view.findViewById(R.id.ivUpload3);
        ivUploadPic4=view.findViewById(R.id.ivUpload4);
        ivUploadPic5=view.findViewById(R.id.ivUpload5);
        ivComplete=view.findViewById(R.id.ivComplete);
        frameLayout1=view.findViewById(R.id.fm1);
        frameLayout2=view.findViewById(R.id.fm2);
        frameLayout3=view.findViewById(R.id.fm3);
        frameLayout4=view.findViewById(R.id.fm4);
        frameLayout5=view.findViewById(R.id.fm5);

        tvOrderNo.setText(orderNo);

        ivTakePic.setOnClickListener(this);
        ivGallery.setOnClickListener(this);
        ivSend.setOnClickListener(this);
        ivCross1.setOnClickListener(this);
        ivCross2.setOnClickListener(this);
        ivCross3.setOnClickListener(this);
        ivCross4.setOnClickListener(this);
        ivCross5.setOnClickListener(this);
        ivUpload1.setOnClickListener(this);
        ivUpload2.setOnClickListener(this);
        ivUpload3.setOnClickListener(this);
        ivUpload4.setOnClickListener(this);
        ivUpload5.setOnClickListener(this);
        ivUploadPic1.setOnClickListener(this);
        ivUploadPic2.setOnClickListener(this);
        ivUploadPic3.setOnClickListener(this);
        ivUploadPic4.setOnClickListener(this);
        ivUploadPic5.setOnClickListener(this);
        frameLayout1.setOnClickListener(this);
        frameLayout2.setOnClickListener(this);
        frameLayout3.setOnClickListener(this);
        frameLayout4.setOnClickListener(this);
        frameLayout5.setOnClickListener(this);
        ivComplete.setOnClickListener(this);

        a=0;
        b=0;
        c=0;
        d=0;
        e=0;


        Dexter.withActivity(getActivity())
                .withPermissions(
                        Manifest.permission.INTERNET,
                        Manifest.permission.CAMERA,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE
                ).withListener(new MultiplePermissionsListener() {
            @Override
            public void onPermissionsChecked(MultiplePermissionsReport report) {

            }

            @Override
            public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {

            }

        }).check();

        return view;
    }

    @Override
    public void onClick(View v) {
        int id=v.getId();

        switch (id)
        {
            case R.id.ivComplete:

                    if (alertDialog==null)
                    {
                        alertDialog= AlertsUtils.createProgressDialog(getActivity());
                        alertDialog.show();
                    }
                    apicall();
                break;
            case R.id.ivSend:

                    if (file1!=null || file2!=null || file3!=null || file4!=null || file5!=null)
                    {

                        linearLayoutComplete.setVisibility(View.VISIBLE);
                        view123.setVisibility(View.VISIBLE);
                        if (alertDialog==null)
                        {
                            alertDialog= AlertsUtils.createProgressDialog(getActivity());
                            alertDialog.show();
                        }
                        Thread timer = new Thread() {
                            public void run() {
                                try {
                                    sleep(5000);

                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                } finally {


                                    alertDialog.dismiss();
                                    alertDialog=null;
                                }
                            }
                        };
                        timer.start();
                    }
                    else
                    {
                        Toast.makeText(getActivity(), "Please select at least one image to attach", Toast.LENGTH_LONG).show();
                    }

//                else
//                {
//                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
//                    builder.setTitle("Uploading failed");
//                    builder.setMessage("Please select all the images!");
//                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            dialog.cancel();
//                        }
//                    });
//                    builder.show();
//                }

                break;
            case R.id.ivGallery:
                galleryIntent();
                break;
            case R.id.ivTakePic:
                cameraIntent();
                break;
            case R.id.ivCross1:
                a=0;
                file1=null;
                ivUpload1.setImageBitmap(null);
                ivUpload1.setScaleType(ImageView.ScaleType.CENTER);
                ivUpload1.setImageResource(R.drawable.picture);
                ivUpload1.setScaleType(ImageView.ScaleType.CENTER);
                rl1.setVisibility(View.GONE);
                break;
            case R.id.ivCross2:
                b=0;
                file2=null;
                ivUpload2.setImageBitmap(null);
                ivUpload2.setScaleType(ImageView.ScaleType.CENTER);
                ivUpload2.setImageResource(R.drawable.picture);
                rl2.setVisibility(View.GONE);
                break;
            case R.id.ivCross3:
                c=0;
                file3=null;
                ivUpload3.setImageBitmap(null);
                ivUpload3.setScaleType(ImageView.ScaleType.CENTER);
                ivUpload3.setImageResource(R.drawable.picture);
                rl3.setVisibility(View.GONE);
                break;
            case R.id.ivCross4:
                d=0;
                file4=null;
                ivUpload4.setImageBitmap(null);
                ivUpload4.setScaleType(ImageView.ScaleType.CENTER);
                ivUpload4.setImageResource(R.drawable.picture);
                rl4.setVisibility(View.GONE);
                break;
            case R.id.ivCross5:
                e=0;
                file5=null;
                ivUpload5.setImageBitmap(null);
                ivUpload5.setScaleType(ImageView.ScaleType.CENTER);
                ivUpload5.setImageResource(R.drawable.picture);
                rl5.setVisibility(View.GONE);
                break;

        }
    }

    public void cameraIntent() {
        a++;
        b++;
        c++;
        d++;
        e++;
        Intent captureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(captureIntent, CAMERA_CAPTURE);
    }

    public void galleryIntent() {
        a++;
        b++;
        c++;
        d++;
        e++;
        Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(i, RESULT_LOAD_IMAGE);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {


        super.onActivityResult(requestCode, resultCode, data);



        if (a==1)
        {
            if (requestCode == RESULT_LOAD_IMAGE && null != data) {
                Uri selectedImageUri = data.getData();
                String imagepath = getPath(selectedImageUri);
                file1 = new File(imagepath);
                UploadFragment.UploadFileToServer uploadFileToServer=new UploadFileToServer();
                uploadFileToServer.execute();
            } else if (resultCode == RESULT_OK && requestCode == CAMERA_CAPTURE && data != null) {
                Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
                file1 = new File(Environment.getExternalStorageDirectory(),
                        System.currentTimeMillis() + ".jpg");
                FileOutputStream fo;
                try {
                    file1.createNewFile();
                    fo = new FileOutputStream(file1);
                    fo.write(bytes.toByteArray());
                    fo.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                ivUpload1.setScaleType(ImageView.ScaleType.FIT_XY);
                ivUpload1.setImageBitmap(thumbnail);
                rl1.setVisibility(View.VISIBLE);


                UploadFragment.UploadFileToServer uploadFileToServer=new UploadFileToServer();
                uploadFileToServer.execute();

                b=0;
                c=0;
                d=0;
                e=0;
            }
        }
        else
        if (b==1)
        {
            if (requestCode == RESULT_LOAD_IMAGE && null != data) {
                Uri selectedImageUri = data.getData();
                String imagepath = getPath(selectedImageUri);
                file2 = new File(imagepath);
                UploadFragment.UploadFileToServer uploadFileToServer=new UploadFileToServer();
                uploadFileToServer.execute();
            } else if (resultCode == RESULT_OK && requestCode == CAMERA_CAPTURE && data != null) {
                Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
                file2 = new File(Environment.getExternalStorageDirectory(),
                        System.currentTimeMillis() + ".jpg");
                FileOutputStream fo;
                try {
                    file2.createNewFile();
                    fo = new FileOutputStream(file2);
                    fo.write(bytes.toByteArray());
                    fo.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                ivUpload2.setScaleType(ImageView.ScaleType.FIT_XY);
                ivUpload2.setImageBitmap(thumbnail);
                rl2.setVisibility(View.VISIBLE);

                UploadFragment.UploadFileToServer uploadFileToServer=new UploadFileToServer();
                uploadFileToServer.execute();
                c=0;
                d=0;
                e=0;
            }
        }
        else
        if (c==1)
        {
            if (requestCode == RESULT_LOAD_IMAGE && null != data) {
                Uri selectedImageUri = data.getData();
                String imagepath = getPath(selectedImageUri);
                file3 = new File(imagepath);
                UploadFragment.UploadFileToServer uploadFileToServer=new UploadFileToServer();
                uploadFileToServer.execute();

            } else if (resultCode == RESULT_OK && requestCode == CAMERA_CAPTURE && data != null) {
                Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
                file3 = new File(Environment.getExternalStorageDirectory(),
                        System.currentTimeMillis() + ".jpg");
                FileOutputStream fo;
                try {
                    file3.createNewFile();
                    fo = new FileOutputStream(file3);
                    fo.write(bytes.toByteArray());
                    fo.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                ivUpload3.setScaleType(ImageView.ScaleType.FIT_XY);
                ivUpload3.setImageBitmap(thumbnail);
                rl3.setVisibility(View.VISIBLE);

                UploadFragment.UploadFileToServer uploadFileToServer=new UploadFileToServer();
                uploadFileToServer.execute();
                d=0;
                e=0;
            }
        }
        if (d==1)
        {
            if (requestCode == RESULT_LOAD_IMAGE && null != data) {
                Uri selectedImageUri = data.getData();
                String imagepath = getPath(selectedImageUri);
                file4 = new File(imagepath);
                UploadFragment.UploadFileToServer uploadFileToServer=new UploadFileToServer();
                uploadFileToServer.execute();
            } else if (resultCode == RESULT_OK && requestCode == CAMERA_CAPTURE && data != null) {
                Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
                file4 = new File(Environment.getExternalStorageDirectory(),
                        System.currentTimeMillis() + ".jpg");
                FileOutputStream fo;
                try {
                    file4.createNewFile();
                    fo = new FileOutputStream(file4);
                    fo.write(bytes.toByteArray());
                    fo.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                ivUpload4.setScaleType(ImageView.ScaleType.FIT_XY);
                ivUpload4.setImageBitmap(thumbnail);
                rl4.setVisibility(View.VISIBLE);


                UploadFragment.UploadFileToServer uploadFileToServer=new UploadFileToServer();
                uploadFileToServer.execute();

                e=0;

            }
        }
        if (e==1)
        {
            if (requestCode == RESULT_LOAD_IMAGE && null != data) {
                Uri selectedImageUri = data.getData();
                String imagepath = getPath(selectedImageUri);
                file5 = new File(imagepath);
                UploadFragment.UploadFileToServer uploadFileToServer=new UploadFileToServer();
                uploadFileToServer.execute();
            } else if (resultCode == RESULT_OK && requestCode == CAMERA_CAPTURE && data != null) {
                Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
                file5 = new File(Environment.getExternalStorageDirectory(),
                        System.currentTimeMillis() + ".jpg");
                FileOutputStream fo;
                try {
                    file5.createNewFile();
                    fo = new FileOutputStream(file5);
                    fo.write(bytes.toByteArray());
                    fo.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                ivUpload5.setScaleType(ImageView.ScaleType.FIT_XY);
                ivUpload5.setImageBitmap(thumbnail);
                rl5.setVisibility(View.VISIBLE);


                UploadFragment.UploadFileToServer uploadFileToServer=new UploadFileToServer();
                uploadFileToServer.execute();

                e=0;

            }
        }

    }

    @SuppressLint("SetTextI18n")
    public String getPath(Uri uri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = getActivity().getContentResolver().query(uri, projection, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        int columnIndex = cursor.getColumnIndex(projection[0]);
        String filePath = cursor.getString(columnIndex);
        if (a==1)
        {
            ivUpload1.setScaleType(ImageView.ScaleType.FIT_XY);
            ivUpload1.setImageBitmap(BitmapFactory.decodeFile(filePath));
            rl1.setVisibility(View.VISIBLE);
            b=0;
            c=0;
            d=0;
            e=0;
        }
        else
        if (b==1)
        {
            ivUpload2.setScaleType(ImageView.ScaleType.FIT_XY);
            ivUpload2.setImageBitmap(BitmapFactory.decodeFile(filePath));
            rl2.setVisibility(View.VISIBLE);
            c=0;
            d=0;
            e=0;
        }
        else
        if (c==1)
        {
            ivUpload3.setScaleType(ImageView.ScaleType.FIT_XY);
            ivUpload3.setImageBitmap(BitmapFactory.decodeFile(filePath));
            rl3.setVisibility(View.VISIBLE);
            d=0;
            e=0;
        }
        else
        if (d==1)
        {
            ivUpload4.setScaleType(ImageView.ScaleType.FIT_XY);
            ivUpload4.setImageBitmap(BitmapFactory.decodeFile(filePath));
            rl4.setVisibility(View.VISIBLE);
            e=0;

        }

        else
        if (e==1)
        {
            ivUpload5.setScaleType(ImageView.ScaleType.FIT_XY);
            ivUpload5.setImageBitmap(BitmapFactory.decodeFile(filePath));
            rl5.setVisibility(View.VISIBLE);


        }

        return cursor.getString(column_index);

    }

    class UploadFileToServer extends AsyncTask<Void, Integer, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onProgressUpdate(Integer... progress) {

        }

        @Override
        protected String doInBackground(Void... params) {
            return uploadFile();
        }

        @SuppressWarnings("deprecation")
        private String uploadFile() {

            String responseString;
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(Api.uploadImage);
            try {
                HTTPMultiPartEntity entity = new HTTPMultiPartEntity(
                        new HTTPMultiPartEntity.ProgressListener() {

                            @Override
                            public void transferred(long num) {
                                publishProgress((int) ((num / (float) 100) * 100));
                            }
                        });
                // Adding file data to http body
                // Extra parameters if you want to pass to server

                if (file1 !=null)
                {
                    entity.addPart("file", new FileBody(file1));
                }
                else
                    if (file2 !=null)
                    {
                        entity.addPart("file", new FileBody(file2));
                    }
                    else
                        if (file3 !=null)
                        {
                            entity.addPart("file", new FileBody(file3));
                        }
                        else
                        if (file4 !=null)
                        {
                            entity.addPart("file", new FileBody(file4));
                        }
                        else
                        if (file5 !=null)
                        {
                            entity.addPart("file", new FileBody(file5));
                        }


                    if (Looper.myLooper() == null) {
                        Looper.prepare();
                    }

                entity.addPart("id", new StringBody(orderId));


                httppost.setEntity(entity);
                // Making server call
                HttpResponse response = httpclient.execute(httppost);
                HttpEntity r_entity = response.getEntity();
                int statusCode = response.getStatusLine().getStatusCode();
                responseString = EntityUtils.toString(r_entity);


            } catch (ClientProtocolException e) {
                responseString = e.toString();
                Log.d("zmaClient", e.getCause().toString());

            } catch (IOException e) {
                responseString = e.toString();
                Log.d("zmaIo", responseString);
            }

            Log.d("zma return string", responseString);
            return responseString;

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            responseMessage=s;
            if (alertDialog!=null)
                alertDialog.dismiss();

            if (s.contains("true"))
            {
                i++;
            }

            else
            {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setMessage(s);
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();

                    }
                });
                builder.show();
            }

        }
    }

    private void apicall() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Api.completedOrders, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("zmaShip",response);
                if (response.contains("true"))
                {
                    if (alertDialog!=null)
                        alertDialog.dismiss();
                    try {
                        JSONObject jsonObject=new JSONObject(response);
                        String message=jsonObject.getString("message");
                        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                        if (message.contains("Completed"))
                        {

                            builder.setMessage("Order Completed!");
                            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                    Fragment fragment=new CompletedOrdersFragment();
                                    getFragmentManager().beginTransaction().replace(R.id.navContainer,fragment).commit();

                                }
                            });
                            builder.show();
                        }
                        else
                        {
                            builder.setMessage(responseMessage);
                            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();

                                }
                            });
                            builder.show();
                        }
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
                params.put("id",orderId);
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
