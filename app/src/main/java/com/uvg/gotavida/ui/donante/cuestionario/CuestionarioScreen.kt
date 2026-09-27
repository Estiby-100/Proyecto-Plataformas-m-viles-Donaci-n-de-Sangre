package com.uvg.gotavida.ui.donante.cuestionario

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.uvg.gotavida.data.fake.FakePreguntasDataSource
import com.uvg.gotavida.data.model.PreguntaElegibilidad
import com.uvg.gotavida.data.model.RespuestaElegibilidad
import com.uvg.gotavida.ui.donante.common.DialogoContactoInstitucion
import com.uvg.gotavida.ui.theme.GotaVidaTheme
import com.uvg.gotavida.ui.theme.WarningAccessible
import com.uvg.gotavida.ui.theme.WarningContainer

enum class VistaCuestionario {
    PREGUNTA,
    RESULTADO_APTO,
    RESULTADO_NO_APTO
}

@Composable
fun CuestionarioRoute(
    modifier: Modifier = Modifier
) {
    var numeroPregunta by remember { mutableStateOf(1) }
    var respuestaSeleccionada by remember { mutableStateOf<RespuestaElegibilidad?>(null) }
    var vista by remember { mutableStateOf(VistaCuestionario.PREGUNTA) }
    var mostrarDialogoContacto by remember { mutableStateOf(false) }

    val pregunta = FakePreguntasDataSource.obtenerPregunta(numeroPregunta)

    CuestionarioScreen(
        vista = vista,
        pregunta = pregunta,
        respuestaSeleccionada = respuestaSeleccionada,
        mostrarDialogoContacto = mostrarDialogoContacto,
        onRespuestaSeleccionada = { respuestaSeleccionada = it },
        onSiguienteClick = {
            if (numeroPregunta < pregunta.total) {
                numeroPregunta += 1
                respuestaSeleccionada = null
            } else {
                vista = VistaCuestionario.RESULTADO_APTO
            }
        },
        onCerrarClick = {},
        onContinuarClick = {},
        onVolverInicioClick = {},
        onConsultarInstitucionClick = { mostrarDialogoContacto = true },
        onLlamarClick = { mostrarDialogoContacto = false },
        onCancelarDialogoClick = { mostrarDialogoContacto = false },
        modifier = modifier
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CuestionarioScreen(
    vista: VistaCuestionario,
    pregunta: PreguntaElegibilidad,
    respuestaSeleccionada: RespuestaElegibilidad?,
    mostrarDialogoContacto: Boolean,
    onRespuestaSeleccionada: (RespuestaElegibilidad) -> Unit,
    onSiguienteClick: () -> Unit,
    onCerrarClick: () -> Unit,
    onContinuarClick: () -> Unit,
    onVolverInicioClick: () -> Unit,
    onConsultarInstitucionClick: () -> Unit,
    onLlamarClick: () -> Unit,
    onCancelarDialogoClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(modifier = modifier) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            when (vista) {
                VistaCuestionario.PREGUNTA -> VistaPregunta(
                    pregunta = pregunta,
                    respuestaSeleccionada = respuestaSeleccionada,
                    onRespuestaSeleccionada = onRespuestaSeleccionada,
                    onSiguienteClick = onSiguienteClick,
                    onCerrarClick = onCerrarClick
                )
                VistaCuestionario.RESULTADO_APTO -> VistaResultadoApto(
                    onContinuarClick = onContinuarClick
                )
                VistaCuestionario.RESULTADO_NO_APTO -> VistaResultadoNoApto(
                    onConsultarInstitucionClick = onConsultarInstitucionClick,
                    onVolverInicioClick = onVolverInicioClick
                )
            }

            if (mostrarDialogoContacto) {
                DialogoContactoInstitucion(
                    institucion = "Hospital Roosevelt",
                    telefono = "0000-0000",
                    onLlamarClick = onLlamarClick,
                    onCancelarClick = onCancelarDialogoClick
                )
            }
        }
    }
}

