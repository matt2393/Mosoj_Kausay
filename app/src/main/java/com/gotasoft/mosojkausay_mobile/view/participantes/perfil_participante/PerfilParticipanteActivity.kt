package com.gotasoft.mosojkausay_mobile.view.participantes.perfil_participante

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import coil.load
import com.estrelladelsur.apptecnico.map.MapDialog
import com.gotasoft.mosojkausay_mobile.R
import com.gotasoft.mosojkausay_mobile.URL_DOWNLOAD_PHOTO_PART
import com.gotasoft.mosojkausay_mobile.databinding.ActivityPerfilParticipanteBinding
import com.gotasoft.mosojkausay_mobile.model.entities.response.ParticipanteResponse
import com.gotasoft.mosojkausay_mobile.view.participantes.EditParticipante.EditParticipanteActivity
import dev.matt2393.utils.location.LocPermission

class PerfilParticipanteActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPerfilParticipanteBinding
    private var lat = "0.0"
    private var lon = "0.0"
    companion object {
        const val PART = "PART"
    }
    private var participante: ParticipanteResponse? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        LocPermission.init(this)
        binding = ActivityPerfilParticipanteBinding.inflate(layoutInflater)
        intent?.let {
            participante = it.getParcelableExtra(EditParticipanteActivity.PART)
        }
        setContentView(binding.root)

        supportActionBar?.setBackgroundDrawable(getDrawable(R.color.colorPrimary))
        with(binding) {
            textNombrePerfilPart.text = participante?.nombre_completo
            textChildNumberPerfilPart.text = participante?.child_number

            editPerfilPadrePart.setText(if (participante?.padre == null) "" else participante?.padre)
            editPerfilOcupacionPadrePart.setText(if (participante?.padre_ocupacion == null) "" else participante?.padre_ocupacion)
            editPerfilCelularPadrePart.setText(if (participante?.padre_telefono == null) "" else participante?.padre_telefono)
            editPerfilMadrePart.setText(if (participante?.madre == null) "" else participante?.madre)
            editPerfilOcupacionMadrePart.setText(if (participante?.madre_ocupacion == null) "" else participante?.madre_ocupacion)
            editPerfilCelularMadrePart.setText(if (participante?.madre_telefono == null) "" else participante?.madre_telefono)
            editPerfilRefFamiliaPart.setText(if (participante?.referencia_familiar == null) "" else participante?.referencia_familiar)
            editPerfilNumRefFamiliaPart.setText(if (participante?.referencia_familiar_telefono == null) "" else participante?.referencia_familiar_telefono)
            editPerfilHijosPart.setText(if (participante?.hijos == null) "" else participante?.hijos.toString())
            editPerfilHijosAfilPart.setText(if (participante?.hijos_afiliados == null) "" else participante?.hijos_afiliados.toString())


            editPerfilNivelPart.setText(if (participante?.nivel == null) "" else participante?.nivel)
            editPerfilUEPart.setText(if (participante?.escuela == null) "" else participante?.escuela)
            editPerfilCursoPart.setText(if (participante?.curso == null) "" else participante?.curso,)
            editPerfilTurnoPart.setText(if (participante?.turno == null) "" else participante?.turno)

            if(!participante?.foto.isNullOrEmpty()) {
                imagePerfilPart.load(
                    data = "${URL_DOWNLOAD_PHOTO_PART}child_number=${participante?.child_number}&type=foto",
                    builder = {
                        listener { _, _ ->
                            binding.imagePerfilPart.visibility = View.VISIBLE
                            binding.lottiePerfilPart.visibility = View.GONE
                        }
                    }
                )
            } else {
                binding.imagePerfilPart.visibility = View.VISIBLE
                binding.lottiePerfilPart.visibility = View.GONE
            }
            if(!participante?.foto_domicilio.isNullOrEmpty()) {
                imagePerfilDomicilio.load(
                    data ="${URL_DOWNLOAD_PHOTO_PART}child_number=${participante?.child_number}&type=foto_domicilio",
                    builder = {
                        listener { _, _ ->
                            binding.imagePerfilDomicilio.visibility = View.VISIBLE
                            binding.lottiePerfilDomicilio.visibility = View.GONE
                        }
                    }
                )
            } else {
                binding.imagePerfilDomicilio.visibility = View.VISIBLE
                binding.lottiePerfilDomicilio.visibility = View.GONE
            }

            fabLocPerfilPart.setOnClickListener {
                if(participante!=null) {
                    MapDialog.newInstance(participante?.latitud?:"", participante?.longitud?:"",participante?.nombre_completo?:"")
                        .show(supportFragmentManager, MapDialog.TAG)
                }
            }
        }
    }
}