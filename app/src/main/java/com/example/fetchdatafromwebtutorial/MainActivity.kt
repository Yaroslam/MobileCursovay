package com.example.fetchdatafromwebtutorial

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.fetchdatafromwebtutorial.Fragments.*
import com.example.fetchdatafromwebtutorial.databinding.ActivityMainBinding
import com.example.fetchdatafromwebtutorial.repository.viewModels.ShoesViewModel


class MainActivity : AppCompatActivity()
{
    companion object {
        const val URL = "56f9-188-66-38-93"
    }
    private val shoesDataModel: ShoesViewModel by viewModels()
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
                R.id.myOrders -> makeCurrentFragment(customerOrdersFragment)
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




//    private fun fetchCurrencyData(): Thread
//    {
//        return Thread {
//            val client = OkHttpClient()
//
//            val request = Request.Builder().url("https://${URL}.eu.ngrok.io/api/Shoes").build();
//            val response = client.newCall(request).execute()
//
//            val body = response.body?.string()
//            this.shoes = Gson().fromJson(body, Array<Shoes>::class.java)
//
//        }
//    }
//
//    private fun updateUI(shoes: Array<Shoes>)
//    {
//        runOnUiThread {
//            kotlin.run {
//                var ind = 0
//                for (i in shoes) {
//                    ind+=10
//                    createImageDynamically(i.img)
//                    createButtonDynamically(i)
//                }
//            }
//        }
//    }
//
//
//    private fun createImageDynamically(imageSource: String){
//        val mainLayout = findViewById<ScrollView>(R.id.mainLayout)
//        val child = mainLayout.findViewById<LinearLayout>(R.id.childlayout)
//        val dynamicImage = ImageView(this)
//
//        Picasso.get().load(imageSource).into(dynamicImage)
//        child.addView(dynamicImage)
//    }
//
//
//    private fun createButtonDynamically(shoes: Shoes) {
//        val mainLayout = findViewById<ScrollView>(R.id.mainLayout)
//        val child = mainLayout.findViewById<LinearLayout>(R.id.childlayout)
//        val dynamicButton = Button(this)
//
//        dynamicButton.layoutParams = LinearLayout.LayoutParams(
//            LinearLayout.LayoutParams.WRAP_CONTENT,
//            LinearLayout.LayoutParams.WRAP_CONTENT
//        )
//        dynamicButton.x = 450F
//        dynamicButton.text = shoes.price_euro
//        dynamicButton.id = shoes.shoe_id
//        dynamicButton.setBackgroundColor(Color.GREEN)
//        dynamicButton.setOnClickListener {
//            val intent = Intent(this, DetailShoesActivity::class.java)
//            Log.i("IDDDDDDDDDDDDDDDDDDDDD", dynamicButton.id.toString())
//            intent.putExtra(DetailShoesActivity.SHOES_ID, dynamicButton.id.toString())
//            startActivity(intent)
//        }
//        child.addView(dynamicButton)
//    }

}