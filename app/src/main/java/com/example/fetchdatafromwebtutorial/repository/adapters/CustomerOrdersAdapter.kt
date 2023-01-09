package com.example.fetchdatafromwebtutorial.repository.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.fetchdatafromwebtutorial.databinding.CustomerOrdersCardBinding
import com.example.fetchdatafromwebtutorial.repository.models.Order
import com.squareup.picasso.Picasso

interface CustomerOrdersActionListener {
    fun deleteOrder(order: Order, button: View)
    fun confirmOrderComplete(order: Order, button: View)
}

class CustomerOrdersAdapter(private val CustomerOrdersActionListener: CustomerOrdersActionListener):
    RecyclerView.Adapter<CustomerOrdersAdapter.CustomerOrderHolder>(), View.OnClickListener  {

    var orders: Array<Order> = emptyArray()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomerOrderHolder
    {
        val layoutInflater = LayoutInflater.from(parent.context)
        val customerOrdersCardBinding = CustomerOrdersCardBinding.inflate(layoutInflater, parent, false)
        customerOrdersCardBinding.takeOrderButton.setOnClickListener(this)
        return CustomerOrderHolder(customerOrdersCardBinding)
    }


    override fun onBindViewHolder(viewHolder: CustomerOrderHolder, position: Int)
    {
        viewHolder.bind(orders[position])
    }

    override fun getItemCount() = orders.size

    override fun onClick(v: View?) {
        val order: Order = v?.tag as Order
        CustomerOrdersActionListener.deleteOrder(order, v)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun refreshOrders(orders: Array<Order>)
    {
        this.orders = orders
        notifyDataSetChanged()
    }


    class CustomerOrderHolder(private val customerOrdersCardBinding: CustomerOrdersCardBinding) : RecyclerView.ViewHolder(customerOrdersCardBinding.root) {

        fun bind(order: Order)  {
            customerOrdersCardBinding.takeOrderButton.tag = order
            customerOrdersCardBinding.takeOrderButton.text = "Отменить заказ"

            customerOrdersCardBinding.confirmOrderComplete.text = "Заказ выполнен"

            customerOrdersCardBinding.executor.text = "Заказчик ${order.customer}"
            customerOrdersCardBinding.brand.text = "Бренд ${order.Shoes_brand}"
            customerOrdersCardBinding.name.text = "Товар ${order.Shoes_name}"
            customerOrdersCardBinding.price.text = "Цена ${order.Shoes_price}"

            Picasso.get().load(order.Shoes_img).into(customerOrdersCardBinding.imageView)
        }
    }

}