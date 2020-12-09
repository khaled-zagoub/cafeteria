package com.cafeteria_project.second_msir.univ_bechare.cafeteria;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DemandProduct extends AppCompatActivity {
    private RequestQueue requestQueue;
    private static String URL = "";
    private StringRequest request;
    ListView ls;
    String IP = "192.168.1.19";
    String PORT = "100";
    ArrayList<String> designation, price, description;
    ProgressDialog dia;
    Adapter2 itemsAdapter;
    EditText DES, PRICE;
    Spinner spiner1;//for Qt
    Spinner spiner2;//for table
    Button add, supp, send;
    TextView totalPrice, textInfo;
    double TOTALPR = 0;
    String info = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final Activity act = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demand_product);
        ls = (ListView) findViewById(R.id.listView2);

        supp = (Button) findViewById(R.id.BtSup);
        send = (Button) findViewById(R.id.buttonSend);
        totalPrice = (TextView) findViewById(R.id.totalPrice);
        textInfo = (TextView) findViewById(R.id.textinfo);

        spiner1 = (Spinner) findViewById(R.id.spinnerQT);
        final ArrayList<String> Qt = new ArrayList<String>();

        Qt.add("quantity");
        for (int k = 1; k < 8; k++) {
            Qt.add(k + "");
        }
        ArrayAdapter<String> adapter = new
                ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, Qt);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spiner1.setAdapter(adapter);

        spiner2 = (Spinner) findViewById(R.id.spinner);
        final ArrayList<String> Tab = new ArrayList<String>();
        Tab.add("table");
        for (int k = 1; k < 30; k++) {
            Tab.add(k + "");
        }
        ArrayAdapter<String> adapter1 = new
                ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, Tab);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spiner2.setAdapter(adapter1);

        supp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TOTALPR = 0;
                totalPrice.setText(TOTALPR + "");

                info = "";
                textInfo.setText(info);
            }
        });

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!textInfo.getText().toString().equals(".")){

                BackgroundThreadRegisterCommands btrc = new BackgroundThreadRegisterCommands();
                int tab01 = 0;
                try {
                    tab01 = Integer.parseInt(spiner2.getSelectedItem() + "");
                    btrc.setContext(act);
                    URL = "http://" + IP + "/cafeteria/registerCommand.php";


                    btrc.setURL(URL);


                     btrc.execute(textInfo.getText() + "", totalPrice.getText() + "",
                             tab01 + "");
                } catch (Exception e) {
                    Toast.makeText(DemandProduct.this, "please select the table", Toast.LENGTH_LONG).show();
                }
            }else  Toast.makeText(DemandProduct.this, "no command added ", Toast.LENGTH_LONG).show();

            }
        });


        designation = new ArrayList<>();
        description = new ArrayList<>();
        price = new ArrayList<>();
        dia = new ProgressDialog(DemandProduct.this);

        dia.setTitle("charging data ");
        dia.setMessage("please wait....");
        dia.show();


        requestQueue = Volley.newRequestQueue(this);

        URL = "http://" + IP + "/cafeteria/recuperatListProduct.php";


        request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>()

        {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (!jsonObject.names().get(0).equals("no response")) {

                        designation = clasify(jsonObject.getString("designation"));
                        description = clasify(jsonObject.getString("description"));
                        price = clasify(jsonObject.getString("price"));
                        itemsAdapter =
                                new Adapter2(designation, price, description, DemandProduct.this);
                        ls.setAdapter(itemsAdapter);
                        dia.dismiss();

                    } else {
                        Toast.makeText(getApplicationContext(), "Error " + jsonObject.getString("eror"), Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        requestQueue.add(request);


    }


    ArrayList<String> clasify(String s) {
        int i = 0;
        String s1 = "";
        ArrayList<String> h = new ArrayList();
        while (i < s.length()) {
            if (s.charAt(i) != '*') {
                s1 = s1 + s.charAt(i);
            } else {
                h.add(s1);
                s1 = "";
            }


            i++;
        }//h.add(s1);
        return h;
    }

    class Adapter2 extends BaseAdapter {


        ArrayList<String> aryDesignation;
        ArrayList<String> aryPrice;
        ArrayList<String> aryDescription;
        Activity act;

        public Adapter2(ArrayList<String> aryDesignation, ArrayList<String> aryPrice,
                        ArrayList<String> aryDescription, Activity act) {

            this.aryDesignation = aryDesignation;
            this.aryPrice = aryPrice;
            this.aryDescription = aryDescription;
            this.act = act;

        }


        @Override
        public int getCount() {

            return aryDesignation.size();
        }

        @Override
        public Object getItem(int position) {

            return null;
        }

        @Override
        public long getItemId(int position) {

            return 0;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            convertView = getLayoutInflater().inflate(R.layout.list_adapter2, null);
            TextView desg = (TextView) convertView.findViewById(R.id.desg);
            TextView description = (TextView) convertView.findViewById(R.id.description);
            TextView price = (TextView) convertView.findViewById(R.id.TxtFprice);


            desg.setText(aryDesignation.get(position));
            description.setText("Add into command");
            price.setText("" + aryPrice.get(position) + " DT");


            description.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String g = spiner1.getSelectedItem().toString();
                    try {
                        double totalPr = Double.parseDouble(aryPrice.get(position).toString()) * Double.parseDouble(g);
                        TOTALPR = TOTALPR + totalPr;
                        totalPrice.setText(TOTALPR + "");
                        String info1 = "Des: " + aryDesignation.get(position) + " Qt:" + g + " Pr:" + aryPrice.get(position).toString() + "\n";
                        info = info + info1;
                        textInfo.setText(info);
                    } catch (Exception e) {
                        Toast.makeText(DemandProduct.this, "select quantity ", Toast.LENGTH_LONG).show();
                    }
                }
            });


            return convertView;
        }


    }


}

class BackgroundThreadRegisterCommands extends AsyncTask<String, String, String> {
    String URL;

    public void setURL(String URL) {
        this.URL = URL;
    }

    @Override
    protected String doInBackground(String... params) {
        String data = "";
        int tmp;
        String command_msg = params[0];
        String total_price = params[1];
        String n_table = params[2];

        try {
            java.net.URL url = new URL(URL);
            String urlParams = "command_msg=" + command_msg + "&total_price=" + total_price +
                    "&n_table=" + n_table;
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setDoOutput(true);
            OutputStream os = httpURLConnection.getOutputStream();
            os.write(urlParams.getBytes());
            os.flush();
            os.close();
            InputStream is = httpURLConnection.getInputStream();
            while ((tmp = is.read()) != -1) {
                data += (char) tmp;
            }
            is.close();
            httpURLConnection.disconnect();

        } catch (Exception e) {
            Toast.makeText(cnx, "please verify your connection into url page ", Toast.LENGTH_LONG).show();
        }

        return data;
    }

    public void setContext(Context c) {
        //for check if data saved or not
        this.cnx = c;
    }

    Context cnx;

    @Override

    protected void onPostExecute(String s) {

            //for check if data saved or not
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(s);

            if(jsonObject.getString("ok").equals("ok")){
                Toast.makeText(cnx, "command saved successfully ", Toast.LENGTH_LONG).show();
            }else{
                Toast.makeText(cnx, "Failed to save command ", Toast.LENGTH_LONG).show();
            }
        } catch (JSONException e) {
            Toast.makeText(cnx, "there is an error  ", Toast.LENGTH_LONG).show();
        }

    }


}