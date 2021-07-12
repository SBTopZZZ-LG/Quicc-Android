package com.sbtopzzz.quicc.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.sbtopzzz.quicc.API.Funcs;
import com.sbtopzzz.quicc.API.Schemas.Event;
import com.sbtopzzz.quicc.API.Schemas.User;
import com.sbtopzzz.quicc.API.Schemas.UserFriend;
import com.sbtopzzz.quicc.Activities.UserHomeActivity_Fragments.SharedClasses.CurrentUser;
import com.sbtopzzz.quicc.Activities.UserHomeActivity_Fragments.UserHomeActivity_Fragment_Home_Objects.MyEventsAdapter;
import com.sbtopzzz.quicc.Activities.UserViewEventActivity_Objects.Dialog_Friend;
import com.sbtopzzz.quicc.Activities.UserViewEventActivity_Objects.Dialog_MyFriendsAdapter;
import com.sbtopzzz.quicc.Activities.UserViewEventActivity_Objects.Invitee;
import com.sbtopzzz.quicc.Activities.UserViewEventActivity_Objects.InviteesAdapter;
import com.sbtopzzz.quicc.Activities.UserViewEventActivity_Objects.Organizer;
import com.sbtopzzz.quicc.Activities.UserViewEventActivity_Objects.OrganizersAdapter;
import com.sbtopzzz.quicc.R;

