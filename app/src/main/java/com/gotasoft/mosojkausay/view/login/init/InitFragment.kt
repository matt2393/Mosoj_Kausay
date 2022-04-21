package com.gotasoft.mosojkausay.view.login.init

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
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
    private var bind: FragmentInitBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        bind = FragmentInitBinding.inflate(inflater, container, false)

        with(bind!!) {
            cardPersonalInit.setOnClickListener {
                requireActivity().supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.containerLogin, LoginFragment(), LoginFragment.TAG)
                    .addToBackStack(LoginFragment.TAG)
                    .commit()
            }
            cardPublicoInit.setOnClickListener {
                startActivity(Intent(requireContext(), NoticiaActivity::class.java))
            }
            textPatrocinadorInit.setOnClickListener {
                requireActivity().supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.containerLogin, FormFragment(), FormFragment.TAG)
                    .addToBackStack(FormFragment.TAG)
                    .commit()
            }
            imageGotasoftInit.setOnClickListener {
                openWeb("https://gotasoft.com/")
            }
            imageChildfundInit.setOnClickListener {
                openWeb("https://www.childfundbolivia.org/")
            }
            imageTopInit.setOnClickListener {
                openWeb("https://mosojkausay.org/")
            }
            cardTheEndsInit.setOnClickListener {
                openWeb("https://the-ends.web.app/")
            }
            buttonInit.setOnClickListener {

                /*startActivity(
                    Intent(Intent.ACTION_VIEW, Uri.parse("http://www.regionalamericacf.org/"))
                )*/
                ModelProgDialog()
                    .show(childFragmentManager, "ModelProgDialog")
            }
            adapter = InitAdapter()
            viewPager2Init.adapter = adapter
            viewPager2Init.clipToPadding = false
            viewPager2Init.clipChildren = false
            viewPager2Init.offscreenPageLimit = 3
            viewPager2Init.getChildAt(0).overScrollMode= RecyclerView.OVER_SCROLL_NEVER
            val com =  CompositePageTransformer()
            com.addTransformer(MarginPageTransformer(40))
            com.addTransformer { page, position ->
                val r = 1 - abs(position)
                page.scaleY = 0.95f + r * 0.05f
            }
            viewPager2Init.setPageTransformer(com)
        }


        flowScopes()

        viewModel.getSlides()
        viewModel.getContador()
        return bind?.root
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
        lifecycleScope.launchWhenStarted {
            viewModel.contador.collect {
                when(it) {
                    is StateData.Success -> {
                        val cont = it.data.cantidad
                        val textCont = "$cont" + if(cont == 1) " Visita" else " Visitas"
                        bind?.textVisitas?.text = textCont
                    }
                    is StateData.Error -> {
                        Log.e(TAG, "flowScopes() contador [${it.error}]")
                    }
                    StateData.Loading -> {
                    }
                    StateData.None -> {
                    }
                }
            }
        }
    }

    private fun openWeb(url: String) {
        startActivity(
            Intent(Intent.ACTION_VIEW, Uri.parse(url))
        )
    }
}