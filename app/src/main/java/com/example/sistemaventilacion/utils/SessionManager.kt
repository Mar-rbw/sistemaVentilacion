package com.example.sistemaventilacion.utils

import android.content.Context

class SessionManager(private val context: Context) {

    private val prefs = context.getSharedPreferences("user_session", Context.MODE_PRIVATE)

    companion object {
        private const val KEY_USERNAME = "username"
        private const val KEY_LOGGED_IN = "logged_in"
    }

    fun saveLogin(username: String) {
        prefs.edit()
            .putString(KEY_USERNAME, username)
            .putBoolean(KEY_LOGGED_IN, true)
            .apply()
    }

    fun getUsername(): String? {
        return prefs.getString(KEY_USERNAME, null)
    }

    fun isLoggedIn(): Boolean {
        return prefs.getBoolean(KEY_LOGGED_IN, false)
    }

    fun clearSession() {
        prefs.edit().clear().apply()
    }
}
