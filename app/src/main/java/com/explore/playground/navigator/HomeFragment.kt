package com.explore.playground.navigator


import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.explore.playground.R
import com.explore.playground.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_home.*

/**
 * A simple [Fragment] subclass.
 */
class HomeFragment : BaseFragment() {
    override fun setInit() {
        btPencet.setOnClickListener {
            it.findNavController().navigate(R.id.homeToAnother)
        }
    }

    override fun setLayoutId(): Int {
        return R.layout.fragment_home
    }
}
