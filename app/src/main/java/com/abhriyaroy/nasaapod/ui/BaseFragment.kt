package com.abhriyaroy.nasaapod.ui

import androidx.fragment.app.Fragment
import com.abhriyaroy.nasaapod.R

open class BaseFragment : Fragment(){

    protected fun showScreen(fragment: Fragment, fragmentName: String) {
        activity?.supportFragmentManager
            ?.beginTransaction()
            ?.replace(R.id.rootFragmentHolder, fragment, fragmentName)
            ?.addToBackStack(fragmentName)
            ?.commit()
    }

}
