package com.uvg.gotavida.data.fake

import com.uvg.gotavida.data.model.Institucion
import com.uvg.gotavida.data.model.TipoInstitucion

/**
 * Lista propia de hospitales y bancos de sangre, desarrollada por el equipo.
 *
 * No existe una API pública oficial con esta información para Guatemala, así
 * que se mantiene aquí. Es una lista referencial para el proyecto: no es un
 * directorio oficial ni está garantizada su actualización.
 */
object FakeHospitalesDataSource {

    val instituciones: List<Institucion> = listOf(
        Institucion("inst-001", "Hospital General San Juan de Dios", "Zona 1, Ciudad de Guatemala", TipoInstitucion.HOSPITAL),
        Institucion("inst-002", "Hospital Roosevelt", "Zona 11, Ciudad de Guatemala", TipoInstitucion.HOSPITAL),
        Institucion("inst-003", "Hospital General de Enfermedades del IGSS", "Zona 9, Ciudad de Guatemala", TipoInstitucion.HOSPITAL),
        Institucion("inst-004", "Hospital Herrera Llerandi", "Zona 10, Ciudad de Guatemala", TipoInstitucion.HOSPITAL),
        Institucion("inst-005", "Hospital Centro Médico", "Zona 10, Ciudad de Guatemala", TipoInstitucion.HOSPITAL),
        Institucion("inst-006", "Hospital La Paz", "Zona 14, Ciudad de Guatemala", TipoInstitucion.HOSPITAL),
        Institucion("inst-007", "Multimédica Vista Hermosa", "Zona 15, Ciudad de Guatemala", TipoInstitucion.HOSPITAL),
        Institucion("inst-008", "Centro Médico Militar", "Zona 16, Ciudad de Guatemala", TipoInstitucion.HOSPITAL),
        Institucion("inst-009", "Hospital Hermano Pedro", "Antigua Guatemala, Sacatepéquez", TipoInstitucion.HOSPITAL),
        Institucion("inst-010", "Cruz Roja Guatemalteca", "Ciudad de Guatemala", TipoInstitucion.BANCO_DE_SANGRE),
    )

    /** Instituciones cuyo nombre contiene el texto (sin distinguir mayúsculas). Con texto vacío devuelve todas. */
    fun buscar(texto: String): List<Institucion> {
        val consulta = texto.trim()
        if (consulta.isEmpty()) return instituciones
        return instituciones.filter { it.nombre.contains(consulta, ignoreCase = true) }
    }
}
