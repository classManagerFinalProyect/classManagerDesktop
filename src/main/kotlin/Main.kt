import Screens.theme.ClassManagerTheme
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.res.loadImageBitmap
import androidx.compose.ui.res.useResource
import androidx.compose.ui.window.*
import Navigation.NavigationHost


fun main() = application() {

    val icon = BitmapPainter(useResource("logo_sin_fondo.png", ::loadImageBitmap))


    val trayState = rememberTrayState()
    val notification = rememberNotification("Notification", "App is active")



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


