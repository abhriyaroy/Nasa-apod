package com.abhriyaroy.nasaapod.ui.apod

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.abhriyaroy.nasaapod.R
import com.abhriyaroy.nasaapod.data.entity.MediaType.IMAGE
import com.abhriyaroy.nasaapod.data.entity.MediaType.VIDEO
import com.abhriyaroy.nasaapod.data.entity.PodEntity
import com.abhriyaroy.nasaapod.databinding.FragmentApodBinding
import com.abhriyaroy.nasaapod.ui.BaseFragment
import com.abhriyaroy.nasaapod.util.ImageLoader
import com.abhriyaroy.nasaapod.util.Serializer
import com.abhriyaroy.nasaapod.util.drawableRes
import com.abhriyaroy.nasaapod.util.visible
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import org.koin.android.ext.android.inject


const val APOD_FRAGMENT_NAME = "ApodFragment"

class ApodFragment : BaseFragment() {

    private val serializer: Serializer by inject()
    private val imageLoader: ImageLoader by inject()
    private val apodDataKey = "apod_data"
    private val binding get() = _binding!!
    private lateinit var podEntity: PodEntity
    private var _binding: FragmentApodBinding? = null
    private var videoPlayer: YouTubePlayer? = null
    private var isInFullScreenMode = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentApodBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        parseArgs()
        decorateView()
        attachClickListener()
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }

    override fun handleBackPress() {
        if (isInFullScreenMode) {
            handleFullScreenModeBackPress()
        } else {
            showPreviosuScreen()
        }
    }

    private fun parseArgs() {
        podEntity = requireArguments().getString(apodDataKey)!!.let {
            serializer.getObjFromString(it, PodEntity::class.java)
        }
    }

    private fun decorateView() {
        binding.titleTextView.text = podEntity.title
        binding.descriptionTextView.text = podEntity.description
        when (podEntity.mediaType) {
            IMAGE -> decorateImageView()
            VIDEO -> decorateVideoView()
        }
    }

    private fun decorateImageView() {
        imageLoader.loadImage(requireContext(), binding.assetImageView, podEntity.mediaUrl)
        binding.assetActionImageView.setImageDrawable(requireContext().drawableRes(R.drawable.ic_zoom))
        binding.assetImageView.visible()
    }

    private fun decorateVideoView() {
        binding.assetVideoPlayer.visible()
        binding.assetActionImageView.setImageDrawable(requireContext().drawableRes(R.drawable.ic_play))
        setupVideoPlayer()
    }

    private fun setupVideoPlayer() {
        lifecycle.addObserver(binding.assetVideoPlayer)
        binding.assetVideoPlayer.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
            override fun onReady(youTubePlayer: YouTubePlayer) {
                youTubePlayer.loadVideo(getVideoIdFromUrl(), 0f)
                youTubePlayer.pause()
                videoPlayer = youTubePlayer
            }
        })
    }

    private fun getVideoIdFromUrl(): String {
        return podEntity.mediaUrl.split("/").let {
            it[it.size - 1].split("?")[0]

        }
    }

    private fun attachClickListener() {
        binding.assetActionButtonWrapper.setOnClickListener {
            isInFullScreenMode = true
            when (podEntity.mediaType) {
                IMAGE -> {
                    animateViewsOutOfScreen()
                }
                VIDEO -> {
                    animateViewsOutOfScreen()
                    binding.assetVideoPlayer.enterFullScreen()
                    videoPlayer?.play()
                }
            }
        }
    }

    private fun animateViewsOutOfScreen() {
        binding.titleTextView.animate().translationY(-1000f)
        binding.calendarWrapperView.animate().translationY(-1000f)
        binding.descriptionTextView.animate().translationY(10000f)
        binding.assetActionButtonWrapper.animate().translationY(1000f)
    }

    private fun animateViewsBackToOriginalPosition() {
        binding.titleTextView.animate().translationY(0f)
        binding.calendarWrapperView.animate().translationY(0f)
        binding.descriptionTextView.animate().translationY(0f)
        binding.assetActionButtonWrapper.animate().translationY(0f)
    }

    private fun handleFullScreenModeBackPress() {
        isInFullScreenMode = false
        animateViewsBackToOriginalPosition()
        when (podEntity.mediaType) {
            IMAGE -> {
            }
            VIDEO -> {
                videoPlayer?.pause()
                binding.assetVideoPlayer.exitFullScreen()
            }
        }
    }

    companion object {

        fun newInstance(podData: PodEntity) =
            ApodFragment().apply {
                arguments = Bundle().apply {
                    putString(apodDataKey, serializer.getStringFromObj(podData))
                }
            }
    }
}
