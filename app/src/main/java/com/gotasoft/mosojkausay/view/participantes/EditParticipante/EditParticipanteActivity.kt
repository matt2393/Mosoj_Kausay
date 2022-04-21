package com.gotasoft.mosojkausay.view.participantes.EditParticipante

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import coil.imageLoader
import coil.load
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.gotasoft.mosojkausay.R
import com.gotasoft.mosojkausay.StateData
import com.gotasoft.mosojkausay.URL_DOWNLOAD_PHOTO_PART
import com.gotasoft.mosojkausay.databinding.ActivityEditParticipanteBinding
import com.gotasoft.mosojkausay.model.entities.request.ParticipanteRequest
import com.gotasoft.mosojkausay.model.entities.response.ParticipanteResponse
import com.gotasoft.mosojkausay.utils.FetchPath
import com.gotasoft.mosojkausay.view.load.LoadDialog
import dev.matt2393.utils.location.LocPermission
import dev.matt2393.utils.permission.ReqPermission
import kotlinx.coroutines.flow.collect
import java.io.File

class EditParticipanteActivity : AppCompatActivity() {
    private val viewModel: EditParticipanteViewModel by viewModels()
    private lateinit var binding: ActivityEditParticipanteBinding

    private var locationCallback: LocationCallback? = null
    private var lat = "0.0"
    private var lon = "0.0"
    private var loadDialog: LoadDialog? = null
    companion object {
        const val PART = "PART"
    }
    private var arrayGenero = arrayListOf(
        "VARON", "MUJER", "OTRO")
    private var arrayCursos = arrayListOf(
        "PRIMERO", "SEGUNDO", "TERCERO",
        "CUARTO", "QUINTO", "SEXTO")
    private var adapterGenero: ArrayAdapter<String>? = null
    private var adapterCursos: ArrayAdapter<String>? = null

