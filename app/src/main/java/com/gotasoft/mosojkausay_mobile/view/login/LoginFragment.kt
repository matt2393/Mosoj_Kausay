package com.gotasoft.mosojkausay_mobile.view.login

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.gotasoft.mosojkausay_mobile.*
import com.gotasoft.mosojkausay_mobile.databinding.FragmentLoginBinding
import com.gotasoft.mosojkausay_mobile.model.entities.request.FcmReq
import com.gotasoft.mosojkausay_mobile.model.entities.request.LoginRequest
import com.gotasoft.mosojkausay_mobile.utils.*
import com.gotasoft.mosojkausay_mobile.view.home.HomeActivity
import com.gotasoft.mosojkausay_mobile.view.load.LoadDialog

class LoginFragment: Fragment() {
    private val viewModel: LoginViewModel by viewModels()
    private var loadDialog: LoadDialog? = null
    private var bind: FragmentLoginBinding? = null
    companion object {
        val TAG = LoginFragment::class.java.name
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        bind = FragmentLoginBinding.inflate(inflater, container, false)
        bind?.editUserLogin?.setText(DEFAULT_USER_LOGIN)
        bind?.editPasswordLogin?.setText(DEFAULT_PASSWORD_LOGIN)
        bind?.buttonLogin?.setOnClickListener {
            val user = bind?.editUserLogin?.text.toString()
            val pass = bind?.editPasswordLogin?.text.toString()
            viewModel.login(LoginRequest(user, pass))
        }
        loadDialog = LoadDialog().apply {
            isCancelable = false
        }
        bind?.textVersionLogin?.text = getString(R.string.version, BuildConfig.VERSION_NAME)

        flows()

        return bind?.root
    }

    private fun flows() {
        lifecycleScope.launchWhenStarted {
            viewModel.loginState.collect {
                when(it){
                    is StateData.Loading -> {
                        loadDialog?.show(childFragmentManager, LoadDialog.TAG)
                    }
                    is StateData.Success -> {
                        loadDialog?.dismiss()
                        if(it.data.success) {
                            setToken(requireContext(), it.data.token)
                            Tools.getFCMToken { tokenFCM ->
                                if (tokenFCM.isNotEmpty()) {
                                    viewModel.editFCM(
                                        "Bearer ${it.data.token}", FcmReq(tokenFCM)
                                    )
                                }
                                TOKEN = it.data.token
                                val dataToken = it.data.token.decodeJWT()
                                val tipoPersonal = if (dataToken.size > 1) {
                                    val tokenR = dataToken[1].fromJsonToken()
                                    when (tokenR.rol) {
                                        US_ADMIN -> TipoPersonal.ADMIN
                                        US_PATROCINIO, US_FACILITADOR, US_TECNICO -> TipoPersonal.TECNICO
                                        else -> TipoPersonal.TECNICO
                                    }
                                } else TipoPersonal.TECNICO
                                startActivity(
                                    Intent(requireContext(), HomeActivity::class.java)
                                        .putExtra(HomeActivity.TIPO, TipoIngreso.PERSONAL.name)
                                        .putExtra(HomeActivity.TIPO_PERSONAL, tipoPersonal.name)
                                )
                                Toast.makeText(requireContext(), it.data.message, Toast.LENGTH_SHORT).show()
                                requireActivity().finish()
                            }
                        } else {
                            messError(it.data.message)
                        }


                    }
                    is StateData.Error -> {
                        loadDialog?.dismiss()
                        messError("Error...")
                    }
                    else -> { }
                }
            }
        }
    }
    private fun messError(mess: String) {
        AlertDialog.Builder(requireContext())
            .setTitle("Error")
            .setMessage(mess)
            .setPositiveButton("Esta bien") { d, _ ->
                bind?.editUserLogin?.setText("")
                bind?.editPasswordLogin?.setText("")
                bind?.editUserLogin?.requestFocus()
            }
            .show()
    }
}