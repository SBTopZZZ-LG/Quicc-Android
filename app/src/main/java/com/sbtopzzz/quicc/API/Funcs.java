package com.sbtopzzz.quicc.API;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.sbtopzzz.quicc.API.Schemas.Event;
import com.sbtopzzz.quicc.API.Schemas.User;
import com.sbtopzzz.quicc.API.Schemas.UserRegister_Body;
import com.sbtopzzz.quicc.API.Schemas.UserSignIn_Body;
import com.sbtopzzz.quicc.HelperClasses.SP;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Funcs {
    public static void userSignIn(String emailId, @NonNull UserSignInResult result) {
        EndPoints client = Client.getClient().create(EndPoints.class);
        UserSignIn_Body body = new UserSignIn_Body(emailId, "");
        String loginToken = SP.pull("userLoginToken");

        Call<Object> call = client.userSignIn(loginToken, body);
        call.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                if (response.code() == 200) {
                    Map<String, Object> map = (Map<String, Object>) response.body();
                    User user2 = new User(map.get("uid").toString(), map.get("name").toString(), map.get("email").toString());

                    result.onSuccess(null, user2);

                    return;
                }

                // Failure
                String errorText = response.body().toString();
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

                    result.onSuccess(loginToken, user2);

                    return;
                }

                // Failure
                String errorText = response.body().toString();
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
                String errorText = response.body() == null ? "unknown" : response.body().toString();
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
                String errorText = response.body() == null ? "unknown" : response.body().toString();
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
}
