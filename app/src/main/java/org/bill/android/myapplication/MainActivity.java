package org.bill.android.myapplication;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import org.bill.android.myapplication.InformationService.KOOArrayOfTable;
import org.bill.android.myapplication.InformationService.KOOBasicHttpBinding_IInformationService;
import org.bill.android.myapplication.InformationService.KOOGetTableRequest;
import org.bill.android.myapplication.InformationService.KOOGetTableResult;
import org.bill.android.myapplication.InformationService.KOORequestHeader;
import org.bill.android.myapplication.InformationService.KOOTable;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;


public class MainActivity extends AppCompatActivity
{
    private Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SharedPreferences sharedPreference = getSharedPreferences("sharedPr", MODE_PRIVATE);
        String token = sharedPreference.getString("token", null);
        Spinner dropdown = (Spinner) findViewById(R.id.spinnerTables);
        QueryTable queryTable = new QueryTable(token);
        try
        {
            KOOGetTableResult result = queryTable.execute().get();
            if (result != null && result.ResultHeader.IsSuccess)
            {
                KOOArrayOfTable tables = result.Tables;
                String[] items = new String[tables.capacity()];
                List<String> tbls = new ArrayList<String>();
                for (KOOTable record : tables)
                {
                    tbls.add(record.TableNumber);
                }

                ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, tbls);
                dropdown.setAdapter(adapter);
            }

        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
        catch (ExecutionException e)
        {
            e.printStackTrace();
        }

    }

    public class QueryTable extends AsyncTask<Void, Void, KOOGetTableResult>
    {
        private final String token;

        public QueryTable(String token)
        {
            this.token = token;
        }

        @Override
        protected KOOGetTableResult doInBackground(Void... params)
        {
            KOOGetTableRequest request = new KOOGetTableRequest();
            KOOGetTableResult result = null;
            KOORequestHeader authentication = new KOORequestHeader();
            authentication.Token = token;
            request.Authentication = authentication;
            KOOBasicHttpBinding_IInformationService service = new KOOBasicHttpBinding_IInformationService();
            try
            {
                result = service.GetTable(request);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            return result;
        }
    }
}
