package com.picker.overlay.ui.picker.album.list

import android.content.res.Resources
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.material.snackbar.Snackbar
import com.picker.overlay.databinding.FragmentAlbumListBinding
import com.picker.overlay.domain.model.PhotoAlbum
import com.picker.overlay.util.autoCleared
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AlbumListFragment: Fragment() {
    private var binding : FragmentAlbumListBinding by autoCleared()
    private val viewModel: AlbumListViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ):View {
        binding = FragmentAlbumListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        with(binding) {
            viewModel = this@AlbumListFragment.viewModel
            lifecycleOwner = viewLifecycleOwner

            rcAlbumList.adapter = AlbumListAdapter().apply {
                setPostInterface { item, binding ->
                    when(item) {
                        is PhotoAlbum -> {
                            binding.root.setOnClickListener {
                                Snackbar.make(it, item.title ?: run { "" }, Snackbar.LENGTH_SHORT).show()
                            }
                        }
                    }

//                    Snackbar.make(view, item.title, Snackbar.LENGTH_SHORT).show()
                }
            }
        }

        viewModel.getAlbumList()
    }
}