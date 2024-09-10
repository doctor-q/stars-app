package cc.doctor.stars_app.state;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.Nullable;

import cc.doctor.stars_app.ui.login.LoginResponse;

public class LoginState {

    private static final String TOKEN = "TOKEN";
    private static final String LOGGED = "LOGGED";

    private SharedPreferences sharedPreferences;
    private static LoginState loginState;

    public static LoginState getInstance(Context context) {
        if (loginState == null) {
            loginState = new LoginState();
            loginState.sharedPreferences = context.getSharedPreferences(LoginState.class.getSimpleName(), Context.MODE_PRIVATE);
        }
        return loginState;
    }

    @Nullable
    public String token() {
        return sharedPreferences.getString(TOKEN, null);
    }

    public boolean logged() {
        return sharedPreferences.getBoolean(LOGGED, false);
    }

    public void setLogged(LoginResponse response) {
        if (loginState == null) {
            return;
        }
        SharedPreferences.Editor editor = loginState.sharedPreferences.edit();
        if (response == null) {
            editor.putBoolean(LOGGED, false);
        } else {
            editor.putBoolean(LOGGED, true);
            editor.putString(TOKEN, response.getToken());
        }
        editor.apply();
    }
}
