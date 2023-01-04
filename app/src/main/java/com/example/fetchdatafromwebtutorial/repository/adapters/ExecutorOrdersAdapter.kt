package com.example.fetchdatafromwebtutorial.repository.adapters


import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.fetchdatafromwebtutorial.databinding.ExecuteOrdersCardBinding
import com.example.fetchdatafromwebtutorial.repository.models.Order
import com.squareup.picasso.Picasso

interface ExecuteOrdersActionListener {
    fun takeOrderToWork(order: Order, button: View)
}

class ExecuteOrdersAdapter(private val ExecuteOrdersActionListener: ExecuteOrdersActionListener):
    RecyclerView.Adapter<ExecuteOrdersAdapter.ExecuteOrderHolder>(), View.OnClickListener  {

    var orders: Array<Order> = emptyArray()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExecuteOrderHolder
    {
        val layoutInflater = LayoutInflater.from(parent.context)
        val ExecuteOrdersCardBinding = ExecuteOrdersCardBinding.inflate(layoutInflater, parent, false)
        ExecuteOrdersCardBinding.takeOrderButton.setOnClickListener(this)
        return ExecuteOrderHolder(ExecuteOrdersCardBinding)
    }


    override fun onBindViewHolder(viewHolder: ExecuteOrderHolder, position: Int)
    {
        viewHolder.bind(orders[position])
    }

    override fun getItemCount() = orders.size

    override fun onClick(v: View?) {
        val order: Order = v?.tag as Order
        ExecuteOrdersActionListener.takeOrderToWork(order, v)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun refreshOrders(orders: Array<Order>)
    {
        this.orders = orders
        notifyDataSetChanged()
    }


    class ExecuteOrderHolder(private val ExecuteOrdersCardBinding: ExecuteOrdersCardBinding) : RecyclerView.ViewHolder(ExecuteOrdersCardBinding.root) {

        fun bind(order: Order)  {
            ExecuteOrdersCardBinding.takeOrderButton.tag = order
            ExecuteOrdersCardBinding.takeOrderButton.text = "Отказаться "

            ExecuteOrdersCardBinding.customer.text = order.customer
            ExecuteOrdersCardBinding.link.text = order.Shoes_link
            ExecuteOrdersCardBinding.brand.text = order.Shoes_brand
            ExecuteOrdersCardBinding.name.text = order.Shoes_name
            ExecuteOrdersCardBinding.price.text = order.Shoes_price

            Picasso.get().load(order.Shoes_img).into(ExecuteOrdersCardBinding.imageView)
        }
    }

}