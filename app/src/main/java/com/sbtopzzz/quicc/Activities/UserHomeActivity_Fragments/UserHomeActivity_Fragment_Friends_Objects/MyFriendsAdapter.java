package com.sbtopzzz.quicc.Activities.UserHomeActivity_Fragments.UserHomeActivity_Fragment_Friends_Objects;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sbtopzzz.quicc.API.Funcs;
import com.sbtopzzz.quicc.Activities.UserHomeActivity_Fragments.SharedClasses.CurrentUser;
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
        View listItem = layoutInflater.inflate(R.layout.userhomeactivity_friends_myfriends_tile1, parent, false);
        MyFriendsAdapter.ViewHolder viewHolder = new MyFriendsAdapter.ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyFriendsAdapter.ViewHolder holder, int position) {
        Friend friend = friends.get(position);

        holder.tvUserName.setText(friend.getUserName());
        holder.tvEmailID.setText(friend.getUserEmailId());

        holder.ibRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Funcs.userFriendsRemove(CurrentUser.user.getEmailId(), friend.getUserEmailId(), new Funcs.UserFriendsRemoveResult() {
                    @Override
                    public void onSuccess() {
                        Toast.makeText(v.getContext(), "Friend removed", Toast.LENGTH_SHORT).show();

                        friends.remove(position);

                        for (int i = 0; i < CurrentUser.user.friends.size(); i++)
                            if (friend.uid.equals(CurrentUser.user.friends.get(i).uid)) {
                                CurrentUser.user.friends.remove(i);
                                break;
                            }

                        MyFriendsAdapter.this.notifyDataSetChanged();
                    }

                    @Override
                    public void onWarning(String errorText) {
                        Toast.makeText(v.getContext(), "Warning: " + errorText, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(@NonNull Throwable t) {
                        Toast.makeText(v.getContext(), "Failure: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return friends.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvUserName, tvEmailID;
        public ImageButton ibRemove;

        public ViewHolder(View holder) {
            super(holder);

            tvUserName = holder.findViewById(R.id.tvUserName);
            tvEmailID = holder.findViewById(R.id.tvEmailID);

            ibRemove = holder.findViewById(R.id.ibRemove);
        }
    }
}
