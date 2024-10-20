# **Build a Color Picker App with Jetpack Compose – Master State Hoisting in Kotlin Multiplatform**

Welcome to the repository for the **Color Picker App** built with **Jetpack Compose** and **Kotlin Multiplatform**. This project demonstrates how to manage state effectively using **state hoisting** between multiple screens.

## **Video Tutorial Overview**

In the accompanying [YouTube video](https://youtu.be/eCgNhCFMpQA), we will walk through:

1. **Understanding state hoisting** and its benefits.
2. **Setting up the project** and adding necessary dependencies.
3. Implementing the **ColorItem data class** to represent colors.
4. **Building navigation** using the state hoisting pattern.
5. Creating the **MainScreen** and **ColorPickerScreen**.
6. Running the app and seeing it in action.
7. **Why state hoisting** is a beneficial approach compared to other methods like shared ViewModels.

---

## **Project Setup**

To get started, download a project using the [Kotlin Multiplatform Wizard](https://kmp.jetbrains.com/) ensure you have a Kotlin Multiplatform project with the following dependencies in your `gradle/libs.versions.toml` file:

```toml
[versions]
# Compose Navigation
jetbrainsNavigationCompose = "2.8.0-alpha10"

[libraries]
# Compose Navigation
navigation-compose-vx = { module = "org.jetbrains.androidx.navigation:navigation-compose", version.ref = "jetbrainsNavigationCompose" }

```

in your `build.gradle` file:

```gradle
kotlin {
    sourceSets {
        commonMain.dependencies {
            // Navigation
            implementation(libs.navigation.compose.vx)
        }
    }
}
```

---

## **Implementing the ColorItem Data Class**

We’ll begin by creating a `ColorItem` data class to represent the available colors in our app:

```kotlin
// ColorItem.kt
package com.rakangsoftware.picker

import androidx.compose.ui.graphics.Color

data class ColorItem(
    val name: String,
    val color: Color
)
```

This simple class holds the color’s name and its corresponding `Color` value.

---

## **MainScreen Composable**

This composable displays the picked color and provides buttons to choose or clear the color:

```kotlin
// MainScreen.kt
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
    onOpenColorPicker: () -> Unit,
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
        if (pickedColor != null) {
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

            Button(onClick = onOpenColorPicker) {
                Text("Pick Another Color")
            }
            Spacer(modifier = Modifier.height(16.dp))

            Button(onClick = onClearColor) {
                Text("Clear Selection")
            }
        } else {
            Text(
                text = "No color selected",
                style = MaterialTheme.typography.titleLarge
            )
            Spacer(modifier = Modifier.height(32.dp))
            Button(onClick = onOpenColorPicker) {
                Text("Pick a Color")
            }
        }
    }
}
```

---

## **Color Picker Screen**

The color picker screen allows the user to select from a list of predefined colors:

```kotlin
// ColorPickerScreen.kt
package com.rakangsoftware.picker

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.font.FontWeight

@Composable
fun ColorPickerScreen(
    onColorPicked: (ColorItem) -> Unit
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
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(colorList) { colorItem ->
            ColorOption(
                colorItem = colorItem,
                onColorPicked = onColorPicked
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
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
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
```

---

## **App Navigation with State Hoisting**

We’ll manage the navigation and hoist the state using the `NavHost` composable:

```kotlin
// AppNavigation.kt
package com.rakangsoftware.picker

import androidx.compose.runtime.*
import androidx.navigation.compose.*
import androidx.compose.ui.graphics.Color

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    var pickedColor by remember { mutableStateOf<ColorItem?>(null) } // Hoisted state

    NavHost(navController, startDestination = "main_screen") {
        composable("main_screen") {
            MainScreen(
                onOpenColorPicker = { navController.navigate("color_picker_screen") },
                onClearColor = { pickedColor = null },
                pickedColor = pickedColor
            )
        }
        composable("color_picker_screen") {
            ColorPickerScreen(
                onColorPicked = { colorItem ->
                    pickedColor = colorItem // Update the hoisted state
                    navController.popBackStack()
                }
            )
        }
    }
}
```
## **Finishing the App**

To complete the app, simply call `AppNavigation` from the `App` function:


```kotlin
// App.kt
package com.rakangsoftware.picker

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    MaterialTheme {
        AppNavigation()
    }
}
```

---



## **Running the App**

After implementing the above components, run the app to see the following in action:
1. Pick a color from the list.
2. Display the selected color in the main screen.
3. Clear the selected color and reset the state.

---

## **Why State Hoisting?**

State hoisting provides several benefits:
- **Decoupled Components**: Independent composables allow for better reusability and testability.
- **Simplified State Management**: The shared state is managed centrally in `AppNavigation`.
- **Unidirectional Data Flow**: Ensures predictable UI behavior.

---

## **Future Enhancements**

You can expand on this project by:
1. Adding more colors or custom color input.
2. Persisting the selected color across app sessions using storage.
3. Improving the UI with animations and enhanced styling.

---

## **Contributing**

Feel free to contribute by submitting issues or pull requests to improve this project!

---

## **License**

This project is licensed under the MIT License.

---

That’s it! Follow the video tutorial for more in-depth explanations and demonstrations.
