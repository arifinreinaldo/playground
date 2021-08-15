package com.explore.playground.navigator


import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.FragmentNavigatorExtras
import com.explore.playground.R
import com.explore.playground.base.BaseFragment
import com.explore.playground.utils.load
import kotlinx.android.synthetic.main.fragment_home.*

/**
 * A simple [Fragment] subclass.
 */
class HomeFragment : BaseFragment() {
    override fun setLayoutId(): Int {
        return R.layout.fragment_home
    }

    override fun setInitialAsset() {
        btPencet.load("https://i.pinimg.com/564x/72/e3/29/72e3297ef891c59f9cc57a5452eaece9.jpg")
        btPencet.setOnClickListener {
            val extras = FragmentNavigatorExtras(
                btPencet to "jojo"
            )
            val values = "https://i.pinimg.com/564x/72/e3/29/72e3297ef891c59f9cc57a5452eaece9.jpg"
            val action =
                HomeFragmentDirections.homeToAnother(values)
            it.findNavController().navigate(action, extras)
        }
    }

    override fun setListener() {

    }

    override fun removeListener() {

    }
}