@Composable
private fun VistaPregunta(
    pregunta: PreguntaElegibilidad,
    respuestaSeleccionada: RespuestaElegibilidad?,
    onRespuestaSeleccionada: (RespuestaElegibilidad) -> Unit,
    onSiguienteClick: () -> Unit,
    onCerrarClick: () -> Unit
) {
    Column(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 20.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onCerrarClick) {
                    Icon(Icons.Filled.Close, contentDescription = "Cerrar")
                }
            }
            LinearProgressIndicator(
                progress = { pregunta.numero.toFloat() / pregunta.total.toFloat() },
                modifier = Modifier.fillMaxWidth(),
                color = MaterialTheme.colorScheme.primary
            )
            Text(
                text = "Pregunta ${pregunta.numero} de ${pregunta.total}",
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(top = 4.dp, bottom = 16.dp)
            )

            Text(pregunta.texto, style = MaterialTheme.typography.titleLarge)
            pregunta.ayuda?.let {
                Text(
                    text = it,
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            OpcionRespuesta(
                texto = "Sí",
                seleccionada = respuestaSeleccionada == RespuestaElegibilidad.SI,
                onClick = { onRespuestaSeleccionada(RespuestaElegibilidad.SI) }
            )
            Spacer(modifier = Modifier.height(12.dp))
            OpcionRespuesta(
                texto = "No",
                seleccionada = respuestaSeleccionada == RespuestaElegibilidad.NO,
                onClick = { onRespuestaSeleccionada(RespuestaElegibilidad.NO) }
            )
        }

        Column(modifier = Modifier.padding(20.dp)) {
            Text(
                text = "Este cuestionario es orientativo y no sustituye la evaluación del personal médico en el centro de donación.",
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(bottom = 12.dp)
            )
            Button(
                onClick = onSiguienteClick,
                enabled = respuestaSeleccionada != null,
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Siguiente")
            }
        }
    }
}

