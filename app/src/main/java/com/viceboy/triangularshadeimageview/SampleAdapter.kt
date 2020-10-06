package com.viceboy.triangularshadeimageview

import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import com.viceboy.library.TriangularShadeImageView

class SampleAdapter(
    private val listOfItems: MutableList<SampleModel>,
    private val index: Int,
    private val callback: (Int) -> Unit
) :
    RecyclerView.Adapter<SampleAdapter.SampleHolder>() {

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): SampleHolder = SampleHolder(p0)

    override fun onBindViewHolder(holder: SampleHolder, p1: Int) {
        holder.bind(listOfItems[p1])
    }

    override fun getItemCount(): Int = listOfItems.size

    private var recyclerView: RecyclerView? = null

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        this.recyclerView = recyclerView
        super.onAttachedToRecyclerView(recyclerView)
    }

    inner class SampleHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_layout, parent, false)
    ) {

        private val shadedImageView = itemView.findViewById<TriangularShadeImageView>(R.id.trView)
        private val headerTextView = itemView.findViewById<TextView>(R.id.tvHeader)

        fun bind(model: SampleModel) {
            shadedImageView.setImageDrawable(itemView.context.getDrawable(model.imgSrc))
            shadedImageView.shadeColor = model.color
            headerTextView.text = model.header

            if (index == adapterPosition) {
                shadedImageView?.postDelayed({
                    shadedImageView.animateDraw()
                }, 40)
            }

            itemView.setOnClickListener {
                val intent = Intent(it.context, DetailsActivity::class.java).apply {
                    putExtra(DetailsActivity.EXTRA_TITLE, model.header)
                    putExtra(DetailsActivity.EXTRA_IMAGE_CARD, model.imgSrc)
                    putExtra(DetailsActivity.EXTRA_IMAGE_TINT, model.color)
                }
                callback.invoke(adapterPosition)
                it.context.startActivity(intent)
            }
        }
    }
}

data class SampleModel(
    val imgSrc: Int = 0,
    val color: Int = 0,
    val header: String = ""
)