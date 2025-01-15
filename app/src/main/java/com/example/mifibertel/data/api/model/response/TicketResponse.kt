package com.example.mifibertel.data.api.model.response

import com.example.mifibertel.domain.model.Ticket
import com.google.gson.annotations.SerializedName
/**
 * Representa la respuesta paginada de la API para los tickets de soporte
 * */
data class TicketResponse(
    // Contador total de tickets disponibles en el sistema
    val count: Int,
    // Lista de tickets en formato DTO recibidos en la respuesta actual
    val results: List<TicketDto>
)
/***
 * Mapea la estructura exacta del JSON de Api para Tickets
 *
 */

data class TicketDto(
    val id: Int,
    // Número de referencia del ticket (usando SerializedName para mapear desde snake_case)
    @SerializedName("ticket_number") val ticketNumber: String,
    // Título o asunto del ticket
    val title: String,
    // Estado actual del ticket (ej: "OPEN", "IN_PROGRESS", etc.)
    val status: String,
    // Nivel de prioridad del ticket (ej: "HIGH", "MEDIUM", "LOW")
    val priority: String,
    // Categoría del ticket (ej: "TECHNICAL", "BILLING")
    val category: String,
    // Fecha y hora de creación (usando SerializedName para mapear desde snake_case)
    @SerializedName("created_at") val createdAt: String
) {
    // Función de extensión que convierte el DTO al modelo de dominio Ticket
    fun toDomain() = Ticket(
        id = id,
        ticketNumber = ticketNumber,
        title = title,
        status = status,
        priority = priority,
        category = category,
        createdAt = createdAt
    )
}