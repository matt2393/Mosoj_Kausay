package com.gotasoft.mosojkausay_mobile.view.participantes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.gotasoft.mosojkausay_mobile.R
import com.gotasoft.mosojkausay_mobile.databinding.ActivityParticipantesBinding
import com.gotasoft.mosojkausay_mobile.utils.TipoPersonal
import com.gotasoft.mosojkausay_mobile.view.participantes.list_participantes.ListParticipantesFragment

class ParticipantesActivity : AppCompatActivity() {
    companion object {
        const val TIPO_PERSONAL = "TipoPersonal"
    }

    private lateinit var bind: ActivityParticipantesBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityParticipantesBinding.inflate(layoutInflater)
        setContentView(bind.root)

        title = "Participantes"
        val tipoPersonal = intent?.getStringExtra(TIPO_PERSONAL) ?: TipoPersonal.TECNICO
        when (tipoPersonal) {
            TipoPersonal.ADMIN.name -> {
                supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.containerParticipantes, ParticipantesFragment(), ParticipantesFragment.TAG)
                    .commit()
            }
            TipoPersonal.TECNICO.name -> {
                supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.containerParticipantes, ListParticipantesFragment(), ListParticipantesFragment.TAG)
                    .commit()
            }
            else -> {}
        }


    }
}