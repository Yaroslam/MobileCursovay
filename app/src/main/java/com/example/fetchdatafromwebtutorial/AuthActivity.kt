package com.example.fetchdatafromwebtutorial

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.fetchdatafromwebtutorial.Fragments.*
import com.example.fetchdatafromwebtutorial.databinding.ActivityAuthBinding
import com.example.fetchdatafromwebtutorial.databinding.ActivityMainBinding





class AuthActivity : AppCompatActivity() {

    private enum class Fragments {REG, LOGIN}
    private var curFragment = Fragments.REG;
    private lateinit var binding: ActivityAuthBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val registerFragment = RegisterFragment()
        val loginFragment = LoginFragment()

        makeCurrentFragment(registerFragment)
        binding.textView.setOnClickListener{
            if(curFragment == Fragments.REG){
                makeCurrentFragment(loginFragment)
                binding.textView.text = "Создать аккаунт"
                curFragment = Fragments.LOGIN

            } else {
                makeCurrentFragment(registerFragment)
                binding.textView.text = "Уже есть аккаунт"
                curFragment = Fragments.REG
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