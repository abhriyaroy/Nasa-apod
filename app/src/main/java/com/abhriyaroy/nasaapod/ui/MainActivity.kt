package com.abhriyaroy.nasaapod.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.abhriyaroy.nasaapod.databinding.ActivityMainBinding
import com.abhriyaroy.nasaapod.ui.loading.LoadingFragment

class MainActivity : BaseActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindLayout()
        showLoadingFragment()
    }

    override fun onBackPressed() {
        (getFragmentAtStackTop() as BaseFragment).handleBackPress()
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
