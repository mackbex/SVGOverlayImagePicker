package com.picker.overlay.ui.picker.album.list

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
import com.google.android.material.snackbar.Snackbar
import com.picker.overlay.R
import com.picker.overlay.SharedViewModel
import com.picker.overlay.databinding.FragmentAlbumListBinding
import com.picker.overlay.domain.model.PhotoAlbum
import com.picker.overlay.ui.picker.album.AlbumsFragmentDirections
import com.picker.overlay.util.Resource
import com.picker.overlay.util.autoCleared
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AlbumListFragment : Fragment() {
    private var binding: FragmentAlbumListBinding by autoCleared()
    private val viewModel: AlbumListViewModel by viewModels()
    private val sharedViewModel: SharedViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAlbumListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        with(binding) {
            viewModel = this@AlbumListFragment.viewModel
            lifecycleOwner = viewLifecycleOwner

            rcAlbumList.adapter = AlbumListAdapter().apply {
                listAdapter = this
                setPostInterface { item, binding ->
                    when (item) {
                        is PhotoAlbum -> {
                            binding.root.setOnClickListener {
                                findNavController().navigate(
                                    AlbumsFragmentDirections.actionAlbumListFragmentToPhotoPickerFragment(
                                        item
                                    )
                                )
                            }
                        }
                    }
                }
            }
        }

        sharedViewModel.checkStorageAccessPermission(this@AlbumListFragment, sharedViewModel.REQUIRED_STORAGE_PERMISSIONS, {
            //파일 접근 권한이 주어졌을 때 collect 시작
            initStates()
            //stateIn 대신 수동으로 호출하는 이유 : Databinding으로 visibility 등 연결이 되어있기 때문에, 권한 획득 여부에 대한 분기를 stateflow 하나로 처리하기 위해 사용.
            viewModel.getAlbumList()
        }, {
            //반대의 경우, Failure
            viewModel.albumListState.value = Resource.Failure(getString(R.string.msg_storage_permission_denied))
        })
    }

    private fun initStates() {
        viewLifecycleOwner.lifecycleScope.launch{
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.albumListState.collect {
                        when(it) {
                            is Resource.Success -> {
                                if(it.data.isEmpty()) {
                                    Snackbar.make(binding.root, getString(R.string.msg_no_image_found), Snackbar.LENGTH_SHORT).show()
                                }
                                else {
                                    (binding.rcAlbumList.adapter as AlbumListAdapter).submitList(it.data.values.toList())
                                }
                            }
                            is Resource.Failure -> {
                                Snackbar.make(binding.rcAlbumList, it.msg, Snackbar.LENGTH_SHORT).show()
                            }
                        }
                    }
                }
            }
        }
    }
}