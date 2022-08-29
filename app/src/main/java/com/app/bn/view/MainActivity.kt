package com.app.bn.view

import android.animation.ObjectAnimator
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewTreeObserver
import android.view.animation.AnticipateInterpolator
import androidx.activity.viewModels
import androidx.core.animation.doOnEnd
import androidx.core.splashscreen.SplashScreen
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.splashscreen.SplashScreenViewProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.bn.R
import com.app.bn.adapter.ResaleClientAdapter
import com.app.bn.data.remote.BnResponse
import com.app.bn.data.remote.Card
import com.app.bn.utils.withNetwork
import com.app.bn.viewModel.UserViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.toast

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel: UserViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen() // splash support for pre android 12 devices
        setSplashScreenDuration()
        setContentView(R.layout.activity_main)

    }

    /**
     * set splash exit interval
     * */
    private fun setSplashScreenDuration() {
        val content: View = findViewById(android.R.id.content)
        content.viewTreeObserver.addOnPreDrawListener(
            object : ViewTreeObserver.OnPreDrawListener {
                override fun onPreDraw(): Boolean {
                    return if (viewModel.isDataReady()) {
                        content.viewTreeObserver.removeOnPreDrawListener(this)
                        setObserver()
                        tv.text = "List"
                        tv.textSize = 24f
                        callApiGetListData()
                        true
                    } else {
                        false
                    }
                }
            }
        )
    }


    /**
     * api call to fetch users list
     * */
    private fun callApiGetListData() {
        withNetwork(true) {
            viewModel.getClientData()
        }
    }

    private fun showProgressBar() {
        progress_bar.visibility = View.VISIBLE
    }

    private fun hideProgressBar() {
        progress_bar.visibility = View.GONE
    }

    private fun setObserver() {
        viewModel.showError.observe(this) {
            hideProgressBar()
            toast(it)
        }
        viewModel.responseResult.observe(this) {
            hideProgressBar()
            if (it is BnResponse) {
                val data = it.cards;
                rv.layoutManager = LinearLayoutManager(this)
                rv.adapter = ResaleClientAdapter(this, data as ArrayList<Card>?)
            }
        }
        viewModel.showLoading.observe(this) {
            showProgressBar()
        }
    }
}