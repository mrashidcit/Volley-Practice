package com.example.android.volleypractice;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.android.volleypractice.Config.Config;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

public class PaymentDetails extends AppCompatActivity {

    TextView txtId, txtAmount, txtStatus;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_details);

        txtId = (TextView) findViewById(R.id.txtId);
        txtAmount = (TextView) findViewById(R.id.txtAmount);
        txtStatus = (TextView) findViewById(R.id.txtStatus);

        // Get Intent
        Intent intent = getIntent();

        String paymentDetails = intent.getStringExtra(Config.PAYMENT_DETAIL);
        String paymentAmount = intent.getStringExtra(Config.PAYMENT_AMOUNT);


        try {
            JSONObject jsonObject = new JSONObject(paymentDetails);
            showDetails(jsonObject.getJSONObject("response"), paymentAmount);
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }


    @Override
    protected void onStart() {
        super.onStart();

    }

    private void showDetails(JSONObject response, String paymentAmount) {

        try {
            txtId.setText(response.getString("id"));
            txtStatus.setText(response.getString("state"));
            txtAmount.setText(String.format("$%s", paymentAmount));
//            txtAmount.setText(response.getString(String.format("$%s", paymentAmount)));

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
