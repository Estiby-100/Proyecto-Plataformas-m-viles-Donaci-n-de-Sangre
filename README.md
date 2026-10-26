# GotaVida

App de donación de sangre para Guatemala. Proyecto del curso de Programación de Plataformas Móviles (UVG), Grupo #6: Esteban Sánchez, Javier Sánchez y Diego Rodriguez.

## Descripción

En Guatemala, cuando alguien necesita sangre con urgencia, casi siempre es la familia la que tiene que buscar donantes, y lo hace con volantes en redes sociales y grupos de WhatsApp. Eso llega a poca gente y no hay forma de comprobar si la solicitud es real.

GotaVida conecta a los donantes con las instituciones. Hay dos tipos de cuenta. La cuenta médica o de institución (hospitales y bancos de sangre) es la única que puede publicar solicitudes de sangre, así los donantes saben que vienen de una institución. La cuenta de donante ve las solicitudes y jornadas cercanas, contesta un cuestionario de elegibilidad, confirma que puede donar y revisa su historial.

## Estado del proyecto

Esta es la entrega 5: navegación entre pantallas, MVI y servicios. El registro y el inicio de sesión son reales, porque usan Firebase. Las solicitudes, lugares, perfiles y notificaciones son datos de prueba.

## Arquitectura

Usamos MVI. Cada pantalla tiene su carpeta con cuatro cosas: el Screen, que es lo que se dibuja, el ViewModel, que tiene la lógica, el State, una data class con todo lo que la pantalla necesita mostrar, y el Event, un sealed interface con las acciones del usuario.

El flujo es siempre el mismo. La pantalla manda un Event, el ViewModel lo procesa y actualiza el State, y la pantalla se vuelve a dibujar con el State nuevo. El ViewModel expone el State con un StateFlow.

Para la navegación usamos Navigation Compose con destinos @Serializable. Los destinos están en `ui/navigation/Destinos.kt` y el grafo en `AppNavHost`. No hay rutas escritas como texto, y entre pantallas solo se manda lo necesario, que es un id o el rol de la cuenta.

## Servicios

El único servicio externo es Firebase Authentication, que usamos para el registro y el inicio de sesión con correo y contraseña. Es la llamada a internet de la app. Usamos el plan Spark, que no cuesta nada.

Para la base de datos local usamos Room. Ahí se guarda en el teléfono el perfil de la cuenta: nombre, correo, rol, estado de la cuenta y, según el caso, el tipo de sangre y la ciudad si es donante, o la institución y el número de colegiado si es médica. Firebase solo guarda el correo y la contraseña, por eso el rol lo leemos de Room cuando alguien inicia sesión.

Hay cosas que tuvimos que desarrollar nosotros porque no encontramos un servicio que sirviera. Una son los datos de prueba (`data/fake/`): solicitudes de donación, lugares cercanos, perfil del donante, preguntas de elegibilidad, historial y notificaciones. Hacen el papel del backend que no existe.

Otra es la lista de hospitales y bancos de sangre (`FakeHospitalesDataSource`). No encontramos una API pública con los hospitales de Guatemala, y el portal de datos abiertos del MSPAS no los publica en formato abierto. Revisamos Google Places, que pide activar la facturación con tarjeta, y OpenStreetMap con Overpass, que es gratis pero tiene límites de consultas y no sabíamos qué tan completos eran los datos de Guatemala.

Al final armamos una lista propia con 10 instituciones. Ya está hecha, pero todavía no la conectamos a ninguna pantalla. La idea es usarla para autocompletar el nombre de la institución cuando se registra una cuenta médica.

La última es la revisión de las cuentas médicas. No hay un backend que revise a las instituciones. Al registrarse, la cuenta médica queda como EN_REVISION en Room y se muestra la pantalla "Tu cuenta está en revisión", pero es una simulación. Igual se puede iniciar sesión, para que se puedan ver todas las funciones.

## Librerías

Para la interfaz usamos Jetpack Compose con Material 3, que nos da los componentes y el tema de colores, tipografía y formas. También usamos Material Icons Extended para los íconos y Activity Compose, que es el punto de entrada de la interfaz.

Para la navegación usamos Navigation Compose junto con kotlinx.serialization, que permite que los destinos sean clases @Serializable en lugar de rutas de texto. Para el estado de cada pantalla usamos Lifecycle ViewModel. Lifecycle Runtime KTX y Core KTX nos dan extensiones de Kotlin para Android.

Para la base de datos local usamos Room. También usamos KSP, que genera el código de Room al compilar.

Para la autenticación usamos Firebase, con la BoM y firebase-auth. Usamos la BoM 34.19.0 porque la 35 no compila con nuestra versión de Kotlin (2.2.10). El plugin google-services lee el archivo google-services.json y configura Firebase, y kotlinx-coroutines-play-services nos deja esperar las respuestas de Firebase con corrutinas.

Por último, el proyecto trae JUnit, Espresso y Compose UI Test como dependencias de pruebas.

## Estructura del proyecto

```
app/src/main/java/com/uvg/gotavida/
├── data/
│   ├── model/        data classes y enums
│   ├── fake/         datos de prueba
│   ├── local/        Room (UsuarioEntity, UsuarioDao, AppDatabase, UsuarioRepository)
│   └── auth/         Firebase Auth (AuthRepository y CuentaRepository)
├── di/               ServiceLocator, crea las dependencias una sola vez
├── splash/           pantalla de carga
├── ui/
│   ├── navigation/   Destinos y AppNavHost
│   ├── theme/        colores, tipografía y tema
│   ├── components/   botones, campos de texto y chips reutilizables
│   ├── auth/         selección de cuenta, login y registro
│   ├── shared/       notificaciones y ajustes
│   ├── donante/      pantallas del donante
│   └── vistaadmin/   pantallas de la cuenta médica
├── GotaVidaApp.kt    inicializa el ServiceLocator
└── MainActivity.kt
```

## Cómo correrla

Hay que abrir el proyecto en Android Studio y sincronizar Gradle. Después se corre en un emulador o celular con Android 8.0 (API 26) o más, y con internet, porque el registro y el login usan Firebase. El archivo `app/google-services.json` ya está en el repositorio, así que no hay que configurar nada más.

## APK

El APK de esta entrega está en la carpeta [APK](APK/).