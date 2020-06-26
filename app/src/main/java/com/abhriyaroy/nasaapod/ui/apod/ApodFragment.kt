package com.abhriyaroy.nasaapod.ui.apod

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.abhriyaroy.nasaapod.R

class ApodFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_apod, container, false)
    }

    companion object {

        fun newInstance(param1: String, param2: String) =
            ApodFragment().apply {

            }
    }
}
