package com.developnetwork.task.auth.view

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.developnetwork.task.common.base.BaseFragment
import com.developnetwork.task.common.utils.UserPreference
import com.developnetwork.task.databinding.FragmentSplashBinding

class SplashFragment : BaseFragment() {

    private lateinit var binding: FragmentSplashBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentSplashBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        Handler(Looper.getMainLooper()).postDelayed({
            navigate()
        }, 1000)
    }

    private fun navigate() {
        if (UserPreference.getLoginState()) {
            findNavController().navigate(SplashFragmentDirections.splashFragmentToHomeFragment())
        } else findNavController().navigate(SplashFragmentDirections.splashFragmentToLoginFragment())
    }

}