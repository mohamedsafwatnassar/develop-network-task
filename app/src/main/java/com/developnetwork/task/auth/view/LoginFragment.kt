package com.developnetwork.task.auth.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.developnetwork.task.R
import com.developnetwork.task.ViewsManager
import com.developnetwork.task.auth.viewmodel.AuthViewModel
import com.developnetwork.task.common.base.BaseFragment
import com.developnetwork.task.common.utils.DataState
import com.developnetwork.task.databinding.FragmentLoginBinding

class LoginFragment : BaseFragment() {

    private lateinit var binding: FragmentLoginBinding
    private lateinit var phoneNumber: String
    private lateinit var password: String
    private val vm: AuthViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireActivity() as ViewsManager).hideProgressBar()
        setBtnListeners()
        subscribeData()
    }

    private fun setBtnListeners() {
        binding.btnLogin.setOnClickListener {
            if (validate()) {
                vm.login()
            }
        }

        binding.txtSignUp.setOnClickListener {
            findNavController().navigate(R.id.loginFragment_to_registerFragment)
        }
    }


    private fun subscribeData() {
        vm.getLoginStateLive.observe(viewLifecycleOwner) {
            it.let {
                when (it) {
                    is DataState.Loading -> {
                        showProgressView()
                    }
                    is DataState.ErrorMessage -> {
                        showToast(it.error)
                        hideProgressView()
                    }
                    is DataState.Finished -> {
                        hideProgressView()
                        findNavController().navigate(LoginFragmentDirections.loginFragmentToHomeFragment())
                    }
                    else -> {}
                }

            }
        }
    }

    private fun validate(): Boolean {
        var isValid = true
        password = binding.edtPassword.text.toString().trim()
        phoneNumber = binding.edtPhoneNumber.text.toString().trim()

        if (phoneNumber.isEmpty()) {
            binding.edtPhoneNumber.error = getString(R.string.required)
            isValid = false
        } else if (phoneNumber.length < 11) {
            binding.edtPhoneNumber.error = getString(R.string.should_be_phone_with_11_number)
            isValid = false
        }
        if (password.isEmpty()) {
            binding.edtPassword.error = getString(R.string.required)
            isValid = false
        } else if (password.length < 8) {
            binding.edtPassword.error = getString(R.string.validPassword)
            isValid = false
        }
        return isValid
    }
}