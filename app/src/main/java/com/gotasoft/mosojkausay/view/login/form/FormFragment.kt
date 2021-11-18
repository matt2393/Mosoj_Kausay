package com.gotasoft.mosojkausay.view.login.form

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.gotasoft.mosojkausay.databinding.FragmentFormBinding

class FormFragment: Fragment() {

    companion object {
        val TAG = FormFragment::class.java.name
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val bind = FragmentFormBinding.inflate(inflater, container, false)
        return bind.root
    }
}