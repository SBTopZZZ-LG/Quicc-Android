package com.sbtopzzz.quicc.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.sbtopzzz.quicc.Activities.UserHomeActivity_Fragments.SharedClasses.CurrentUser;
import com.sbtopzzz.quicc.Activities.UserHomeActivity_Fragments.UserHomeActivity_Fragment_Friends;
import com.sbtopzzz.quicc.Activities.UserHomeActivity_Fragments.UserHomeActivity_Fragment_Home;
import com.sbtopzzz.quicc.Activities.UserHomeActivity_Fragments.UserHomeActivity_Fragment_Invitations;
import com.sbtopzzz.quicc.HelperClasses.NavDrawers;
import com.sbtopzzz.quicc.HelperClasses.SP;
import com.sbtopzzz.quicc.R;

public class UserHomeActivity extends AppCompatActivity {
    private FrameLayout root;

    // Fragments
    private UserHomeActivity_Fragment_Home fragment_home;
    private UserHomeActivity_Fragment_Invitations fragment_invitations;
    private UserHomeActivity_Fragment_Friends fragment_friends;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        NavDrawers.addNavDrawerWithFragmentSupport(this, R.menu.userhomeactivity_menu1, new NavDrawers.NavItemActionHandler() {
            @Override
            public void onCreate(@Nullable FrameLayout root, @NonNull NavigationView navView) {
                UserHomeActivity.this.root = root;

                fragment_home = new UserHomeActivity_Fragment_Home(UserHomeActivity.this);
                fragment_invitations = new UserHomeActivity_Fragment_Invitations(UserHomeActivity.this);
                fragment_friends = new UserHomeActivity_Fragment_Friends();

                setFragment(fragment_home);
                navView.setCheckedItem(R.id.nav_home);
            }

            @Override
            public void onMenuItemClick(int itemId, @NonNull NavDrawers.NavOptions options) {
                if (itemId == R.id.nav_home) {
                    setFragment(fragment_home);
                } else if (itemId == R.id.nav_invitations) {
                    setFragment(fragment_invitations);
                } else if (itemId == R.id.nav_friends) {
                    setFragment(fragment_friends);
                } else if (itemId == R.id.nav_logout) {
                    signOut();
                }
            }
        });
    }

    private void setFragment(Fragment fragment) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(root.getId(), fragment);
        ft.commit();
    }

    private boolean signedOut = false;
    private void signOut() {
        SP.push("userLoginToken", null);
        SP.push("userLoginEmail", null);

        CurrentUser.user = null;

        finish();
    }

    @Override
    public void onBackPressed() {
        if (signedOut)
            super.onBackPressed();
        else {
            signOut();
            signedOut = true;
        }
    }
}