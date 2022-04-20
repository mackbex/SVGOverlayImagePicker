package com.picker.overlay.ui.picker.photo

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.picker.overlay.databinding.FragmentPhotoPickerBinding
import com.picker.overlay.util.autoCleared
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PhotoPickerFragment: Fragment() {
    private var binding : FragmentPhotoPickerBinding by autoCleared()
    private val viewModel: PhotoPickerViewModel by viewModels()
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

            rcPhotoList.adapter = PhotoPickerAdapter().apply {
                listAdapter = this
                setPostInterface { item, binding ->
                    binding.root.setOnClickListener {
                        findNavController().navigate(PhotoPickerFragmentDirections.actionPhotoPickerFragmentToOverlayFragment(this@PhotoPickerFragment.viewModel.getPhotoItem(args.album.title, item)))
                    }
                }
            }

            navBack.setOnClickListener {
                findNavController().navigateUp()
            }

        }

        viewModel.getPhotoList(args.album)
    }
}