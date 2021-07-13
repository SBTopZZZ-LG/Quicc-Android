package com.sbtopzzz.quicc.API;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.sbtopzzz.quicc.API.Schemas.Event;
import com.sbtopzzz.quicc.API.Schemas.EventCreate_Body;
import com.sbtopzzz.quicc.API.Schemas.EventDelete_Body;
import com.sbtopzzz.quicc.API.Schemas.EventMembers_Default;
import com.sbtopzzz.quicc.API.Schemas.EventUpdate_Body;
import com.sbtopzzz.quicc.API.Schemas.SearchUser;
import com.sbtopzzz.quicc.API.Schemas.User;
import com.sbtopzzz.quicc.API.Schemas.UserFriend;
import com.sbtopzzz.quicc.API.Schemas.UserFriends_Default;
import com.sbtopzzz.quicc.API.Schemas.UserGet_Body;
import com.sbtopzzz.quicc.API.Schemas.UserRegister_Body;
import com.sbtopzzz.quicc.API.Schemas.UserSearch_Default;
import com.sbtopzzz.quicc.API.Schemas.UserSignIn_Body;
import com.sbtopzzz.quicc.Activities.UserHomeActivity_Fragments.SharedClasses.CurrentUser;
import com.sbtopzzz.quicc.HelperClasses.SP;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Funcs {
    public enum UserFriendsState {
        FRIEND_REQUEST_CREATED,
        FRIEND_REQUEST_ACCEPTED
    }

    private static String getLoginToken() {
        return SP.pull("userLoginToken");
    }

    public static void userSignIn(String emailId, @NonNull UserSignInResult result) {
        EndPoints client = Client.getClient().create(EndPoints.class);
        UserSignIn_Body body = new UserSignIn_Body(emailId, "");

        Call<Object> call = client.userSignIn(getLoginToken(), body);
        call.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                if (response.code() == 200) {
                    Map<String, Object> map = (Map<String, Object>) response.body();
                    User user2 = new User(map.get("uid").toString(), map.get("name").toString(), map.get("email").toString());

                    List<Object> list = (List<Object>) map.get("friends");

                    List<UserFriend> friends = new ArrayList<>();

                    if (list != null)
                        for (Object obj : list) {
                            Map<String, Object> map2 = (Map<String, Object>) obj;

                            friends.add(new UserFriend(map2.get("userUid").toString(), Double.parseDouble(map2.get("status").toString())));
                        }

                    user2.friends = friends;

                    result.onSuccess(null, user2);

                    return;
                }

                // Failure
                String errorText = response.body() == null ? String.valueOf(response.code()) : response.body().toString();
                result.onWarning(errorText);
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                result.onFailure(t);
            }
        });
    }
    public static void userSignIn(String emailId, String password, @NonNull UserSignInResult result) {
        EndPoints client = Client.getClient().create(EndPoints.class);
        UserSignIn_Body body = new UserSignIn_Body(emailId, password);

        Call<Object> call = client.userSignIn(body);
        call.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                if (response.code() == 200) {
                    Map<String, Object> map = (Map<String, Object>) response.body();

                    String loginToken = map.get("loginToken").toString();

                    Map<String, Object> user = (Map<String, Object>) map.get("user");
                    User user2 = new User(user.get("uid").toString(), user.get("name").toString(), user.get("email").toString());

                    List<Object> list = (List<Object>) map.get("friends");

                    List<UserFriend> friends = new ArrayList<>();
                    if (list != null)
                        for (Object obj : list) {
                            Map<String, Object> map2 = (Map<String, Object>) obj;

                            friends.add(new UserFriend(map2.get("userUid").toString(), Double.parseDouble(map2.get("status").toString())));
                        }

                    user2.friends = friends;

                    result.onSuccess(loginToken, user2);

                    return;
                }

                // Failure
                String errorText = response.body() == null ? String.valueOf(response.code()) : response.body().toString();
                result.onWarning(errorText);
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                result.onFailure(t);
            }
        });
    }
    public interface UserSignInResult {
        void onSuccess(@Nullable String loginToken, @NonNull User user);
        void onWarning(String errorText);
        void onFailure(@NonNull Throwable t);
    }

    public static void userRegister(String emailId, String password, @NonNull UserRegisterResult result) {
        EndPoints client = Client.getClient().create(EndPoints.class);
        UserRegister_Body body = new UserRegister_Body(emailId, password);

        Call<Object> call = client.userRegister(body);
        call.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                if (response.code() == 200) {
                    result.onSuccess();

                    return;
                }

                // Failure
                String errorText = response.body() == null ? String.valueOf(response.code()) : response.body().toString();
                result.onWarning(errorText);
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                result.onFailure(t);
            }
        });
    }
    public interface UserRegisterResult {
        void onSuccess();
        void onWarning(String errorText);
        void onFailure(@NonNull Throwable t);
    }

    public static void userGetByEmail(String emailId, String targetEmailId, @NonNull UserGetResult result) {
        EndPoints client = Client.getClient().create(EndPoints.class);
        UserGet_Body body = new UserGet_Body(emailId, targetEmailId, null);

        Call<Object> call = client.userGet(getLoginToken(), body);
        call.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                if (response.code() == 200) {
                    Map<String, Object> map = (Map<String, Object>) response.body();

                    User user = new User(map.get("uid").toString(), map.get("name").toString(), targetEmailId);

                    result.onSuccess(user);

                    return;
                }

                // Failure
                String errorText = response.body() == null ? String.valueOf(response.code()) : response.body().toString();
                result.onWarning(errorText);
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                result.onFailure(t);
            }
        });
    }
    public static void userGetByUid(String emailId, String userUid, @NonNull UserGetResult result) {
        EndPoints client = Client.getClient().create(EndPoints.class);
        UserGet_Body body = new UserGet_Body(emailId, null, userUid);

        Call<Object> call = client.userGet(getLoginToken(), body);
        call.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                if (response.code() == 200) {
                    Map<String, Object> map = (Map<String, Object>) response.body();

                    User user = new User(userUid, map.get("name").toString(), map.get("email").toString());

                    result.onSuccess(user);

                    return;
                }

                System.out.println("Error code: " + response.code());

                // Failure
                String errorText = response.body() == null ? String.valueOf(response.code()) : response.body().toString();
                result.onWarning(errorText);
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                result.onFailure(t);
            }
        });
    }
    public interface UserGetResult {
        void onSuccess(@NonNull User user);
        void onWarning(String errorText);
        void onFailure(@NonNull Throwable t);
    }

    public static void userFriendsAdd(String emailId, String targetEmailId, @NonNull UserFriendsAddResult result) {
        EndPoints client = Client.getClient().create(EndPoints.class);
        UserFriends_Default body = new UserFriends_Default(emailId, targetEmailId);

        Call<Object> call = client.userFriendsAdd(getLoginToken(), body);
        call.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                if (response.code() == 200) {
                    String responseText = response.body().toString();

                    System.out.println("Response: " + responseText);

                    CurrentUser.ReloadUser();

                    if (responseText.contains("friendRequestCreated"))
                        result.onSuccess(UserFriendsState.FRIEND_REQUEST_CREATED);
                    else
                        result.onSuccess(UserFriendsState.FRIEND_REQUEST_ACCEPTED);

                    return;
                }

                // Failure
                String errorText = response.body() == null ? String.valueOf(response.code()) : response.body().toString();
                result.onWarning(errorText);
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                result.onFailure(t);
            }
        });
    }
    public interface UserFriendsAddResult {
        void onSuccess(@NonNull UserFriendsState state);
        void onWarning(String errorText);
        void onFailure(@NonNull Throwable t);
    }

    public static void userFriendsRemove(String emailId, String targetEmailId, @NonNull UserFriendsRemoveResult result) {
        EndPoints client = Client.getClient().create(EndPoints.class);
        UserFriends_Default body = new UserFriends_Default(emailId, targetEmailId);

        Call<Object> call = client.userFriendsRemove(getLoginToken(), body);
        call.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                if (response.code() == 200) {
                    CurrentUser.ReloadUser();

                    result.onSuccess();

                    return;
                }

                // Failure
                String errorText = response.body() == null ? String.valueOf(response.code()) : response.body().toString();
                result.onWarning(errorText);
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                result.onFailure(t);
            }
        });
    }
    public interface UserFriendsRemoveResult {
        void onSuccess();
        void onWarning(String errorText);
        void onFailure(@NonNull Throwable t);
    }

    public static void userFriendOne(String emailId, String targetEmailId, @NonNull UserFriendOneResult result) {
        EndPoints client = Client.getClient().create(EndPoints.class);
        UserFriends_Default body = new UserFriends_Default(emailId, targetEmailId);

        Call<Object> call = client.userFriendsGetOne(getLoginToken(), body);
        call.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                if (response.code() == 200) {
                    Map<String, Object> map = (Map<String, Object>) response.body();

                    if (map.keySet().size() == 0) {
                        result.onSuccess(null);
                        return;
                    }

                    UserFriend friend = new UserFriend(map.get("userUid").toString(),
                            Double.parseDouble(map.get("status").toString()));

                    result.onSuccess(friend);

                    return;
                }

                // Failure
                String errorText = response.body() == null ? String.valueOf(response.code()) : response.body().toString();
                result.onWarning(errorText);
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                result.onFailure(t);
            }
        });
    }
    public interface UserFriendOneResult {
        void onSuccess(@Nullable UserFriend friend);
        void onWarning(String errorText);
        void onFailure(@NonNull Throwable t);
    }

    public static void userFriendsGet(String emailId, @NonNull UserFriendsGetResult result) {
        EndPoints client = Client.getClient().create(EndPoints.class);
        UserFriends_Default body = new UserFriends_Default(emailId, null);

        Call<Object> call = client.userFriendsGet(getLoginToken(), body);
        call.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                if (response.code() == 200) {
                    List<Object> list = (List<Object>) response.body();

                    List<UserFriend> friends = new ArrayList<>();
                    for (Object obj : list) {
                        Map<String, Object> map = (Map<String, Object>) obj;

                        System.out.println(map);

                        friends.add(new UserFriend(map.get("userUid").toString(), Double.parseDouble(map.get("status").toString())));
                    }

                    result.onSuccess(friends);

                    return;
                }

                // Failure
                String errorText = response.body() == null ? String.valueOf(response.code()) : response.body().toString();
                result.onWarning(errorText);
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                result.onFailure(t);
            }
        });
    }
    public interface UserFriendsGetResult {
        void onSuccess(@NonNull List<UserFriend> friends);
        void onWarning(String errorText);
        void onFailure(@NonNull Throwable t);
    }

    public static void userSearchByName(String emailId, String expression, @NonNull UserSearchResult result) {
        EndPoints client = Client.getClient().create(EndPoints.class);
        UserSearch_Default body = new UserSearch_Default(emailId, expression);

        Call<Object> call = client.userSearchByName(getLoginToken(), body);
        call.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                if (response.code() == 200) {
                    List<Object> list = (List<Object>) response.body();

                    List<SearchUser> users = new ArrayList<>();
                    for (Object obj : list) {
                        Map<String, Object> map = (Map<String, Object>) obj;

                        if (!map.get("uid").toString().equals(CurrentUser.user.uid))
                            users.add(new SearchUser(map.get("uid").toString(), map.get("name").toString(), map.get("email").toString()));
                    }

                    result.onSuccess(users);

                    return;
                }

                // Failure
                String errorText = response.body() == null ? String.valueOf(response.code()) : response.body().toString();
                result.onWarning(errorText);
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                result.onFailure(t);
            }
        });
    }
    public static void userSearchByEmail(String emailId, String expression, @NonNull UserSearchResult result) {
        EndPoints client = Client.getClient().create(EndPoints.class);
        UserSearch_Default body = new UserSearch_Default(emailId, expression);

        Call<Object> call = client.userSearchByEmail(getLoginToken(), body);
        call.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                if (response.code() == 200) {
                    List<Object> list = (List<Object>) response.body();

                    List<SearchUser> users = new ArrayList<>();
                    for (Object obj : list) {
                        Map<String, Object> map = (Map<String, Object>) obj;

                        if (!map.get("uid").toString().equals(CurrentUser.user.uid))
                            users.add(new SearchUser(map.get("uid").toString(), map.get("name").toString(), map.get("email").toString()));
                    }

                    result.onSuccess(users);

                    return;
                }

                // Failure
                String errorText = response.body() == null ? String.valueOf(response.code()) : response.body().toString();
                result.onWarning(errorText);
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                result.onFailure(t);
            }
        });
    }
    public static void userSearch(String emailId, String expression, @NonNull UserSearchResult result) {
        EndPoints client = Client.getClient().create(EndPoints.class);
        UserSearch_Default body = new UserSearch_Default(emailId, expression);

        Call<Object> call = client.userSearch(getLoginToken(), body);
        call.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                if (response.code() == 200) {
                    List<Object> list = (List<Object>) response.body();

                    List<SearchUser> users = new ArrayList<>();
                    for (Object obj : list) {
                        Map<String, Object> map = (Map<String, Object>) obj;

                        if (!map.get("uid").toString().equals(CurrentUser.user.uid))
                            users.add(new SearchUser(map.get("uid").toString(), map.get("name").toString(), map.get("email").toString()));
                    }

                    result.onSuccess(users);

                    return;
                }

                // Failure
                String errorText = response.body() == null ? String.valueOf(response.code()) : response.body().toString();
                result.onWarning(errorText);
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                result.onFailure(t);
            }
        });
    }
    public interface UserSearchResult {
        void onSuccess(@NonNull List<SearchUser> users);
        void onWarning(String errorText);
        void onFailure(@NonNull Throwable t);
    }

    public static void eventCreate(@NonNull String emailId, @NonNull Event event, @NonNull EventCreateResult result) {
        EndPoints client = Client.getClient().create(EndPoints.class);
        EventCreate_Body body = new EventCreate_Body(emailId, event);

        Call<Object> call = client.eventCreate(getLoginToken(), body);
        call.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                if (response.code() == 200) {
                    Map<String, Object> map = (Map<String, Object>) response.body();
                    String eventUid = map.get("uid").toString();

                    result.onSuccess(eventUid);

                    return;
                }

                // Failure
                String errorText = response.body() == null ? String.valueOf(response.code()) : response.body().toString();
                result.onWarning(errorText);
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                result.onFailure(t);
            }
        });
    }
    public interface EventCreateResult {
        void onSuccess(@NonNull String eventUid);
        void onWarning(String errorText);
        void onFailure(@NonNull Throwable t);
    }

    public static void eventDelete(@NonNull String emailId, @NonNull String eventUid, @NonNull EventDeleteResult result) {
        EndPoints client = Client.getClient().create(EndPoints.class);
        EventDelete_Body body = new EventDelete_Body(emailId, eventUid);

        Call<Object> call = client.eventDelete(getLoginToken(), body);
        call.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                if (response.code() == 200) {
                    result.onSuccess();

                    return;
                }

                // Failure
                String errorText = response.body() == null ? String.valueOf(response.code()) : response.body().toString();
                result.onWarning(errorText);
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                result.onFailure(t);
            }
        });
    }
    public interface EventDeleteResult {
        void onSuccess();
        void onWarning(String errorText);
        void onFailure(@NonNull Throwable t);
    }

    public static void eventUpdate(@NonNull String emailId, @NonNull String eventUid, @NonNull Event event, @NonNull EventUpdateResult result) {
        EndPoints client = Client.getClient().create(EndPoints.class);
        EventUpdate_Body body = new EventUpdate_Body(emailId, eventUid, event);

        Call<Object> call = client.eventUpdate(getLoginToken(), body);
        call.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                if (response.code() == 200) {
                    result.onSuccess();

                    return;
                }

                // Failure
                String errorText = response.body() == null ? String.valueOf(response.code()) : response.body().toString();
                result.onWarning(errorText);
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                result.onFailure(t);
            }
        });
    }
    public interface EventUpdateResult {
        void onSuccess();
        void onWarning(String errorText);
        void onFailure(@NonNull Throwable t);
    }

    public static void eventsGet(@NonNull String userUid, @NonNull EventsGetResult result) {
        eventsGet(userUid, false, result);
    }
    public static void eventsGet(@NonNull String userUid, boolean isActive, @NonNull EventsGetResult result) {
        EndPoints client = Client.getClient().create(EndPoints.class);

        Call<Object> call = client.eventsGet(userUid, isActive ? "true" : "false");
        call.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                if (response.code() == 200) {
                    List<Object> list = (List<Object>) response.body();
                    List<Event> events = new ArrayList<>();
                    for (Object item : list) {
                        Map<String, Object> map = (Map<String, Object>) item;

                        String startDate = map.get("startDate").toString().split("E")[0].replace(".", "");
                        Long startDateLong = Long.parseLong(startDate);
                        String endDate = map.get("endDate").toString().split("E")[0].replace(".", "");
                        Long endDateLong = Long.parseLong(endDate);

                        List<String> members = new ArrayList<>();
                        for (Object member : ((List<Object>) map.get("members")))
                            members.add(member.toString());

                        List<String> visitedMembers = new ArrayList<>();
                        for (Object member : ((List<Object>) map.get("visitedMembers")))
                            visitedMembers.add(member.toString());

                        Event event = new Event(map.get("uid").toString(), map.get("host").toString(),
                                map.get("title").toString(), startDateLong, endDateLong,
                                members, visitedMembers);

                        events.add(event);
                    }

                    result.onSuccess(events);

                    return;
                }

                // Failure
                String errorText = response.body() == null ? String.valueOf(response.code()) : response.body().toString();
                result.onWarning(errorText);
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                result.onFailure(t);
            }
        });
    }
    public interface EventsGetResult {
        void onSuccess(@NonNull List<Event> events);
        void onWarning(String errorText);
        void onFailure(@NonNull Throwable t);
    }

    public static void eventsInvited(@NonNull String userUid, @NonNull EventsInvitedResult result) {
        eventsInvited(userUid, false, result);
    }
    public static void eventsInvited(@NonNull String userUid, boolean isActive, @NonNull EventsInvitedResult result) {
        EndPoints client = Client.getClient().create(EndPoints.class);

        Call<Object> call = client.eventsInvited(userUid, isActive ? "true" : "false");
        call.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                if (response.code() == 200) {
                    List<Object> list = (List<Object>) response.body();
                    List<Event> events = new ArrayList<>();
                    for (Object item : list) {
                        Map<String, Object> map = (Map<String, Object>) item;

                        String startDate = map.get("startDate").toString().split("E")[0].replace(".", "");
                        Long startDateLong = Long.parseLong(startDate);
                        String endDate = map.get("endDate").toString().split("E")[0].replace(".", "");
                        Long endDateLong = Long.parseLong(endDate);

                        List<String> members = new ArrayList<>();
                        for (Object member : ((List<Object>) map.get("members")))
                            members.add(member.toString());

                        List<String> visitedMembers = new ArrayList<>();
                        for (Object member : ((List<Object>) map.get("visitedMembers")))
                            visitedMembers.add(member.toString());

                        Event event = new Event(map.get("uid").toString(), map.get("host").toString(),
                                map.get("title").toString(), startDateLong, endDateLong,
                                members, visitedMembers);

                        events.add(event);
                    }

                    result.onSuccess(events);

                    return;
                }

                // Failure
                String errorText = response.body() == null ? String.valueOf(response.code()) : response.body().toString();
                result.onWarning(errorText);
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                result.onFailure(t);
            }
        });
    }
    public interface EventsInvitedResult {
        void onSuccess(@NonNull List<Event> invitedEvents);
        void onWarning(String errorText);
        void onFailure(@NonNull Throwable t);
    }

    public static void eventGet(@NonNull String eventUid, @NonNull EventGetResult result) {
        EndPoints client = Client.getClient().create(EndPoints.class);

        Call<Object> call = client.eventGet(eventUid);
        call.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                if (response.code() == 200) {
                    Map<String, Object> map = (Map<String, Object>) response.body();

                    String startDate = map.get("startDate").toString().split("E")[0].replace(".", "");
                    Long startDateLong = Long.parseLong(startDate);
                    String endDate = map.get("endDate").toString().split("E")[0].replace(".", "");
                    Long endDateLong = Long.parseLong(endDate);

                    List<String> members = new ArrayList<>();
                    for (Object member : ((List<Object>) map.get("members")))
                        members.add(member.toString());

                    List<String> visitedMembers = new ArrayList<>();
                    for (Object member : ((List<Object>) map.get("visitedMembers")))
                        visitedMembers.add(member.toString());

                    Event event = new Event(map.get("uid").toString(), map.get("host").toString(),
                            map.get("title").toString(), startDateLong, endDateLong,
                            members, visitedMembers);

                    result.onSuccess(event);

                    return;
                }

                // Failure
                String errorText = response.body() == null ? String.valueOf(response.code()) : response.body().toString();
                result.onWarning(errorText);
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                result.onFailure(t);
            }
        });
    }
    public interface EventGetResult {
        void onSuccess(@NonNull Event event);
        void onWarning(String errorText);
        void onFailure(@NonNull Throwable t);
    }

    public static void eventMembersAdd(@NonNull String emailId, @NonNull String targetEmailId, @NonNull String eventUid,
                                       @NonNull EventMembersAddResult result) {
        EndPoints client = Client.getClient().create(EndPoints.class);
        EventMembers_Default body = new EventMembers_Default(emailId, targetEmailId, eventUid);
        
        Call<Object> call = client.eventMembersAdd(getLoginToken(), body);
        call.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                if (response.code() == 200) {
                    result.onSuccess();

                    return;
                }

                // Failure
                String errorText = response.body() == null ? String.valueOf(response.code()) : response.body().toString();
                result.onWarning(errorText);
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                result.onFailure(t);
            }
        });
    }
    public interface EventMembersAddResult {
        void onSuccess();
        void onWarning(String errorText);
        void onFailure(@NonNull Throwable t);
    }

    public static void eventMembersRemove(@NonNull String emailId, @NonNull String targetEmailId, @NonNull String eventUid,
                                          @NonNull EventMembersRemoveResult result) {
        EndPoints client = Client.getClient().create(EndPoints.class);
        EventMembers_Default body = new EventMembers_Default(emailId, targetEmailId, eventUid);

        Call<Object> call = client.eventMembersRemove(getLoginToken(), body);
        call.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                if (response.code() == 200) {
                    result.onSuccess();

                    return;
                }

                // Failure
                String errorText = response.body() == null ? String.valueOf(response.code()) : response.body().toString();
                result.onWarning(errorText);
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                result.onFailure(t);
            }
        });
    }
    public interface EventMembersRemoveResult {
        void onSuccess();
        void onWarning(String errorText);
        void onFailure(@NonNull Throwable t);
    }

    public static void eventMembersGet(@NonNull String emailId, @NonNull String eventUid,
                                          @NonNull EventMembersGetResult result) {
        EndPoints client = Client.getClient().create(EndPoints.class);
        EventMembers_Default body = new EventMembers_Default(emailId, null, eventUid);

        Call<Object> call = client.eventMembersGet(getLoginToken(), body);
        call.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                if (response.code() == 200) {
                    List<Object> list = (List<Object>) response.body();

                    List<String> userUids = new ArrayList<>();

                    if (list != null)
                        for (Object obj : list) {
                            userUids.add(obj.toString());
                        }

                    result.onSuccess(userUids);

                    return;
                }

                // Failure
                String errorText = response.body() == null ? String.valueOf(response.code()) : response.body().toString();
                result.onWarning(errorText);
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                result.onFailure(t);
            }
        });
    }
    public interface EventMembersGetResult {
        void onSuccess(@NonNull List<String> userUids);
        void onWarning(String errorText);
        void onFailure(@NonNull Throwable t);
    }
}
