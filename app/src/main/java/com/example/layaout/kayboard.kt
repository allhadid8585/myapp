package com.example.layaout

import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.relocation.bringIntoViewRequester
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import kotlin.math.sin


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun kayboard (){

    var fullname by remember {
        mutableStateOf("")
    }
    var name by remember {
        mutableStateOf("")
    }
    var password by remember {
        mutableStateOf("")
    }

    var focusrequester = remember {
        FocusRequester()
    }

    var focus = LocalFocusManager.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .clickable { focus.clearFocus() }
        ,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
        ) {

        TextField(
            value = fullname,
            singleLine = true,
            onValueChange = {fullname = it},
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Words,
                keyboardType = KeyboardType.Uri,
                imeAction = ImeAction.Search
                ),
            keyboardActions = KeyboardActions(
                onSearch = {
                    focus.moveFocus(FocusDirection.Down)
                }
            ),
        )
        TextField(
            value = fullname,
            singleLine = true,
            onValueChange = {fullname = it},
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Words,
                keyboardType = KeyboardType.Uri,
                imeAction = ImeAction.Search
            ),
            keyboardActions = KeyboardActions(
                onSearch = {
                    focus.moveFocus(FocusDirection.Down)
                }
            ),
        )
        TextField(
            value = fullname,
            singleLine = true,
            onValueChange = {fullname = it},
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Words,
                keyboardType = KeyboardType.Uri,
                imeAction = ImeAction.Search
            ),
            keyboardActions = KeyboardActions(
                onSearch = {
                    focus.moveFocus(FocusDirection.Down)
                }
            ),
        )
        TextField(
            value = fullname,
            singleLine = true,
            onValueChange = {fullname = it},
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Words,
                keyboardType = KeyboardType.Uri,
                imeAction = ImeAction.Search
            ),
            keyboardActions = KeyboardActions(
                onSearch = {
                    focus.moveFocus(FocusDirection.Down)
                }
            ),
        )

        TextField(
            value = name,
            onValueChange = {name = it},
            singleLine = true
        )
        TextField(
            value = password,
            onValueChange = {password = it},
            singleLine = true
        )
    }
}