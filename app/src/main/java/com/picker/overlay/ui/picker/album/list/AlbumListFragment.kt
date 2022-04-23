package com.picker.overlay.ui.picker.album.list

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
import com.picker.overlay.R
import com.picker.overlay.databinding.FragmentAlbumListBinding
import com.picker.overlay.domain.model.PhotoAlbum
import com.picker.overlay.ui.picker.album.AlbumsFragmentDirections
import com.picker.overlay.util.RequiredPermissions
import com.picker.overlay.util.wrapper.Resource
import com.picker.overlay.util.autoCleared
import com.picker.overlay.util.isPermissionGranted
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

/**
 * 메인 뷰의 tab layout 안 viewpager 내부 fragment
 */
@AndroidEntryPoint
class AlbumListFragment : Fragment() {
    private var binding: FragmentAlbumListBinding by autoCleared()
    private val viewModel: AlbumListViewModel by viewModels()
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
                //아이템 클릭시의 이벤트 처리
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

        initStates()
        requestStoragePermission.launch(RequiredPermissions.STORAGE_PERMISSIONS)
    }

    override fun onResume() {
        super.onResume()
        if(isPermissionGranted(requireContext(), RequiredPermissions.STORAGE_PERMISSIONS)) {
            viewModel.getAlbumList()
        }
    }

    /**
     * 파일 관련 권한 체크.
     */
    private val requestStoragePermission = this.registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
        val requiredList = mutableListOf<String>()
        permissions.entries.forEach {
            if(!it.value) requiredList.add(it.key)
        }

        if(requiredList.size <= 0) {
            viewModel.getAlbumList()
        }
        else {
            viewModel.albumListState.value = Resource.Failure(getString(R.string.msg_storage_permission_denied))
        }
    }

    /**
     * state collector 선언
     */
    private fun initStates() {
        viewLifecycleOwner.lifecycleScope.launch{
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.albumListState.collect {
                        when(it) {
                            is Resource.Success -> {
                                if(it.data.isEmpty()) {
                                    Toast.makeText(requireContext(), getString(R.string.msg_no_image_found), Toast.LENGTH_SHORT).show()
                                }
                                else {
                                    (binding.rcAlbumList.adapter as AlbumListAdapter).submitList(it.data.values.toList())
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