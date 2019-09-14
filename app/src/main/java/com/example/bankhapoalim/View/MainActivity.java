package com.example.bankhapoalim.View;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.bankhapoalim.R;
import com.example.bankhapoalim.Utils.Utils;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

public class MainActivity extends AppCompatActivity implements
        BottomNavigationView.OnNavigationItemSelectedListener {

    public NavController navController;

    public BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide(); //hide the title bar

        if (!Utils.isNetworkConnected(this)) {
            showNoInternetAlert();
        }else{
            setupNavigation();
        }
    }

    private void setupNavigation() {

        bottomNavigationView = findViewById(R.id.bottomNavigationView);

        navController = Navigation.findNavController(this, R.id.navHostFragment);

        NavigationUI.setupWithNavController(bottomNavigationView, navController);

        bottomNavigationView.setOnNavigationItemSelectedListener(this);

    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        menuItem.setChecked(true);

        int id = menuItem.getItemId();

        switch (id) {

            case R.id.movies:
                navController.navigate(R.id.moviesFragment, null,
                        new NavOptions.Builder()
                                .setLaunchSingleTop(true).build());
                break;
            case R.id.favorites:
                navController.navigate(R.id.favoritesFragment, null,
                        new NavOptions.Builder().setLaunchSingleTop(true).build());
                break;

        }
        return true;

    }

    private void showNoInternetAlert() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(getString(R.string.no_internet))
                .setCancelable(false)
                .setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        finish();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }
}
