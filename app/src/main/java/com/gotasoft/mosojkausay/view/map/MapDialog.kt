package com.estrelladelsur.apptecnico.map

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.location.Location
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapsInitializer
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PolylineOptions
import com.google.maps.android.PolyUtil
import com.gotasoft.mosojkausay.BuildConfig
import com.gotasoft.mosojkausay.StateData
import com.gotasoft.mosojkausay.databinding.DialogMapBinding
import com.gotasoft.mosojkausay.model.entities.response.ParticipanteResponse
import com.gotasoft.mosojkausay.model.entities.response.RutasMap
import com.gotasoft.mosojkausay.view.load.LoadDialog
import dev.matt2393.utils.location.LocPermission
import kotlinx.coroutines.flow.collect

class MapDialog: DialogFragment(), OnMapReadyCallback {

    private val viewModel: MapViewModel by activityViewModels()

    private var googleMap: GoogleMap? = null
    private var loadDialog: LoadDialog? = null

    private var locationCallback: LocationCallback? = null
    private var loc: Location? = null
    companion object {
        val TAG = MapDialog::class.java.name
        private const val PARTICIPANTE = "PARTICIPANTE"
        fun newInstance(part: ParticipanteResponse) = MapDialog().apply {
            arguments = Bundle().apply {
                putParcelable(PARTICIPANTE, part)
            }
        }
    }
    private var binding: DialogMapBinding? = null
    private var participante: ParticipanteResponse? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        LocPermission.init(this)
        val alert = AlertDialog.Builder(requireContext())
        binding = DialogMapBinding.inflate(requireActivity().layoutInflater, null, false)
        alert.setView(binding?.root)


        arguments?.let {
            participante = it.getParcelable(PARTICIPANTE)
        }

        binding?.mapView?.onCreate(savedInstanceState)
        binding?.mapView?.getMapAsync(this)
        MapsInitializer.initialize(requireContext())

        flowStart()
        return alert.create()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    @SuppressLint("MissingPermission")
    override fun onMapReady(p0: GoogleMap) {
        googleMap = p0
        var isLoadRuta = false
        if(participante!=null){
            if(!participante!!.latitud.isNullOrEmpty() && !participante!!.longitud.isNullOrEmpty()){
                LocPermission.launch(
                    success = {
                        val locRequest = LocationRequest.create()
                            .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                            .setNumUpdates(2)
                            .setInterval(100)
                            .setFastestInterval(100)
                        googleMap?.isMyLocationEnabled = true
                        LocationServices.getFusedLocationProviderClient(requireContext())
                            .requestLocationUpdates(locRequest, object: LocationCallback() {
                                override fun onLocationResult(p0: LocationResult) {
                                    if(!isLoadRuta) {
                                        isLoadRuta = true
                                        loc = p0.lastLocation
                                        val origen = "${loc!!.latitude},${loc!!.longitude}"
                                        val destino =
                                            "${participante!!.latitud},${participante!!.longitud}"
                                        viewModel.getRutas(
                                            origen,
                                            destino,
                                            BuildConfig.MAPS_API_KEY
                                        )
                                    }
                                }
                            }, Looper.getMainLooper())
                    },
                    error = {
                        Toast.makeText(requireContext(), "Error, necesita persmisos de localización", Toast.LENGTH_SHORT).show()
                    }
                )
            }
        }
    }


    override fun onResume() {
        super.onResume()
        binding?.mapView?.onResume()
    }

    override fun onStart() {
        super.onStart()
        binding?.mapView?.onStart()
        if(locationCallback!=null) {
            LocationServices.getFusedLocationProviderClient(requireContext())
                .removeLocationUpdates(locationCallback!!)
        }
    }

    override fun onPause() {
        super.onPause()
        binding?.mapView?.onPause()
    }

    override fun onStop() {
        super.onStop()
        binding?.mapView?.onStop()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        binding?.mapView?.onLowMemory()
    }

    override fun onDestroy() {
        binding?.mapView?.onDestroy()
        super.onDestroy()
    }

    private fun dibujar(rutas: RutasMap) {
        googleMap?.clear()
        if(participante!=null){
            if(!participante!!.latitud.isNullOrEmpty() && !participante!!.longitud.isNullOrEmpty()){
                val lat = participante!!.latitud!!.toDouble()
                val lng = participante!!.longitud!!.toDouble()
                val latLng = LatLng(lat, lng)
                googleMap?.addMarker(
                    MarkerOptions().position(latLng)
                        .title(participante!!.nombre_completo)
                        .snippet(participante!!.nombre_completo)
                )
                googleMap?.moveCamera(CameraUpdateFactory.newLatLngZoom(
                    LatLng(loc!!.latitude, loc!!.longitude), 17f
                ))

                rutas.routes.forEachIndexed { index, route ->
                    val colorP =
                        if(index == 0) Color.rgb(238, 118, 56)
                        else Color.rgb(163, 163, 163)
                    val puntos = PolyUtil.decode(route.overview_polyline.points)
                    googleMap?.addPolyline(
                        PolylineOptions().addAll(puntos)
                            .color(colorP)
                    )
                }

            }
        }
    }


    private fun flowStart() {
        lifecycleScope.launchWhenStarted {
            viewModel.rutas.collect {
                when(it) {
                    is StateData.Success -> {
                        dibujar(it.data)
                    }
                    is StateData.Error -> {
                        Log.e("MAPError",it.error.toString())
                        Toast.makeText(requireContext(), "No se pudo encontrar una ruta", Toast.LENGTH_SHORT).show()
                    }
                    is StateData.Loading -> {
                        loadDialog = LoadDialog()
                        loadDialog?.isCancelable = false
                        loadDialog?.show(childFragmentManager, "load")
                    }
                    is StateData.None -> {
                        if(loadDialog != null) {
                            loadDialog?.dismiss()
                        }
                    }
                }
            }
        }
    }
}