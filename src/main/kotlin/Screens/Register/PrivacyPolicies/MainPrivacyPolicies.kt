package Screens.Register.PrivacyPolicies

import Screens.Register.PrivacyPolicies.Items.textItem
import Screens.Register.PrivacyPolicies.Items.textTitle
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


@Composable
fun MainPrivacyPolicies() {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Políticas de privacidad")
                }
            )
        },
        content = {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxSize(),
                content = {
                    LazyColumn(
                        modifier = Modifier.fillMaxWidth(0.8f),
                        content = {
                            item {
                                textTitle("Nuestras políticas de privacidad")
                            }
                            item {
                                textItem(
                                    "Su privacidad es importante para nosotros. Es política de Class Manager respetar su privacidad y cumplir con cualquier ley y " +
                                            "regulación aplicable con respecto a cualquier información persona que podamos recopilar sobre usted.\n" +
                                            "Esta política es efectiva a partir del 1 de noviembre de 2021 y se actualizó por última vez el 1 de noviembre de 2021"
                                )
                            }
                            item {
                                textTitle(
                                    "Registro de datos"
                                )
                            }
                            item {
                                textItem(
                                    "Cuando visita nuestro sitio web, nuestros servidores pueden registrar automáticamente los datos estándar proporcionados\n" +
                                            "por su navegador web. Puede incluir la dirección de Protocolo de Internet (IP) de su dispositivo, el tipo y la versión de su navegador, las\n" +
                                            "páginas que visita, la hora y la fecha de su visita, el tiempo que pasa en cada página, otros detalles sobre su visita y los detalles técnicos que ocurren\n" +
                                            "en junto con cualquier error que pueda encontrar.\n" +
                                            "Tenga en cuenta que, si bien es posible que esta información no sea de identificación personal por sí misma," +
                                            "es posible combinarla con otros datos para identificarpersonalmente a personas individuales."
                                )
                            }
                            item {
                                textTitle(
                                    "Colección y uso de información"
                                )
                            }
                            item {
                                textItem(
                                    "Podemos recopilar información personal de usted cuando realiza cualquiera de las siguientes acciones en nuestro sitio web:\n" +
                                            "Utilice un dispositivo móvil o un navegador web para acceder a nuestro contenido.\nContáctenos por correo electrónico, redes sociales o cualquier " +
                                            "tecnología similar.\nCuando nos mencionas en las redes sociales." +
                                            "Podemos recopilar, retener, usar y divulgar información para los siguientes propósitos, y la información personal no se procesará más de una manera que sea incompatible con estos propósitos:" +
                                            "Tenga en cuenta que podemos combinar la información que recopilamos sobre usted con información general o datos de investigación que recibimos de otras fuentes confiables."
                                )
                            }
                            item {
                                textTitle(
                                    "Seguridad sobre tu información personal"
                                )
                            }
                            item {
                                textItem(
                                    "Cuando recopilamos y procesamos información personal, y mientras conservamos esta información, la protegeremos dentro de medios comercialmente aceptables para evitar pérdidas y robos, así como acceso, divulgación, copia, uso o modificación no autorizados.\n" +
                                            "Aunque haremos todo lo posible para proteger la información personal que nos proporcione, le aconsejamos que ningún método de transmisión o almacenamiento electrónico es 100% seguro y nadie puede garantizar la seguridad absoluta de los datos. Cumpliremos con las leyes que nos sean aplicables con respecto a cualquier violación de datos.\n" +
                                            "Usted es responsable de seleccionar cualquier contraseña y su nivel de seguridad general, garantizando la seguridad de su propia información dentro de los límites de nuestros servicios."
                                )
                            }
                            item {
                                textTitle(
                                    "¿Durante cuanto tiempo almacenamos su información personal?"
                                )
                            }
                            item {
                                textItem(
                                    "Conservamos su información personal solo durante el tiempo que sea necesario. Este período de tiempo puede depender de para qué estamos usando su información, de acuerdo con esta política de privacidad. Si su información personal ya no es necesaria, la eliminaremos o la haremos anónima eliminando todos los detalles que lo identifiquen.\n" +
                                            "Sin embargo, si es necesario, podemos retener su información personal para nuestro cumplimiento de una obligación legal, contable o de informes o para fines de archivo en el interés público, fines de investigación científica o histórica o fines estadísticos."
                                )
                            }
                            item {
                                textTitle(
                                    "Transferencia internacional de información personal"
                                )
                            }
                            item {
                                textItem(
                                    "La información personal que recopilamos se almacena y / o procesa donde nosotros o nuestros socios, afiliados y proveedores externos tenemos instalaciones. " +
                                            "Tenga en cuenta que las ubicaciones a las que almacenamos, procesamos o transferimos su información personal pueden no tener las mismas leyes de protección de datos que el país en el que " +
                                            "inicialmente proporcionó la información. Si transferimos su información personal a terceros en otros países: (i) realizaremos esas transferencias de acuerdo con los requisitos de la ley aplicable; y (ii) protegeremos la información personal transferida de acuerdo con esta política de privacidad."
                                )
                            }
                            item {
                                textTitle(
                                    "Derechos y control sobre su información personal"
                                )
                            }
                            item {
                                textItem(
                                    "Siempre se reserva el derecho a retenernos información personal, en el entendido de que su experiencia en nuestro sitio web puede verse afectada. No lo discriminaremos por ejercer cualquiera de sus derechos sobre su información personal. Si nos proporciona información personal, comprende que la recopilaremos, la conservaremos, la usaremos y la divulgaremos de acuerdo con esta política de privacidad. Conserva el derecho a solicitar detalles de cualquier información personal que tengamos sobre usted.\n" +
                                            "Si recibimos información personal sobre usted de un tercero, la protegeremos como se establece en esta política de privacidad. Si usted es un tercero que proporciona información personal sobre otra persona, declara y garantiza que tiene el consentimiento de dicha persona para proporcionarnos la información personal.\n" +
                                            "Si previamente ha aceptado que usemos su información personal con fines de marketing directo, puede cambiar de opinión en cualquier momento. Le proporcionaremos la posibilidad de darse de baja de nuestra base de datos de correo electrónico u optar por no recibir comunicaciones. Tenga en cuenta que es posible que necesitemos solicitarle información específica para ayudarnos a confirmar su identidad.\n" +
                                            "Si cree que la información que tenemos sobre usted es inexacta, desactualizada, incompleta, irrelevante o engañosa, comuníquese con nosotros utilizando los detalles proporcionados en esta política de privacidad. Tomaremos medidas razonables para corregir cualquier información que se considere inexacta, incompleta, engañosa o desactualizada.\n" +
                                            "Si cree que hemos violado una ley de protección de datos relevante y desea presentar una queja, comuníquese con nosotros utilizando los detalles a continuación y bríndenos todos los detalles de la supuesta violación. Investigaremos su queja de inmediato y le responderemos, por escrito, exponiendo el resultado de nuestra investigación y los pasos que tomaremos para tratar su queja. También tiene derecho a ponerse en contacto con un organismo regulador o una autoridad de protección de datos en relación con su queja."
                                )
                            }
                            item {
                                textTitle(
                                    "Uso de Cookies"
                                )
                            }
                            item {
                                textItem(
                                    "Usamos \"cookies\" para recopilar información sobre usted y su actividad en nuestro sitio. Una cookie es un pequeño fragmento de datos que nuestro sitio web almacena en su computadora y al que accede cada vez que lo visita, para que podamos comprender cómo utiliza nuestro sitio. Esto nos ayuda a ofrecerle contenido según las preferencias que haya especificado."
                                )
                            }
                            item {
                                textTitle(
                                    "Límites de nuestra política"
                                )
                            }
                            item {
                                textItem(
                                    "Nuestro sitio web puede tener enlaces a sitios externos que no son operados por nosotros. Tenga en cuenta que no tenemos control sobre el contenido y las políticas de esos sitios, y no podemos aceptar responsabilidad u obligación por sus respectivas prácticas de privacidad."
                                )
                            }
                            item {
                                textTitle(
                                    "Cambios sobre la política"
                                )
                            }
                            item {
                                textItem(
                                    "At our discretion, we may change our privacy policy to reflect updates to our business processes, current acceptable practices, or legislative or regulatory changes. If we decide to change this privacy policy, we will post the changes here at the same link by which you are accessing this privacy policy.\n" +
                                            "Si así lo requiere la ley, obtendremos su permiso o le daremos la oportunidad de optar por participar u optar por no participar, según corresponda, de cualquier nuevo uso de su información personal."
                                )
                            }
                            item {
                                textTitle(
                                    "Contacte con nosotros"
                                )
                            }
                            item {
                                textItem(
                                    "Para cualquier pregunta o inquietud con respecto a su privacidad, puede comunicarse con nosotros utilizando los siguientes detalles:"
                                )
                            }
                            item {
                                Spacer(modifier = Modifier.padding(5.dp))
                                textItem("Nombre: Daniel Sainero Compañ")
                            }
                            item {
                                textItem("Correo: danielSainero.d@gmail.com")
                                Spacer(modifier = Modifier.padding(5.dp))
                            }
                        }
                    )
                }
            )
        }
    )
}