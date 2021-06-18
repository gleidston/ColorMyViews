package com.meupet.colormyviews

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    lateinit var boxOne: TextView
    lateinit var boxTwo: TextView
    lateinit var boxThree: TextView
    lateinit var boxFour: TextView
    lateinit var boxFive: TextView
    var boxColor1 =0
    var boxColor2 =0
    var boxColor3 =0
    var boxColor4 =0
    var boxColor5 =0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var changeColor = R.color.grey
        var redButton = findViewById<Button>(R.id.btn_red)
        var yelowButton = findViewById<Button>(R.id.btn_yelow)
        var greenButton = findViewById<Button>(R.id.btn_green)
        boxOne = findViewById<TextView>(R.id.box_one)
        boxTwo= findViewById<TextView>(R.id.box_two_text)
        boxThree= findViewById<TextView>(R.id.boxThree)
        boxFour= findViewById<TextView>(R.id.boxFour)
        boxFive= findViewById<TextView>(R.id.boxFive)

        redButton.setOnClickListener {

            changeColor =R.color.red
        }

        yelowButton.setOnClickListener {

            changeColor =R.color.yelon
        }

        greenButton.setOnClickListener {

            changeColor =R.color.green
        }
        boxOne.setOnClickListener {
            boxOne.setBackgroundResource(changeColor)
        }

        boxTwo.setOnClickListener {
            boxTwo.setBackgroundResource(changeColor)
        }
        boxThree.setOnClickListener {
            boxThree.setBackgroundResource(changeColor)
        }
        boxFour.setOnClickListener {
            boxFour.setBackgroundResource(changeColor)
        }

        boxFive.setOnClickListener {
            boxFive.setBackgroundResource(changeColor)
        }




    }



    override fun onStop() {
        super.onStop()
        val sahredPreferences = getSharedPreferences("colors", Context.MODE_PRIVATE)
        val editor =sahredPreferences.edit()

        editor.putInt("boxOne",boxColor1)
    }
}