@file:Suppress("DEPRECATION")

package com.example.layaout.myIncomeExpence.helperFunctions

import android.app.Activity
import android.content.Context
import android.content.res.Configuration
import android.os.Build
import androidx.compose.runtime.Composable
import androidx.compose.runtime.staticCompositionLocalOf
import com.example.layaout.ui.theme.translate
import java.util.Locale


fun changeLanguage(activity: Activity, languageCode: String) {
    LocaleHelper.saveLanguagePreference(activity, languageCode)
    LocaleHelper.setAppLocale(activity, languageCode)

    activity.recreate() // بازسازی اکتیویتی بدون ریستارت کامل
}


object LocaleHelper {
    fun setAppLocale(context: Context, language: String): Context {
        saveLanguagePreference(context, language)

        val locale = Locale(language)
        Locale.setDefault(locale)

        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            // اندروید 12 و بالاتر
            val config = Configuration(context.resources.configuration)
            config.setLocale(locale)
            context.createConfigurationContext(config)
        } else {
            // اندروید 11 و پایین‌تر
            val resources = context.resources
            val config = resources.configuration
            config.setLocale(locale)
            resources.updateConfiguration(config, resources.displayMetrics)
            context
        }
    }

    fun saveLanguagePreference(context: Context, language: String) {
        val prefs = context.getSharedPreferences("settings", Context.MODE_PRIVATE)
        prefs.edit().putString("app_language", language).apply()
    }

    fun loadLanguagePreference(context: Context): String {
        val prefs = context.getSharedPreferences("settings", Context.MODE_PRIVATE)
        return prefs.getString("app_language", "en") ?: "en"
    }
}

val systemLanguage : String
    get() = Locale.getDefault().language

val LocalLanguageApp = staticCompositionLocalOf { "en" }

@Composable
fun getText (key: String): String {
    val currentLanguage = LocalLanguageApp.current
    return translate[currentLanguage]?.get(key) ?: translate["en"]?.get(key) ?: key
}
