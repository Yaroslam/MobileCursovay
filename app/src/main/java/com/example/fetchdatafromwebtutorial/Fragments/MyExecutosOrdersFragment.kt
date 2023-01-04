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
import com.example.fetchdatafromwebtutorial.servises.DynamicCreator
import com.google.gson.Gson
import okhttp3.*
import okhttp3.HttpUrl.Companion.toHttpUrl


/**
 * A simple [Fragment] subclass.
 * Use the [MyExecutosOrdersFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MyExecutosOrdersFragment : Fragment() {

    private lateinit var orders: Array<Order>;
    private var creator: DynamicCreator = DynamicCreator();

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fetchCurrencyData().start()
        return inflater.inflate(R.layout.fragment_my_executos_orders, container, false)
    }
    private fun fetchCurrencyData(): Thread
    {
        return Thread {
            val client = OkHttpClient()

            val queryUrlBuilder: HttpUrl.Builder = "https://${DetailShoesActivity.URL}.eu.ngrok.io/api/orders/getUserAsExecutorOrders".toHttpUrl().newBuilder()
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
                val mainLayout = view?.findViewById<ScrollView>(R.id.mainLayout)
                val parent = mainLayout?.findViewById<LinearLayout>(R.id.childlayout)
                if(orders.isEmpty()){
                    val text =  creator.createTextView(requireActivity(), LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT,"Заказов нет")
                    parent?.addView(text)
                } else {
                    for (i in orders) {
                        Log.i("TAAAAAAAAAAG", i.Shoes_img)
                        val layout = creator.createLinerLayout(requireActivity(), LinearLayout.LayoutParams.WRAP_CONTENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.VERTICAL)
                        val shoesImage = creator.createImageView(requireActivity(), i.Shoes_img)

                        val priceText = creator.createTextView(requireActivity(),LinearLayout.LayoutParams.WRAP_CONTENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT,"Цена: ${i.Shoes_price}")
                        val brandText = creator.createTextView(requireActivity(),LinearLayout.LayoutParams.WRAP_CONTENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT,"Брэнд: ${i.Shoes_brand}")
                        val nameText = creator.createTextView(requireActivity(),LinearLayout.LayoutParams.WRAP_CONTENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT,"Название: ${i.Shoes_name}")
                        val customerText = creator.createTextView(requireActivity(),LinearLayout.LayoutParams.WRAP_CONTENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT,"Заказчик: ${i.customer}")
                        val linkText = creator.createTextView(requireActivity(),LinearLayout.LayoutParams.WRAP_CONTENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT,"Ссылка: ${i.Shoes_link}")
                        val refuseButton = creator.createButton(requireActivity(), LinearLayout.LayoutParams.WRAP_CONTENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT, "отказаться", Color.RED, this::refuseOrder
                        )
                        refuseButton.id = i.Order_id

                        layout.addView(shoesImage)
                        layout.addView(priceText)
                        layout.addView(brandText)
                        layout.addView(nameText)
                        layout.addView(customerText)
                        layout.addView(linkText)
                        layout.addView(refuseButton)
                        parent?.addView(layout)
                        Log.i("NEWEEEEEEEEE", "Цена: ${i.Shoes_price}")
                    }
                }
            }
        }

    }

    fun refuseOrder(button: Button){
        refuseOrderThread(button.id).start()
        button.isClickable = false;
        button.setBackgroundColor(Color.GRAY)
    }

    private fun refuseOrderThread(order_id: Int): Thread{
        return Thread {
            val client = OkHttpClient()

            val requestBody: RequestBody = FormBody.Builder()
                .add("id", order_id.toString())
                .build()

            val request: Request = Request.Builder()
                .url("https://${DetailShoesActivity.URL}.eu.ngrok.io/api/orders/refuseOrder")
                .post(requestBody)
                .build()
            Log.e("ERRROR", order_id.toString())
            val response = client.newCall(request).execute()
        }
    }



}