package com.abhriyaroy.nasaapod.ui.loading

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.abhriyaroy.nasaapod.R

class LoadingFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_loading, container, false)
    }

    companion object {
        fun newInstance(param1: String, param2: String) =
            LoadingFragment().apply {

            }
    }
}