    private var participante: ParticipanteResponse? = null
    private enum class TypePhoto {
        foto, foto_domicilio
    }
    private var currentTypePhoto = TypePhoto.foto
    private var register: ActivityResultLauncher<Intent>? =null
    private var urDataImage: Uri? = null
    @SuppressLint("MissingPermission")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditParticipanteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        LocPermission.init(this)
        ReqPermission.init(this)
        intent?.let {
            participante = it.getParcelableExtra(PART)
        }
        register = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if(it.data!=null && it.data?.data!=null) {
                val uriData = it.data?.data!!
                val path = FetchPath.getPath(this, uriData)
                val foto = File(path)
                urDataImage = uriData
                viewModel.savePhotoPart(participante!!.child_number, currentTypePhoto.name, foto)

            }
        }



        loadDialog = LoadDialog()
        loadDialog?.isCancelable = false

        adapterGenero = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, arrayGenero)
        adapterCursos = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, arrayCursos)
        with(binding) {
            editGeneroPart.setAdapter(adapterGenero)
            editCursoPart.setAdapter(adapterCursos)

            textNombreEditPart.text = participante?.nombre_completo
            textChildNumberEditPart.text = participante?.child_number
            //textNomPatroEditPart.text = participant

            editPadrePart.setText(if (participante?.padre == null) "" else participante?.padre)
            editOcupacionPadrePart.setText(if (participante?.padre_ocupacion == null) "" else participante?.padre_ocupacion)
            editCelularPadrePart.setText(if (participante?.padre_telefono == null) "" else participante?.padre_telefono)
            editMadrePart.setText(if (participante?.madre == null) "" else participante?.madre)
            editOcupacionMadrePart.setText(if (participante?.madre_ocupacion == null) "" else participante?.madre_ocupacion)
            editCelularMadrePart.setText(if (participante?.madre_telefono == null) "" else participante?.madre_telefono)
            editRefFamiliaPart.setText(if (participante?.referencia_familiar == null) "" else participante?.referencia_familiar)
            editNumRefFamiliaPart.setText(if (participante?.referencia_familiar_telefono == null) "" else participante?.referencia_familiar_telefono)
            editHijosPart.setText(if (participante?.hijos == null) "" else participante?.hijos.toString())
            editHijosAfilPart.setText(if (participante?.hijos_afiliados == null) "" else participante?.hijos_afiliados.toString())

            editLatPart.setText(if (participante?.latitud == null) "" else participante?.latitud)
            editLongPart.setText(if (participante?.longitud == null) "" else participante?.longitud)
            if(!participante?.latitud.isNullOrEmpty()) {
                lat = participante?.latitud ?: ""
            }
            if(!participante?.longitud.isNullOrEmpty()) {
                lon = participante?.longitud ?: ""
            }
            editGradoEstPart.setText(if (participante?.nivel == null) "" else participante?.nivel)
            editUEPart.setText(if (participante?.escuela == null) "" else participante?.escuela)
            editCursoPart.setText(if (participante?.curso == null) "" else participante?.curso, false)
            editTurnoPart.setText(if (participante?.turno == null) "" else participante?.turno)

            imageLoader.memoryCache.clear()
            if(!participante?.foto.isNullOrEmpty()) {
                imagePart.load("${URL_DOWNLOAD_PHOTO_PART}child_number=${participante?.child_number}&type=foto")
            }
            if(!participante?.foto_domicilio.isNullOrEmpty()) {
                imageDomicilio.load("${URL_DOWNLOAD_PHOTO_PART}child_number=${participante?.child_number}&type=foto_domicilio")
            }

            buttonLocPart.setOnClickListener {
                LocPermission.launch(
                    success = {
                        if(locationCallback!=null) {
                            val locRequest = LocationRequest.create()
                                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                                .setNumUpdates(2)
                                .setInterval(100)
                                .setFastestInterval(100)
                            LocationServices.getFusedLocationProviderClient(this@EditParticipanteActivity)
                                .requestLocationUpdates(locRequest, locationCallback!!, Looper.getMainLooper())
                        }
                    },
                    error = {
                        Toast.makeText(this@EditParticipanteActivity, "Necesita permisos o activar gps", Toast.LENGTH_SHORT).show()
                    }
                )
            }

            buttonGuardarPart.setOnClickListener {

                val part = ParticipanteRequest()

                part.padre = binding.editPadrePart.text.toString()
                part.padre_ocupacion = binding.editOcupacionPadrePart.text.toString()
                part.padre_telefono = binding.editCelularPadrePart.text.toString()
                part.madre = binding.editMadrePart.text.toString()
                part.madre_ocupacion = binding.editOcupacionMadrePart.text.toString()
                part.madre_telefono = binding.editCelularMadrePart.text.toString()
                part.referencia_familiar = binding.editRefFamiliaPart.text.toString()
                part.referencia_familiar_telefono = binding.editNumRefFamiliaPart.text.toString()
                part.hijos = if(binding.editHijosPart.text.toString().isEmpty()) "0" else binding.editHijosPart.text.toString()
                part.hijos_afiliados = if(binding.editHijosAfilPart.text.toString().isEmpty()) "0" else binding.editHijosAfilPart.text.toString()

                part.latitud = lat.toString()
                part.longitud = lon.toString()

                part.nivel = editGradoEstPart.text.toString()
                part.curso = editCursoPart.text.toString()
                part.escuela = editUEPart.text.toString()
                part.turno = editTurnoPart.text.toString()

                part.child_number = participante?.child_number!!
                part.id_village = participante?.id_village!!
                part.village = participante?.village
                part.nombre_completo = participante?.nombre_completo
                part.fecha_nacimiento = participante?.fecha_nacimiento

                viewModel.editParticipante(part)
            }

            cardImagePart.setOnClickListener {
                currentTypePhoto = TypePhoto.foto
                selectImage()
            }
            imageDomicilio.setOnClickListener {
                currentTypePhoto = TypePhoto.foto_domicilio
                selectImage()
            }

        }

        locationCallback = object : LocationCallback(){
            override fun onLocationResult(p0: LocationResult) {
                lat = p0.lastLocation.latitude.toString()
                lon = p0.lastLocation.longitude.toString()
                binding.editLatPart.setText(lat)
                binding.editLongPart.setText(lon)
            }
        }

        lifecycleScope.launchWhenStarted {
            viewModel.savePart.collect {
                when(it) {
                    is StateData.Success -> {
                        loadDialog?.dismiss()
                        Toast.makeText(this@EditParticipanteActivity, it.data.message, Toast.LENGTH_SHORT).show()
                        finish()
                    }
                    is StateData.Error -> {
                        loadDialog?.dismiss()
                        Toast.makeText(this@EditParticipanteActivity, it.error.message, Toast.LENGTH_SHORT).show()
                    }
                    is StateData.Loading -> {
                        loadDialog?.show(supportFragmentManager, "Load")
                    }
                    is StateData.None -> { }
                }
            }
        }
        lifecycleScope.launchWhenStarted {
            viewModel.savePhoto.collect {
                when(it) {
                    is StateData.Success -> {
                        loadDialog?.dismiss()
                        if(urDataImage!=null) {
                            when (currentTypePhoto) {
                                TypePhoto.foto -> {
                                    binding.imagePart.setImageURI(urDataImage)
                                }
                                TypePhoto.foto_domicilio -> {
                                    binding.imageDomicilio.setImageURI(urDataImage)
                                }
                            }
                        }
                        Toast.makeText(this@EditParticipanteActivity, "Se subio la imagen con éxito", Toast.LENGTH_SHORT).show()
                    }
                    is StateData.Error -> {
                        loadDialog?.dismiss()
                        Toast.makeText(this@EditParticipanteActivity, "No se pudo subir", Toast.LENGTH_SHORT).show()
                    }
                    StateData.Loading -> {
                        loadDialog?.show(supportFragmentManager, "Load2")
                    }
                    StateData.None -> {

                    }
                }
            }
        }
    }

    private fun selectImage() {
        ReqPermission.launch(
            permission = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE),
            result = { p->
                if(p[Manifest.permission.READ_EXTERNAL_STORAGE] == true && p[Manifest.permission.WRITE_EXTERNAL_STORAGE] == true) {
                    ImagePicker.with(this)
                        .createIntent { intent->
                            register?.launch(intent)
                        }
                } else {
                    Toast.makeText(this, "Se necesita permisos.", Toast.LENGTH_SHORT).show()
                }
            }
        )
    }
}