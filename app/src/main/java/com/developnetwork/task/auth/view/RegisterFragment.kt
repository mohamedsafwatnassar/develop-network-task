package com.developnetwork.task.auth.view

import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.developnetwork.task.R
import com.developnetwork.task.auth.viewmodel.AuthViewModel
import com.developnetwork.task.common.base.BaseFragment
import com.developnetwork.task.common.utils.DataState
import com.developnetwork.task.databinding.FragmentRegisterBinding

class RegisterFragment : BaseFragment() {

    private lateinit var binding: FragmentRegisterBinding
    private val vm: AuthViewModel by viewModels()

    private lateinit var username: String
    private lateinit var email: String
    private lateinit var phoneNumber: String
    private lateinit var password: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setBtnListeners()
        subscribeData()
    }

    private fun setBtnListeners() {
        binding.btnSignUp.setOnClickListener {
            if (validate()) {
                vm.register()
            }
        }
    }

    private fun subscribeData() {
        vm.getRegisterStateLive.observe(viewLifecycleOwner) {
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
                        findNavController().navigate(RegisterFragmentDirections.registerFragmentToHomeFragment())
                    }
                    else -> {}
                }

            }
        }
    }


    private fun validate(): Boolean {
        var isValid = true
        username = binding.edtUserName.text.toString().trim()
        phoneNumber = binding.edtPhone.text.toString().trim()
        email = binding.edtEmail.text.toString().trim()
        password = binding.edtPassword.text.toString().trim()

        if (username.isEmpty()) {
            binding.edtUserName.error = getString(R.string.required)
            isValid = false
        }
        if (email.isEmpty()) {
            binding.edtEmail.error = getString(R.string.required)
            isValid = false
        } else if (!validEmail()) {
            binding.edtEmail.error = getString(R.string.please_enter_a_valid_email)
            isValid = false
        }
        if (phoneNumber.isEmpty()) {
            binding.edtPhone.error = getString(R.string.required)
            isValid = false
        } else if (phoneNumber.length < 11) {
            binding.edtPhone.error = getString(R.string.should_be_phone_with_11_number)
            isValid = false
        }
        if (password.isEmpty()) {
            binding.edtPassword.error = getString(R.string.required)
            isValid = false
        } else if (password.length < 8) {
            binding.edtPassword.error = getString(R.string.password_should_be_more_than_8_characters)
            isValid = false
        }
        return isValid
    }

    private fun validEmail(): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
}