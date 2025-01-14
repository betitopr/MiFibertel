package com.example.mifibertel.data.local

import android.content.Context
import com.example.mifibertel.domain.model.AuthToken
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class TokenManager @Inject constructor(
    @ApplicationContext private val context: Context) {
    private val prefs = context.getSharedPreferences("auth_prefs", Context.MODE_PRIVATE)

    fun saveToken(token: AuthToken) {
        prefs.edit()
            .putString("access_token", token.access)
            .putString("refresh_token", token.refresh)
            .apply()
    }

    fun getAccessToken(): String? {
        return prefs.getString("access_token", null)
    }

    fun getRefreshToken(): String? {
        return prefs.getString("refresh_token", null)
    }

    fun clearTokens() {
        prefs.edit().clear().apply()
    }
}