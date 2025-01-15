package com.example.mifibertel.domain.model

/**
 *  Modelo que representa un ticket de soporte o incidencia
 */

data class Ticket(
    val id: Int,
    // Número de referencia del ticket (ej: "TK-2025-001")
    val ticketNumber: String,
    // Título o asunto del ticket
    val title: String,
    // Estado actual del ticket (ej: "OPEN", "IN_PROGRESS", "RESOLVED")
    val status: String,
    // Nivel de prioridad del ticket (ej: "HIGH", "MEDIUM", "LOW")
    val priority: String,
    // Categoría o tipo de ticket (ej: "TECHNICAL", "BILLING", "SUPPORT")
    val category: String,
    // Fecha y hora de creación del ticket
    val createdAt: String
)
