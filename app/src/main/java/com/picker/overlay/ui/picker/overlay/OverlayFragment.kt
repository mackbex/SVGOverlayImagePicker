package com.picker.overlay.ui.picker.overlay

import android.graphics.drawable.PictureDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.caverock.androidsvg.SVG
import com.google.android.material.snackbar.Snackbar
import com.picker.overlay.SharedViewModel
import com.picker.overlay.databinding.FragmentPhotoOverlayBinding
import com.picker.overlay.util.OverlayResult
import com.picker.overlay.util.Resource
import com.picker.overlay.util.autoCleared
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class OverlayFragment:Fragment() {

    private var binding : FragmentPhotoOverlayBinding by autoCleared()
    private val viewModel: OverlayViewModel by viewModels()
    private val args: OverlayFragmentArgs by navArgs()
    private val sharedViewModel: SharedViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPhotoOverlayBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        with(binding) {
            viewModel = this@OverlayFragment.viewModel
            model = args.photo
            lifecycleOwner = viewLifecycleOwner

            rcOverlayList.adapter = OverlayAdapter().apply {
                setPostInterface { item, binding ->
                    binding.root.setOnClickListener {
                        imgSvg.setImageDrawable(PictureDrawable(SVG.getFromAsset(requireContext().assets, item).renderToPicture()))
                    }
                }
            }

            navBack.setOnClickListener {
                findNavController().navigateUp()
            }
        }

        sharedViewModel.checkStorageAccessPermission(this@OverlayFragment, sharedViewModel.REQUIRED_STORAGE_PERMISSIONS, {
            initStates()
            viewModel.getOverlayResources("svg")
        }, {
            findNavController().navigateUp()
        })
    }

    private fun initStates() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.overlayResourcesState.collect {
                        when(it) {
                            is Resource.Failure -> {
                                Snackbar.make(binding.rcOverlayList, it.msg, Snackbar.LENGTH_SHORT).show()
                            }
                        }
                    }
                }
                launch {
                    viewModel.overlayImagesState.collect {
                        when(it) {
                            is OverlayResult.Failure -> {
                                Snackbar.make(binding.rcOverlayList, it.msg, Snackbar.LENGTH_SHORT).show()
                            }
                            is OverlayResult.Success -> {
                                findNavController().navigateUp()
                            }
                        }
                    }
                }
            }
        }
    }
}