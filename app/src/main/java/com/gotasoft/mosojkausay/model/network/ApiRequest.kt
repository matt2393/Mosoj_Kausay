package com.gotasoft.mosojkausay.model.network

import com.gotasoft.mosojkausay.model.entities.request.*
import com.gotasoft.mosojkausay.model.entities.response.*
import okhttp3.MultipartBody
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

    @GET("noticias/mostrar/{id}")
    suspend fun showNoticia(
        @Path("id") id: Int = 10
    ): NoticiaShowRes

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


    @GET("momentos-magicos")
    suspend fun getMM(@Header("Authorization") token: String = "",
                      @Query("value") value: String = "",
                      @Query("items") items: Int = 10,
                      @Query("page") page: Int = 1): List<MMResponse>

    @POST("momentos-magicos")
    suspend fun addMM(@Header("Authorization") token: String = "",
                      @Body mmRequest: MMRequest): MMResponse

    @GET("testimonios-adicionales/{id}")
    suspend fun getTestAdMM(@Header("Authorization") token: String = "",
                            @Path("id") id: Int): List<TestimonioAdResponse>

    @POST("testimonios-adicionales")
    suspend fun addTestAdMM(@Header("Authorization") token: String = "",
                            @Body testAd: TestimonioAdRequest): TestimonioAdResponse


    @GET("fotos/listar/{id}")
    suspend fun getFotosMM(@Header("Authorization") token: String = "",
                           @Path("id") id: Int): List<FotoMMResponse>

    @Multipart
    @POST("fotos")
    suspend fun addFotoMM(@Header("Authorization") token: String = "",
                          @Query("descripcion") desc: String = "",
                          @Query("momento_magico_id") mmId: Int = 0,
                          @Part foto: MultipartBody.Part): FotoMMAddResponse

    @POST("patrocinadores")
    suspend fun registrarPatrocinador(@Body patrocinadorRequest: PatrocinadorRequest): PatrocinadorResponse

    @GET("seguimientos")
    suspend fun getSegs(@Header("Authorization") token: String = "",
                        @Query("gestion") gestion: String = "",
                        @Query("tipo") tipo: String = "",
                        @Query("activo") activo: Int = 1): List<SeguimientoResponse>

    @GET("seguimientos/mis-seguimientos")
    suspend fun getMisSegs(@Header("Authorization") token: String = "",
                           @Query("tipo") tipo: String = ""): List<SeguimientoResponse>


    @POST("seguimientos")
    suspend fun addSegs(@Header("Authorization") token: String = "",
                        @Body seguimientoCreateRequest: SeguimientoCreateRequest): SeguimientoResponse

    @PUT("seguimientos/{id}")
    suspend fun editSegs(@Header("Authorization") token: String = "",
                         @Path("id") id: Int = 0,
                         @Body seguimientoEditRequest: SeguimientoEditRequest): MessGenericResponse

    @PUT("seguimientos/set-activo/{id}")
    suspend fun editSegsActivoInactivo(@Header("Authorization") token: String = "",
                                       @Path("id") id: Int = 0,
                                       @Body segActivoInactivoRequest: SegActivoInactivoRequest): MessGenericResponse2

    @POST("contador")
    suspend fun agregarContador(): AgregarContadorRes

    @GET("contador/contar")
    suspend fun getContador(): ContadorRes

    @Multipart
    @POST("participantes/save-foto")
    suspend fun savePhotoPart(
                          @Query("child_number") childNumber: String = "",
                          @Query("type") type: String = "",
                          @Part foto: MultipartBody.Part): SuccessRes

    @GET("participantes/totales/{gestion}")
    suspend fun getPartTotales(@Path("gestion") gestion: String): List<PartTotales>

    @PUT("fsm-token")
    suspend fun editFCMToken(@Header("Authorization") token: String = "",
                             @Body fcmReq: FcmReq): MessGenericResponse
}