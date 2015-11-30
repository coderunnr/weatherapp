package com.example.myhp.jsontest;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class Jsonweather extends Activity implements View.OnClickListener{
    TextView tv;
    EditText et;
    Button b;
    String cityname;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        tv=(TextView)findViewById(R.id.textView);
        et=(EditText)findViewById(R.id.editText);
        b=(Button)findViewById(R.id.button);
        b.setOnClickListener(this);



    }

    @Override
    public void onClick(View v) {
        cityname=et.getText().toString();
        new JSONTask().execute("http://api.openweathermap.org/data/2.5/weather?q="+cityname+"&appid=2de143494c0b295cca9337e1e96b00e0");
    }

    public class JSONTask extends AsyncTask<String,String,String>
    {


        @Override
        protected String doInBackground(String... params) {
            HttpURLConnection connection=null;
            BufferedReader reader=null;
            double temp;
            try {
                URL url=new URL(params[0]);
                connection=(HttpURLConnection)url.openConnection();
                connection.connect();
                InputStream in=connection.getInputStream();
                reader=new BufferedReader(new InputStreamReader(in));
                StringBuffer report=new StringBuffer();
                String line="";
                while((line=reader.readLine())!=null)
                    report.append(line);

                JSONObject ob=new JSONObject(report.toString());
                JSONObject ob1=ob.getJSONObject("main");
                temp=ob1.getDouble("temp");

                return "temperature is:"+temp+" F";
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            } finally{
                if(connection!=null)
                    connection.disconnect();
                if(reader!=null)
                    try {
                        reader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
            }
            return null;

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            tv.setText(s);
        }
    }
}
