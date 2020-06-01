package com.example.trial6

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.SeekBar
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.Float.max

class MainActivity : AppCompatActivity(), SeekBar.OnSeekBarChangeListener {

    var srcBitmap: Bitmap? = null
    var dstBitmap: Bitmap? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        srcBitmap = BitmapFactory.decodeResource(this.resources, R.drawable.maxresdefault)
        dstBitmap = srcBitmap!!.copy(srcBitmap!!.config, true)

        imageView.setImageBitmap(dstBitmap)

        sldSigma.setOnSeekBarChangeListener(this)
    }

    fun btnFlip_click(view: View){
        myFlip(srcBitmap!!, dstBitmap!!)
    }

    fun doBlur(){
        val sigma = max(1.0F, sldSigma.progress / 10F)
        this.myBlur(srcBitmap!!, dstBitmap!!, sigma)
    }
    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    external fun stringFromJNI(): String
    external fun myFlip(bitmapIn: Bitmap, bitmapOut: Bitmap)
    external fun myBlur(bitmapIn: Bitmap, bitmapOut: Bitmap, Sigma:Float)

    companion object {
        // Used to load the 'native-lib' library on application startup.
        init {
            System.loadLibrary("native-lib")
        }
    }

    override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
        doBlur()
    }

    override fun onStartTrackingTouch(seekBar: SeekBar?) {}

    override fun onStopTrackingTouch(seekBar: SeekBar?) {}
}
