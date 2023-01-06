package com.example.fetchdatafromwebtutorial

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.fetchdatafromwebtutorial.Fragments.*
import com.example.fetchdatafromwebtutorial.constants.LINK
import com.example.fetchdatafromwebtutorial.databinding.ActivityMainBinding
import com.example.fetchdatafromwebtutorial.repository.viewModels.ShoesViewModel
import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody


class MainActivity : AppCompatActivity()
{
    companion object {
        const val URL = "56f9-188-66-38-93"
    }
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val shoesFragment = ShoeListFragment()
        val allOrdersFragment = AllOrdersFragment()
        val customerOrdersFragment = MyOrdersFragment()
        val executosOrdersFragment = MyExecutosOrdersFragment()

        makeCurrentFragment(shoesFragment)
        binding.bottomMenu.setOnNavigationItemReselectedListener {
            when(it.itemId) {
                R.id.shoesList -> makeCurrentFragment(shoesFragment)
                R.id.AllOrders -> makeCurrentFragment(allOrdersFragment)
                R.id.myOrders ->  makeCurrentFragment(customerOrdersFragment)
                R.id.executeOrders -> makeCurrentFragment(executosOrdersFragment)
            }
        }

    }

    private fun makeCurrentFragment(fragment: Fragment){
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fl_wrapper, fragment)
            commit()
        }
    }


}