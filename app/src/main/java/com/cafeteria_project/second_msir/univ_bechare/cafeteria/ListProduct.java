package com.cafeteria_project.second_msir.univ_bechare.cafeteria;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ListProduct extends AppCompatActivity {
    private RequestQueue requestQueue;
    private static String URL = "";
    private StringRequest request;
    ListView ls;
    EditText ip, port;
    ArrayList<String> designation, price, description;
    ProgressDialog dia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_product);

        ls = (ListView) findViewById(R.id.listView);

        designation = new ArrayList<>();
        description = new ArrayList<>();
        price = new ArrayList<>();
        dia = new ProgressDialog(ListProduct.this);


        dia.setTitle("charging data ");
        dia.setMessage("please wait....");
     //   dia.show();


        requestQueue = Volley.newRequestQueue(this);

        String IP = "192.168.1.19";
        String PORT = "100";
        URL = "http://" + IP +  "/cafeteria/recuperatListProduct.php";
        System.out.println("URL123 : "+URL);

        request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>()

        {
            @Override
            public void onResponse(String response) {
                System.out.println("REPPP"+response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (!jsonObject.names().get(0).equals("eror")) {

                        designation = clasify(jsonObject.getString("designation"));
                        description = clasify(jsonObject.getString("description"));
                        price = clasify(jsonObject.getString("price"));
                        itemsAdapter =
                                new Adapter(designation, price, description, ListProduct.this);
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
                System.out.println("error message : "+error.getMessage());
            }
        }) ;

        requestQueue.add(request);
    }

    Adapter itemsAdapter;

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
        }
        return h;
    }

    class Adapter extends BaseAdapter {

        ArrayList<String> aryDesignation;
        ArrayList<String> aryPrice;
        ArrayList<String> aryDescription;
        Activity act;
        int[] images = {R.drawable.black_coffe, R.drawable.coffe_milk, R.drawable.mille_feul,
                R.drawable.chamiya, R.drawable.basbousa, R.drawable.makroute, R.drawable.pain_chocolat,
                R.drawable.croissant};

        public Adapter(ArrayList<String> aryDesignation, ArrayList<String> aryPrice,
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
            convertView = getLayoutInflater().inflate(R.layout.list_adapter, null);
            TextView desg = (TextView) convertView.findViewById(R.id.desg);
            TextView description = (TextView) convertView.findViewById(R.id.description);
            TextView price = (TextView) convertView.findViewById(R.id.TxtFprice);
            ImageView Im = (ImageView) convertView.findViewById(R.id.imageView);


            desg.setText(aryDesignation.get(position));
            description.setText("Description");
            price.setText("" + aryPrice.get(position) + " DT");
            String r ="";r= aryDesignation.get(position).toString();

            switch (r) {
                case "black coffe": {
                    Im.setImageResource(images[0]);
                    break;
                }
                case "coffe with milk": {
                    Im.setImageResource(images[1]);
                    break;
                }
                case "gateau": {
                    Im.setImageResource(images[3]);
                    break;
                }
                case "cake": {
                    Im.setImageResource(images[4]);
                    break;
                }
                /*case "makroute": {
                    Im.setImageResource(images[5]);
                    break;
                }*/
                case "pain chocolat": {
                    Im.setImageResource(images[6]);
                    break;
                }
                case "croissant": {
                    Im.setImageResource(images[7]);
                    break;
                }
                case "mille feuille": {

                    Im.setImageResource(images[2]);
                    break;
                }

            }

            description.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(act, DetailProduct.class);

                    i.putExtra("desg", aryDesignation.get(position));
                    i.putExtra("desc", aryDescription.get(position));


                    startActivity(i);
               }
            });

           return convertView;
        }


    }

}
