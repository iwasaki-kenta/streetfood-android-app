package hkust.com.bitwise;

import android.app.Activity;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import hkust.com.bitwise.fragments.BrowseFoodFragment_;
import hkust.com.bitwise.fragments.BrowseOrdersFragment_;
import hkust.com.bitwise.fragments.LikesFragment_;
import hkust.com.bitwise.fragments.PopularVendorsFragment_;
import hkust.com.bitwise.utils.FragmentUtil;

@EActivity(R.layout.activity_main)
public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, FragmentManager.OnBackStackChangedListener {

    @ViewById
    Toolbar toolbar;

    @ViewById
    DrawerLayout drawer;

    @ViewById
    NavigationView navigationView;

    @ViewById
    RelativeLayout currentFragment;

    Fragment browseFoodFragment;
    Fragment browseOrdersFragment;
    Fragment popularFragment;
    Fragment likesFragment;

    Fragment nextFragment;

    DrawerListener toggle;

    @AfterViews
    void initState() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toggle = new DrawerListener(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        toggle.setToolbarNavigationClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!toggle.isDrawerIndicatorEnabled()) {
                    getSupportFragmentManager().popBackStackImmediate();
                }
            }
        });
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);

        browseFoodFragment = BrowseFoodFragment_.builder().build();
        browseOrdersFragment = BrowseOrdersFragment_.builder().build();
        popularFragment = PopularVendorsFragment_.builder().build();
        likesFragment = LikesFragment_.builder().build();

        FragmentUtil.gotoFragment(getSupportFragmentManager(), browseFoodFragment, false, null);

        getSupportFragmentManager().addOnBackStackChangedListener(this);
    }


    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (!item.isChecked()) {
            item.setChecked(true);

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    drawer.closeDrawers();
                }
            }, 150);

            if (id == R.id.browse_vendors) {
                nextFragment = browseFoodFragment;
            } else if (id == R.id.popular_vendors) {
                nextFragment = popularFragment;
            } else if (id == R.id.liked_vendors) {
                nextFragment = likesFragment;
            } else if (id == R.id.orders) {
                nextFragment = browseOrdersFragment;
            }

            drawer.closeDrawer(GravityCompat.START);
        }
        return true;
    }

    @Override
    public void onBackStackChanged() {
        int backStackEntryCount = getSupportFragmentManager()
                .getBackStackEntryCount();
        toggle.setDrawerIndicatorEnabled(backStackEntryCount == 0);
    }

    private class DrawerListener extends ActionBarDrawerToggle {

        public DrawerListener(Activity activity, DrawerLayout drawerLayout,
                              Toolbar toolbar, int openDrawerContentDescRes,
                              int closeDrawerContentDescRes) {
            super(activity, drawerLayout, toolbar, openDrawerContentDescRes,
                    closeDrawerContentDescRes);
        }

        public void onDrawerOpened(View drawerView) {
            setDrawerIndicatorEnabled(true);
        }

        @Override
        public void onDrawerClosed(View view) {
            if (nextFragment != null) {
                getSupportFragmentManager().popBackStack("",
                        FragmentManager.POP_BACK_STACK_INCLUSIVE);
                FragmentUtil.gotoFragment(getSupportFragmentManager(), nextFragment, false, null);

                nextFragment = null;
            }

            int backStackEntryCount = getSupportFragmentManager()
                    .getBackStackEntryCount();
            setDrawerIndicatorEnabled(backStackEntryCount == 0);
        }

    }
}
