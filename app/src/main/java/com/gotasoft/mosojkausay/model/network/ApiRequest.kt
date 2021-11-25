package com.gotasoft.mosojkausay.model.network

import com.gotasoft.mosojkausay.model.entities.request.LoginRequest
import com.gotasoft.mosojkausay.model.entities.request.MessageRequest
import com.gotasoft.mosojkausay.model.entities.request.ParticipanteRequest
import com.gotasoft.mosojkausay.model.entities.request.ValidarCorresRequest
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

    @GET("planillas")
    suspend fun getPlanillas(@Header("Authorization") token: String = "",
                             @Query("activo") activo: Int = 1,
                             @Query("tipo") tipo: String = "reply"): List<PlanillaResponse>

    @GET("planillas/mis-correspondencias")
    suspend fun getMisCorresReply(@Header("Authorization") token: String = "",
                             @Query("key") key: String = "participant_name",
                             @Query("value") value: String = "",
                             @Query("planilla_id") planilla_id: String = "",
                             @Query("validacion") validacion: String = "pendiente",
                             @Query("tipo") tipo: String = "reply"): List<ReplyResponse>

    @GET("planillas/mis-correspondencias")
    suspend fun getMisCorresWelcome(@Header("Authorization") token: String = "",
                                  @Query("key") key: String = "participant_name",
                                  @Query("value") value: String = "",
                                  @Query("planilla_id") planilla_id: String = "",
                                  @Query("validacion") validacion: String = "pendiente",
                                  @Query("tipo") tipo: String = "welcome"): List<WelcomeResponse>

    @GET("planillas/mis-correspondencias")
    suspend fun getMisCorresDfc(@Header("Authorization") token: String = "",
                                  @Query("key") key: String = "participant_name",
                                  @Query("value") value: String = "",
                                  @Query("planilla_id") planilla_id: String = "",
                                  @Query("validacion") validacion: String = "pendiente",
                                  @Query("tipo") tipo: String = "dfc"): List<DfcResponse>

    @GET("planillas/mis-correspondencias")
    suspend fun getMisCorresUnavailable(@Header("Authorization") token: String = "",
                                  @Query("key") key: String = "participant_name",
                                  @Query("value") value: String = "",
                                  @Query("planilla_id") planilla_id: String = "",
                                  @Query("validacion") validacion: String = "pendiente",
                                  @Query("tipo") tipo: String = "unavailable"): List<UnavailableResponse>


    @GET("planillas/correspondencias")
    suspend fun getCorresReply(@Header("Authorization") token: String = "",
                                  @Query("key") key: String = "participant_name",
                                  @Query("value") value: String = "",
                                  @Query("planilla_id") planilla_id: String = "",
                                  @Query("validacion") validacion: String = "pendiente",
                                  @Query("tipo") tipo: String = "reply"): List<ReplyResponse>

    @GET("planillas/correspondencias")
    suspend fun getCorresWelcome(@Header("Authorization") token: String = "",
                                    @Query("key") key: String = "participant_name",
                                    @Query("value") value: String = "",
                                    @Query("planilla_id") planilla_id: String = "",
                                    @Query("validacion") validacion: String = "pendiente",
                                    @Query("tipo") tipo: String = "welcome"): List<WelcomeResponse>

    @GET("planillas/correspondencias")
    suspend fun getCorresDfc(@Header("Authorization") token: String = "",
                                @Query("key") key: String = "participant_name",
                                @Query("value") value: String = "",
                                @Query("planilla_id") planilla_id: String = "",
                                @Query("validacion") validacion: String = "pendiente",
                                @Query("tipo") tipo: String = "dfc"): List<DfcResponse>

    @GET("planillas/correspondencias")
    suspend fun getCorresUnavailable(@Header("Authorization") token: String = "",
                                        @Query("key") key: String = "participant_name",
                                        @Query("value") value: String = "",
                                        @Query("planilla_id") planilla_id: String = "",
                                        @Query("validacion") validacion: String = "pendiente",
                                        @Query("tipo") tipo: String = "unavailable"): List<UnavailableResponse>


    @PUT("planillas/validar-correspondencia/{id}")
    suspend fun validarCorres(@Header("Authorization") token: String = "",
                              @Path("id") id: String = "",
                              @Body validarCorresRequest: ValidarCorresRequest): ValidarCorresResponse

    @GET("mensajeria/get-destinatarios")
    suspend fun getDestinatarios(@Header("Authorization") token: String = "",
                                 @Query("mensajeriaId") messId: Int): List<DestinatarioResponse>
}