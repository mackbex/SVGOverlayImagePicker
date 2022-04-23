package com.picker.overlay.ui.picker.album

import android.content.res.Resources
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.google.android.material.tabs.TabLayoutMediator
import com.picker.overlay.R
import com.picker.overlay.SharedViewModel
import com.picker.overlay.databinding.FragmentAlbumsBinding
import com.picker.overlay.util.autoCleared
import com.picker.overlay.util.wrapper.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AlbumsFragment: Fragment() {

    private var binding : FragmentAlbumsBinding by autoCleared()
    private val viewModel: AlbumsViewModel by viewModels()
    private val sharedViewModel: SharedViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ):View {
        binding = FragmentAlbumsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        with(binding) {
            viewModel = this@AlbumsFragment.viewModel
            lifecycleOwner = viewLifecycleOwner

            pagerAlbum.adapter = AlbumsAdapter(this@AlbumsFragment)

            TabLayoutMediator(tabAlbum, pagerAlbum) { tab, position ->
                tab.text = when(position) {
                    0-> { getString(R.string.title_tab_my_albums)}
                    else -> { throw Resources.NotFoundException("No position found.")}
                }
            }.attach()
        }
    }
}