package com.picker.overlay.ui.picker.photo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.picker.overlay.databinding.FragmentPhotoPickerBinding
import com.picker.overlay.util.autoCleared
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PhotoPickerFragment: Fragment() {
    private var binding : FragmentPhotoPickerBinding by autoCleared()
    private val viewModel: PhotoPickerViewModel by viewModels()

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
            lifecycleOwner = viewLifecycleOwner


        }

    }

}