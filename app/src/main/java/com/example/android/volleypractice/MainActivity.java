package com.example.android.volleypractice;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.stripe.android.model.Card;
import com.stripe.android.view.CardInputWidget;

public class MainActivity extends AppCompatActivity {

    private static final String LOG_TAG = MainActivity.class.getSimpleName();

    private CardInputWidget mCardInputWidget;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }

    @Override
    protected void onStart() {
        super.onStart();

        Card card = new Card("4242-4242-4242-4242", 12, 2017, "123");

        if (!card.validateCard()) {
            card.getEx
            Log.v(LOG_TAG, "InValid Card No.");
        } else {
            Log.v(LOG_TAG, "Valid Card No.");
        }

    }
}
