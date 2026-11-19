# GotaVida

App de donación de sangre para Guatemala. Proyecto del curso de Programación de Plataformas Móviles (UVG), Grupo #6: Esteban Sánchez, Javier Sánchez y Diego Rodriguez.

## Descripción

En Guatemala, cuando alguien necesita sangre con urgencia, casi siempre es la familia la que tiene que buscar donantes, y lo hace con volantes en redes sociales y grupos de WhatsApp. Eso llega a poca gente y no hay forma de comprobar si la solicitud es real.

GotaVida conecta a los donantes con las instituciones. Hay dos tipos de cuenta. La cuenta médica o de institución (hospitales y bancos de sangre) es la única que puede publicar solicitudes de sangre y jornadas de donación, así los donantes saben que vienen de una institución. La cuenta de donante ve las solicitudes y jornadas, contesta un cuestionario de elegibilidad, confirma que puede donar, ve los lugares en un mapa y revisa sus notificaciones.

## Estado del proyecto

Esta es la entrega final. El registro, el inicio de sesión, las solicitudes, las jornadas y la lista de hospitales son reales: viven en Firebase y se actualizan en tiempo real entre un teléfono y otro. Las notificaciones y el perfil se guardan en el teléfono con Room. Hay partes que siguen siendo de ejemplo, y están explicadas más abajo en la sección "Qué es real y qué es de ejemplo".

## Arquitectura

Usamos MVI. Cada pantalla tiene su carpeta con cuatro cosas: el Screen, que es lo que se dibuja, el ViewModel, que tiene la lógica, el State, una data class con todo lo que la pantalla necesita mostrar, y el Event, un sealed interface con las acciones del usuario.

El flujo es siempre el mismo. La pantalla manda un Event, el ViewModel lo procesa y actualiza el State, y la pantalla se vuelve a dibujar con el State nuevo. El ViewModel expone el State con un StateFlow. Los ViewModels nunca hablan directamente con Firebase ni con Room: siempre pasan por un repositorio.

Para la navegación usamos Navigation Compose con destinos @Serializable. Los destinos están en `ui/navigation/Destinos.kt` y el grafo en `AppNavHost`. No hay rutas escritas como texto, y entre pantallas solo se manda lo necesario, que es un id o el rol de la cuenta.

## Servicios externos

Usamos dos servicios de Firebase, con el plan Spark, que no cuesta nada. Los dos son llamadas reales a internet.

Firebase Authentication maneja el registro y el inicio de sesión con correo y contraseña.

Cloud Firestore es la base de datos en la nube. Tiene dos colecciones. En `solicitudes` se guardan las solicitudes de sangre y las jornadas que publican las médicas, con su estado (activa, cerrada o asignada). Cuando una médica publica algo, el donante lo ve al instante, porque la app escucha la colección con un listener en tiempo real convertido en Flow. En `instituciones` se guardan los hospitales y bancos de sangre, que se usan para autocompletar la institución en el registro de la cuenta médica y para poner la dirección en cada solicitud.

Si no hay internet, Firestore guarda las escrituras en el teléfono y las envía cuando vuelve la conexión. Para que la pantalla no se quede cargando para siempre, las escrituras tienen un límite de 8 segundos. Las reglas de Firestore exigen tener la sesión iniciada para leer y escribir.

La lista de hospitales la armamos nosotros. No encontramos una API pública con los hospitales de Guatemala, y el portal de datos abiertos del MSPAS no los publica en formato abierto. Revisamos Google Places, que pide activar la facturación con tarjeta, y OpenStreetMap con Overpass, que es gratis pero tiene límites de consultas y no sabíamos qué tan completos eran los datos de Guatemala. Al final armamos una lista propia con 10 instituciones y la subimos a Firestore. La primera vez que alguien entra al registro de una cuenta médica, la app sube la lista si la colección está vacía. Si Firestore no responde, usa la misma lista guardada en el código (`FakeHospitalesDataSource`) como respaldo.

## Almacenamiento local

Usamos Room. Ahí se guarda en el teléfono el perfil de la cuenta (nombre, correo, rol, estado de la cuenta y, según el caso, el tipo de sangre y la ciudad si es donante, o la institución y el número de colegiado si es médica) y las notificaciones. Firebase solo guarda el correo y la contraseña, por eso el rol lo leemos de Room cuando alguien inicia sesión.

La base de datos va en la versión 2 y usa migración destructiva. Si alguien tiene instalada una versión anterior y actualiza, se pierde el perfil local y hay que volver a registrarse.

