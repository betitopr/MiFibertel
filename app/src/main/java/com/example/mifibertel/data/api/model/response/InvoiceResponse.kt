package com.example.mifibertel.data.api.model.response

import com.example.mifibertel.domain.model.Invoice
import com.google.gson.annotations.SerializedName

/**
 * Representa la respuesta paginada de la API
 * para las facturas
 * */
data class InvoiceResponse(
    // Número total de facturas disponibles
    val count: Int,
    // Lista de facturas en el formato DTO (Data Transfer Object)
    val results: List<InvoiceDto>
)
// DTO que mapea exactamente la estructura JSON que viene de la API
data class InvoiceDto(
    val id: Int,
    // Número de factura (usando SerializedName para mapear snake_case de la API)
    @SerializedName("invoice_number") val invoiceNumber: String,
    val amount: String,
    // Estado de la factura
    val status: String,
    // Fecha de vencimiento (usando SerializedName para mapear snake_case)

    @SerializedName("due_date") val dueDate: String,
    // Descripción de la factura
    val description: String
) {
    // Función de extensión para convertir el DTO al modelo de dominio Invoice
    fun toDomain() = Invoice(
        id = id,
        invoiceNumber = invoiceNumber,
        // Convierte el amount de String a Double, si falla usa 0.0 como valor por defecto

        amount = amount.toDoubleOrNull() ?: 0.0,
        status = status,
        dueDate = dueDate,
        description = description
    )
}