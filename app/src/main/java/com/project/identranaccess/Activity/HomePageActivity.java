package com.project.identranaccess.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.project.identranaccess.Fragment.HomeFragment;
import com.project.identranaccess.Fragment.ProfileFragment;
import com.project.identranaccess.Fragment.HistoricalListFragment;
import com.project.identranaccess.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomePageActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
    private long pressedTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);

        bottomNav.setOnNavigationItemSelectedListener(this);
        bottomNav.setSelectedItemId(R.id.home);

        // bottomNav.setPointerIcon(R.drawable.circle_background);

        //    bottomNav.setOnNavigationItemSelectedListener(navListener);
        //    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();

    }

    HomeFragment firstFragment = new HomeFragment();
    HistoricalListFragment secondFragment = new HistoricalListFragment();
    ProfileFragment thirdFragment = new ProfileFragment();
    public static final int MY_BUTTON = R.id.home;

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
   //     switch (item.getItemId()) {

        if(item.getItemId()==R.id.home){
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, firstFragment)
                    .commit();
        } else if(item.getItemId()==R.id.historical){
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, secondFragment)
                    .commit();
        } else if(item.getItemId()==R.id.profile){
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, thirdFragment)
                    .commit();
        }

        return true;

         /*   case R.id.historical:
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, secondFragment)
                        .commit();
                return true;

            case R.id.profile:
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, thirdFragment)
                        .commit();
               */
       // }
//item.setIcon(R.drawable.historical_icon);
      //  return false;
    }

    public void onBackPressed() {

        if (pressedTime + 2000 > System.currentTimeMillis()) {
            super.onBackPressed();
            finish();
        } else {
            Toast.makeText(getBaseContext(), "Press back again to exit", Toast.LENGTH_SHORT).show();
        }
        pressedTime = System.currentTimeMillis();

    }
}


  /*  private final BottomNavigationView.OnNavigationItemSelectedListener navListener = item -> {
        // By using switch we can easily get
        // the selected fragment
        // by using there id.
        Fragment selectedFragment = null;
        int itemId = item.getItemId();
        if (itemId == R.id.algorithm) {
            selectedFragment = new HomeFragment();
        } else if (itemId == R.id.course) {
            selectedFragment = new VisitorFragment();
        } else if (itemId == R.id.profile) {
            selectedFragment = new ProfileFragment();
        }
        // It will help to replace the
        // one fragment to other.
        if (selectedFragment != null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();
        }

        return true;
    };*/



