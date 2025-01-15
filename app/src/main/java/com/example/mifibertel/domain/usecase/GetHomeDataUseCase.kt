package com.example.mifibertel.domain.usecase

import com.example.mifibertel.domain.model.Invoice
import com.example.mifibertel.domain.model.Ticket
import com.example.mifibertel.domain.repository.IHomeRepository
import javax.inject.Inject

/**
 * Casos de uso para obtener la información necesaria en la pantalla Home
 * Siguiendo el principio de responsabilidad única, cada caso de uso maneja una operación específica
 */

/**s
 * Caso de uso para obtener la lista de facturas
 * Encapsula la lógica de negocio relacionada con la obtención de facturas
 */
class GetInvoicesUseCase @Inject constructor(
    // Inyección del repositorio a través de su interfaz
    private val homeRepository: IHomeRepository
) {
    /**
     * Operador invoke permite llamar la clase como una función
     * @return Result<List<Invoice>> con la lista de facturas o error
     */
    suspend operator fun invoke(): Result<List<Invoice>> =
        homeRepository.getInvoices()
}
/**
 * Caso de uso para obtener la lista de tickets de soporte
 * Encapsula la lógica de negocio relacionada con la obtención de tickets
 */
class GetTicketsUseCase @Inject constructor(
    private val homeRepository: IHomeRepository
) {
    suspend operator fun invoke(): Result<List<Ticket>> =
        homeRepository.getTickets()
}