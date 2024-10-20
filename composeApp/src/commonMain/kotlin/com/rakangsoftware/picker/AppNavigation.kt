package com.rakangsoftware.picker

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    var pickedColor by remember { mutableStateOf<ColorItem?>(null) }

    NavHost(navController, startDestination = "main_screen") {
        composable("main_screen") {
            MainScreen(
                onOPenColorPicker = { navController.navigate("color_picker_screen")},
                onClearColor = { pickedColor = null },
                pickedColor = pickedColor
            )
        }

        composable("color_picker_screen") {
            ColorPickerScreen(onColorPicked = { colorItem ->
                pickedColor = colorItem
                navController.popBackStack()
            })
        }
    }
}