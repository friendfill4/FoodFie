package com.friendfill.foodifie;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.friendfill.foodifie.fragment.BillFragment;
import com.friendfill.foodifie.fragment.HomeFragment;
import com.friendfill.foodifie.fragment.MenuFragment;
import com.friendfill.foodifie.fragment.MenuMoreFragment;
import com.friendfill.foodifie.fragment.MoreFragment;
import com.friendfill.foodifie.fragment.OrderFragment;
import com.friendfill.foodifie.fragment.SearchItemFragment;
import com.friendfill.foodifie.fragment.StaffFragment;
import com.friendfill.foodifie.fragment.TableFragment;
import com.friendfill.foodifie.helper.BottomNavigationViewHelper;

import butterknife.BindView;
import butterknife.ButterKnife;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class MainActivity extends AppCompatActivity implements HomeFragment.OnFragmentInteractionListener, TableFragment.OnFragmentInteractionListener, MenuFragment.OnFragmentInteractionListener, MenuMoreFragment.OnFragmentInteractionListener, SearchItemFragment.OnFragmentInteractionListener, BillFragment.OnFragmentInteractionListener, MoreFragment.OnFragmentInteractionListener, StaffFragment.OnFragmentInteractionListener, OrderFragment.OnFragmentInteractionListener {

    @Nullable
    @BindView(R.id.navigation)
    BottomNavigationView navigation;
    @Nullable
    @BindView(R.id.fragment_container)
    LinearLayout fragment_container;
    @Nullable
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    TextView toolbar_title;
    FragmentManager fragmentManager;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("Fragment Life", "Activity Home On Create");
        setContentView(R.layout.activity_main);
        ButterKnife.setDebug(true);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        fragmentManager = getSupportFragmentManager();
        fragmentManager.addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {
                if (fragmentManager.getBackStackEntryCount() == 0) {
                    finish();
                }
            }
        });
        LoadFragment(R.id.menu_item_home);
        toolbar_title = (TextView) toolbar.findViewById(R.id.toolbar_title);
        BottomNavigationViewHelper.disableShiftMode(navigation);

        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                LoadFragment(item.getItemId());
                return true;
            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("Fragment Life", "Activity Home On Resume");
        RemoveToolbarBack(null);
    }

    public void LoadFragment(int FragmentType) {

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Fragment fragment = fragmentManager.findFragmentByTag("" + FragmentType);
        if (fragment != null) {
            fragmentTransaction.replace(R.id.fragment_container, fragment, "" + FragmentType);
        } else {
            switch (FragmentType) {

                case R.id.menu_item_home:
                    HomeFragment homeFragment = new HomeFragment();
                    fragmentTransaction.replace(R.id.fragment_container, homeFragment, "" + FragmentType);
                    RemoveToolbarBack(homeFragment);
                    fragmentTransaction.addToBackStack("");
                    break;
                case R.id.menu_item_table:
                    TableFragment tableFragment = new TableFragment();

                    fragmentTransaction.replace(R.id.fragment_container, tableFragment, "" + FragmentType);
                    RemoveToolbarBack(tableFragment);
                    fragmentTransaction.addToBackStack("");
                    break;
                case R.id.menu_item_menu:
                    MenuFragment menuFragment = new MenuFragment();
                    fragmentTransaction.replace(R.id.fragment_container, menuFragment, "" + FragmentType);
                    fragmentTransaction.addToBackStack("");
                    RemoveToolbarBack(menuFragment);
                    break;
                case R.id.action_search_home:
                    SearchItemFragment searchFragment = new SearchItemFragment();
                    fragmentTransaction.replace(R.id.fragment_container, searchFragment, "" + FragmentType);
                    fragmentTransaction.addToBackStack("");
                    RemoveToolbarBack(searchFragment);
                    break;
                case R.id.menu_item_bill:
                    BillFragment billFragment = new BillFragment();
                    fragmentTransaction.replace(R.id.fragment_container, billFragment, "" + FragmentType);
                    fragmentTransaction.addToBackStack("");
                    RemoveToolbarBack(billFragment);
                    break;
                case R.id.menu_item_more:
                    MoreFragment moreFragment = new MoreFragment();
                    fragmentTransaction.replace(R.id.fragment_container, moreFragment, "" + FragmentType);
                    fragmentTransaction.addToBackStack("");
                    RemoveToolbarBack(moreFragment);
                    break;

            }
        }


        fragmentTransaction.commit();

    }

    public void LoadFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.addToBackStack("");
        fragmentTransaction.commit();

    }


    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void SetTitleBarTitle(String title) {
        toolbar_title.setText(title);
    }

    @Override
    public void SetNavigationItem(int item) {
        navigation.setSelectedItemId(item);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @SuppressLint("RestrictedApi")
    @Override
    public void SetToolbarBack(Fragment fragment) {
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(getDrawable(R.drawable.ic_action_arrow_left));

    }

    @SuppressLint("RestrictedApi")
    @Override
    public void RemoveToolbarBack(Fragment fragment) {
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
    }

    @Override
    public void RemoveFragment(Fragment fragment) {
        getSupportFragmentManager().popBackStackImmediate();
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("Fragment Life", "Activity Home On Stop");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("Fragment Life", "Activity Home On Start");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("Fragment Life", "Activity Home On Destroy");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_search_home)
            LoadFragment(R.id.action_search_home);
        return super.onOptionsItemSelected(item);
    }
}
