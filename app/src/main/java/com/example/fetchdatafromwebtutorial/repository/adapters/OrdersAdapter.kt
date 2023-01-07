package com.example.fetchdatafromwebtutorial.repository.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.fetchdatafromwebtutorial.databinding.AllOrdersCardBinding
import com.example.fetchdatafromwebtutorial.repository.models.Order
import com.squareup.picasso.Picasso


interface OrdersActionListener {
    fun takeOrderToWork(order: Order, button: View)
}

class OrdersAdapter(private val OrdersActionListener: OrdersActionListener): RecyclerView.Adapter<OrdersAdapter.OrderHolder>(), View.OnClickListener  {


    var orders: Array<Order> = emptyArray()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderHolder
    {
        val layoutInflater = LayoutInflater.from(parent.context)
        val AllOrdersCardBinding = AllOrdersCardBinding.inflate(layoutInflater, parent, false)
        AllOrdersCardBinding.takeOrderButton.setOnClickListener(this)
        return OrderHolder(AllOrdersCardBinding)
    }


    override fun onBindViewHolder(viewHolder: OrderHolder, position: Int)
    {
        viewHolder.bind(orders[position])
    }

    override fun getItemCount() = orders.size

    override fun onClick(v: View?) {
        val order: Order = v?.tag as Order
        OrdersActionListener.takeOrderToWork(order, v)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun refreshOrders(orders: Array<Order>)
    {
        this.orders = orders
        notifyDataSetChanged()
    }


    class OrderHolder(private val allOrdersCardBinding: AllOrdersCardBinding) : RecyclerView.ViewHolder(allOrdersCardBinding.root) {

        fun bind(order: Order)  {
            allOrdersCardBinding.takeOrderButton.tag = order
            allOrdersCardBinding.takeOrderButton.text = "Взять заказ"

            allOrdersCardBinding.customer.text = order.customer
            allOrdersCardBinding.link.text = order.Shoes_link
            allOrdersCardBinding.brand.text = order.Shoes_brand
            allOrdersCardBinding.name.text = order.Shoes_name
            allOrdersCardBinding.price.text = order.Shoes_price

            Picasso.get().load(order.Shoes_img).into(allOrdersCardBinding.imageView)
        }
    }

}