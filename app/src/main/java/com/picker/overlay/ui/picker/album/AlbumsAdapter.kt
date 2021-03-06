package com.picker.overlay.ui.picker.album

import android.content.res.Resources
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.picker.overlay.ui.picker.album.list.AlbumListFragment

/**
 *  viewpager 어뎁터
 */
class AlbumsAdapter(fragment:Fragment) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int {
        return 1
    }

    override fun createFragment(position: Int): Fragment {
        return when(position) {
            0 -> { AlbumListFragment() }
            else -> { throw Resources.NotFoundException("No fragment found.")}
        }
    }
}

