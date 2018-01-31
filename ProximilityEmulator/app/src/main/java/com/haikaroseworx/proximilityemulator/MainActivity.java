package com.haikaroseworx.proximilityemulator;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity {

    private EditText camera,shots;
    private Button emulateBtn;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        camera = findViewById(R.id.camera);
        shots = findViewById(R.id.shots);

        emulateBtn = findViewById(R.id.emulateBtn);
        emulateBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String camNo = camera.getText().toString().trim();
                String shotsNo = shots.getText().toString().trim();

                if(camNo.length()>0 && shotsNo.length()>0){
                    emulate(camNo,shotsNo);
                }
            }
        });
    }

    private void emulate(String camNo,String shotsNo){
        AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
        RequestParams requestParams = new RequestParams();
        requestParams.put("camera",camNo);
        requestParams.put("shots",shotsNo);

        progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setTitle("Performing Action");
        progressDialog.setMessage("Emulating proximity");

        asyncHttpClient.post("http://www.primepost.haikarose.com/feedback", requestParams, new TextHttpResponseHandler() {

            @Override
            public void onStart() {
                super.onStart();
                progressDialog.show();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Toast.makeText(getBaseContext(),"Please try again later",Toast.LENGTH_LONG).show();
                progressDialog.dismiss();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {

                progressDialog.dismiss();
                final AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);
                alertDialog.setTitle("Feedback was submitted");
                alertDialog.setMessage("Thank you for your feedback , we will get back to you soon");
                alertDialog.setPositiveButton("OKAY", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });

                alertDialog.show();
            }
        });
    }
}
