package com.sbtopzzz.quicc.Activities.UserHomeActivity_Fragments.SharedClasses;

import androidx.annotation.NonNull;

import com.sbtopzzz.quicc.API.Funcs;
import com.sbtopzzz.quicc.API.Schemas.User;

public class CurrentUser {
    public static void ReloadUser() {
        Funcs.userGetByEmail(user.getEmailId(), user.getEmailId(), new Funcs.UserGetResult() {
            @Override
            public void onSuccess(@NonNull User user) {
                CurrentUser.user = user;
            }

            @Override
            public void onWarning(String errorText) {
                user = null;
            }

            @Override
            public void onFailure(@NonNull Throwable t) {
                user = null;
            }
        });
    }
    public static User user;
}
