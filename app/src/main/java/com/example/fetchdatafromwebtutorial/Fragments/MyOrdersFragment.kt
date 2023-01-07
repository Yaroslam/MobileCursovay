package com.example.fetchdatafromwebtutorial.Fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fetchdatafromwebtutorial.AuthActivity
import com.example.fetchdatafromwebtutorial.constants.LINK
import com.example.fetchdatafromwebtutorial.databinding.FragmentMyOrdersBinding
import com.example.fetchdatafromwebtutorial.repository.adapters.CustomerOrdersActionListener
import com.example.fetchdatafromwebtutorial.repository.adapters.CustomerOrdersAdapter
import com.example.fetchdatafromwebtutorial.repository.models.Order
import com.example.fetchdatafromwebtutorial.repository.viewModels.CustomerOrdersViewModel
import okhttp3.*


class MyOrdersFragment : Fragment() {

    private val customerOrderDataModel: CustomerOrdersViewModel by viewModels()
    private lateinit var  adapter: CustomerOrdersAdapter;
    private lateinit var binding: FragmentMyOrdersBinding



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        adapter = CustomerOrdersAdapter(object : CustomerOrdersActionListener {
            override fun deleteOrder(order: Order, button: View){
                deleteOrder(order.Order_id)
                Toast.makeText(context, "Вы удалили заказ", Toast.LENGTH_SHORT).show()
            }

            override fun confirmOrderComplete(order: Order, button: View){
                confirmOrderComplete(order.Order_id)
                Toast.makeText(context, "Вы подтведили выполнение заказа", Toast.LENGTH_SHORT).show()
            }


        })
        binding = FragmentMyOrdersBinding.inflate(layoutInflater)
        binding.orderList.layoutManager = LinearLayoutManager(this.context)
        binding.orderList.adapter = adapter

        customerOrderDataModel.getListOrders().observe(this, Observer {
            it?.let {
                adapter.refreshOrders(it)
            }
        })

    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return binding.root
    }


    fun deleteOrder(order_id: Int){
        deleteOrderThread(order_id).start()
    }

    fun confirmOrderComplete(order_id: Int){
        confirmOrderCompleteThread(order_id).start()
    }

    fun confirmOrderCompleteThread(order_id: Int): Thread{
        return Thread {
            val client = OkHttpClient()

            val requestBody: RequestBody = FormBody.Builder()
                .add("id", order_id.toString())
                .build()

            val request: Request = Request.Builder()
                .url("https://${LINK}.eu.ngrok.io/api/orders/completeOrder")
                .header("Authorization", "Bearer ${AuthActivity.authToken}")
                .post(requestBody)
                .build()
            val response = client.newCall(request).execute()
            customerOrderDataModel.updateOrders()
        }
    }

    private fun deleteOrderThread(order_id: Int): Thread{
        return Thread {
            val client = OkHttpClient()

            val requestBody: RequestBody = FormBody.Builder()
                .add("id", order_id.toString())
                .build()

            val request: Request = Request.Builder()
                .url("https://${LINK}.eu.ngrok.io/api/orders/delete")
                .header("Authorization", "Bearer ${AuthActivity.authToken}")
                .post(requestBody)
                .build()
            Log.e("ERRROR", order_id.toString())
            val response = client.newCall(request).execute()
            customerOrderDataModel.updateOrders()
        }
    }



}