package com.sbtopzzz.quicc.HelperClasses;

import android.app.Activity;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import com.sbtopzzz.quicc.R;

public class NavDrawers {
    public static void addNavDrawer(@NonNull AppCompatActivity activity, int LAYOUT, int menu, @NonNull NavItemActionHandler handler) {
        DrawerLayout root = (DrawerLayout) View.inflate(activity, R.layout.helperclass_navdrawers_1, null);
        ((Activity) activity).setContentView(root);

        FrameLayout frameLayout = root.findViewById(R.id.fragment_container);
        View main = View.inflate(activity, LAYOUT, null);
        frameLayout.addView(main);

        Toolbar toolbar = root.findViewById(R.id.toolbar);
        activity.setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle((Activity) activity, root, toolbar,
                R.string.navigation_open, R.string.navigation_close);
        root.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navView = root.findViewById(R.id.nav_view);
        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();

                handler.onMenuItemClick(id, new NavOptions() {
                    @Override
                    public NavigationView getNavigationView() {
                        return navView;
                    }
                });

                root.closeDrawer(GravityCompat.START);
                return true;
            }
        });

        navView.inflateMenu(menu);

        handler.onCreate(null, navView);
    }
    public static void addNavDrawerWithFragmentSupport(@NonNull AppCompatActivity activity, int menu, @NonNull NavItemActionHandler handler) {
        DrawerLayout root = (DrawerLayout) View.inflate(activity, R.layout.helperclass_navdrawers_1, null);
        ((Activity) activity).setContentView(root);

        Toolbar toolbar = root.findViewById(R.id.toolbar);
        activity.setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(activity, root, toolbar,
                R.string.navigation_open, R.string.navigation_close);
        root.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navView = root.findViewById(R.id.nav_view);
        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();

                handler.onMenuItemClick(id, new NavOptions() {
                    @Override
                    public NavigationView getNavigationView() {
                        return navView;
                    }
                });

                root.closeDrawer(GravityCompat.START);
                return true;
            }
        });

        navView.inflateMenu(menu);

        handler.onCreate(root.findViewById(R.id.fragment_container), navView);
    }
    public interface NavItemActionHandler {
        void onCreate(@Nullable FrameLayout root, @NonNull NavigationView navView);
        void onMenuItemClick(int itemId, @NonNull NavOptions options);
    }
    public interface NavOptions {
        NavigationView getNavigationView();
    }
}