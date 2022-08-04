package com.developnetwork.task.auth.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.developnetwork.task.common.utils.DataState
import com.developnetwork.task.common.utils.UserPreference

class AuthViewModel : ViewModel() {

    private val _getLoginState: MutableLiveData<DataState<Void>> =
        MutableLiveData()
    val getLoginStateLive: LiveData<DataState<Void>>
        get() = _getLoginState

    private val _getRegisterState: MutableLiveData<DataState<Void>> =
        MutableLiveData()
    val getRegisterStateLive: LiveData<DataState<Void>>
        get() = _getRegisterState

    fun login() {
        _getLoginState.postValue(DataState.Loading)
        saveUserToken("WMDPWSNRIFMSHSKSDLFSHWNWKDJDHSKSLSDSFJHSDFHJA")
        UserPreference.setLoginState(true)
        _getLoginState.postValue(DataState.Finished)
    }

    fun register() {
        _getRegisterState.postValue(DataState.Loading)
        saveUserToken("WMDPWSNRIFMSHSKSDLFSHWNWKDJDHSKSLSDSFJHSDFHJA")
        UserPreference.setLoginState(true)
        _getRegisterState.postValue(DataState.Finished)
    }

    private fun saveUserToken(token: String) {
        UserPreference.saveUserToken(token)
    }


}