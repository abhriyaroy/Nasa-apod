package com.abhriyaroy.nasaapod.ui.loading

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.abhriyaroy.nasaapod.R
import com.abhriyaroy.nasaapod.data.entity.PodEntity
import com.abhriyaroy.nasaapod.databinding.FragmentLoadingBinding
import com.abhriyaroy.nasaapod.util.Status.*
import com.abhriyaroy.nasaapod.viewmodel.PodViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_loading.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class LoadingFragment : Fragment() {

    private var _binding: FragmentLoadingBinding? = null
    private val binding get() = _binding!!
    private lateinit var date: String
    private val podViewModel: PodViewModel by sharedViewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoadingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observePodData()
        podViewModel.getPod(date)
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }

    private fun observePodData() {
        podViewModel.podResultData.observe(viewLifecycleOwner, Observer { movies ->
            when (movies.status) {
                LOADING -> binding.lottieView.playAnimation()
                SUCCESS -> showPodData(movies.data!!)
                ERROR -> showErrorMessageWithRetryOption()
            }
        })
    }

    private fun showPodData(podEntity: PodEntity) {
        println("show pod data $podEntity")
    }

    private fun showErrorMessageWithRetryOption() {
        Snackbar.make(coordinatorSplash, R.string.failed_to_load_pod, Snackbar.LENGTH_INDEFINITE)
            .apply {
                setAction(R.string.retry) { podViewModel.getPod(date) }
                show()
            }
    }

    companion object {
        fun newInstance(date: String = "") =
            LoadingFragment().apply {
                this.date = date
            }
    }
}
