package com.explore.playground.navigator


import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.explore.playground.R
import com.explore.playground.base.BaseFragment
import com.explore.playground.utils.write

/**
 * A simple [Fragment] subclass.
 */
class AnotherFragment : BaseFragment() {
    override fun setLayoutId(): Int {
        return R.layout.fragment_another
    }

    override fun setInitialAsset() {
        val args: AnotherFragmentArgs by navArgs()
        val flow = args.id
        write(flow)
    }

    override fun setListener() {

    }

    override fun removeListener() {
    }

}