@Composable
private fun OpcionRespuesta(
    texto: String,
    seleccionada: Boolean,
    onClick: () -> Unit
) {
    val colorFondo = if (seleccionada) {
        MaterialTheme.colorScheme.primaryContainer
    } else {
        MaterialTheme.colorScheme.surface
    }
    val colorBorde = if (seleccionada) {
        MaterialTheme.colorScheme.primary
    } else {
        MaterialTheme.colorScheme.outline
    }
    Surface(
        color = colorFondo,
        shape = MaterialTheme.shapes.small,
        border = androidx.compose.foundation.BorderStroke(1.dp, colorBorde),
        onClick = onClick,
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(texto, style = MaterialTheme.typography.bodyLarge)
            if (seleccionada) {
                Icon(
                    imageVector = Icons.Filled.CheckCircle,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}

@Composable
private fun VistaResultadoApto(
    onContinuarClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = Icons.Filled.CheckCircle,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.tertiary,
            modifier = Modifier.size(64.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            "¡Buenas noticias!",
            style = MaterialTheme.typography.headlineSmall,
            textAlign = TextAlign.Center
        )
        Text(
            "Según tus respuestas, podrías ser elegible para donar.",
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(top = 8.dp)
        )
        Text(
            "La confirmación final la hará el personal médico en el lugar.",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(top = 4.dp)
        )
        Spacer(modifier = Modifier.height(24.dp))
        Button(
            onClick = onContinuarClick,
            shape = MaterialTheme.shapes.medium,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Continuar")
        }
    }
}

@Composable
private fun VistaResultadoNoApto(
    onConsultarInstitucionClick: () -> Unit,
    onVolverInicioClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Surface(
            color = WarningContainer,
            shape = androidx.compose.foundation.shape.CircleShape
        ) {
            Icon(
                imageVector = Icons.Filled.Warning,
                contentDescription = null,
                tint = WarningAccessible,
                modifier = Modifier
                    .padding(16.dp)
                    .size(32.dp)
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            "Por ahora podrías no ser elegible",
            style = MaterialTheme.typography.headlineSmall,
            textAlign = TextAlign.Center
        )
        Text(
            "Una de tus respuestas indica que deberías consultar con el personal médico antes de donar.",
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(top = 8.dp)
        )
        Spacer(modifier = Modifier.height(24.dp))
        OutlinedButton(
            onClick = onConsultarInstitucionClick,
            shape = MaterialTheme.shapes.medium,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Consultar con la institución")
        }
        TextButton(onClick = onVolverInicioClick) {
            Text("Volver al inicio")
        }
    }
}

@Preview(showBackground = true, heightDp = 844, widthDp = 390)
@Composable
private fun PreviewCuestionarioSinSeleccion() {
    GotaVidaTheme {
        CuestionarioScreen(
            vista = VistaCuestionario.PREGUNTA,
            pregunta = FakePreguntasDataSource.obtenerPregunta(2),
            respuestaSeleccionada = null,
            mostrarDialogoContacto = false,
            onRespuestaSeleccionada = {},
            onSiguienteClick = {},
            onCerrarClick = {},
            onContinuarClick = {},
            onVolverInicioClick = {},
            onConsultarInstitucionClick = {},
            onLlamarClick = {},
            onCancelarDialogoClick = {}
        )
    }
}

@Preview(showBackground = true, heightDp = 844, widthDp = 390)
@Composable
private fun PreviewCuestionarioConSeleccion() {
    GotaVidaTheme {
        CuestionarioScreen(
            vista = VistaCuestionario.PREGUNTA,
            pregunta = FakePreguntasDataSource.obtenerPregunta(2),
            respuestaSeleccionada = RespuestaElegibilidad.SI,
            mostrarDialogoContacto = false,
            onRespuestaSeleccionada = {},
            onSiguienteClick = {},
            onCerrarClick = {},
            onContinuarClick = {},
            onVolverInicioClick = {},
            onConsultarInstitucionClick = {},
            onLlamarClick = {},
            onCancelarDialogoClick = {}
        )
    }
}

@Preview(showBackground = true, heightDp = 844, widthDp = 390)
@Composable
private fun PreviewCuestionarioResultadoApto() {
    GotaVidaTheme {
        CuestionarioScreen(
            vista = VistaCuestionario.RESULTADO_APTO,
            pregunta = FakePreguntasDataSource.obtenerPregunta(5),
            respuestaSeleccionada = RespuestaElegibilidad.SI,
            mostrarDialogoContacto = false,
            onRespuestaSeleccionada = {},
            onSiguienteClick = {},
            onCerrarClick = {},
            onContinuarClick = {},
            onVolverInicioClick = {},
            onConsultarInstitucionClick = {},
            onLlamarClick = {},
            onCancelarDialogoClick = {}
        )
    }
}

@Preview(showBackground = true, heightDp = 844, widthDp = 390)
@Composable
private fun PreviewCuestionarioResultadoNoApto() {
    GotaVidaTheme {
        CuestionarioScreen(
            vista = VistaCuestionario.RESULTADO_NO_APTO,
            pregunta = FakePreguntasDataSource.obtenerPregunta(5),
            respuestaSeleccionada = RespuestaElegibilidad.NO,
            mostrarDialogoContacto = false,
            onRespuestaSeleccionada = {},
            onSiguienteClick = {},
            onCerrarClick = {},
            onContinuarClick = {},
            onVolverInicioClick = {},
            onConsultarInstitucionClick = {},
            onLlamarClick = {},
            onCancelarDialogoClick = {}
        )
    }
}

@Preview(showBackground = true, heightDp = 844, widthDp = 390)
@Composable
private fun PreviewCuestionarioResultadoNoAptoConDialogo() {
    GotaVidaTheme {
        CuestionarioScreen(
            vista = VistaCuestionario.RESULTADO_NO_APTO,
            pregunta = FakePreguntasDataSource.obtenerPregunta(5),
            respuestaSeleccionada = RespuestaElegibilidad.NO,
            mostrarDialogoContacto = true,
            onRespuestaSeleccionada = {},
            onSiguienteClick = {},
            onCerrarClick = {},
            onContinuarClick = {},
            onVolverInicioClick = {},
            onConsultarInstitucionClick = {},
            onLlamarClick = {},
            onCancelarDialogoClick = {}
        )
    }
}
