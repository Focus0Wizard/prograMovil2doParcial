package com.calyrsoft.ucbp1.core

import android.content.Context
import android.content.SharedPreferences

class AuthManager(private val context: Context) {
    private val prefs: SharedPreferences by lazy {
        context.getSharedPreferences("app_auth", Context.MODE_PRIVATE)
    }

    fun saveLoginState(isLoggedIn: Boolean, username: String = "") {
        prefs.edit().apply {
            putBoolean("is_logged_in", isLoggedIn)
            if (username.isNotEmpty()) {
                putString("username", username)
            }
        }.apply()
    }

    fun isUserLoggedIn(): Boolean {
        return prefs.getBoolean("is_logged_in", false)
    }

    fun getLoggedInUsername(): String {
        return prefs.getString("username", "") ?: ""
    }

    fun logout() {
        prefs.edit().remove("is_logged_in").remove("username").apply()
    }
}