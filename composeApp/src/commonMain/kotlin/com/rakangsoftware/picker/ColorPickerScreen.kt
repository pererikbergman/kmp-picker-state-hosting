package com.rakangsoftware.picker

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun ColorPickerScreen(
    onColorPicked:(ColorItem) -> Unit
) {
    val colorList = listOf(
        ColorItem("Red", Color.Red),
        ColorItem("Green", Color.Green),
        ColorItem("Blue", Color.Blue),
        ColorItem("Yellow", Color.Yellow),
        ColorItem("Cyan", Color.Cyan),
        ColorItem("Magenta", Color.Magenta),
        ColorItem("Black", Color.Black),
        ColorItem("White", Color.White),
        ColorItem("Gray", Color.Gray),
        ColorItem("Orange", Color(0xFFFFA500))
    )

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(colorList) { colorItem ->
            ColorOption(
                colorItem, onColorPicked
            )
        }
    }
}

@Composable
fun ColorOption(
    colorItem: ColorItem,
    onColorPicked: (ColorItem) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onColorPicked(colorItem) }
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Box(
            modifier = Modifier
                .size(48.dp)
                .background(colorItem.color, shape = MaterialTheme.shapes.medium)
                .border(2.dp, Color.Black.copy(alpha = 0.5f), shape = MaterialTheme.shapes.medium)
        )

        Spacer(modifier = Modifier.width(16.dp))

        Text(
            text = colorItem.name,
            style = MaterialTheme.typography.bodyLarge.copy(
                color = MaterialTheme.colorScheme.onSurface,
                fontWeight = FontWeight.Medium
            )
        )
    }
}