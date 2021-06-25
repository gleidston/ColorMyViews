package com.meupet.colormyviews

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.media.Image
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.FileProvider
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream

class MainActivity : AppCompatActivity() {


    lateinit var boxOne: TextView
    lateinit var boxTwo: TextView
    lateinit var boxThree: TextView
    lateinit var boxFour: TextView
    lateinit var boxFive: TextView

    var boxOneColor = R.color.grey
    var boxTwoColor = R.color.grey
    var boxThreeColor = R.color.grey
    var boxFourColor = R.color.grey
    var boxFiveColor = R.color.grey


    val sharedPreferences: SharedPreferences
        get() {
            return this.getSharedPreferences("colors", Context.MODE_PRIVATE)
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        boxOne = findViewById(R.id.box_one)
        boxTwo = findViewById(R.id.box_two)
        boxThree = findViewById(R.id.boxThree)
        boxFour = findViewById(R.id.boxFour)
        boxFive = findViewById(R.id.boxFive)

        boxOneColor = sharedPreferences.getInt("boxOne", R.color.grey)
        boxTwoColor = sharedPreferences.getInt("boxTwo", R.color.grey)
        boxThreeColor = sharedPreferences.getInt("boxThree", R.color.grey)
        boxFourColor = sharedPreferences.getInt("boxFour", R.color.grey)
        boxFiveColor = sharedPreferences.getInt("boxFive", R.color.grey)
        boxOne.setBackgroundResource(boxOneColor)
        boxTwo.setBackgroundResource(boxTwoColor)
        boxThree.setBackgroundResource(boxThreeColor)
        boxFour.setBackgroundResource(boxFourColor)
        boxFive.setBackgroundResource(boxFiveColor)


        var chanceColor = R.color.grey
        var redButton = findViewById<Button>(R.id.btn_red)
        var yellowButton = findViewById<Button>(R.id.btn_yellow)
        var greenButton = findViewById<Button>(R.id.btn_green)
        var btnTela = findViewById<FloatingActionButton>(R.id.fab)
        var view = findViewById<View>(R.id.parentLayout)




        redButton.setOnClickListener {
            chanceColor = R.color.red
        }

        yellowButton.setOnClickListener {
            chanceColor = R.color.yelon
        }

        greenButton.setOnClickListener {
            chanceColor = R.color.green
        }

        boxOne.setOnClickListener {
            boxOne.setBackgroundResource(chanceColor)
            boxOneColor = chanceColor
        }

        boxTwo.setOnClickListener {
            boxTwo.setBackgroundResource(chanceColor)
            boxTwoColor = chanceColor
        }

        boxThree.setOnClickListener {
            boxThree.setBackgroundResource(chanceColor)
            boxThreeColor = chanceColor
        }

        boxFour.setOnClickListener {
            boxFour.setBackgroundResource(chanceColor)
            boxFourColor = chanceColor
        }

        boxFive.setOnClickListener {
            boxFive.setBackgroundResource(chanceColor)
            boxFiveColor = chanceColor
        }
        btnTela.setOnClickListener {
            val bitmap = getViewAsBitmap(view)
            if (bitmap != null) {
                saveScreenshot(bitmap, "ScreenshotView")

                if (intent.resolveActivity(this.packageManager) != null) {
                    //startActivity(intent)
                    startActivity(Intent.createChooser(intent, "Share with"));
                } else {
                    Toast.makeText(this, "Você não tem um aplicativo compátvel", Toast.LENGTH_LONG)
                }

            }
        }

        // this method saves the image to gallery
        //-----------------------------------------------------------------------

        // To share Image --------------





    }


    private fun saveScreenshot(imageBitmap: Bitmap, filename: String) {
        val dirPath = applicationContext.filesDir
        val file = File(dirPath, "$filename.jpg")
        val fileOutputStream = FileOutputStream(file)
        try {
            imageBitmap.compress(Bitmap.CompressFormat.JPEG, 85, fileOutputStream)
            fileOutputStream.apply {
                flush()
                close()
            }
            val imageUri = FileProvider.getUriForFile(
                this@MainActivity,
                "com.devventure.colormyviews.provider",
                file
            )
            shareImageUri(imageUri)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
    @SuppressLint("QueryPermissionsNeeded")
    private fun shareImageUri(uri: Uri) {
        val shareIntent = Intent(Intent.ACTION_SEND).apply {
            putExtra(Intent.EXTRA_STREAM, uri)
            type = "image/*"
        }
        applicationContext?.packageManager?.run {
            if (shareIntent.resolveActivity(this) != null)
                startActivity(Intent.createChooser(shareIntent, "Share images to.."))
            else
                Toast.makeText(applicationContext, "Impossível executar", Toast.LENGTH_LONG).show()
        }
    }

    private fun getViewAsBitmap(mView: View): Bitmap? {
        val bitmap = Bitmap.createBitmap(mView.width, mView.height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        val bgDraw = mView.background
        if (bgDraw != null)
            bgDraw.draw(canvas)
        else
            canvas.drawColor(Color.WHITE)
        mView.draw(canvas)
        return bitmap
    }
    override fun onStop() {
        super.onStop()

        val editor = sharedPreferences.edit()

        editor.putInt("boxOne", boxOneColor)
        editor.putInt("boxTwo", boxTwoColor)
        editor.putInt("boxThree", boxThreeColor)
        editor.putInt("boxFour", boxFourColor)
        editor.putInt("boxFive", boxFiveColor)

        editor.commit()

    }
}