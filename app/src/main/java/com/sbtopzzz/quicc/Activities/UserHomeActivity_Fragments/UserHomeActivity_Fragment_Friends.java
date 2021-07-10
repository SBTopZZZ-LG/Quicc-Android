package com.sbtopzzz.quicc.Activities.UserHomeActivity_Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.sbtopzzz.quicc.API.Funcs;
import com.sbtopzzz.quicc.API.Schemas.User;
import com.sbtopzzz.quicc.API.Schemas.UserFriend;
import com.sbtopzzz.quicc.Activities.UserHomeActivity_Fragments.SharedClasses.CurrentUser;
import com.sbtopzzz.quicc.Activities.UserHomeActivity_Fragments.UserHomeActivity_Fragment_Friends_Objects.Friend;
import com.sbtopzzz.quicc.Activities.UserHomeActivity_Fragments.UserHomeActivity_Fragment_Friends_Objects.MyFriendsAdapter;
import com.sbtopzzz.quicc.Activities.UserSearchActivity;
import com.sbtopzzz.quicc.R;

import java.util.ArrayList;
import java.util.Currency;
import java.util.List;

public class UserHomeActivity_Fragment_Friends extends Fragment {
    private RecyclerView rvMyFriends;
    private FloatingActionButton fab;

    public UserHomeActivity_Fragment_Friends() {
        // Required empty public constructor
    }

    public static UserHomeActivity_Fragment_Friends newInstance() {
        UserHomeActivity_Fragment_Friends fragment = new UserHomeActivity_Fragment_Friends();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View main = inflater.inflate(R.layout.fragment_user_home_activity___friends, container, false);
        Initialize(main);
        GetMyFriends();

        return main;
    }

    private void Initialize(View parent) {
        rvMyFriends = parent.findViewById(R.id.rvMyFriends);
        fab = parent.findViewById(R.id.fabSearch);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), UserSearchActivity.class));
            }
        });
    }

    private void GetMyFriends() {
        Funcs.userFriendsGet(CurrentUser.user.getEmailId(), new Funcs.UserFriendsGetResult() {
            @Override
            public void onSuccess(@NonNull List<UserFriend> friends) {
                List<Friend> uiFriends = new ArrayList<>();

                MyFriendsAdapter adapter = new MyFriendsAdapter(uiFriends);

                for (UserFriend friend : friends) {
                    Funcs.userGetByUid(CurrentUser.user.getEmailId(), friend.uid, new Funcs.UserGetResult() {
                        @Override
                        public void onSuccess(@NonNull User user) {
                            uiFriends.add(new Friend(user.uid, user.getName(), user.getEmailId()));

                            adapter.notifyDataSetChanged();
                        }

                        @Override
                        public void onWarning(String errorText) {

                        }

                        @Override
                        public void onFailure(@NonNull Throwable t) {

                        }
                    });
                }

                rvMyFriends.setHasFixedSize(true);
                rvMyFriends.setLayoutManager(new LinearLayoutManager(getContext()));
                rvMyFriends.setAdapter(adapter);
            }

            @Override
            public void onWarning(String errorText) {
                Toast.makeText(getContext(), "Warning: " + errorText, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(@NonNull Throwable t) {
                Toast.makeText(getContext(), "Failure: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}