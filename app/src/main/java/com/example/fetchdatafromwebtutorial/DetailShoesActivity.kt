package com.example.fetchdatafromwebtutorial

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.fetchdatafromwebtutorial.repository.models.DetailShoes
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import okhttp3.*
import okhttp3.HttpUrl.Companion.toHttpUrl


class DetailShoesActivity : AppCompatActivity() {

    companion object {
        const val SHOES_ID = "SHOES_ID"
        const val URL = "56f9-188-66-38-93"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_shoes)

        fetchCurrencyData().start()
    }


    private fun fetchCurrencyData(): Thread
    {
        return Thread {
            val client = OkHttpClient()

            val queryUrlBuilder: HttpUrl.Builder = "https://${URL}.eu.ngrok.io/api/getSingleShoesInfo".toHttpUrl().newBuilder()
            queryUrlBuilder.addQueryParameter("id", intent.getStringExtra(SHOES_ID))

            val request: Request = Request.Builder()
                .url(queryUrlBuilder.build())
                .build()

            val response = client.newCall(request).execute()
            val body = response.body?.string()
            val singleShoes: DetailShoes = Gson().fromJson(body, DetailShoes::class.java)
            updateUI(singleShoes)
        }
    }

    private fun fetchOrderShoes(): Thread {
        return Thread {

            val client = OkHttpClient()

            val requestBody: RequestBody = FormBody.Builder()
                .add("shoes", intent.getStringExtra(SHOES_ID).toString())
                .add("customer", "1")
                .build()

            val request: Request = Request.Builder()
                .url("https://${URL}.eu.ngrok.io/api/orders/create")
                .post(requestBody)
                .build()

            val response = client.newCall(request).execute()
            Log.e("FATAL ERROR", intent.getStringExtra(SHOES_ID).toString())

        }
    }

    fun orderShoes(view: View)
    {
        fetchOrderShoes().start()
        Toast.makeText(this, "Вы сделали заказ", Toast.LENGTH_SHORT).show()
    }

    private fun updateUI(shoes: DetailShoes)
    {
        runOnUiThread {
            kotlin.run {
                val shoesImage: ImageView = findViewById(R.id.shoesImage)
                val shoesName: TextView = findViewById(R.id.shoesName)
                val shoesDesc: TextView = findViewById(R.id.sizesText)
                val shoesPrice: TextView = findViewById(R.id.priceText)
                val shoesBrand: TextView = findViewById(R.id.brandText)

                Picasso.get().load(shoes.img).into(shoesImage)
                shoesName.text = shoes.shoes_name
                shoesDesc.text = shoes.description
                shoesPrice.text = shoes.price_euro
                shoesBrand.text = shoes.brand
            }
        }
    }

}



