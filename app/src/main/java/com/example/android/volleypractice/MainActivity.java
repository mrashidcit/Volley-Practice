package com.example.android.volleypractice;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class MainActivity extends AppCompatActivity {

    private TextView mResponseTextView;

    private RequestQueue mRequestQueue;
    private StringRequest mStringRequest;
    private String mUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mResponseTextView = (TextView) findViewById(R.id.tv_response);
        mUrl = "http://www.google.com";

        // Initialize the RequestQueue.
        mRequestQueue = Volley.newRequestQueue(this);


        mStringRequest = new StringRequest(Request.Method.GET, mUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        mResponseTextView.setText("Response is: " + response.substring(0, 500));
                    }
                },
                new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                mResponseTextView.setText("That didn't work!");
            }
        });



    }

    @Override
    protected void onStart() {
        super.onStart();
        mResponseTextView.setText("Loading....");
        mRequestQueue.add(mStringRequest);



    }
}
