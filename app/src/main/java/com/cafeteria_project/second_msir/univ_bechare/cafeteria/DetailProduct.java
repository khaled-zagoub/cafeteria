package com.cafeteria_project.second_msir.univ_bechare.cafeteria;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class DetailProduct extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_product);
        Intent i=getIntent();
        TextView txt=(TextView)findViewById(R.id.textVDesc);
        txt.setText(i.getStringExtra("desc"));
        ImageView Im=(ImageView)findViewById(R.id.imageView2);

        int []images={R.drawable.black_coffe,R.drawable.coffe_milk,R.drawable.mille_feul,
                R.drawable.chamiya,R.drawable.basbousa,R.drawable.makroute,
                R.drawable.pain_chocolat,
                R.drawable.croissant};

        switch (i.getStringExtra("desg").toString()){
            case "black coffe":{
                Im.setImageResource(images[0]);break;
            }
            case "coffe with milk":{
                Im.setImageResource(images[1]);break;
            }case "chamiya":{
                Im.setImageResource(images[3]);break;
            }
            case "cake":{
                Im.setImageResource(images[4]);break;
            }case "makroute":{
                Im.setImageResource(images[5]);break;
            }case "pain chocolat":{
                Im.setImageResource(images[6]);break;
            }case "croissant":{
                Im.setImageResource(images[7]);break;
            }
            case "mille_feuille":{
                Im.setImageResource(images[2]);break;
            }
    }
}

}
