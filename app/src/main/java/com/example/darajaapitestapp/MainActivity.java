package com.example.darajaapitestapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.androidstudy.daraja.Daraja;
import com.androidstudy.daraja.DarajaListener;
import com.androidstudy.daraja.model.AccessToken;
import com.androidstudy.daraja.model.LNMExpress;
import com.androidstudy.daraja.model.LNMResult;
import com.androidstudy.daraja.util.TransactionType;

public class MainActivity extends AppCompatActivity {

    private EditText amount, account;
    private Button stkpushBtn;
    private Daraja daraja;
    private String mPesaResult;
    private GenericLoader genericLoader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        stkpushBtn = findViewById(R.id.stk_push_btn);

        amount = findViewById(R.id.amount);
        account = findViewById(R.id.account);

        daraja = Daraja.with("4BMXY3dMmUcYH8EVsHNi7LmMMRW2Qb0l", "iGylY5r8YMwVgLYX", new DarajaListener<AccessToken>() {
            @Override
            public void onResult(@NonNull AccessToken accessToken) {

                //Log.i(MainActivity.this.getClass().getSimpleName(), accessToken.getAccess_token());
                //Toast.makeText(MainActivity.this, "TOKEN : " + accessToken.getAccess_token(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(String error) {

                Toast.makeText(MainActivity.this, "Error " + error, Toast.LENGTH_SHORT).show();
            }
        });

        genericLoader = new GenericLoader();
    }

    @Override
    protected void onStart() {
        super.onStart();

        stkpushBtn.setOnClickListener(new View.OnClickListener() {

            @SuppressLint("StaticFieldLeak")
            @Override
            public void onClick(View view) {


                if (TextUtils.isEmpty(amount.getText().toString()) || TextUtils.isEmpty(account.getText().toString())) {
                    Toast.makeText(getApplicationContext(), "All  Fields are required ", Toast.LENGTH_SHORT).show();
                }


                new AsyncTask<Void, Void, String>() {

                    @Override
                    protected void onPreExecute() {
                        super.onPreExecute();

                        genericLoader.show(getSupportFragmentManager(), "tag");
                        // Toast.makeText(getApplicationContext(),"started call to mpesa ...",Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    protected String doInBackground(Void... voids) {
                        return initiateStkPush();
                    }

                    @Override
                    protected void onPostExecute(String aMessage) {
                        super.onPostExecute(aMessage);
                        Toast.makeText(getApplicationContext(), aMessage, Toast.LENGTH_SHORT).show();
                    }
                }.execute();
            }


        });


    }

    private String initiateStkPush() {

        //Get Phone Number from User Input

        String amountSent = amount.getText().toString().trim();
        String user_account_name = account.getText().toString();


        //TODO :: REPLACE WITH YOUR OWN CREDENTIALS  :: THIS IS SANDBOX DEMO
        LNMExpress lnmExpress = new LNMExpress(
                "174379",
                "bfb279f9aa9bdbcf158e97dd71a467cd2e0c893059b10f78e6b72ada1ed2c919",  //https://developer.safaricom.co.ke/test_credentials
                TransactionType.CustomerPayBillOnline,
                amountSent,
                "phone-number",
                "174379",
                "phone-number",
                "http://mycallbackurl.com/checkout.php",
                "user_account_name",
                "API TESTING"
        );

        daraja.requestMPESAExpress(lnmExpress,
                new DarajaListener<LNMResult>() {
                    @Override
                    public void onResult(@NonNull LNMResult lnmResult) {
                        Log.i(MainActivity.this.getClass().getSimpleName(), lnmResult.ResponseDescription);
                        mPesaResult = "Success " + lnmResult.ResponseDescription;
                        genericLoader.dismiss();

                    }

                    @Override
                    public void onError(String error) {
                        Log.i(MainActivity.this.getClass().getSimpleName(), error);
                        mPesaResult = "Error : " + error;
                        genericLoader.dismiss();


                    }
                }
        );


        return mPesaResult;

    }
}
