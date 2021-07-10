package com.sbtopzzz.quicc.Activities.UserHomeActivity_Fragments.UserHomeActivity_Fragment_Friends_Objects;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sbtopzzz.quicc.Activities.UserHomeActivity_Fragments.UserHomeActivity_Fragment_Home_Objects.MyEventsAdapter;
import com.sbtopzzz.quicc.R;

import java.util.List;

public class MyFriendsAdapter extends RecyclerView.Adapter<MyFriendsAdapter.ViewHolder> {
    private final List<Friend> friends;

    public MyFriendsAdapter(List<Friend> friends) {
        this.friends = friends;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.userhomeactivity_friends_myfriends_tile1, parent, false);
        MyFriendsAdapter.ViewHolder viewHolder = new MyFriendsAdapter.ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyFriendsAdapter.ViewHolder holder, int position) {
        Friend friend = friends.get(position);

        holder.tvUserName.setText(friend.getUserName());
        holder.tvEmailID.setText(friend.getUserEmailId());
    }

    @Override
    public int getItemCount() {
        return friends.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvUserName, tvEmailID;

        public ViewHolder(View holder) {
            super(holder);

            tvUserName = holder.findViewById(R.id.tvUserName);
            tvEmailID = holder.findViewById(R.id.tvEmailID);
        }
    }
}
