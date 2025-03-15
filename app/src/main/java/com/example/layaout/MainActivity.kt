package com.example.layaout

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.rememberNavController
import com.example.layaout.myIncomeExpence.MainScreen
import com.example.layaout.myIncomeExpence.helperFunctions.LocalLanguageApp
import com.example.layaout.myIncomeExpence.helperFunctions.LocaleHelper
import com.example.layaout.myIncomeExpence.helperFunctions.systemLanguage
import com.example.layaout.myIncomeExpence.screen.NativeChatList
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {

        applyLocale()
        super.onCreate(savedInstanceState)

        setContent {
            MaterialTheme {

                val navController = rememberNavController()
                Log.d("TransactionScreen", "NavController created")
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Log.d("TestLog", "MainActivity started!")

                    CompositionLocalProvider(LocalLanguageApp provides systemLanguage) {
                        MainScreen(activity = this)
                    }

                }
            }
        }
    }
    private fun applyLocale() {
        val language = LocaleHelper.loadLanguagePreference(this)
        LocaleHelper.setAppLocale(this, language)
    }
}
