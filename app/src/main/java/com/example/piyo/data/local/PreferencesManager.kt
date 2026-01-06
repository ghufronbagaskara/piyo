package com.example.piyo.data.local

import android.content.Context
import android.content.SharedPreferences

class PreferencesManager(context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences(
        "piyo_preferences",
        Context.MODE_PRIVATE
    )

    fun hasSeenWelcomePopup(): Boolean {
        return prefs.getBoolean(KEY_WELCOME_POPUP_SHOWN, false)
    }

    fun setWelcomePopupShown() {
        prefs.edit().putBoolean(KEY_WELCOME_POPUP_SHOWN, true).apply()
    }

    companion object {
        private const val KEY_WELCOME_POPUP_SHOWN = "welcome_popup_shown"
    }
}

