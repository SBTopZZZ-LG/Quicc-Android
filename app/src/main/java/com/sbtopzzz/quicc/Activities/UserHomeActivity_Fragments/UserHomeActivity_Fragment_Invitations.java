package com.sbtopzzz.quicc.Activities.UserHomeActivity_Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sbtopzzz.quicc.R;

public class UserHomeActivity_Fragment_Invitations extends Fragment {
    public UserHomeActivity_Fragment_Invitations() {
        // Required empty public constructor
    }
    public static UserHomeActivity_Fragment_Invitations newInstance(String param1, String param2) {
        UserHomeActivity_Fragment_Invitations fragment = new UserHomeActivity_Fragment_Invitations();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user_home_activity___invitations, container, false);
    }
}