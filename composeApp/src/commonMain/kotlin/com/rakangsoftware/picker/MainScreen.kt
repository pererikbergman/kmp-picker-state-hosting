package com.rakangsoftware.picker

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun MainScreen(
    onOPenColorPicker: () -> Unit,
    onClearColor: () -> Unit,
    pickedColor: ColorItem?
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if(pickedColor != null) {
            Text(
                text = "Picked Color: ${pickedColor.name}",
                style = MaterialTheme.typography.titleLarge
            )
            Spacer(modifier = Modifier.height(16.dp))
            Box(
                modifier = Modifier
                    .size(100.dp)
                    .background(pickedColor.color, shape = MaterialTheme.shapes.large)
                    .border(2.dp, Color.Black.copy(alpha = 0.5f), shape = MaterialTheme.shapes.large)
            )
            Spacer(modifier = Modifier.height(16.dp))

            Button(onClick = onOPenColorPicker) {
                Text(text = "Pick Another Color")
            }
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = onClearColor) {
                Text(text = "Clear Selection")
            }
        } else {
            Text(
                text = "No color selected",
                style = MaterialTheme.typography.titleLarge
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = onOPenColorPicker) {
                Text(text = "Pick a Color")
            }
        }
    }
}