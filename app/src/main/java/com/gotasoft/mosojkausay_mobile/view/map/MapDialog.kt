package com.estrelladelsur.apptecnico.map

import android.annotation.SuppressLint
import android.app.Dialog
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
import com.google.android.gms.maps.model.Polyline
import com.google.android.gms.maps.model.PolylineOptions
import com.google.maps.android.PolyUtil
import com.gotasoft.mosojkausay_mobile.BuildConfig
import com.gotasoft.mosojkausay_mobile.StateData
import com.gotasoft.mosojkausay_mobile.databinding.DialogMapBinding
import com.gotasoft.mosojkausay_mobile.model.entities.response.RutasMap
import com.gotasoft.mosojkausay_mobile.view.load.LoadDialog
import dev.matt2393.utils.location.LocPermission

class MapDialog : DialogFragment(), OnMapReadyCallback {

    private val viewModel: MapViewModel by activityViewModels()

    private var googleMap: GoogleMap? = null
    private var loadDialog: LoadDialog? = null

    private var locationCallback: LocationCallback? = null
    private var loc: Location? = null

    companion object {
        val TAG = MapDialog::class.java.name
        private const val LAT = "LAT"
        private const val LNG = "LNG"
        private const val NAME = "NAME"

        fun newInstance(lat: String, lng: String, name: String) = MapDialog().apply {
            arguments = Bundle().apply {
                putString(LAT, lat)
                putString(LNG, lng)
                putString(NAME, name)
            }
        }
    }

    private var binding: DialogMapBinding? = null

    private var lat: String? = null
    private var lng: String? = null
    private var name: String? = null

    private var polylines: MutableList<Polyline> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        LocPermission.init(this)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val alert = AlertDialog.Builder(requireContext())
        binding = DialogMapBinding.inflate(requireActivity().layoutInflater, null, false)
        alert.setView(binding?.root)


        arguments?.let {
            lat = it.getString(LAT)
            lng = it.getString(LNG)
            name = it.getString(NAME)
        }

        binding?.mapView?.onCreate(savedInstanceState)
        binding?.mapView?.getMapAsync(this)
        MapsInitializer.initialize(requireContext())

        flowStart()
        return alert.create()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    @SuppressLint("MissingPermission")
    override fun onMapReady(p0: GoogleMap) {
        googleMap = p0
        var isLoadRuta = false
        if (!lat.isNullOrEmpty() && !lng.isNullOrEmpty()) {
            try {
                val lat2 = lat!!.toDouble()
                val lng2 = lng!!.toDouble()
                val latLng = LatLng(lat2, lng2)
                googleMap?.addMarker(
                    MarkerOptions().position(latLng)
                        .title(name ?: "")
                        .snippet(name ?: "")
                )
                googleMap?.moveCamera(
                    CameraUpdateFactory.newLatLngZoom(
                        latLng, 17f
                    )
                )
            } catch (ex: NumberFormatException) {
                Log.e(
                    "LocationFormatError",
                    "Error al convertir latitud y longitud a Double LAT: $lat LNG: $lng"
                )
            }


            LocPermission.launch(
                success = {
                    val locRequest = LocationRequest.create()
                        .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                        .setNumUpdates(2)
                        .setInterval(100)
                        .setFastestInterval(100)
                    googleMap?.isMyLocationEnabled = true
                    LocationServices.getFusedLocationProviderClient(requireContext())
                        .requestLocationUpdates(locRequest, object : LocationCallback() {
                            override fun onLocationResult(p0: LocationResult) {
                                if (!isLoadRuta) {
                                    isLoadRuta = true
                                    loc = p0.lastLocation
                                    val origen = "${loc!!.latitude},${loc!!.longitude}"
                                    val destino =
                                        "$lat,$lng"
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
                    Toast.makeText(
                        requireContext(),
                        "Error, necesita persmisos de localización",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            )
        }
        if (loadDialog == null) {
            loadDialog = LoadDialog()
            loadDialog?.isCancelable = false
            loadDialog?.show(childFragmentManager, "load")
        }
    }


    override fun onResume() {
        super.onResume()
        binding?.mapView?.onResume()
    }

    override fun onStart() {
        super.onStart()
        binding?.mapView?.onStart()
        if (locationCallback != null) {
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
        polylines.forEach {
            it.remove()
        }
        polylines.clear()
        polylines = mutableListOf()
        rutas.routes.forEachIndexed { index, route ->
            val colorP =
                if (index == 0) Color.rgb(238, 118, 56)
                else Color.rgb(163, 163, 163)
            val puntos = PolyUtil.decode(route.overview_polyline.points)
            val poly = googleMap?.addPolyline(
                PolylineOptions().addAll(puntos)
                    .color(colorP)
            )
            if (poly != null) {
                polylines.add(poly)
            }
        }
    }


    private fun flowStart() {
        lifecycleScope.launchWhenStarted {
            viewModel.rutas.collect {
                when (it) {
                    is StateData.Success -> {
                        dibujar(it.data)
                    }
                    is StateData.Error -> {
                        Log.e("MAPError", it.error.toString())
                        Toast.makeText(
                            requireContext(),
                            "No se pudo encontrar una ruta",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    is StateData.Loading -> {
                        if (loadDialog == null) {
                            loadDialog = LoadDialog()
                            loadDialog?.isCancelable = false
                            loadDialog?.show(childFragmentManager, "load")
                        }
                    }
                    is StateData.None -> {
                        if (loadDialog != null) {
                            loadDialog?.dismiss()
                            loadDialog = null
                        }
                    }
                }
            }
        }
    }
}