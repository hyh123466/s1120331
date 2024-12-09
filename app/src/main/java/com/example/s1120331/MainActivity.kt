package com.example.s1120331

import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.detectDragGesturesAfterLongPress
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.geometry.Offset
import com.example.s1120331.ui.theme.S1120331Theme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AppContent()
            requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        }
    }
}

@Composable
fun AppContent() {
    val colors = listOf(
        Color(0xff95fe95), // 顏色 1
        Color(0xfffdca0f), // 顏色 2
        Color(0xfffea4a4), // 顏色 3
        Color(0xffa5dfed)  // 顏色 4
    )

    val currentColorIndex = remember { mutableStateOf(0) }

    // 使用 mutableStateOf 創建的 offsetX 變數
    var offsetX by remember { mutableStateOf(0f) }
    var isDragging by remember { mutableStateOf(false) }  // 用來檢測是否在拖曳中

    // 處理拖曳的狀態
    val draggableState = rememberDraggableState { delta ->
        offsetX += delta
    }

    // 當拖曳結束後，根據方向變換顏色
    LaunchedEffect(offsetX) {
        if (!isDragging) {  // 只有在拖曳結束後才處理顏色更新
            if (offsetX > 0) {  // 右滑，改變顏色
                currentColorIndex.value = (currentColorIndex.value + 1) % colors.size
            } else if (offsetX < -1) {  // 左滑，改變顏色
                currentColorIndex.value = (currentColorIndex.value - 1 + colors.size) % colors.size
            }
            offsetX = 0f  // 重置 offset
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(colors[currentColorIndex.value]) // 顯示背景顏色
            .draggable(
                state = draggableState, // 拖曳的狀態
                orientation = Orientation.Horizontal, // 設置為水平方向拖曳
                onDragStarted = { isDragging = true },  // 開始拖曳
                onDragStopped = { isDragging = false }  // 拖曳停止
            ),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text("2024期末上機考(資管二B黃鎰弘)")

            Image(
                painter = painterResource(id = R.drawable.class_b),
                contentDescription = "Class Image"
            )

            Text("遊戲持續時間 0 秒")

            Text("您的成績 0 分")

            Button(onClick = {
                finishApp()
            }) {
                Text("結束App")
            }
        }
    }
}

fun finishApp() {
    android.os.Process.killProcess(android.os.Process.myPid())
}
