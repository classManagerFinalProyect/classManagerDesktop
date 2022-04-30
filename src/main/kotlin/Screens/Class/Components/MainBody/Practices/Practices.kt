package Screens.Class.Components.MainBody.Practices

import ScreenItems.bigTextFieldWithErrorMessage
import Screens.Class.ViewModelClass
import Screens.theme.blue
import Utils.isDate
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import data.remote.Practice

@Composable
fun practices() {

    var selectedPractices by remember { mutableStateOf(Practice("","","","","","","")) }
    var dateError by remember { mutableStateOf(false) }
    val messageDateClassError by remember { mutableStateOf("La fecha debe seguir el siguiente formato: dd/mm/yyyy") }

    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        content = {

                Column(
                    modifier = Modifier
                        .fillMaxHeight()
                        .fillMaxWidth(0.3f),
                    content = {
                        LazyColumn(
                            content = {

                                item {
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceEvenly,
                                        verticalAlignment = Alignment.CenterVertically,
                                        content = {
                                            Text(
                                                modifier = Modifier
                                                    .padding(bottom = 5.dp),
                                                text = "Listado de prácticas",
                                                fontSize = 20.sp
                                            )
                                            IconButton(
                                                onClick = {

                                                },
                                                content = {

                                                    Icon(
                                                        imageVector = Icons.Default.Add,
                                                        contentDescription = "Añadir práctica",
                                                        tint = blue
                                                    )
                                                }
                                            )
                                        }
                                    )

                                }

                                itemsIndexed(ViewModelClass.currentPractices) { index: Int, item: Practice ->
                                    Surface(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .height(60.dp),
                                        content = {
                                            Column(
                                                modifier = Modifier.fillMaxHeight(),
                                                content = {
                                                    TextButton(
                                                        onClick =  {
                                                            selectedPractices = item

                                                        },
                                                        content = {
                                                            Text(
                                                                modifier = Modifier.fillMaxWidth(),
                                                                text = item.name,
                                                                textAlign = TextAlign.Start
                                                            )
                                                        }
                                                    )
                                                    Column(
                                                        modifier = Modifier
                                                            .fillMaxWidth(0.9f)
                                                            .border(BorderStroke(1.dp, Color.LightGray)),
                                                        content = {
                                                            Spacer(modifier = Modifier.padding(1.dp))
                                                        }
                                                    )
                                                }
                                            )

                                        }
                                    )

                                }
                            }
                        )
                    }
                )



                Column(
                    modifier = Modifier
                        .fillMaxHeight()
                        .background(Color.White)
                        .fillMaxWidth()
                        .background(Color.LightGray),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    content = {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth(0.9f)
                                .fillMaxHeight(0.9f)
                                .background(Color.Magenta),
                            content = {
                                bigTextFieldWithErrorMessage(
                                    text = "Fecha de entrega",
                                    value = selectedPractices.deliveryDate,
                                    onValueChange = { selectedPractices.deliveryDate = it },
                                    validateError = ::isDate,
                                    errorMessage = messageDateClassError,
                                    changeError = { dateError = it },
                                    error = dateError,
                                    mandatory = true,
                                    KeyboardType = KeyboardType.Text
                                )
                            }
                        )


                    }
                )





        }
    )
}