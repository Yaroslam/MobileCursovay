package com.example.fetchdatafromwebtutorial.Fragments

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.example.fetchdatafromwebtutorial.DetailShoesActivity
import com.example.fetchdatafromwebtutorial.R
import com.example.fetchdatafromwebtutorial.repository.models.Order
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import okhttp3.*
import okhttp3.HttpUrl.Companion.toHttpUrl


class MyOrdersFragment : Fragment() {

    private lateinit var orders: Array<Order>;

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fetchCurrencyData().start()
        return inflater.inflate(R.layout.fragment_my_orders, container, false)
    }

    private fun fetchCurrencyData(): Thread
    {
        return Thread {
            val client = OkHttpClient()

            val queryUrlBuilder: HttpUrl.Builder = "https://${DetailShoesActivity.URL}.eu.ngrok.io/api/orders/getUserAsCustomerOrders".toHttpUrl().newBuilder()
            queryUrlBuilder.addQueryParameter("userId", "1")

            val request: Request = Request.Builder()
                .url(queryUrlBuilder.build())
                .build()

            val response = client.newCall(request).execute()

            val body = response.body?.string()
            this.orders = Gson().fromJson(body, Array<Order>::class.java)
            updateUI(orders)
        }
    }



    private fun updateUI(orders: Array<Order>)
    {
        activity?.runOnUiThread {
            kotlin.run {
                if(orders.isEmpty()){
                    val mainLayout = view?.findViewById<ScrollView>(R.id.mainLayout)
                    val child = mainLayout?.findViewById<LinearLayout>(R.id.childlayout)
                    createTextDynamically("Заказов нет", child!!)
                } else {
                    for (i in orders) {
                        Log.e("ERRRRRRRROE", i.Order_id.toString())
                        createLinerLayoutDynamically(i)
                    }
                }
            }
        }

    }




    private fun createLinerLayoutDynamically(order: Order){
        val mainLayout = view?.findViewById<ScrollView>(R.id.mainLayout)
        val child = mainLayout?.findViewById<LinearLayout>(R.id.childlayout)

        val dynamicLayout = LinearLayout(activity)

        dynamicLayout.layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        dynamicLayout.orientation = LinearLayout.VERTICAL
        dynamicLayout.x = 350F
        createImageDynamically(order.Shoes_img, dynamicLayout)
        createButtonDynamically(order, dynamicLayout)

        createTextDynamically("Цена: ${order.Shoes_price}", dynamicLayout)
        createTextDynamically("Бренд: ${order.Shoes_brand}", dynamicLayout)
        createTextDynamically("Название: ${order.Shoes_name}", dynamicLayout)
        createTextDynamically("Исполнитель: ${order.executor}", dynamicLayout)

        child?.addView(dynamicLayout)
    }


    private fun createImageDynamically(imageSource: String, parent: LinearLayout){
        val dynamicImage = ImageView(activity)
        dynamicImage.layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
//        dynamicImage.x = 350F
        Picasso.get().load(imageSource).into(dynamicImage)
        parent.addView(dynamicImage)
    }

    private fun createTextDynamically(text: String, parent: LinearLayout){
        val textDynamic = TextView(activity)
        textDynamic.layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.MATCH_PARENT
        )
//        textDynamic.x = 350F
        textDynamic.text = text
        parent.addView(textDynamic)
    }

    fun deleteOrder(order_id: Int){
        deleteOrderThread(order_id).start()
    }

    private fun deleteOrderThread(order_id: Int): Thread{
        return Thread {
            val client = OkHttpClient()

            val requestBody: RequestBody = FormBody.Builder()
                .add("id", order_id.toString())
                .build()

            val request: Request = Request.Builder()
                .url("https://${DetailShoesActivity.URL}.eu.ngrok.io/api/orders/delete")
                .post(requestBody)
                .build()
            Log.e("ERRROR", order_id.toString())
            val response = client.newCall(request).execute()
        }
    }

    private fun createButtonDynamically(order: Order, parent: LinearLayout) {
        val dynamicButton = Button(activity)
//        dynamicButton.x = 350F
        dynamicButton.layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        dynamicButton.text = "Удалить заказ"
        dynamicButton.id = order.Order_id
        dynamicButton.setBackgroundColor(Color.GREEN)

        dynamicButton.setOnClickListener {
            deleteOrder(dynamicButton.id)
            dynamicButton.isClickable = false;
            dynamicButton.setBackgroundColor(Color.GRAY)
        }


        parent.addView(dynamicButton)
    }








}