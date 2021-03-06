package com.picker.overlay.ui.picker.album

import android.content.res.Resources
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.material.tabs.TabLayoutMediator
import com.picker.overlay.R
import com.picker.overlay.databinding.FragmentAlbumsBinding
import com.picker.overlay.util.autoCleared
import dagger.hilt.android.AndroidEntryPoint

/**
 * 메인 fragment.
 */
@AndroidEntryPoint
class AlbumsFragment: Fragment() {

    private var binding : FragmentAlbumsBinding by autoCleared()
    private val viewModel: AlbumsViewModel by viewModels()


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

            // Tab layout과 뷰페이져 연결.
            TabLayoutMediator(tabAlbum, pagerAlbum) { tab, position ->
                tab.text = when(position) {
                    0-> { getString(R.string.title_tab_my_albums)}
                    else -> { throw Resources.NotFoundException("No position found.")}
                }
            }.attach()
        }
    }
}