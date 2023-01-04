package com.example.fetchdatafromwebtutorial.servises

import android.content.Context
import android.graphics.Color
import android.text.method.LinkMovementMethod
import android.view.OrientationEventListener
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.squareup.picasso.Picasso

class DynamicCreator {

    fun createButton(context: FragmentActivity, width: Int, height:Int, text: String, color: Int, action: (button: Button) -> Unit): Button{
        val dynamicButton = Button(context)
        dynamicButton.layoutParams = LinearLayout.LayoutParams(
            width, height
        )
        dynamicButton.text = text
        dynamicButton.setBackgroundColor(color)

        dynamicButton.setOnClickListener {
            action(dynamicButton)
        }

        return dynamicButton
    }

    fun createTextView(context: FragmentActivity, width: Int, height:Int, text: String): TextView
    {
        val textDynamic = TextView(context)
        textDynamic.layoutParams = LinearLayout.LayoutParams(
            width, height
        )
        textDynamic.text = text
//        не забудь про смещение по x
        return textDynamic
    }

    fun createImageView(context: FragmentActivity, imageSource: String): ImageView
    {
        val image = ImageView(context)
        Picasso.get().load(imageSource).into(image)
//        не забудь про смещение по x
        return image
    }

    fun connectFamily(child: View, parent: ViewGroup){
        parent.addView(child)
    }

    fun createLinerLayout(context: Context, width: Int, height:Int, orientation: Int): LinearLayout {
        val dynamicLayout = LinearLayout(context)

        dynamicLayout.layoutParams = LinearLayout.LayoutParams(
           width, height
        )
        dynamicLayout.orientation = orientation

        return dynamicLayout
    }







}