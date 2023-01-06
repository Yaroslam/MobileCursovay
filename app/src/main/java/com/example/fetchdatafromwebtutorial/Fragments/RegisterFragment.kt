package com.example.fetchdatafromwebtutorial.Fragments

import android.content.Intent
import android.os.Bundle
import android.os.SystemClock.sleep
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.example.fetchdatafromwebtutorial.AuthActivity
import com.example.fetchdatafromwebtutorial.DetailShoesActivity
import com.example.fetchdatafromwebtutorial.MainActivity
import com.example.fetchdatafromwebtutorial.R
import com.example.fetchdatafromwebtutorial.constants.LINK
import com.example.fetchdatafromwebtutorial.databinding.FragmentLoginBinding
import com.example.fetchdatafromwebtutorial.databinding.FragmentRegisterBinding
import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import org.json.JSONArray
import org.json.JSONObject


class RegisterFragment : Fragment() {


    private lateinit var binding: FragmentRegisterBinding
    private var responseCode: Int = 200;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentRegisterBinding.inflate(layoutInflater)

        val button: Button = binding.regButton
        button.setOnClickListener {
            register()
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }


    private fun registerThred(): Thread {
        return Thread {
            val client = OkHttpClient()

            val requestBody: RequestBody = FormBody.Builder()
                .add("name", binding.nameInput.text.toString())
                .add("email", binding.emailInput.text.toString())
                .add("password", binding.passwordInput.text.toString())
                .build()

            val request: Request = Request.Builder()
                .url("https://${LINK}.eu.ngrok.io/reg/registration")
                .post(requestBody)
                .build()
            val response = client.newCall(request).execute()
            responseCode = response.code

            if(responseCode == 200){
                val jsonObj = JSONObject(response.body?.string() ?: "token")
                val map = jsonObj.toMap()
                AuthActivity.authToken = map["token"] as String
                Log.e("TOKEN", map["token"] as String)
            }
        }
    }


    fun JSONObject.toMap(): Map<String, *> = keys().asSequence().associateWith {
        when (val value = this[it])
        {
            is JSONArray ->
            {
                val map = (0 until value.length()).associate { Pair(it.toString(), value[it]) }
                JSONObject(map).toMap().values.toList()
            }
            is JSONObject -> value.toMap()
            JSONObject.NULL -> null
            else            -> value
        }
    }

    fun register(){
        registerThred().start()
        sleep(3_000)
        if (responseCode == 200){
            Toast.makeText(this.context, "Вы ввели все верно", Toast.LENGTH_LONG).show()
            val intent = Intent(activity, MainActivity::class.java)
            startActivity(intent)
        } else {
            Toast.makeText(this.context, "Вы ввели что-то неврено", Toast.LENGTH_LONG).show()
        }
    }



}