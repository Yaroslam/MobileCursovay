package com.example.fetchdatafromwebtutorial.repository.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.fetchdatafromwebtutorial.databinding.ListShoeCardBinding
import com.example.fetchdatafromwebtutorial.repository.models.Shoes
import com.squareup.picasso.Picasso



interface PersonActionListener {
    fun getSHoes(shoe: Shoes)
}

class ShoesAdapter(private val PersonActionListener: PersonActionListener): RecyclerView.Adapter<ShoesAdapter.ShoesHolder>(), View.OnClickListener  {


    private var shoes: Array<Shoes> =  emptyArray()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShoesHolder
    {
        val layoutInflater = LayoutInflater.from(parent.context)
        val listShoeCardBinding =ListShoeCardBinding.inflate(layoutInflater, parent, false)
        listShoeCardBinding.showShoesButton.setOnClickListener(this)
        return ShoesHolder(listShoeCardBinding)
    }


    override fun onBindViewHolder(viewHolder: ShoesHolder, position: Int)
    {
        viewHolder.bind(shoes[position])
    }

    override fun getItemCount() = shoes.size

    override fun onClick(v: View?) {
        val shoe: Shoes = v?.tag as Shoes
        PersonActionListener.getSHoes(shoe)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun refreshShoes(shoes: Array<Shoes>)
    {
        this.shoes = shoes
        notifyDataSetChanged()
    }


    class ShoesHolder(private val itemShoeCardBinding: ListShoeCardBinding) : RecyclerView.ViewHolder(itemShoeCardBinding.root) {

        fun bind(shoes: Shoes)  {
            Picasso.get().load(shoes.img).into(itemShoeCardBinding.imageView)
            itemShoeCardBinding.showShoesButton.tag = shoes
        }
    }

}


