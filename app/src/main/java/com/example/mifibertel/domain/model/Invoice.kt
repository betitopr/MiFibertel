package com.example.mifibertel.domain.model

/**Modelo que representa una factura en el Sistema
 * */
data class Invoice(
    val id: Int,
    // Número de factura, generalmente con un formato específico (ej: "INV-2025-001")
    val invoiceNumber: String,
    // Monto total de la factura
    val amount: Double,
    // Estado actual de la factura (ej: "PENDING", "PAID", "OVERDUE")
    val status: String,
    // Fecha de vencimiento de la factura
    val dueDate: String,
    // Descripción o concepto de la factura
    val description: String
)

