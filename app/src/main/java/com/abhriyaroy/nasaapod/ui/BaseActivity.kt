package com.abhriyaroy.nasaapod.ui

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.abhriyaroy.nasaapod.ui.loading.LOADING_FRAGMENT_NAME

open class BaseActivity : AppCompatActivity() {

    protected fun getFragmentAtStackTop(): Fragment? {
        return supportFragmentManager.findFragmentByTag(getFragmentNameAtStackTop())
    }

    protected fun getFragmentNameAtStackTop() =
        if (supportFragmentManager.backStackEntryCount == 0) {
            LOADING_FRAGMENT_NAME
        } else {
            supportFragmentManager
                .getBackStackEntryAt(supportFragmentManager.backStackEntryCount - 1).name!!
        }
}
