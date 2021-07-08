package com.sbtopzzz.quicc.Activities;

import android.os.Bundle;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.navigation.NavigationView;
import com.sbtopzzz.quicc.Activities.UserHomeActivity_Fragments.SharedClasses.CurrentUser;
import com.sbtopzzz.quicc.Activities.UserHomeActivity_Fragments.UserHomeActivity_Fragment_Home;
import com.sbtopzzz.quicc.Activities.UserHomeActivity_Fragments.UserHomeActivity_Fragment_Invitations;
import com.sbtopzzz.quicc.HelperClasses.NavDrawers;
import com.sbtopzzz.quicc.HelperClasses.SP;
import com.sbtopzzz.quicc.R;

public class UserHomeActivity extends AppCompatActivity {
    private FrameLayout root;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        NavDrawers.addNavDrawerWithFragmentSupport(this, R.menu.userhomeactivity_menu1, new NavDrawers.NavItemActionHandler() {
            @Override
            public void onCreate(@Nullable FrameLayout root, @NonNull NavigationView navView) {
                UserHomeActivity.this.root = root;

                setFragment(new UserHomeActivity_Fragment_Home(UserHomeActivity.this));
                navView.setCheckedItem(R.id.nav_home);
            }

            @Override
            public void onMenuItemClick(int itemId, @NonNull NavDrawers.NavOptions options) {
                if (itemId == R.id.nav_home) {
                    setFragment(new UserHomeActivity_Fragment_Home(UserHomeActivity.this));
                } else if (itemId == R.id.nav_invitations) {
                    setFragment(new UserHomeActivity_Fragment_Invitations());
                } else if (itemId == R.id.nav_logout) {
                    SP.push("userLoginToken", null);
                    SP.push("userLoginEmail", null);

                    CurrentUser.user = null;

                    finish();
                }
            }
        });
    }

    private void setFragment(Fragment fragment) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(root.getId(), fragment);
        ft.commit();
    }
}