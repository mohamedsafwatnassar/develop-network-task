package com.developnetwork.task.common.utils

import com.orhanobut.hawk.Hawk

object UserPreference {

    fun saveUserToken(token: String) {
        Hawk.put("token", token)
    }

    fun setLoginState(loginState: Boolean) {
        Hawk.put("LoginState", loginState)
    }

    fun getLoginState(): Boolean {
        return Hawk.get("LoginState") ?: false
    }

    fun getUserToken(): String {
        return Hawk.get<String>("token") ?: ""
    }

    fun signOut() {
        Hawk.put("token", null)
        Hawk.put("LoginState", false)
    }

}