package com.harryjjacobs.musiq.spotify

import android.content.Context
import android.content.SharedPreferences
import java.util.concurrent.TimeUnit


object SpotifyAuthHelper {
    const val CLIENT_ID = "808fb42b646648008d475bb0c60cde58";
    const val CLIENT_SECRET = "60f6e0d7a27a4d479de4bd0d2a6f493b";
    const val REDIRECT_URI = "com.harryjjacobs.musiq://callback";
    private const val ACCESS_TOKEN_NAME = "webapi.credentials.access_token";
    private const val ACCESS_TOKEN = "access_token";
    private const val EXPIRES_AT = "expires_at";

    fun setToken(
        context: Context,
        token: String?,
        expiresIn: Int,
        unit: TimeUnit
    ) {
        val appContext: Context = context.applicationContext
        val now = System.currentTimeMillis()
        val expiresAt: Long = now + unit.toMillis(expiresIn.toLong())
        val sharedPref =
            getSharedPreferences(
                appContext
            )
        val editor = sharedPref.edit()
        editor.putString(ACCESS_TOKEN, token)
        editor.putLong(EXPIRES_AT, expiresAt)
        editor.apply()
    }

    private fun getSharedPreferences(appContext: Context): SharedPreferences {
        return appContext.getSharedPreferences(ACCESS_TOKEN_NAME, Context.MODE_PRIVATE)
    }

    fun getToken(context: Context): String? {
        val appContext: Context = context.applicationContext
        val sharedPref =
            getSharedPreferences(
                appContext
            )
        val token = sharedPref.getString(ACCESS_TOKEN, null)
        val expiresAt = sharedPref.getLong(EXPIRES_AT, 0L)
        return if (token == null || expiresAt < System.currentTimeMillis()) {
            null
        } else token
    }
}