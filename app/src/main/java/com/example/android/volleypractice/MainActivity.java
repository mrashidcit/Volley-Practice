package com.example.android.volleypractice;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.android.volleypractice.Config.Config;
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;

import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;

public class MainActivity extends AppCompatActivity {

    public static final int PAYPAL_REQUEST_CODE = 7171;
    private static final String LOG_TAG = MainActivity.class.getSimpleName();

    private static PayPalConfiguration config = new PayPalConfiguration()
            .environment(PayPalConfiguration.ENVIRONMENT_SANDBOX)
            .clientId(Config.PAYPAL_CLIENT_ID);


    Button btnPayNow;
    EditText edtAmount;

    String amount = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Start Paypal Service
        Intent intent = new Intent(this, PayPalService.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);
        startService(intent);

        btnPayNow = (Button) findViewById(R.id.btn_pay_now);
        edtAmount = (EditText) findViewById(R.id.edt_amount);

        btnPayNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                processPayment();
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();

//        String jsonString = "{\"client\":{\"environment\":\"sandbox\",\"paypal_sdk_version\":\"2.16.0\",\"platform\":\"Android\",\"product_name\":\"PayPal-Android-SDK\"},\"response\":{\"create_time\":\"2018-02-19T11:23:36Z\",\"id\":\"PAY-60S9375198199183FLKFLHJI\",\"intent\":\"sale\",\"state\":\"approved\"},\"response_type\":\"payment\"}";
//
////         Pass Dummy Data to PaymentDetails
//        try {
//
//            JSONObject responseJsonObject = new JSONObject(jsonString);
//
//
//            String paymentDetails = responseJsonObject.toString();
//
//            Intent intent = new Intent(this, PaymentDetails.class);
//
////            intent.put("PaymentDetails", paymentDetails);
//            intent.putExtra(Config.PAYMENT_DETAIL, paymentDetails);
//            intent.putExtra(Config.PAYMENT_AMOUNT, "100");
//
//            startActivity(intent);
//
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }


    }

    @Override
    protected void onDestroy() {
        stopService(new Intent(this, PayPalService.class));
        super.onDestroy();

    }

    private void processPayment() {
        amount = edtAmount.getText().toString();
        PayPalPayment payPalPayment = new PayPalPayment(new BigDecimal(Integer.valueOf(amount)), "USD", "Donate for EDMTDev", PayPalPayment.PAYMENT_INTENT_SALE);

        Intent intent = new Intent(this, PaymentActivity.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);
        intent.putExtra(PaymentActivity.EXTRA_PAYMENT, payPalPayment);
        startActivityForResult(intent, PAYPAL_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        showPaymentDetail(requestCode, resultCode, data);

    }


    // Show Detail Of order in PaymentDetails Activity
    private void showPaymentDetail(int requestCode, int resultCode, Intent data) {
        if (requestCode == PAYPAL_REQUEST_CODE) {

            PaymentConfirmation confirmation = data.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);

            if (confirmation != null) {
                String paymentDetails = confirmation.toJSONObject().toString();

                Log.v(LOG_TAG, "Response: " + paymentDetails);

                Intent intent = new Intent(this, PaymentDetails.class);
                intent.putExtra(Config.PAYMENT_DETAIL, paymentDetails);
                intent.putExtra("PaymentAmount", amount);

                startActivity(intent);


            } else if (resultCode == Activity.RESULT_CANCELED) {
                Toast.makeText(this, "Cancel", Toast.LENGTH_SHORT).show();
            } else if (resultCode == PaymentActivity.RESULT_EXTRAS_INVALID) {
                Toast.makeText(this, "Invalid", Toast.LENGTH_SHORT).show();
            }

        }

    }


}













