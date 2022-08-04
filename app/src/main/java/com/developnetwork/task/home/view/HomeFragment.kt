package com.developnetwork.task.home.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.developnetwork.task.R
import com.developnetwork.task.common.base.BaseFragment
import com.developnetwork.task.common.utils.DataState
import com.developnetwork.task.common.utils.UserPreference
import com.developnetwork.task.databinding.FragmentHomeBinding
import com.developnetwork.task.home.adapter.ProductAdapter
import com.developnetwork.task.home.dialogs.ProductClickDialog
import com.developnetwork.task.home.model.ProductItem
import com.developnetwork.task.home.viewmodel.HomeViewModel

class HomeFragment : BaseFragment() {
    private lateinit var binding: FragmentHomeBinding
    private val vm: HomeViewModel by viewModels()
    private val productAdapter by lazy {
        ProductAdapter(productClickCallBack)
    }

    private val productClickCallBack: (product: ProductItem) -> Unit = { product ->
        val dialog = ProductClickDialog(product)
        dialog.show(
            requireActivity().supportFragmentManager,
            "ProductClickDialog"
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribeData()
        setBtnListeners()
    }

    private fun setBtnListeners() {
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                productAdapter.filter.filter(newText)
                return false
            }
        })

        binding.logOut.setOnClickListener {
            UserPreference.setLoginState(false)
            UserPreference.signOut()
            findNavController().navigate(R.id.HomeFragment_to_LoginFragment)
        }
    }

    private fun subscribeData() {
        vm.getAllProductsLive.observe(viewLifecycleOwner) {
            it.let {
                when (it) {
                    is DataState.Loading -> {
                        showProgressView()
                    }
                    is DataState.Success -> {
                        binding.rvProducts.apply {
                            adapter = productAdapter
                            productAdapter.setData(it.data!!)
                        }
                        hideProgressView()
                    }
                    is DataState.ErrorMessage -> {
                        showToast(it.error)
                        hideProgressView()
                    }
                    is DataState.Finished -> {
                        hideProgressView()
                    }
                    else -> {
                        hideProgressView()
                    }
                }

            }
        }
    }

    override fun onStart() {
        super.onStart()
        vm.getAllProducts()
    }


}