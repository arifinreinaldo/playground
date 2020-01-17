package com.explore.playground.navigator


import android.util.Log
import androidx.fragment.app.Fragment
import com.explore.playground.R
import com.explore.playground.base.BaseFragment

/**
 * A simple [Fragment] subclass.
 */
class AnotherFragment : BaseFragment() {
    override fun setLayoutId(): Int {
        return R.layout.fragment_another
    }

    override fun setInit() {
        val args = HomeFragmentDirections.homeToAnother().arguments
        val values = args.getString("id")
        Log.d("VALUE", values)
    }

}
