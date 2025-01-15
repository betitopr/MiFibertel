package com.example.mifibertel.data.repository

import com.example.mifibertel.data.api.ApiService
import com.example.mifibertel.domain.model.Invoice
import com.example.mifibertel.domain.model.Ticket
import com.example.mifibertel.domain.repository.IHomeRepository
import javax.inject.Inject
/**
 * Implementación concreta del IHomeRepository que maneja las operaciones de datos
 * para la pantalla Home usando la API
 */
class HomeRepository @Inject constructor(
    // Inyección del servicio API necesario para hacer las llamadas al backend

    private val apiService: ApiService
) : IHomeRepository {
    /**
     * Implementación de getInvoices que obtiene las facturas desde la API
     *
     * @return Result que contiene la lista de facturas o un error
     */
    override suspend fun getInvoices(): Result<List<Invoice>> {
        return try {
            // Hacer la llamada a la API
            val response = apiService.getInvoices()
            if (response.isSuccessful) {
                // Si la llamada es exitosa, mapear los DTOs a modelos de dominio
                Result.success(
                    response.body()?.results?.map { it.toDomain() } ?: emptyList()
                )
            } else {
                // Si hay error, devolver el mensaje de error del backend
                Result.failure(Exception(response.errorBody()?.string()))
            }
        } catch (e: Exception) {
            // Capturar cualquier error de red u otro tipos
            Result.failure(e)
        }
    }
    /**
     * Implementación de getTickets que obtiene los tickets desde la API
     *
     * @return Result que contiene la lista de tickets o un error
     */
    override suspend fun getTickets(): Result<List<Ticket>> {
        return try {
            val response = apiService.getTickets()
            if (response.isSuccessful) {
                Result.success(
                    response.body()?.results?.map { it.toDomain() } ?: emptyList()
                )
            } else {
                Result.failure(Exception(response.errorBody()?.string()))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}