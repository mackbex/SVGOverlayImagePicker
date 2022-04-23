package com.picker.overlay.ui.picker.photo

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
import com.google.android.material.snackbar.Snackbar
import com.picker.overlay.R
import com.picker.overlay.SharedViewModel
import com.picker.overlay.databinding.FragmentPhotoPickerBinding
import com.picker.overlay.domain.model.Photo
import com.picker.overlay.util.wrapper.Resource
import com.picker.overlay.util.autoCleared
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PhotoPickerFragment: Fragment() {
    private var binding : FragmentPhotoPickerBinding by autoCleared()
    private val viewModel: PhotoPickerViewModel by viewModels()
    private val sharedViewModel: SharedViewModel by activityViewModels()
    private val args: PhotoPickerFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ):View {
        binding = FragmentPhotoPickerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        with(binding) {
            viewModel = this@PhotoPickerFragment.viewModel
            model = args.album
            lifecycleOwner = viewLifecycleOwner

            rcPhotoList.apply {
                adapter = PhotoPickerAdapter().apply {
                    listAdapter = this
                    setPostInterface { item, binding ->
                        binding.root.setOnClickListener {
                            when(item) {
                                is Photo -> {
                                    findNavController().navigate(PhotoPickerFragmentDirections.actionPhotoPickerFragmentToOverlayFragment(
                                        item
                                    ))
                                }
                                else -> {
                                    Snackbar.make(binding.root, getString(R.string.err_no_overlay_target), Snackbar.LENGTH_SHORT).show()
                                }
                            }
                        }
                    }
                }
            }

            navBack.setOnClickListener {
                findNavController().navigateUp()
            }
        }


        sharedViewModel.checkStorageAccessPermission(this@PhotoPickerFragment, sharedViewModel.REQUIRED_STORAGE_PERMISSIONS, {
            initStates()
            viewModel.getPhotoList(args.album)
        }, {
            findNavController().navigateUp()
        })
    }

    private fun initStates() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.photoListState.collect {
                        when(it) {
                            is Resource.Success -> {
                                if(it.data.isEmpty()) {
                                    Snackbar.make(binding.root, getString(R.string.msg_no_image_found), Snackbar.LENGTH_SHORT).show()
                                }
                                else {
                                    (binding.rcPhotoList.adapter as PhotoPickerAdapter).submitList(it.data)
                                }
                            }
                            is Resource.Failure -> {
                                Snackbar.make(binding.rcPhotoList, it.msg, Snackbar.LENGTH_SHORT).show()
                            }
                        }
                    }
                }
            }
        }
    }
}