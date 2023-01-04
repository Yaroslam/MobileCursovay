package com.example.fetchdatafromwebtutorial.Fragments

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fetchdatafromwebtutorial.DetailShoesActivity
import com.example.fetchdatafromwebtutorial.databinding.FragmentAllOrdersBinding
import com.example.fetchdatafromwebtutorial.databinding.FragmentMyOrdersBinding
import com.example.fetchdatafromwebtutorial.databinding.FragmentShoeListBinding
import com.example.fetchdatafromwebtutorial.repository.adapters.OrdersActionListener
import com.example.fetchdatafromwebtutorial.repository.adapters.OrdersAdapter
import com.example.fetchdatafromwebtutorial.repository.models.Order
import com.example.fetchdatafromwebtutorial.repository.models.Shoes
import com.example.fetchdatafromwebtutorial.repository.viewModels.OrdersViewModel
import com.example.fetchdatafromwebtutorial.repository.viewModels.ShoesViewModel
import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody


class AllOrdersFragment : Fragment() {

    private val orderDataModel: OrdersViewModel by viewModels()
    private lateinit var binding: FragmentAllOrdersBinding
    private lateinit var  adapter: OrdersAdapter;


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        adapter = OrdersAdapter(object : OrdersActionListener {
            override fun takeOrderToWork(order: Order, button: View){
                takeOrderToWorkThread(order).start()
                Toast.makeText(context, "Вы взяли заказ", Toast.LENGTH_SHORT).show()
            }
        })
        binding = FragmentAllOrdersBinding.inflate(layoutInflater)
        binding.orderList.layoutManager = LinearLayoutManager(this.context)
        binding.orderList.adapter = adapter

        orderDataModel.getListOrders().observe(this, Observer {
            it?.let {
                adapter.refreshShoes(it)
            }
        })

    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return binding.root
    }


    private fun takeOrderToWorkThread(order: Order): Thread {
        return Thread {
            val client = OkHttpClient()

            val requestBody: RequestBody = FormBody.Builder()
                .add("id", order.Order_id.toString()).add("executor", "1")
                .build()

            val request: Request = Request.Builder()
                .url("https://${DetailShoesActivity.URL}.eu.ngrok.io/api/orders/takeToWorkOrder")
                .post(requestBody)
                .build()
            Log.e("ERRROR", order.Order_id.toString())
            val response = client.newCall(request).execute()
            orderDataModel.updateOrders()
        }
    }




}