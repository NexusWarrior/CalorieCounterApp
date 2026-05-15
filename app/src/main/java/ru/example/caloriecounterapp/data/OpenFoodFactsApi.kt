package ru.example.caloriecounterapp.data

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

// Модели для парсинга JSON
data class ProductResponse(val product: Product?, val status: Int)
data class Product(val product_name: String?, val nutriments: Nutriments?)
data class Nutriments(
    val energy_kcal_100g: Double?,
    val proteins_100g: Double?,
    val fat_100g: Double?,
    val carbohydrates_100g: Double?
)

// Интерфейс запроса
interface OpenFoodFactsApi {
    @GET("api/v2/product/{barcode}.json")
    suspend fun getProduct(@Path("barcode") barcode: String): ProductResponse

    companion object {
        fun create(): OpenFoodFactsApi {
            return Retrofit.Builder()
                .baseUrl("https://ru.openfoodfacts.org/") // Меняем world на ru
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(OpenFoodFactsApi::class.java)
        }
    }
}