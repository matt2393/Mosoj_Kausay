package com.gotasoft.mosojkausay.model.network

import com.gotasoft.mosojkausay.model.entities.request.LoginRequest
import com.gotasoft.mosojkausay.model.entities.request.MessageRequest
import com.gotasoft.mosojkausay.model.entities.request.ParticipanteRequest
import com.gotasoft.mosojkausay.model.entities.response.*
import retrofit2.http.*

interface ApiRequest {
    @POST("signin")
    suspend fun login(@Body loginRequest: LoginRequest): LoginResponse

    @GET("participantes/get-villas")
    suspend fun getVillas(): List<VillaResponse>

    @GET("participantes")
    suspend fun getParticipantes(@Query("key") key: String = "nombre_completo",
                                 @Query("value") value: String = "",
                                 @Query("order") order: String = "asc",
                                 @Query("by") by: String = "child_number",
                                 @Query("items") items: Int = 10, 
                                 @Query("village") village: String = "", 
                                 @Query("page") page: Int = 1,
                                 @Query("from") from: Int = 0,
                                 @Query("to") to: Int = 5): AllParticipanteResponse

    @PUT("participantes")
    suspend fun editParticipante(@Body participanteRequest: ParticipanteRequest): ResponseGeneric

    @GET("slides")
    suspend fun getSlides(): List<SlideResponse>

    @GET("noticias/publicadas")
    suspend fun getNoticias(@Query("value") value: String = "",
                            @Query("items") items: Int = 10,
                            @Query("page") page: Int = 1): List<NoticiaPublicadaResponse>

    @GET("mensajeria/mis-mensajes")
    suspend fun getMensajes(@Header("Authorization") token: String = "",
                            @Query("value") value: String = "",
                            @Query("items") items: Int = 10,
                            @Query("page") page: Int = 1): List<MensajeResponse>

    @GET("mensajeria")
    suspend fun getMensajesAll(@Header("Authorization") token: String = "",
                            @Query("value") value: String = "",
                            @Query("items") items: Int = 10,
                            @Query("page") page: Int = 1): List<MessageResponse>

    @GET("usuarios/personal")
    suspend fun getPersonal(@Header("Authorization") token: String = "",
                            @Query("key") key: String = "username",
                            @Query("value") value: String = "",
                            @Query("keyUsuario") keyUsuario: String = "rol",
                            @Query("valueUsuario") valueUsuario: String = ""): List<PersonalResponse>

    @POST("mensajeria")
    suspend fun addMess(@Header("Authorization") token: String = "",
                        @Body messageRequest: MessageRequest): MessageResponse
}