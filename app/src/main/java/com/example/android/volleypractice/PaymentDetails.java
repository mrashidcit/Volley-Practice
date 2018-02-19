package com.example.android.volleypractice;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

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

        String paymentDetails = intent.getStringExtra("PaymentDetails");

        try {
            JSONObject jsonObject = new JSONObject(paymentDetails);
            showDetails(jsonObject.getJSONObject("response"), intent.getStringExtra("PaymentAmount"));
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    private void showDetails(JSONObject response, String paymentAmounts) {

        try {
            txtId.setText(response.getString("id"));
            txtStatus.setText(response.getString("state"));
            txtAmount.setText(response.getString(String.format("$%s", paymentAmounts)));

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
