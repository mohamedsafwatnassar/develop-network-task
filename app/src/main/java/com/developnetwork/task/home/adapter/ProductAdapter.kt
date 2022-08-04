package com.developnetwork.task.home.adapter

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.developnetwork.task.databinding.ItemProductsBinding
import com.developnetwork.task.home.model.ProductItem
import me.relex.circleindicator.CircleIndicator
import java.util.*

class ProductAdapter(
    private val itemProductClickCallBack: (productItem: ProductItem) -> Unit
) :
    RecyclerView.Adapter<ProductAdapter.ProductViewHolder>(), Filterable {

    private var productList = ArrayList<ProductItem>()
    private var productFilteredList = productList

    private lateinit var binding: ItemProductsBinding

    private lateinit var viewPager: ViewPager
    private lateinit var circleIndicator: CircleIndicator
    private lateinit var imagesPagerAdapter: ImagesPagerAdapter

    class ProductViewHolder(itemView: ItemProductsBinding) :
        RecyclerView.ViewHolder(itemView.root) {
        val txtDiscount = itemView.txtDiscount
        val txtHighRate = itemView.txtHighRate
        val txtTitle = itemView.txtTitle
        val txtPrice = itemView.txtPrice
        val txtDescription = itemView.txtDescription
        val imgDiscount = itemView.imgDiscount
        val vpImages = itemView.vpImages
        val circleIndicatorImage = itemView.circleIndicatorImage
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        binding = ItemProductsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val productItem = productList[position]
        bindData(productItem, holder)
    }

    private fun bindData(productItem: ProductItem, holder: ProductViewHolder) {
        viewPager = holder.vpImages
        circleIndicator = holder.circleIndicatorImage

        val imagesListUri = castStringToUri(productItem.images)

        imagesPagerAdapter =
            ImagesPagerAdapter(imagesListUri, productItem, itemProductClickCallBack)
        viewPager.adapter = imagesPagerAdapter
        circleIndicator.setViewPager(viewPager)

        holder.txtHighRate.text = productItem.rating.toString()
        holder.txtDiscount.text = productItem.discountPercentage.toString() + " %"

        if (productItem.stock > 50) {
            holder.txtTitle.text = productItem.price.toString() + "LE"
            holder.txtPrice.text = productItem.title
            holder.txtDescription.text = productItem.description
        } else {
            holder.txtTitle.text = productItem.title
            holder.txtPrice.text = productItem.price.toString() + "LE"
            holder.txtDescription.text = productItem.description
        }

        if (productItem.rating > 0) {
            holder.imgDiscount.visibility = View.VISIBLE
            holder.txtDiscount.visibility = View.VISIBLE
        } else {
            holder.imgDiscount.visibility = View.GONE
            holder.txtDiscount.visibility = View.GONE
        }

        binding.root.setOnClickListener {
            itemProductClickCallBack.invoke(productItem)
        }
    }

    override fun getItemCount(): Int {
        return productFilteredList.size
    }

    fun setData(products: ArrayList<ProductItem>) {
        productList = products
        productFilteredList = products
        notifyDataSetChanged()
    }


    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint.toString()
                productFilteredList = if (charSearch.isEmpty()) {
                    productList
                } else {
                    val resultList = ArrayList<ProductItem>()
                    for (row in productList) {
                        if (row.title.trim().lowercase(Locale.getDefault())
                                .contains(charSearch.trim())
                        ) {
                            resultList.add(row)
                        }
                    }
                    resultList
                }
                val filterResults = FilterResults()
                filterResults.values = productFilteredList
                return filterResults
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                productFilteredList = results?.values as ArrayList<ProductItem>
                notifyDataSetChanged()
            }
        }
    }

    private fun castStringToUri(x: ArrayList<String>): ArrayList<Uri> {
        val n = ArrayList<Uri>()
        for (i in x) {
            n.add(Uri.parse(i))
        }
        return n
    }

}