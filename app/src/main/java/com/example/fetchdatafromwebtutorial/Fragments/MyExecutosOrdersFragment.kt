package com.example.fetchdatafromwebtutorial.Fragments

import android.os.Bundle
import android.os.SystemClock
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
import com.example.fetchdatafromwebtutorial.DetailShoesActivity
import com.example.fetchdatafromwebtutorial.R
import com.example.fetchdatafromwebtutorial.constants.LINK
import com.example.fetchdatafromwebtutorial.databinding.FragmentMyExecutosOrdersBinding
import com.example.fetchdatafromwebtutorial.databinding.FragmentMyOrdersBinding
import com.example.fetchdatafromwebtutorial.repository.adapters.CustomerOrdersActionListener
import com.example.fetchdatafromwebtutorial.repository.adapters.CustomerOrdersAdapter
import com.example.fetchdatafromwebtutorial.repository.adapters.ExecuteOrdersActionListener
import com.example.fetchdatafromwebtutorial.repository.adapters.ExecuteOrdersAdapter
import com.example.fetchdatafromwebtutorial.repository.models.Order
import com.example.fetchdatafromwebtutorial.repository.viewModels.ExecutorOrdersViewModel
import okhttp3.*


/**
 * A simple [Fragment] subclass.
 * Use the [MyExecutosOrdersFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MyExecutosOrdersFragment : Fragment() {

    private val executorOrderDataModel: ExecutorOrdersViewModel by viewModels()
    private lateinit var  adapter: ExecuteOrdersAdapter;
    private lateinit var binding: FragmentMyExecutosOrdersBinding

//обновление по удалению работает

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        adapter = ExecuteOrdersAdapter(object : ExecuteOrdersActionListener {
            override fun refuseOrder(order: Order, button: View){
                refuseOrder(order.Order_id)
                Toast.makeText(context, "Вы отказались от заказа", Toast.LENGTH_SHORT).show()
            }
        })
        binding = FragmentMyExecutosOrdersBinding.inflate(layoutInflater)
        binding.orderList.layoutManager = LinearLayoutManager(this.context)
        binding.orderList.adapter = adapter

        executorOrderDataModel.getListOrders().observe(this, Observer {
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


    fun refuseOrder(order_id: Int){
        refuseOrderThread(order_id).start()
    }


    private fun refuseOrderThread(order_id: Int): Thread{
        return Thread {
            val client = OkHttpClient()

            val requestBody: RequestBody = FormBody.Builder()
                .add("id", order_id.toString())
                .build()

            val request: Request = Request.Builder()
                .url("https://${LINK}.eu.ngrok.io/api/orders/refuseOrder")
                .header("Authorization", "Bearer ${AuthActivity.authToken}")
                .post(requestBody)
                .build()
            Log.e("ERRROR", order_id.toString())
            val response = client.newCall(request).execute()
            executorOrderDataModel.updateOrders()
        }
    }



}