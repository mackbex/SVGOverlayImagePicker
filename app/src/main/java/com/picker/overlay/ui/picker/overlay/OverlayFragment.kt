package com.picker.overlay.ui.picker.overlay

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import com.picker.overlay.databinding.FragmentPhotoOverlayBinding
import com.picker.overlay.ui.picker.photo.PhotoPickerFragmentDirections
import com.picker.overlay.util.autoCleared
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OverlayFragment:Fragment() {

    private var binding : FragmentPhotoOverlayBinding by autoCleared()
    private val viewModel: OverlayViewModel by viewModels()
    private val args: OverlayFragmentArgs by navArgs()

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
                        Snackbar.make(it, item, Snackbar.LENGTH_SHORT).show()
                    }
                }
            }

            navBack.setOnClickListener {
                findNavController().navigateUp()
            }
        }
    }
}