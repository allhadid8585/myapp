package com.example.layaout

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SwipeToDeleteList() {
    var items by remember { mutableStateOf((1..10).map { "آیتم $it" }) }
    var itemToDelete by remember { mutableStateOf<String?>(null) } // برای ذخیره آیتمی که قرار است حذف شود


    LazyColumn {
        items(items, key = { it }) { item ->






        }
    }
}

@Composable
fun DeleteItem (
    item: String?,
    onDismissRequest: ()-> Unit,
    onConfirm: () -> Unit
){
    AlertDialog(
        onDismissRequest = {
            onDismissRequest()
        }, // بستن دیالوگ بدون حذف
        title = { Text("حذف آیتم") },
        text = { Text("آیا از حذف \"$item\" مطمئن هستید؟") },
        confirmButton = {
            TextButton(onClick = {
                onConfirm()
                onDismissRequest()
            }) {
                Text("بله", color = Color.Red)
            }
        },
        dismissButton = {
            TextButton(onClick = {
                onDismissRequest()
            }) {
                Text("خیر")
            }
        }
    )
}