## Intents

En el mapa, el botón "Cómo llegar" abre la app de mapas del teléfono con el nombre y la dirección del lugar, usando un intent implícito.

## Qué es real y qué es de ejemplo

Es real: el registro y el inicio de sesión, las solicitudes y jornadas que publican las médicas, el feed del donante, el detalle de cada solicitud, el cierre de una solicitud por parte de la médica, la lista de hospitales, el nombre y los datos del perfil y las notificaciones guardadas en Room.

Es de ejemplo o está simplificado:

- La revisión de las cuentas médicas. No hay un backend que revise a las instituciones. Al registrarse, la cuenta médica queda como EN_REVISION y se muestra la pantalla "Tu cuenta está en revisión", pero es una simulación. Igual se puede iniciar sesión, para que se puedan ver todas las funciones.
- Las notificaciones. Las que se ven al principio son de ejemplo y se cargan la primera vez que se abre la app. Las que se crean al confirmar una donación se guardan en el teléfono de quien confirma, no llegan al teléfono de la médica.
- El mapa. Es una vista esquemática: los puntos se colocan según la zona de cada solicitud y no son coordenadas reales. La vista de lista es la más confiable.
- El historial de donaciones, las insignias y las estadísticas del dashboard médico.
- Las preguntas del cuestionario de elegibilidad, que están escritas en el código (`data/fake/`).
- La confirmación de donación no se guarda en la nube.

## Librerías

Para la interfaz usamos Jetpack Compose con Material 3, que nos da los componentes y el tema de colores, tipografía y formas. También usamos Material Icons Extended para los íconos y Activity Compose, que es el punto de entrada de la interfaz.

Para la navegación usamos Navigation Compose junto con kotlinx.serialization, que permite que los destinos sean clases @Serializable en lugar de rutas de texto. Para el estado de cada pantalla usamos Lifecycle ViewModel. Lifecycle Runtime KTX y Core KTX nos dan extensiones de Kotlin para Android.

Para la base de datos local usamos Room. También usamos KSP, que genera el código de Room al compilar.

Para los servicios en la nube usamos Firebase, con la BoM y las librerías firebase-auth y firebase-firestore. Usamos la BoM 34.19.0 porque la 35 no compila con nuestra versión de Kotlin (2.2.10). El plugin google-services lee el archivo google-services.json y configura Firebase, y kotlinx-coroutines-play-services nos deja esperar las respuestas de Firebase con corrutinas.

Por último, el proyecto trae JUnit, Espresso y Compose UI Test como dependencias de pruebas.

## Estructura del proyecto

```
app/src/main/java/com/uvg/gotavida/
├── data/
│   ├── model/        data classes y enums
│   ├── fake/         datos de ejemplo y lista de respaldo de hospitales
│   ├── local/        Room (usuarios y notificaciones: entidades, DAOs, AppDatabase y repositorios)
│   ├── remote/       Firestore (SolicitudesRepository e InstitucionesRepository)
│   └── auth/         Firebase Auth (AuthRepository y CuentaRepository)
├── di/               ServiceLocator, crea las dependencias una sola vez
├── splash/           pantalla de carga
├── ui/
│   ├── navigation/   Destinos y AppNavHost
│   ├── theme/        colores, tipografía y tema
│   ├── components/   botones, campos de texto y chips reutilizables
│   ├── auth/         selección de cuenta, login y registro
│   ├── shared/       notificaciones y ajustes
│   ├── donante/      pantallas del donante (home, mapa, detalle, cuestionario, confirmación, perfil)
│   └── vistaadmin/   pantallas de la cuenta médica (dashboard, nueva solicitud, solicitudes, perfil)
├── GotaVidaApp.kt    inicializa el ServiceLocator
└── MainActivity.kt
```

## Cómo correrla

Hay que abrir el proyecto en Android Studio y sincronizar Gradle. Después se corre en un emulador o celular con Android 8.0 (API 26) o más, y con internet, porque el registro, el login y las solicitudes usan Firebase. El archivo `app/google-services.json` ya está en el repositorio, así que no hay que configurar nada más.

Para probar el flujo completo se necesitan dos cuentas: una médica, que publica una solicitud o jornada, y una de donante, que la ve y confirma. La cuenta médica queda "en revisión", pero igual puede iniciar sesión.

## APK

El APK de la entrega final está en la carpeta [APK](APK/) y también se distribuyó por Firebase App Distribution.
