package com.abhriyaroy.nasaapod.ui.loading

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.abhriyaroy.nasaapod.R
import com.abhriyaroy.nasaapod.data.entity.PodEntity
import com.abhriyaroy.nasaapod.databinding.FragmentLoadingBinding
import com.abhriyaroy.nasaapod.exception.NoInternetException
import com.abhriyaroy.nasaapod.ui.BaseFragment
import com.abhriyaroy.nasaapod.ui.apod.APOD_FRAGMENT_NAME
import com.abhriyaroy.nasaapod.ui.apod.ApodFragment
import com.abhriyaroy.nasaapod.util.ResourceResult
import com.abhriyaroy.nasaapod.util.Status.*
import com.abhriyaroy.nasaapod.viewmodel.PodViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_loading.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import kotlin.system.exitProcess

const val LOADING_FRAGMENT_NAME = "LoadingFragment"

class LoadingFragment : BaseFragment() {

    private var _binding: FragmentLoadingBinding? = null
    private val binding get() = _binding!!
    private lateinit var dateToLoadPodFor: String
    private val podViewModel: PodViewModel by sharedViewModel()
    private val podDateKey = "pod_date"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoadingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        parseArgs()
        observePodData()
        podViewModel.getPod(dateToLoadPodFor)
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }

    override fun handleBackPress() {
        exitProcess(1)
    }

    private fun parseArgs() {
        dateToLoadPodFor = requireArguments().getString(podDateKey)!!
    }

    private fun observePodData() {
        podViewModel.podResultData.observe(viewLifecycleOwner, Observer { pod ->
            when (pod.status) {
                LOADING -> binding.lottieView.playAnimation()
                SUCCESS -> showPodData(pod.data!!)
                ERROR -> handleErrorState(pod)
            }
        })
    }

    private fun showPodData(podEntity: PodEntity) {
        showScreen(ApodFragment.newInstance(podEntity), APOD_FRAGMENT_NAME)
    }

    private fun handleErrorState(result: ResourceResult<PodEntity>) {
        if (result.error is NoInternetException) {
            showErrorMessageWithRetryOption()
        } else {
            showGenericError()
        }
    }

    private fun showErrorMessageWithRetryOption() {
        Snackbar.make(coordinatorSplash, R.string.failed_to_load_pod, Snackbar.LENGTH_INDEFINITE)
            .apply {
                setAction(R.string.retry) { podViewModel.getPod(dateToLoadPodFor) }
                show()
            }
    }

    private fun showGenericError() {
        Snackbar.make(coordinatorSplash, R.string.something_went_wrong, Snackbar.LENGTH_INDEFINITE)
    }

    companion object {
        fun newInstance(year: Int? = null, month: Int? = null, dayOfMonth: Int? = null) =
            LoadingFragment().apply {
                arguments = Bundle().apply {
                    if (year != null && month != null && dayOfMonth != null) {
                        putString(podDateKey, "$year-$month-$dayOfMonth")
                    } else {
                        putString(podDateKey, "")
                    }
                }
            }
    }
}
