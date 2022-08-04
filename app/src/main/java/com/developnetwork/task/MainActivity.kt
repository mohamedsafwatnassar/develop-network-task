package com.developnetwork.task

import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Bundle
import android.provider.Settings
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.developnetwork.task.common.services.ConnectivityReceiver
import com.developnetwork.task.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity(), ViewsManager,
    ConnectivityReceiver.ConnectivityReceiverListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private lateinit var navHostFragment: NavHostFragment

    private var isNetworkConnected: Boolean = false
    private lateinit var snackBar: Snackbar
    private var isActivityShown = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        isActivityShown = true
        initNavigationComponent()
        initSnackBar()
        registerReceivers()
    }

    private fun initNavigationComponent() {
        navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
        navController.setGraph(R.navigation.nav_graph, intent.extras)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    private fun initSnackBar() {
        snackBar = Snackbar.make(
            binding.root,
            this.resources.getString(R.string.no_internet),
            Snackbar.LENGTH_INDEFINITE
        ).setAction(getString(R.string.retry)) {
            startActivity(Intent(Settings.ACTION_WIRELESS_SETTINGS))
        }.setActionTextColor(ContextCompat.getColor(this, R.color.colorAccent))
    }

    override fun showProgressBar() {
        binding.loadingView.visibility = View.VISIBLE
    }

    override fun hideProgressBar() {
        binding.loadingView.visibility = View.GONE
    }

    private fun registerReceivers() {
        val intentFilter = IntentFilter()
        intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION)
        registerReceiver(
            ConnectivityReceiver(), intentFilter
        )
        ConnectivityReceiver.connectivityReceiverListener = this
    }

    override fun onNetworkConnectionChanged(isConnected: Boolean) {
        isNetworkConnected = isConnected
        if (isConnected && isActivityShown) {
            snackBar.dismiss()
        } else {
            if (this::snackBar.isInitialized && isActivityShown)
                snackBar.show()
        }
    }
}