package com.example.helldivers2companionapp.utils

import android.content.Context
import android.content.SharedPreferences

class SessionManager(context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences("user_session", Context.MODE_PRIVATE)

    companion object {
        const val KEY_IS_LOGGED_IN = "is_logged_in"
        const val KEY_USERNAME = "username"
        const val KEY_ROLE = "role"
        const val KEY_USER_ID = "user_id" // Tambahan
    }

    // Update fungsi saveSession untuk simpan ID juga
    fun saveSession(username: String, role: String, userId: Int) {
        val editor = prefs.edit()
        editor.putBoolean(KEY_IS_LOGGED_IN, true)
        editor.putString(KEY_USERNAME, username)
        editor.putString(KEY_ROLE, role)
        editor.putInt(KEY_USER_ID, userId) // Simpan ID
        editor.apply()
    }

    fun clearSession() {
        val editor = prefs.edit()
        editor.clear()
        editor.apply()
    }

    fun isLoggedIn(): Boolean = prefs.getBoolean(KEY_IS_LOGGED_IN, false)
    fun getRole(): String? = prefs.getString(KEY_ROLE, null)
    fun getUsername(): String? = prefs.getString(KEY_USERNAME, null)

    // Tambahan getter ID
    fun getUserId(): Int = prefs.getInt(KEY_USER_ID, -1)
}