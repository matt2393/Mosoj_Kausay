package com.gotasoft.mosojkausay

const val URL_SERVER = "http://35.169.148.98:3001"//"https://gotasoft.com:3001/"
const val URL_RUTAS_MAPS = "https://maps.googleapis.com/maps/api/"

const val URL_DOWNLOAD_IMAGE = "${URL_SERVER}slides/download?id="
const val URL_DOWNLOAD_PHOTO_MM = "${URL_SERVER}fotos/download?id="

const val URL_DOWNLOAD_FILE = "${URL_SERVER}multimedia/download?id="
const val URL_DOWNLOAD_PHOTO_PART = "${URL_SERVER}participantes/download?"

const val TODOS = "Todos"

const val DEFAULT_USER_LOGIN = ""//"aluna"
const val DEFAULT_PASSWORD_LOGIN = ""//""123456"


const val US_ADMIN = "administrador"
const val US_PATROCINIO = "responsable_patrocinio"
const val US_FACILITADOR = "facilitador_programas"
const val US_TECNICO = "tecnico"
const val US_FINANZAS = "finanzas"
const val US_PARTICIPANTE = "participante"


/**
 * Map
 */
const val MODO_CAMINATA = "walking"
const val MODO_CONDUCCION = "driving"
const val MODO_BICICLETA = "bicycling"

/**
 * Aux
 */
var TOKEN = ""

val arrayGestion = arrayListOf("2021", "2022", "2023", "2024",
    "2025", "2026", "2027", "2028", "2029", "2030", "2031", "2032", "2033", "2034", "2035", "2036")
val arrayTipoSeg = arrayListOf("actividad", "participacion", "presupuesto")
val arrayModeloProgValue = arrayListOf("creciendo_contigo", "ninez_segura_protegida",
    "me_quiero_me_cuido", "pacto", "administracion", "patrocinio", "proteccion")

val arrayModeloProg = arrayListOf("Creciendo Contigo", "Niñez Segura y Protegida",
    "Me Quiero me Cuido", "Pacto", "Administración", "Patrocinio", "Protección")

val arrayMeses = arrayListOf("", "Enero", "Febrero", "Marzo", "Abril", "Mayo",
    "Junio", "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre")


/**
 *
 *
 * {
"username": "mbautista",
"password": "10516362"
}
 *
 */