# SIMCA — Sistema Inteligente de Monitoreo de Condiciones de Aula

Proyecto del curso CC3108 Internet de las Cosas — Universidad del Valle de Guatemala, Ciclo 2, 2026.
Sección 10, Equipo 03: Joseph Gongora, Esteban Sánchez y Javier Sánchez.

SIMCA mide las condiciones de un aula con un nodo ESP32 y envía los datos en JSON por MQTT a un
broker Mosquitto en una Raspberry Pi, donde un programa suscriptor los procesa y los guarda.

```
ESP32 (simca-01) --Wi-Fi / MQTT--> Mosquitto (Raspberry Pi) --> simca_gateway.py --> SQLite
```

## Estructura

```
simca_final/           Proyecto ESP-IDF del nodo IoT
  CMakeLists.txt
  main/
    main.c             Programa principal (tareas FreeRTOS)
    simca_config.h     Red, pines, umbrales y calibración
    dht22.c / .h       Driver del DHT22
    rc522.c / .h       Driver del lector RFID RC522
raspberry/
  simca_gateway.py     Programa suscriptor de la Raspberry Pi
```

## Entradas y salidas

| Componente | Señal | Pin |
|---|---|---|
| DHT22 | Temperatura y humedad | GPIO4 |
| MQ-135 | CO2 (analógica, divisor 10k/20k) | GPIO34 |
| KY-037 | Ruido (analógica) | GPIO35 |
| LDR | Luz (analógica) | GPIO36 |
| SW-420 | Vibración (interrupción) | GPIO14 |
| MC-38 | Puerta | GPIO16 |
| Switch | Aula en uso | GPIO17 |
| Pulsador | Reporte manual / silenciar alarma | GPIO33 |
| RC522 | Tarjetas RFID (SPI) | MISO 25, MOSI 23, SCK 19, SS 22, RST 21 |
| LED RGB | Ocupación del aula | R 32, G 12, B 13 |
| Buzzer | Alarma | GPIO18 |
| LED de la placa | Conexión con el broker | GPIO2 |

## Temas MQTT

| Tema | Dirección | Contenido |
|---|---|---|
| `seccion10/equipo03/simca/telemetria` | Nodo → Raspberry | Lecturas cada 5 s |
| `seccion10/equipo03/simca/eventos` | Nodo → Raspberry | RFID, puerta, switch, vibración, botón, alertas |
| `seccion10/equipo03/simca/estado` | Nodo → Raspberry | `online` / `offline` (LWT, retenido) |
| `seccion10/equipo03/simca/cmd` | Raspberry → Nodo | `{"ping":true}`, `{"buzzer":true}`, `{"silenciar_s":60}` |

## Cómo ejecutarlo

**Nodo ESP32**

1. En `simca_config.h`, poner el SSID y la contraseña del hotspot y la IP de la Raspberry en `SIMCA_MQTT_URI`.
2. Compilar y flashear con ESP-IDF (Espressif-IDE o `idf.py build flash monitor`).

**Raspberry Pi**

```bash
sudo apt install -y mosquitto mosquitto-clients python3-paho-mqtt
```

Agregar al final de `/etc/mosquitto/mosquitto.conf`:

```
listener 1883
allow_anonymous true
```

```bash
sudo systemctl restart mosquitto
hostname -I                      # IP que va en SIMCA_MQTT_URI
python3 simca_gateway.py         # base de datos en ~/simca/simca.db
```
