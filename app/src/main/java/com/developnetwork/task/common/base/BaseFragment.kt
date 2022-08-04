package com.developnetwork.task.common.base

import androidx.fragment.app.Fragment
import com.developnetwork.task.ViewsManager
import com.google.android.material.snackbar.Snackbar

open class BaseFragment : Fragment() {

    protected fun showToast(text: String) {
        Snackbar.make(requireView(), text, Snackbar.LENGTH_LONG).show()
    }

    protected fun showProgressView() {
        (requireActivity() as ViewsManager).showProgressBar()
    }

    protected fun hideProgressView() {
        (requireActivity() as ViewsManager).hideProgressBar()
    }
}
