package com.example.mifibertel.di

import android.content.Context
import com.example.mifibertel.data.api.ApiService
import com.example.mifibertel.data.api.AuthInterceptor
import com.example.mifibertel.data.local.TokenManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

/**
 * Centraliza la configuración de red en un solo lugar
 */
@Module
@InstallIn(SingletonComponent::class)// Se instala en el componente Singleton, lo que significa que las dependencias vivirán durante toda la vida de la aplicación
object NetworkModule {
    // Proporciona la URL base de nuestra API
    @Provides
    @Singleton
    fun provideBaseUrl() = "http://192.168.1.5:8000/"// URL para el emulador de Android que apunta a localhost

    // Proporciona el TokenManager que se encargará de gestionar los tokens JWT
    @Provides
    @Singleton
    fun provideTokenManager(@ApplicationContext context: Context): TokenManager {
        return TokenManager(context)// guarda los tokens en SharedPreferences
    }
    // Proporciona el interceptor de autenticación que añadirá los tokens JWT a las peticiones
    @Provides
    @Singleton
    fun provideAuthInterceptor(tokenManager: TokenManager): AuthInterceptor {
        return AuthInterceptor(tokenManager)
    }


    // Configura y proporciona el cliente OkHttp

    @Provides
    @Singleton
    fun provideOkHttpClient(authInterceptor: AuthInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY// Añade logs detallados de las peticiones
            })
            .addInterceptor(authInterceptor)// Añade el interceptor de autenticación
            .connectTimeout(30, TimeUnit.SECONDS)      // Aumentar timeout de conexión
            .readTimeout(30, TimeUnit.SECONDS)         // Aumentar timeout de lectura
            .writeTimeout(30, TimeUnit.SECONDS)        // Aumentar timeout de escritura
            .build()
    }

    // Configura y proporciona la instancia de Retrofit
    @Provides
    @Singleton
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        baseUrl: String
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(
//                GsonBuilder()
//                    .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
//                    .create()
            ))// Usa Gson para convertir JSON
            .build()
    }
    // Crea y proporciona la interfaz de ApiService
    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }
}
