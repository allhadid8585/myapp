package com.example.layaout.delete

import android.annotation.SuppressLint
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectDragGesturesAfterLongPress
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
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
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.consumePositionChange
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.input.pointer.positionChange
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.math.abs


@SuppressLint("UseOfNonLambdaOffsetOverload")
@Composable
fun SwipeToDelete(
    modifier: Modifier = Modifier,
    onConfirmRequest: () -> Unit,
    content: @Composable () -> Unit
) {
    var isOpen by remember { mutableStateOf(false) }
    var offsetX by remember { mutableStateOf(0f) }
    val animatedOffsetX by animateFloatAsState(
        targetValue = if (offsetX < -90f) -100f else 0f, label = "OffsetAnimation"
    )
    var boxHeight by remember { mutableStateOf(IntSize.Zero) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 10.dp, end = 10.dp, bottom = 1.dp)
    ) {
        Box(
            modifier = Modifier.fillMaxWidth()
        ) {
            // پس زمینه قرمز (حذف)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .size(with(LocalDensity.current) { boxHeight.height.toDp() })
                    .clip(RoundedCornerShape(10.dp))
                    .background(Color.Red)
                    .padding(end = 20.dp),
                contentAlignment = Alignment.CenterEnd
            ) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "حذف",
                    tint = Color.White
                )
            }

            // محتوای اصلی (کارت قابل کشیدن)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .offset(x = animatedOffsetX.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(Color.White)
                    .pointerInput(Unit) {
                        detectHorizontalDragGestures(
                            onHorizontalDrag = { change, dragAmount ->

                                if (abs(change.position.x) > abs(change.position.y)) { // درگ افقی باشد
                                    if (change.positionChange() != Offset.Zero) {
                                        change.consume()
                                        offsetX = (offsetX + dragAmount).coerceIn(-100f, 0f)
                                    }
                                }
                            },
                            onDragEnd = {
                                if (offsetX < -90f) {
                                    isOpen = true
                                } else {
                                    offsetX = 0f
                                }
                            }
                        )
                    }
                    .onGloballyPositioned { boxHeight = it.size },
                contentAlignment = Alignment.CenterStart
            ) {
                content()
                if (isOpen) {
                    DeleteItem(
                        onDismissRequest = {
                            isOpen = false
                            offsetX = 0f
                        }
                    ) {
                        isOpen = false
                        offsetX = 0f
                        onConfirmRequest()
                    }
                }
            }
        }
    }
}



@Composable
fun DeleteItem (
    onDismissRequest: ()-> Unit,
    onConfirmRequest: () -> Unit
){
    AlertDialog(
        onDismissRequest = {
            onDismissRequest()
        }, // بستن دیالوگ بدون حذف
        title = { Text("Delete Item") },
        text = { Text("Do you want to delete it") },
        confirmButton = {
            TextButton(onClick = {
                onConfirmRequest()
                onDismissRequest()
            }) {
                Text("Yes", color = Color.Red)
            }
        },
        dismissButton = {
            TextButton(onClick = {
                onDismissRequest()
            }) {
                Text("No")
            }
        }
    )
}
