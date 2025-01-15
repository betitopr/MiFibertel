package com.example.mifibertel.data.api

import com.example.mifibertel.data.api.model.dto.UserDto
import com.example.mifibertel.data.api.model.request.LoginRequest
import com.example.mifibertel.data.api.model.response.InvoiceResponse
import com.example.mifibertel.data.api.model.response.LoginResponse
import com.example.mifibertel.data.api.model.response.TicketResponse
import com.example.mifibertel.domain.model.AuthToken
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
/**
 * Interfaz que define los endpoints de la API para autenticación y usuario
 */
interface ApiService {
    /**
     * Endpoint para iniciar sesión
     * @param loginRequest Credenciales del usuario
     * @return Respuesta con tokens y datos del usuario
     */
    @POST("api/v1/accounts/auth/token/")
    suspend fun login(@Body loginRequest: LoginRequest): Response<LoginResponse>

    /**
     * Endpoint para renovar el token de acceso
     * @param refreshToken Token de actualización
     * @return Nuevo par de tokens
     */
    @POST("api/v1/accounts/auth/token/refresh/")
    suspend fun refreshToken(@Body refreshToken: Map<String, String>): Response<AuthToken>

    /**
     * Endpoint para obtener información del usuario actual
     * @param token Token de autenticación
     * @return Información del usuario
     */
    @GET("api/v1/accounts/users/me/")
    suspend fun getCurrentUser(): Response<UserDto>

    /**
     * Endpoint para obtener la lista de facturas del usuario
     *
     * Hace una petición GET a /api/v1/billing/invoices/
     * La respuesta incluye una lista paginada de facturas
     *
     * @return Response<InvoiceResponse> que contiene:
     * - count: Número total de facturas
     * - results: Lista de facturas en formato DTO
     */
    @GET("api/v1/billing/invoices/")
    suspend fun getInvoices(): Response<InvoiceResponse>

    /**
     * Endpoint para obtener la lista de tickets de soporte del usuario
     *
     * Hace una petición GET a /api/v1/support/tickets/
     * La respuesta incluye una lista paginada de tickets
     *
     * @return Response<TicketResponse> que contiene:
     * - count: Número total de tickets
     * - results: Lista de tickets en formato DTO
     */

    @GET("api/v1/support/tickets/")
    suspend fun getTickets(): Response<TicketResponse>

}