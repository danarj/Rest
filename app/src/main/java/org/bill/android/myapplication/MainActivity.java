package org.bill.android.myapplication;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.bill.android.myapplication.membershipService.RIVAuthenticateUserResult;
import org.bill.android.myapplication.membershipService.RIVBasicHttpBinding_IMembershipService;
import org.bill.android.myapplication.membershipService.RIVRequestHeader;
import org.bill.android.myapplication.membershipService.RIVResultHeader;


public class MainActivity extends AppCompatActivity {

    private TextView txtViewResult;
    private EditText editTextUserName;
    private EditText editTextPassword;
    private Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtViewResult = (TextView) findViewById(R.id.txtViewResult);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        editTextUserName = (EditText) findViewById(R.id.editTextUserName);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {

            @Override public void onClick(View v) {
                new Login().execute(editTextUserName.getText().toString(), editTextPassword.getText().toString());
            }
        });
//        AsyncTaskRunner asyncTaskRunner = new AsyncTaskRunner();
//        asyncTaskRunner.execute();
    }

    private class Login extends AsyncTask<String, String, String> {
        private String resp;

        @Override
        protected String doInBackground(String... params) {
            // publishProgress("Traying to login..."); // Calls onProgressUpdate()
            try {
                RIVRequestHeader request = new RIVRequestHeader();
                request.Username = params[0];
                request.Password = params[1];
                try {
                    RIVBasicHttpBinding_IMembershipService membershipService = new RIVBasicHttpBinding_IMembershipService();
                    RIVAuthenticateUserResult rivAuthenticateUserResult = membershipService.AuthenticateUser(request);
                    RIVResultHeader resultHeader = rivAuthenticateUserResult.ResultHeader;
                    if (resultHeader.IsSuccess) {
                        resp = "Success";
                    }
                    Log.e("error", resultHeader.ResultMessage);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } catch (Exception e) {
                e.printStackTrace();
                resp = e.getMessage();
            }
            return resp;
        }

        @Override
        protected void onPostExecute(String result) {
            // execution of result of Long time consuming operation
            txtViewResult.setText(resp);
        }

        @Override
        protected void onPreExecute() {
            // Things to be done before execution of long running operation. For
            // example showing ProgessDialog
        }

        @Override
        protected void onProgressUpdate(String... text) {
            txtViewResult.setText(text[0]);
            // Things to be done while execution of long running operation is in
            // progress. For example updating ProgessDialog
        }
    }
}
