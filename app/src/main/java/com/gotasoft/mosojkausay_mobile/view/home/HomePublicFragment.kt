package com.gotasoft.mosojkausay_mobile.view.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.gotasoft.mosojkausay_mobile.databinding.FragmentHomePublicBinding


class HomePublicFragment: Fragment() {

    companion object {
        val TAG = HomePublicFragment::class.java.name
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val bind = FragmentHomePublicBinding.inflate(inflater, container, false)
        return bind.root
    }
}