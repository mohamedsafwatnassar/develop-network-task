package com.developnetwork.task.home.dialogs

import android.app.Dialog
import android.graphics.Color
import android.graphics.Point
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import androidx.fragment.app.DialogFragment
import com.bumptech.glide.Glide
import com.developnetwork.task.R
import com.developnetwork.task.databinding.ProductItemInDetailsBinding
import com.developnetwork.task.home.model.ProductItem

class ProductClickDialog(
    private val product: ProductItem,
) : DialogFragment() {

    private lateinit var binding: ProductItemInDetailsBinding

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        if (dialog != null && dialog!!.window != null) {
            dialog!!.window!!.setBackgroundDrawable(ColorDrawable(Color.WHITE))
            dialog!!.window!!.requestFeature(Window.FEATURE_NO_TITLE)
        }
        return super.onCreateDialog(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ProductItemInDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dialog?.window?.setBackgroundDrawableResource(android.R.color.transparent)
        initView()
    }

    private fun initView() {
        binding.price.text = getString(R.string.price, product.price.toString())
        binding.title.text = product.title
        binding.description.text = product.description
        Glide.with(this.requireContext())
            .load(product.images[0])
            .into(binding.image)
    }

    private fun setDialogStyle(dialog: Dialog?) {
        val window = dialog?.window
        val size = Point()
        val display = window?.windowManager?.defaultDisplay
        display?.getSize(size)
        window?.setLayout(
            WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT

        )
        window?.setGravity(Gravity.CENTER)
    }

    override fun onStart() {
        super.onStart()
        setDialogStyle(dialog)
    }
}