import org.ocpsoft.prettytime.PrettyTime;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UserViewEventActivity extends AppCompatActivity {
    private String eventUid;

    private TextView tvEventName;
    private RecyclerView rvOrganizers, rvInvitees;
    private TextView tvEventStart;

    private Button btnAddMembers, btnJoinEvent;

    private PrettyTime p = new PrettyTime();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_view_event);
        Initialize();
    }

    private void Initialize() {
        eventUid = getIntent().getExtras().getString("eventUid");

        tvEventName = findViewById(R.id.tvEventName);

        rvOrganizers = findViewById(R.id.rvOrganizers);
        rvInvitees = findViewById(R.id.rvInvitees);

        tvEventStart = findViewById(R.id.tvEventStart);

        btnAddMembers = findViewById(R.id.btnAddMembers);
        btnJoinEvent = findViewById(R.id.btnJoinEvent);

        LoadEvent();
    }

    private void LoadEvent() {
        Funcs.eventGet(eventUid, new Funcs.EventGetResult() {
            @Override
            public void onSuccess(@NonNull Event event) {
                tvEventName.setText(event.getTitle());

                List<Organizer> organizers = new ArrayList<Organizer>();

                OrganizersAdapter adapter = new OrganizersAdapter(UserViewEventActivity.this, organizers);
                rvOrganizers.setHasFixedSize(true);
                rvOrganizers.setLayoutManager(new LinearLayoutManager(UserViewEventActivity.this));
                rvOrganizers.setAdapter(adapter);

                Funcs.userGetByUid(CurrentUser.user.getEmailId(), event.getHost(), new Funcs.UserGetResult() {
                    @Override
                    public void onSuccess(@NonNull User user) {
                        organizers.add(new Organizer(user.uid.equals(CurrentUser.user.uid) ? "YOU" : user.getName(), "HOST"));

                        if (user.uid.equals(CurrentUser.user.uid)) {
                            // Enable addMembers button

                            btnAddMembers.setText("Add Members");
                            btnAddMembers.setEnabled(true);
                            btnAddMembers.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(UserViewEventActivity.this);
                                    builder.setCancelable(true);
                                    builder.setTitle("Add Members");

                                    View main = View.inflate(UserViewEventActivity.this, R.layout.uservieweventactivity_addmembers_alertdialogview, null);
                                    RecyclerView rvMyFriends = main.findViewById(R.id.rvMyFriends);

                                    builder.setView(main);

                                    AlertDialog dialog = builder.show();

                                    Funcs.userFriendsGet(CurrentUser.user.getEmailId(), new Funcs.UserFriendsGetResult() {
                                        @Override
                                        public void onSuccess(@NonNull List<UserFriend> friends) {
                                            List<Dialog_Friend> friendList = new ArrayList<>();

                                            Toast.makeText(UserViewEventActivity.this, "Friends: " + friends.size(), Toast.LENGTH_SHORT).show();

                                            Dialog_MyFriendsAdapter adapter1 = new Dialog_MyFriendsAdapter(eventUid, friendList);
                                            rvMyFriends.setHasFixedSize(true);
                                            rvMyFriends.setLayoutManager(new LinearLayoutManager(UserViewEventActivity.this));
                                            rvMyFriends.setAdapter(adapter1);

                                            for (UserFriend friend : friends)
                                                if (friend.getStatus() == UserFriend.UserFriendStatus.SENDER_ACCEPTED || friend.getStatus() == UserFriend.UserFriendStatus.RECEIVER_ACCEPTED)
                                                    Funcs.userGetByUid(CurrentUser.user.getEmailId(), friend.uid, new Funcs.UserGetResult() {
                                                        @Override
                                                        public void onSuccess(@NonNull User user) {
                                                            Dialog_Friend dialogFriend = new Dialog_Friend(friend.uid, user.getName(), user.getEmailId());
                                                            friendList.add(dialogFriend);

                                                            adapter1.notifyDataSetChanged();
                                                        }

                                                        @Override
                                                        public void onWarning(String errorText) {
                                                            Toast.makeText(UserViewEventActivity.this, "Warning: " + errorText, Toast.LENGTH_SHORT).show();
                                                        }

                                                        @Override
                                                        public void onFailure(@NonNull Throwable t) {
                                                            Toast.makeText(UserViewEventActivity.this, "Failure: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                                                        }
                                                    });
                                        }

                                        @Override
                                        public void onWarning(String errorText) {
                                            Toast.makeText(UserViewEventActivity.this, "Warning: " + errorText, Toast.LENGTH_SHORT).show();
                                        }

                                        @Override
                                        public void onFailure(@NonNull Throwable t) {
                                            Toast.makeText(UserViewEventActivity.this, "Failure: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    });

                                    dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                                        @Override
                                        public void onDismiss(DialogInterface dialog) {
                                            LoadEvent();
                                        }
                                    });
                                }
                            });
                        }

                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onWarning(String errorText) {

                    }

                    @Override
                    public void onFailure(@NonNull Throwable t) {

                    }
                });

                List<Invitee> invitees = new ArrayList<>();

                InviteesAdapter adapter2 = new InviteesAdapter(UserViewEventActivity.this, invitees);
                rvInvitees.setHasFixedSize(true);
                rvInvitees.setLayoutManager(new LinearLayoutManager(UserViewEventActivity.this));
                rvInvitees.setAdapter(adapter2);

                for (String inviteeUid : event.getMembers())
                    Funcs.userGetByUid(CurrentUser.user.getEmailId(), inviteeUid, new Funcs.UserGetResult() {
                        @Override
                        public void onSuccess(@NonNull User user) {
                            invitees.add(new Invitee(user.uid.equals(CurrentUser.user.uid) ? "YOU" : user.getName(), user.getEmailId()));

                            adapter2.notifyDataSetChanged();
                        }

                        @Override
                        public void onWarning(String errorText) {
                            Toast.makeText(UserViewEventActivity.this, "Warning: " + errorText, Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onFailure(@NonNull Throwable t) {
                            Toast.makeText(UserViewEventActivity.this, "Failure: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });

                Date today = new Date();
                Date startDate = new Date(event.getStartDate());
                Date endDate = new Date(event.getEndDate());

                if (today.after(startDate)) {
                    if (today.before(endDate)) {
                        // Event has started
                        tvEventStart.setText("Started " + p.format(startDate));
                        btnJoinEvent.setText("Join Event");
                        btnJoinEvent.setEnabled(true);
                    } else {
                        // Event has ended
                        tvEventStart.setText("Ended " + p.format(endDate));
                        btnJoinEvent.setText("Event has ended");
                        btnJoinEvent.setEnabled(false);
                    }
                } else
                    tvEventStart.setText("Will start " + p.format(startDate));
            }

            @Override
            public void onWarning(String errorText) {
                Toast.makeText(UserViewEventActivity.this, "Main Warning: " + errorText, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(@NonNull Throwable t) {
                Toast.makeText(UserViewEventActivity.this, "Main Failure: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}