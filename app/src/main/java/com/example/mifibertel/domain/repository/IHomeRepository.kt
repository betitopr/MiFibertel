package com.example.mifibertel.domain.repository

import com.example.mifibertel.domain.model.Invoice
import com.example.mifibertel.domain.model.Ticket
/**
 * Interfaz que define las operaciones del repositorio para la pantalla Home
 * Sigue el patrón Repository y el principio de inversión de dependencias
 */
interface IHomeRepository {
    /**
     * Obtiene la lista de facturas del usuario
     *
     * @return Result<List<Invoice>> que puede contener:
     * - Success: Lista de facturas convertidas al modelo de dominio
     * - Failure: Error ocurrido durante la obtención de datos
     */
    suspend fun getInvoices(): Result<List<Invoice>>
    /**
     * Obtiene la lista de tickets de soporte del usuario
     *
     * @return Result<List<Ticket>> que puede contener:
     * - Success: Lista de tickets convertidos al modelo de dominio
     * - Failure: Error ocurrido durante la obtención de datos
     */
    suspend fun getTickets(): Result<List<Ticket>>
}