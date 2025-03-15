
/**
package com.example.layaout

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue


import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.fragment.app.FragmentActivity
import com.example.layaout.lazylistState.MyLyziListScreen
import com.example.layaout.tiempicker.DateRangePickerExample
import com.example.layaout.tiempicker.FilterItemsScreen
import com.example.layaout.tiempicker.MainScreen
import com.example.layaout.ui.theme.LayaoutTheme
import dagger.hilt.android.components.FragmentComponent

class MainActivity : FragmentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            LayaoutTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    DateRangePickerExample( this)
                }
            }
        }
    }
}

@Composable
fun AnimTest (){
    Column {
        var visible by remember {
            mutableStateOf(true)
        }
        Switch(checked =visible , onCheckedChange = {visible = !visible})


        val density = LocalDensity.current
        AnimatedVisibility(visible = visible,
            enter = slideInHorizontally { with(density){-40.dp.roundToPx()} } + expandVertically(expandFrom = Alignment.Top)+ fadeIn(initialAlpha = 0f),
            exit = slideOutVertically()+ shrinkVertically()+ fadeOut()
        ) {
            Text(text = "Hello World", modifier = Modifier


            )
        }
    }



} **/