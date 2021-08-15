package com.explore.playground.navigator


import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import androidx.transition.TransitionInflater
import com.explore.playground.R
import com.explore.playground.base.BaseFragment
import com.explore.playground.utils.load
import com.explore.playground.utils.write
import kotlinx.android.synthetic.main.fragment_another.*

/**
 * A simple [Fragment] subclass.
 */
class AnotherFragment : BaseFragment() {
    override fun setLayoutId(): Int {
        return R.layout.fragment_another
    }

    override fun setInitialAsset() {
        val args: AnotherFragmentArgs by navArgs()
        sharedElementEnterTransition =
            TransitionInflater.from(context).inflateTransition(android.R.transition.move)
        ivLandscape.load(args.id)
        val flow = args.id
        write(flow)
    }

    override fun setListener() {

    }

    override fun removeListener() {
    }

}
