package com.app.bn.view.demo

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
import com.app.bn.R
import com.app.bn.data.remote.User
import com.app.bn.utils.withNetwork
import com.app.bn.view.PagingDemoActivity
import com.app.bn.viewModel.UserViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel: UserViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val splashScreen = installSplashScreen() // splash support for pre android 12 devices
        setSplashScreenDuration()
        setContentView(R.layout.activity_main)
        // setSplashExitAnimation(splashScreen) // set splash anim
        tvPaging.setOnClickListener { startActivity<PagingDemoActivity>() }
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
                        callApiGetUsers()
                        true
                    } else {
                        false
                    }
                }
            }
        )
    }

    /**
     * add animation to splash
     * */
    private fun setSplashExitAnimation(splashScreen: SplashScreen) {
        splashScreen.setOnExitAnimationListener { splashScreenView ->
            configureObjectAnimator(splashScreenView) { slideUpAnimation ->
                with(slideUpAnimation) {
                    interpolator = AnticipateInterpolator()
                    duration = 3000L
                    doOnEnd {
                        splashScreenView.remove()
                    }
                    start()
                }
            }
        }
    }

    private fun configureObjectAnimator(
        splashScreenView: SplashScreenViewProvider,
        onComplete: (ObjectAnimator) -> Unit
    ) {
        val objectAnimator = ObjectAnimator.ofFloat(
            splashScreenView.view,
            View.TRANSLATION_X,
            0f,
            -splashScreenView.view.height.toFloat()
        )
        onComplete.invoke(objectAnimator)
    }

    /**
     * api call to fetch users list
     * */
    private fun callApiGetUsers() {
        withNetwork(true) {
            viewModel.getUser(1)
        }
    }

    private fun setObserver() {
        viewModel.showError.observe(this, {
            toast(it)
        })
        viewModel.responseResult.observe(this, {
            if (it is User)
                toast("Api response :: User found ${it.name}")
        })
        viewModel.showLoading.observe(this, {
            // show hide progress bar using boolean flag
        })
    }
}