package com.gotasoft.mosojkausay.view.login.init

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import com.gotasoft.mosojkausay.R
import com.gotasoft.mosojkausay.StateData
import com.gotasoft.mosojkausay.databinding.FragmentInitBinding
import com.gotasoft.mosojkausay.view.home.HomeActivity
import com.gotasoft.mosojkausay.view.login.LoginFragment
import com.gotasoft.mosojkausay.view.login.form.FormFragment
import com.gotasoft.mosojkausay.view.noticia.NoticiaActivity
import kotlinx.coroutines.flow.collect
import kotlin.math.abs

class InitFragment: Fragment() {

    private val viewModel: InitViewModel by viewModels()
    companion object {
        val TAG = InitFragment::class.java.name
    }
    private var adapter: InitAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val bind = FragmentInitBinding.inflate(inflater, container, false)

        bind.cardPersonalInit.setOnClickListener {
            requireActivity().supportFragmentManager
                .beginTransaction()
                .replace(R.id.containerLogin, LoginFragment(), LoginFragment.TAG)
                .addToBackStack(LoginFragment.TAG)
                .commit()
        }
        bind.cardPublicoInit.setOnClickListener {
            startActivity(Intent(requireContext(), NoticiaActivity::class.java))
        }
        bind.textPatrocinadorInit.setOnClickListener {
            requireActivity().supportFragmentManager
                .beginTransaction()
                .replace(R.id.containerLogin, FormFragment(), FormFragment.TAG)
                .addToBackStack(FormFragment.TAG)
                .commit()
        }
        adapter = InitAdapter()
        bind.viewPager2Init.adapter = adapter
        bind.viewPager2Init.clipToPadding = false
        bind.viewPager2Init.clipChildren = false
        bind.viewPager2Init.offscreenPageLimit = 3
        bind.viewPager2Init.getChildAt(0).overScrollMode= RecyclerView.OVER_SCROLL_NEVER
        val com =  CompositePageTransformer()
        com.addTransformer(MarginPageTransformer(40))
        com.addTransformer { page, position ->
            val r = 1 - abs(position)
            page.scaleY = 0.95f + r * 0.05f
        }
        bind.viewPager2Init.setPageTransformer(com)

        flowScopes()

        viewModel.getSlides()
        return bind.root
    }

    private fun flowScopes() {
        lifecycleScope.launchWhenStarted {
            viewModel.slides.collect {
                when(it){
                    is StateData.Success -> {
                        adapter?.arraySlides = it.data
                        adapter?.notifyItemRangeInserted(0, it.data.size)
                    }
                    is StateData.Error -> {

                    }
                    StateData.Loading -> {

                    }
                    StateData.None -> { }
                }
            }
        }
    }
}