package org.bill.android.myapplication;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import org.bill.android.myapplication.membershipService.RIVAuthenticateUserResult;
import org.bill.android.myapplication.membershipService.RIVBasicHttpBinding_IMembershipService;
import org.bill.android.myapplication.membershipService.RIVRequestHeader;
import org.bill.android.myapplication.membershipService.RIVResultHeader;


public class MainActivity extends AppCompatActivity {

    private TextView finalResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        finalResult = (TextView) findViewById(R.id.viewMain);
        AsyncTaskRunner asyncTaskRunner = new AsyncTaskRunner();
        asyncTaskRunner.execute();
    }

    private class AsyncTaskRunner extends AsyncTask<String, String, String> {
        private String resp;

        @Override
        protected String doInBackground(String... params) {
            publishProgress("Sleeping..."); // Calls onProgressUpdate()
            try {
                RIVRequestHeader request = new RIVRequestHeader();
                request.Username = "danar";
                request.Password = "12345";
                try {
                    RIVBasicHttpBinding_IMembershipService membershipService = new RIVBasicHttpBinding_IMembershipService();
                    RIVAuthenticateUserResult rivAuthenticateUserResult = membershipService.AuthenticateUser(request);
                    RIVResultHeader resultHeader = rivAuthenticateUserResult.ResultHeader;
                    if (resultHeader.IsSuccess) {
                        resp="Success";
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
            finalResult.setText(resp);
        }

        @Override
        protected void onPreExecute() {
            // Things to be done before execution of long running operation. For
            // example showing ProgessDialog
        }

        @Override
        protected void onProgressUpdate(String... text) {
            finalResult.setText(text[0]);
            // Things to be done while execution of long running operation is in
            // progress. For example updating ProgessDialog
        }
    }
}
