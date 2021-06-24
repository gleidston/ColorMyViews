package com.meupet.colormyviews

import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.graphics.Canvas
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
            val intent = Intent(Intent.ACTION_SEND)
            intent.putExtra(Intent.EXTRA_TEXT, "Voce ")
            intent.type = "image/*"
            if (intent.resolveActivity(this.packageManager) != null) {
                startActivity(intent)
            } else {
                Toast.makeText(this, "Você não tem um aplicativo compátvel", Toast.LENGTH_LONG)
            }

        }
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
    // this method saves the image to gallery
    //-----------------------------------------------------------------------
    fun getBitMapFromView(view: ImageView):Bitmap? {
        val bitmap =
            Bitmap.createBitmap(view.width, view.height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        view.draw(canvas)
        return bitmap
    }


    private fun saveMediaToStorage(bitmap: Bitmap) {
        // Generating a file name
        val filename = "${System.currentTimeMillis()}.jpg"

        // Output stream
        var fos: OutputStream? = null

        // For devices running android >= Q
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            // getting the contentResolver
            this.contentResolver?.also { resolver ->

                // Content resolver will process the contentvalues
                val contentValues = ContentValues().apply {

                    // putting file information in content values
                    put(MediaStore.MediaColumns.DISPLAY_NAME, filename)
                    put(MediaStore.MediaColumns.MIME_TYPE, "image/jpg")
                    put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES)
                }

                // Inserting the contentValues to
                // contentResolver and getting the Uri
                val imageUri: Uri? = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)

                // Opening an outputstream with the Uri that we got
                fos = imageUri?.let { resolver.openOutputStream(it) }
            }
        } else {
            // These for devices running on android < Q
            val imagesDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
            val image = File(imagesDir, filename)
            fos = FileOutputStream(image)
        }

        fos?.use {
            // Finally writing the bitmap to the output stream that we opened
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, it)
            Toast.makeText(this , "Captured View and saved to Gallery" , Toast.LENGTH_SHORT).show()
        }
    }
    private fun getScreenShotFromView(v: View?): Bitmap? {
        // create a bitmap object
        var screenshot: Bitmap? = null
        try {
            // inflate screenshot object
            // with Bitmap.createBitmap it
            // requires three parameters
            // width and height of the view and
            // the background color
            screenshot = Bitmap.createBitmap(v!!.measuredWidth, v.measuredHeight, Bitmap.Config.ARGB_8888)
            // Now draw this bitmap on a canvas
            val canvas = Canvas(screenshot)
            v.draw(canvas)
        } catch (e: Exception) {
            Log.e("GFG", "Failed to capture screenshot because:" + e.message)
        }
        // return the bitmap
        return screenshot
    }

}