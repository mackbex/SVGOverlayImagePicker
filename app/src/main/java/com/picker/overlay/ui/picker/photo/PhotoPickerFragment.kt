package com.picker.overlay.ui.picker.photo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
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
                                    Toast.makeText(requireContext(), getString(R.string.err_no_overlay_target), Toast.LENGTH_SHORT).show()
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

        initStates()
        requestStoragePermission.launch(sharedViewModel.REQUIRED_STORAGE_PERMISSIONS)
    }

    private val requestStoragePermission = this.registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
        val requiredList = mutableListOf<String>()
        permissions.entries.forEach {
            if(!it.value) requiredList.add(it.key)
        }

        if(requiredList.size <= 0) {
            viewModel.getPhotoList(args.album)
        }
        else {
            findNavController().navigateUp()
        }
    }

    private fun initStates() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.photoListState.collect {
                        when(it) {
                            is Resource.Success -> {
                                if(it.data.isEmpty()) {
                                    Toast.makeText(requireContext(), getString(R.string.msg_no_image_found), Toast.LENGTH_SHORT).show()
                                }
                                else {
                                    (binding.rcPhotoList.adapter as PhotoPickerAdapter).submitList(it.data)
                                }
                            }
                            is Resource.Failure -> {
                                Toast.makeText(requireContext(), it.msg, Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                }
            }
        }
    }
}