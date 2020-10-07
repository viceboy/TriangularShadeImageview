package com.viceboy.triangularshadeimageview

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import kotlinx.android.synthetic.main.activity_details.*


class DetailsActivity : AppCompatActivity() {

    private var mTitle : String = ""

    private var mImageSrc: Int = 0
    private var mColorTint : Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        supportActionBar?.hide()
        intent?.extras?.apply {
            mTitle = getString(EXTRA_TITLE)?:""
            mImageSrc = getInt(EXTRA_IMAGE_CARD)
            mColorTint = getInt(EXTRA_IMAGE_TINT)
        }

        tvToolbar?.text = mTitle
        ivDetails?.setImageResource(mImageSrc)
        ivDetails?.shadeColor = ContextCompat.getColor(this,mColorTint)
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        ivDetails?.postDelayed( {
            ivDetails.animateDraw()
        },100)
        super.onWindowFocusChanged(hasFocus)
    }

    companion object {
        const val EXTRA_TITLE = "title"
        const val EXTRA_IMAGE_CARD = "image_src"
        const val EXTRA_IMAGE_TINT = "color_tint"
    }
}