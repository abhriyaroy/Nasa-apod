package com.abhriyaroy.nasaapod.ui

import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.abhriyaroy.nasaapod.databinding.ActivityMainBinding
import com.abhriyaroy.nasaapod.ui.loading.LoadingFragment

class MainActivity : BaseActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        enableFullScreenMode()
        super.onCreate(savedInstanceState)
        bindLayout()
        showLoadingFragment()
    }

    override fun onBackPressed() {
        (getFragmentAtStackTop() as BaseFragment).handleBackPress()
    }

    private fun enableFullScreenMode(){
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
    }

    private fun bindLayout() {
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    private fun showLoadingFragment() {
        supportFragmentManager.beginTransaction()
            .apply {
                replace(binding.rootFragmentHolder.id, LoadingFragment.newInstance())
                commit()
            }
    }
}
