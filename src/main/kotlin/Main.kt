// Copyright 2000-2021 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
import Navigation.NavigationHost
import Screens.theme.ClassManagerTheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyShortcut
import androidx.compose.ui.res.loadImageBitmap
import androidx.compose.ui.res.useResource
import androidx.compose.ui.window.*


fun main() = application() {

    val icon = BitmapPainter(useResource("logo_sin_fondo.png", ::loadImageBitmap))


    val trayState = rememberTrayState()
    val notification = rememberNotification("Notification", "App is active")

    var action by remember { mutableStateOf("Last action: None") }


    Tray(
        state = trayState,
        icon = icon,
        menu = {
            Item(
                text ="Send notification",
                onClick = { trayState.sendNotification(notification) }
            )
            Item(
                text = "Quit App",
                onClick = ::exitApplication
            )
        }
    )

    Window(
        onCloseRequest = ::exitApplication,
        icon = icon,
        title = "Class Manager"
    ) {
        MenuBar(
            content = {
                Menu(
                    text = "Options",
                    mnemonic = 'O',
                    content = {
                        Item("Send notification", onClick = { trayState.sendNotification(notification) })
                        Item("Exit", onClick = ::exitApplication )
                    }
                )
            }
        )

        ClassManagerTheme {
            NavigationHost()
        }
    }

}


