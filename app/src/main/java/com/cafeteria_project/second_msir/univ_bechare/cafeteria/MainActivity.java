package com.cafeteria_project.second_msir.univ_bechare.cafeteria;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle mtoggle;
    NavigationView navigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        navigationView= (NavigationView) findViewById(R.id.drawer_nav);
        drawerLayout= (DrawerLayout) findViewById(R.id.drawer);
        mtoggle=new ActionBarDrawerToggle(this,drawerLayout,R.string.open,R.string.Close);
        drawerLayout.addDrawerListener(mtoggle);
        mtoggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        navigationView.setNavigationItemSelectedListener(nav);

    }
    NavigationView.OnNavigationItemSelectedListener nav=new NavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(MenuItem item) {
           switch (item.getItemId()) {
               case R.id.home: {
                   startActivity(new Intent(MainActivity.this, ListProduct.class));
                   break;
               }

               case R.id.add: {
                   Intent i = new Intent(MainActivity.this, DemandProduct.class);
                   startActivity(i);
                   break;
               }
               case R.id.search: {
                   Intent i = new Intent(MainActivity.this, MapsActivity.class);
                   startActivity(i);
                   break;
               }
               case R.id.about: {
                   Intent i = new Intent(MainActivity.this, About.class);
                   startActivity(i);
                   break;
               }
           }
       return true; }
    };

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(mtoggle.onOptionsItemSelected(item))
            return true;

   return super.onOptionsItemSelected(item);}
}
