package com.android4dev.navigationview;

import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    //Defining Variables
    private Toolbar toolbar;
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initializing Toolbar and setting it as the actionbar
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Initializing NavigationView
        navigationView = (NavigationView)
                findViewById(R.id.navigation_view);

        //Default Fragment
        HomeFragment homefragment = new HomeFragment();
        android.support.v4.app.FragmentTransaction homeFragmentTransaction
                = getSupportFragmentManager().beginTransaction();
        homeFragmentTransaction.replace(R.id.frame,homefragment);
        homeFragmentTransaction.commit();

        //Setting Navigation View Item Selected Listener to handle the item click of the navigation menu
        navigationView.setNavigationItemSelectedListener
                (new NavigationView.OnNavigationItemSelectedListener() {

            // This method will trigger on item Click of navigation menu
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                //Checking if the item is in checked state or not, if not make it in checked state
                if(menuItem.isChecked()) menuItem.setChecked(false);
                else menuItem.setChecked(true);

                //Closing drawer on item click
                drawerLayout.closeDrawers();

                //Check to see which item was being clicked and perform appropriate action
                switch (menuItem.getItemId()){

                    case R.id.home:
                        HomeFragment homefragment = new HomeFragment();
                        android.support.v4.app.FragmentTransaction homeFragmentTransaction
                                = getSupportFragmentManager().beginTransaction();
                        homeFragmentTransaction.replace(R.id.frame,homefragment);
                        homeFragmentTransaction.commit();
                        return true;

                    case R.id.a1:

                        DoctorsFragment a1fragment = new DoctorsFragment();
                        android.support.v4.app.FragmentTransaction a1FragmentTransaction
                                = getSupportFragmentManager().beginTransaction();
                        a1FragmentTransaction.replace(R.id.frame,a1fragment);
                        a1FragmentTransaction.commit();
                        return true;

/*
                    case R.id.a7:
                        NotificationsFragment a7fragment = new NotificationsFragment();
                        android.support.v4.app.FragmentTransaction a7FragmentTransaction
                                = getSupportFragmentManager().beginTransaction();
                        a7FragmentTransaction.replace(R.id.frame,a7fragment);
                        //a7FragmentTransaction.commit();
                        return true;

                    case R.id.a8:
                        SettingsFragment a8fragment = new SettingsFragment();
                        android.support.v4.app.FragmentTransaction a8FragmentTransaction
                                = getSupportFragmentManager().beginTransaction();
                        a8FragmentTransaction.replace(R.id.frame,a8fragment);
                        //a8FragmentTransaction.commit();
                        return true;
*/

                    case R.id.a10:
                        RoomsFragment a10fragment = new RoomsFragment();
                        android.support.v4.app.FragmentTransaction a10FragmentTransaction
                                = getSupportFragmentManager().beginTransaction();
                        a10FragmentTransaction.replace(R.id.frame,a10fragment);
                        a10FragmentTransaction.commit();
                        return true;


                    case R.id.a23:
                        BoardsFragment a23fragment = new BoardsFragment();
                        android.support.v4.app.FragmentTransaction a23FragmentTransaction
                                = getSupportFragmentManager().beginTransaction();
                        a23FragmentTransaction.replace(R.id.frame,a23fragment);
                        a23FragmentTransaction.commit();
                        return true;

                    default:
                        Toast.makeText(getApplicationContext(),"Please try again",Toast.LENGTH_SHORT).show();
                        return true;

                }
            }
        });

        // Initializing Drawer Layout and ActionBarToggle
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        ActionBarDrawerToggle actionBarDrawerToggle
                = new ActionBarDrawerToggle(this,drawerLayout,toolbar,
                R.string.openDrawer, R.string.closeDrawer){

            @Override
            public void onDrawerClosed(View drawerView) {
                // Code here will be triggered once the drawer closes as we dont want anything to happen so we leave this blank
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                // Code here will be triggered once the drawer open as we dont want anything to happen so we leave this blank
                super.onDrawerOpened(drawerView);
            }
        };

        //Setting the actionbarToggle to drawer layout
        drawerLayout.setDrawerListener(actionBarDrawerToggle);

        //calling sync state is necessay or else your hamburger icon wont show up
        actionBarDrawerToggle.syncState();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
