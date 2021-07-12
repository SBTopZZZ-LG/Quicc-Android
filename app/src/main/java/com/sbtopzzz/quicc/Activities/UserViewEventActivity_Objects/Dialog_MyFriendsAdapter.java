package com.sbtopzzz.quicc.Activities.UserViewEventActivity_Objects;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sbtopzzz.quicc.API.Funcs;
import com.sbtopzzz.quicc.Activities.UserHomeActivity_Fragments.SharedClasses.CurrentUser;
import com.sbtopzzz.quicc.R;

import java.util.List;

public class Dialog_MyFriendsAdapter extends RecyclerView.Adapter<Dialog_MyFriendsAdapter.ViewHolder> {
    private final String eventUid;
    private List<String> inviteeUids;
    private final List<Dialog_Friend> friends;

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        Funcs.eventMembersGet(CurrentUser.user.getEmailId(), eventUid, new Funcs.EventMembersGetResult() {
            @Override
            public void onSuccess(@NonNull List<String> userUids) {
                inviteeUids = userUids;

                notifyDataSetChanged();
            }

            @Override
            public void onWarning(String errorText) {
                Toast.makeText(recyclerView.getContext(), "EventMembersGet Warning: " + errorText, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(@NonNull Throwable t) {
                Toast.makeText(recyclerView.getContext(), "EventMembersGet Failure: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public Dialog_MyFriendsAdapter(String eventUid, List<Dialog_Friend> friends) {
        this.eventUid = eventUid;
        this.friends = friends;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View main = inflater.inflate(R.layout.uservieweventactivity_addmembers_alertdialog_tile1, parent, false);
        ViewHolder viewHolder = new ViewHolder(main);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull Dialog_MyFriendsAdapter.ViewHolder holder, int position) {
        Dialog_Friend friend = friends.get(position);

        holder.tvUserName.setText(friend.getUserName());
        holder.tvEmailID.setText(friend.getEmailId());

        if (inviteeUids.contains(friend.uid)) {
            holder.btnInvite.setTextColor(Color.BLACK);
            holder.btnInvite.setText("Invited");
            holder.btnInvite.setBackgroundColor(Color.WHITE);
        }

        holder.btnInvite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!inviteeUids.contains(friend.uid)) {
                    holder.btnInvite.setEnabled(false);
                    holder.btnInvite.setTextColor(Color.BLACK);
                    holder.btnInvite.setText("Invited");
                    holder.btnInvite.setBackgroundColor(Color.WHITE);

                    Funcs.eventMembersAdd(CurrentUser.user.getEmailId(), friend.getEmailId(), eventUid, new Funcs.EventMembersAddResult() {
                        @Override
                        public void onSuccess() {
                            holder.btnInvite.setEnabled(true);

                            inviteeUids.add(friend.uid);
                        }

                        @Override
                        public void onWarning(String errorText) {
                            Toast.makeText(v.getContext(), "Warning: " + errorText, Toast.LENGTH_SHORT).show();

                            holder.btnInvite.setTextColor(Color.WHITE);
                            holder.btnInvite.setEnabled(true);
                            holder.btnInvite.setText("Invite");
                            holder.btnInvite.setBackgroundColor(v.getContext().getResources().getColor(R.color.design_default_color_primary));
                        }

                        @Override
                        public void onFailure(@NonNull Throwable t) {
                            Toast.makeText(v.getContext(), "Failure: " + t.getMessage(), Toast.LENGTH_SHORT).show();

                            holder.btnInvite.setTextColor(Color.WHITE);
                            holder.btnInvite.setEnabled(true);
                            holder.btnInvite.setText("Invite");
                            holder.btnInvite.setBackgroundColor(v.getContext().getResources().getColor(R.color.design_default_color_primary));
                        }
                    });
                } else {
                    holder.btnInvite.setEnabled(false);
                    holder.btnInvite.setTextColor(Color.WHITE);
                    holder.btnInvite.setText("Invite");
                    holder.btnInvite.setBackgroundColor(v.getContext().getResources().getColor(R.color.design_default_color_primary));

                    Funcs.eventMembersRemove(CurrentUser.user.getEmailId(), friend.getEmailId(), eventUid, new Funcs.EventMembersRemoveResult() {
                        @Override
                        public void onSuccess() {
                            holder.btnInvite.setEnabled(true);

                            inviteeUids.remove(friend.uid);
                        }

                        @Override
                        public void onWarning(String errorText) {
                            Toast.makeText(v.getContext(), "Warning: " + errorText, Toast.LENGTH_SHORT).show();

                            holder.btnInvite.setTextColor(Color.BLACK);
                            holder.btnInvite.setEnabled(true);
                            holder.btnInvite.setText("Invited");
                            holder.btnInvite.setBackgroundColor(Color.WHITE);
                        }

                        @Override
                        public void onFailure(@NonNull Throwable t) {
                            Toast.makeText(v.getContext(), "Failure: " + t.getMessage(), Toast.LENGTH_SHORT).show();

                            holder.btnInvite.setTextColor(Color.BLACK);
                            holder.btnInvite.setEnabled(true);
                            holder.btnInvite.setText("Invited");
                            holder.btnInvite.setBackgroundColor(Color.WHITE);
                        }
                    });
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return friends.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvUserName, tvEmailID;
        public Button btnInvite;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvUserName = itemView.findViewById(R.id.tvUserName);
            tvEmailID = itemView.findViewById(R.id.tvEmailID);

            btnInvite = itemView.findViewById(R.id.btnInvite);
        }
    }

}
