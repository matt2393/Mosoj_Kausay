package com.gotasoft.mosojkausay.view.participantes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.gotasoft.mosojkausay.R
import com.gotasoft.mosojkausay.databinding.ActivityParticipantesBinding
import com.gotasoft.mosojkausay.utils.TipoPersonal
import com.gotasoft.mosojkausay.view.mess.crear_mess.CrearMess1Fragment
import com.gotasoft.mosojkausay.view.participantes.list_participantes.ListParticipantesFragment

class ParticipantesActivity : AppCompatActivity() {
    companion object {
        const val TIPO_PERSONAL = "TipoPersonal"
    }

    private lateinit var bind: ActivityParticipantesBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityParticipantesBinding.inflate(layoutInflater)
        setContentView(bind.root)

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