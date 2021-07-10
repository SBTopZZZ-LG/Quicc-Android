package com.sbtopzzz.quicc.Activities.UserSearchActivity_Objects;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sbtopzzz.quicc.API.Funcs;
import com.sbtopzzz.quicc.API.Schemas.UserFriend;
import com.sbtopzzz.quicc.Activities.UserHomeActivity_Fragments.SharedClasses.CurrentUser;
import com.sbtopzzz.quicc.R;

import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder> {
    private final List<Search> searches;

    public SearchAdapter(List<Search> searches) {
        this.searches = searches;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View item = inflater.inflate(R.layout.usersearchactivity_users_item1, parent, false);
        SearchAdapter.ViewHolder viewHolder = new ViewHolder(item);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull SearchAdapter.ViewHolder holder, int position) {
        Search search = searches.get(position);

        holder.tvUserName.setText(search.getUserName());
        holder.tvEmailID.setText(search.getUserEmailId());

        for (UserFriend userFriend : CurrentUser.user.friends)
            if (userFriend.uid.equals(search.uid)) {
                holder.btnAddFriend.setEnabled(false);
                if ((userFriend.getStatus() == UserFriend.UserFriendStatus.RECEIVER_ACCEPTED ||
                        userFriend.getStatus() == UserFriend.UserFriendStatus.SENDER_ACCEPTED)) {
                    holder.btnAddFriend.setText("Friend");
                } else if (userFriend.getStatus() == UserFriend.UserFriendStatus.SENT)
                    holder.btnAddFriend.setText("Request Sent");
                else if (userFriend.getStatus() == UserFriend.UserFriendStatus.RECEIVED)
                    holder.btnAddFriend.setText("Accept Request");

                break;
            }

        holder.btnAddFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.btnAddFriend.setEnabled(false);
                holder.btnAddFriend.setText("Request Sent");

                Funcs.userFriendsAdd(CurrentUser.user.getEmailId(), search.getUserEmailId(), new Funcs.UserFriendsAddResult() {
                    @Override
                    public void onSuccess(@NonNull Funcs.UserFriendsState state) {
                        if (state == Funcs.UserFriendsState.FRIEND_REQUEST_ACCEPTED)
                            holder.btnAddFriend.setText("Friend");
                    }

                    @Override
                    public void onWarning(String errorText) {
                        holder.btnAddFriend.setEnabled(true);
                        holder.btnAddFriend.setText("Add Friend");

                        System.out.println("Warning: " + errorText);
                    }

                    @Override
                    public void onFailure(@NonNull Throwable t) {
                        System.out.println(t.getMessage());
                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return searches.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvUserName, tvEmailID;
        public Button btnAddFriend;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvUserName = itemView.findViewById(R.id.tvUserName);
            tvEmailID = itemView.findViewById(R.id.tvEmailID);
            btnAddFriend = itemView.findViewById(R.id.btnAddFriend);
        }
    }
}
