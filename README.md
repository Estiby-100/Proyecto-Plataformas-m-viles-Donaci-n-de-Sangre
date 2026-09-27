# GotaVida

Proyecto de Programación de Plataformas Móviles (UVG) — Grupo #6: Esteban Sánchez, Javier Sánchez y Diego Rodriguez.

App de donación de sangre que conecta donantes con instituciones (hospitales, bancos de sangre, cruz roja) que publican solicitudes urgentes y jornadas de donación.

## Estado actual

Cuarto entregable: vistas en Jetpack Compose de todas las pantallas, sin navegación real ni lógica de negocio todavía (fuentes de datos "fake" + `@Preview` por estado).

## Estructura del proyecto

```
app/src/main/java/com/uvg/gotavida/
├── data/
│   ├── model/     # Data classes (SolicitudDonacion, PreguntaElegibilidad, HistorialDonacion, LugarMapa)
│   └── fake/       # Fuentes de datos fake (FakeXDataSource) usadas mientras no hay backend
├── ui/
│   ├── theme/      # Color.kt, Type.kt, Theme.kt — sistema "Confianza Cálida" sobre Material 3
│   └── donante/    # Flujo del donante (pantallas 6-11), a cargo de Esteban Sánchez
│       ├── common/        # Componentes compartidos del flujo donante (badges, tarjetas, diálogos, bottom nav)
│       ├── home/           # 6. Feed de solicitudes y jornadas
│       ├── detalle/        # 7. Detalle de una solicitud
│       ├── cuestionario/   # 8. Cuestionario de elegibilidad
│       ├── confirmacion/   # 9. Confirmación de disponibilidad
│       ├── mapa/           # 10. Mapa de lugares cercanos
│       └── perfil/         # 11. Perfil del donante
└── MainActivity.kt
```

Cada compañero trabaja en su propia carpeta bajo `ui/` (por ejemplo `ui/medico/`, `ui/institucional/`, `ui/auth/`) para evitar conflictos entre flujos.

## Convenciones usadas (flujo donante)

- Patrón **Route/Screen**: un `XRoute` con estado y lógica mínima, y un `XScreen` sin estado (stateless) que recibe todo por parámetros — así cada estado de la pantalla se puede previsualizar con `@Preview` sin necesitar la app corriendo.
- Solo se usan colores/tipografía/formas de `MaterialTheme` (`colorScheme`, `typography`, `shapes`); los únicos valores hexadecimales del proyecto viven en `ui/theme/Color.kt`.
- Todo el contenido del cuestionario de elegibilidad es demostrativo y no debe tratarse como criterio médico validado.
