package com.viceboy.triangularshadeimageview

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var indexForAnim = -1
    private var adapter : SampleAdapter ?=null

    private val list =
        mutableListOf(
            SampleModel(R.drawable.tree_img, R.color.colorAccent, "Impressions"),
            SampleModel(R.drawable.nature_blue_img, R.color.lightBlue, "Lightning"),
            SampleModel(R.drawable.img_green, R.color.green, "Environmental"),
            SampleModel(R.drawable.img_env_green, R.color.grassGreen, "Colloidal")
        )


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_main)
        rvItems.setHasFixedSize(true)


    }

    override fun onResume() {
        adapter = SampleAdapter(list,indexForAnim) {
            indexForAnim = it
        }
        rvItems.adapter = adapter
        super.onResume()
    }
}