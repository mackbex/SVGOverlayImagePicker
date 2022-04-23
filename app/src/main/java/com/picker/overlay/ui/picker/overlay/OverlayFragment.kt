package com.picker.overlay.ui.picker.overlay

import android.graphics.drawable.PictureDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.caverock.androidsvg.SVG
import com.picker.overlay.databinding.FragmentPhotoOverlayBinding
import com.picker.overlay.util.RequiredPermissions
import com.picker.overlay.util.wrapper.OverlayResult
import com.picker.overlay.util.wrapper.Resource
import com.picker.overlay.util.autoCleared
import com.picker.overlay.util.deco.ResourceListDecoration
import com.picker.overlay.util.isPermissionGranted
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

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

            rcOverlayList.apply {
                adapter = OverlayAdapter().apply {
                    setPostInterface { item, binding ->
                        binding.root.setOnClickListener {
                            //이미지보다 리소스 크기가 클 경우, 이미지뷰 최대 크기를 이미지 크기로 변경.
                            if(imgSvg.measuredWidth > imgOriginal.drawable.intrinsicWidth || imgSvg.measuredHeight > imgOriginal.drawable.intrinsicHeight) {
                                val modifiedSize = if(imgOriginal.drawable.intrinsicWidth > imgOriginal.drawable.intrinsicHeight) imgOriginal.drawable.intrinsicHeight else imgOriginal.drawable.intrinsicWidth
                                imgSvg.layoutParams.width = modifiedSize
                                imgSvg.layoutParams.height = modifiedSize
                            }
                            imgSvg.setImageDrawable(PictureDrawable(SVG.getFromAsset(requireContext().assets, item).renderToPicture()))
                            imgSvg.tag = item
                        }
                    }
                }
                addItemDecoration(ResourceListDecoration(requireContext()))
            }

            navBack.setOnClickListener {
                findNavController().navigateUp()
            }
        }

        initStates()

        requestStoragePermission.launch(RequiredPermissions.STORAGE_PERMISSIONS)
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
            viewModel.getOverlayResources("svg")
        }
        else {
            //권한이 없으면 메인화면으로 이동.
            findNavController().navigate(OverlayFragmentDirections.actionOverlayFragmentToAlbumListFragment())
        }
    }

    /**
     * state collector 선언
     */
    private fun initStates() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.overlayResourcesState.collect {
                        when(it) {
                            is Resource.Failure -> {
                                Toast.makeText(requireContext(), it.msg, Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                }
                launch {
                    viewModel.overlayImagesState.collect {
                        when(it) {
                            is OverlayResult.Failure -> {
                                Toast.makeText(requireContext(), it.msg, Toast.LENGTH_SHORT).show()
                                //이미지 미선택의 경우엔 다시 init 상태로 전환
                                if(binding.imgSvg.drawable == null) {
                                    viewModel.overlayImagesState.value = OverlayResult.Init
                                }
                                //오버레이 실패 시, 뒤로 이동.
                                else {
                                    findNavController().navigateUp()
                                }
                            }
                            is OverlayResult.Success -> {
                                findNavController().navigate(OverlayFragmentDirections.actionOverlayFragmentToAlbumListFragment())
                            }
                        }
                    }
                }
            }
        }
    }
}