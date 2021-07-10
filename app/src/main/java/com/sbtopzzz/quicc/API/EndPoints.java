package com.sbtopzzz.quicc.API;

import com.sbtopzzz.quicc.API.Schemas.EventCreate_Body;
import com.sbtopzzz.quicc.API.Schemas.EventDelete_Body;
import com.sbtopzzz.quicc.API.Schemas.EventJoin_Body;
import com.sbtopzzz.quicc.API.Schemas.EventUpdate_Body;
import com.sbtopzzz.quicc.API.Schemas.UserFriends_Default;
import com.sbtopzzz.quicc.API.Schemas.UserGet_Body;
import com.sbtopzzz.quicc.API.Schemas.UserRegister_Body;
import com.sbtopzzz.quicc.API.Schemas.UserSearch_Default;
import com.sbtopzzz.quicc.API.Schemas.UserSignIn_Body;
import com.sbtopzzz.quicc.API.Schemas.UserUpdate_Body;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface EndPoints {
    @POST("/signIn")
    Call<Object> userSignIn(@Body UserSignIn_Body body);

    @POST("/signIn")
    Call<Object> userSignIn(@Header("authorization") String loginToken, @Body UserSignIn_Body body);

    @POST("/signOut")
    Call<Object> userSignOut(@Header("authorization") String loginToken, @Body UserSignIn_Body body);

    @POST("/user")
    Call<Object> userGet(@Header("authorization") String loginToken, @Body UserGet_Body body);

    @POST("/update")
    Call<Object> userUpdate(@Header("authorization") String loginToken, @Body UserUpdate_Body body);

    @POST("/register")
    Call<Object> userRegister(@Body UserRegister_Body body);

    @POST("/user/friends/add")
    Call<Object> userFriendsAdd(@Header("authorization") String loginToken, @Body UserFriends_Default body);

    @POST("/user/friends/remove")
    Call<Object> userFriendsRemove(@Header("authorization") String loginToken, @Body UserFriends_Default body);

    @POST("/user/friends")
    Call<Object> userFriendsGet(@Header("authorization") String loginToken, @Body UserFriends_Default body);

    @POST("/user/friends/one")
    Call<Object> userFriendsGetOne(@Header("authorization") String loginToken, @Body UserFriends_Default body);

    @POST("/user/search/name")
    Call<Object> userSearchByName(@Header("authorization") String loginToken, @Body UserSearch_Default body);

    @POST("/user/search/email")
    Call<Object> userSearchByEmail(@Header("authorization") String loginToken, @Body UserSearch_Default body);

    @POST("/user/search")
    Call<Object> userSearch(@Header("authorization") String loginToken, @Body UserSearch_Default body);

    @POST("/createEvent")
    Call<Object> eventCreate(@Header("authorization") String loginToken, @Body EventCreate_Body body);

    @POST("/deleteEvent")
    Call<Object> eventDelete(@Header("authorization") String loginToken, @Body EventDelete_Body body);

    @POST("/updateEvent")
    Call<Object> eventUpdate(@Header("authorization") String loginToken, @Body EventUpdate_Body body);

    @GET("/invitedEvents")
    Call<Object> eventsInvited(@Query("id") String userUid);

    @GET("/invitedEvents")
    Call<Object> eventsInvited(@Query("id") String userUid, @Query("active") String isActive);

    @GET("/events")
    Call<Object> eventsGet(@Query("hostId") String userUid);

    @GET("/events")
    Call<Object> eventsGet(@Query("hostId") String userUid, @Query("active") String isActive);

    @GET("/event")
    Call<Object> eventGet(@Query("event") String eventUid);

    @POST("/joinEvent")
    Call<Object> eventJoin(@Header("authorization") String loginToken, @Body EventJoin_Body body);
}
