package com.example.fetchdatafromwebtutorial.Fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fetchdatafromwebtutorial.DetailShoesActivity
import com.example.fetchdatafromwebtutorial.databinding.FragmentShoeListBinding
import com.example.fetchdatafromwebtutorial.repository.adapters.PersonActionListener
import com.example.fetchdatafromwebtutorial.repository.adapters.ShoesAdapter
import com.example.fetchdatafromwebtutorial.repository.models.Shoes
import com.example.fetchdatafromwebtutorial.repository.viewModels.ShoesViewModel



class ShoeListFragment : Fragment() {

    private val shoesDataModel: ShoesViewModel by viewModels()
    private lateinit var binding: FragmentShoeListBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val adapter = ShoesAdapter(object : PersonActionListener {
            override fun getSHoes(shoe: Shoes) {
                val intent = Intent(activity, DetailShoesActivity::class.java)
                intent.putExtra(DetailShoesActivity.SHOES_ID, shoe.shoe_id.toString())
                startActivity(intent)
            }
        })
        binding = FragmentShoeListBinding.inflate(layoutInflater)
        binding.shoesList.layoutManager = LinearLayoutManager(this.context)
        binding.shoesList.adapter = adapter

        shoesDataModel.getListUsers().observe(this, Observer {
            adapter.refreshShoes(it)
        })
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }



}