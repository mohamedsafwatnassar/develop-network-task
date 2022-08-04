package com.developnetwork.task.home.adapter

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter
import com.bumptech.glide.Glide
import com.developnetwork.task.R
import com.developnetwork.task.home.model.ProductItem
import kotlinx.android.synthetic.main.item_image.view.*

class ImagesPagerAdapter(
    private val listUri: ArrayList<Uri>,
    private val productItem: ProductItem,
    private val itemProductClickCallBack: (productItem: ProductItem) -> Unit
) : PagerAdapter() {

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val view: View = LayoutInflater.from(container.context)
            .inflate(R.layout.item_image, container, false)

        Glide.with(container.context)
            .load(listUri[position].toString().trim())
            .into(view.imgRoundedPager)

        view.rootView.setOnClickListener {
            itemProductClickCallBack.invoke(productItem)
        }

        // Add view to viewpager
        container.addView(view)

        return view
    }

    override fun getCount(): Int {
        return listUri.size
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        // Remove view
        container.removeView(`object` as View)
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object`
    }

    override fun getItemPosition(`object`: Any): Int {
        return POSITION_NONE
    }

}