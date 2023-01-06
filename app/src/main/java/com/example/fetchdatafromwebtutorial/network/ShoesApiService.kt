package com.example.fetchdatafromwebtutorial.network

import com.example.fetchdatafromwebtutorial.constants.LINK
import com.example.fetchdatafromwebtutorial.repository.models.Order
import com.example.fetchdatafromwebtutorial.repository.models.Shoes
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Header

private const val BASE_URL = " https://${LINK}.eu.ngrok.io"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

/**
 * A public interface that exposes the [getShoes] method
 */
interface ShoesApiService {
    /**
     * Returns a [List] of [MarsPhoto] and this method can be called from a Coroutine.
     * The @GET annotation indicates that the "photos" endpoint will be requested with the GET
     * HTTP method
     */
    @GET("/api/Shoes")
    suspend fun getShoes(@Header("Authorization") token: String): Array<Shoes>

    @GET("/api/orders/getAllOrders")
    suspend fun getOrders(@Header("Authorization") token: String): Array<Order>

    @GET("/api/orders/getUserAsExecutorOrders")
    suspend fun getExecuteOrders(@Header("Authorization") token: String): Array<Order>

    @GET("/api/orders/getUserAsCustomerOrders")
    suspend fun getCustomerOrders(@Header("Authorization") token: String): Array<Order>


}

/**
 * A public Api object that exposes the lazy-initialized Retrofit service
 */
object ShoesApi {
    val retrofitService: ShoesApiService by lazy { retrofit.create(ShoesApiService::class.java) }
}
