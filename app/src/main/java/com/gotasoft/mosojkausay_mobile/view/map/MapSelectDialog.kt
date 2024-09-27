package com.gotasoft.mosojkausay_mobile.view.map

import android.annotation.SuppressLint
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapsInitializer
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.gotasoft.mosojkausay_mobile.databinding.DialogMapSelectBinding
import com.gotasoft.mosojkausay_mobile.model.entities.response.ParticipanteResponse
import dev.matt2393.utils.location.LocPermission

class MapSelectDialog: DialogFragment(), OnMapReadyCallback {

    private var googleMap: GoogleMap? = null

    private var locationCallback: LocationCallback? = null
    private var marker: Marker? = null
    companion object {
        val TAG = MapSelectDialog::class.java.name
        private const val PARTICIPANTE = "PARTICIPANTE"
        fun newInstance(participante: ParticipanteResponse?, result: (lat: Double, lng: Double) -> Unit) = MapSelectDialog().apply {
            arguments = Bundle().apply {
                putParcelable(PARTICIPANTE, participante)
            }
            onResultDialog = object: OnResultDialog {
                override fun resultDialog(lat: Double, lng: Double) {
                    result.invoke(lat, lng)
                }
            }
        }
    }

    interface OnResultDialog {
        fun resultDialog(lat: Double, lng: Double)
    }

    private var onResultDialog: OnResultDialog? = null
    private var binding: DialogMapSelectBinding? = null
    private var participante: ParticipanteResponse? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        LocPermission.init(this)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val alert = AlertDialog.Builder(requireContext())
        binding = DialogMapSelectBinding.inflate(requireActivity().layoutInflater, null, false)
        alert.setView(binding?.root)


        arguments?.let {
            participante = it.getParcelable(PARTICIPANTE)
        }

        binding?.mapViewSelect?.onCreate(savedInstanceState)
        binding?.mapViewSelect?.getMapAsync(this)
        MapsInitializer.initialize(requireContext())

        binding?.buttonSelect?.setOnClickListener {
            if (marker!=null) {
                onResultDialog?.resultDialog(marker!!.position.latitude, marker!!.position.longitude)
                dismiss()
            } else {
                Toast.makeText(requireContext(), "No se selecciono una ubicación", Toast.LENGTH_SHORT).show()
            }
        }

        return alert.create()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    @SuppressLint("MissingPermission")
    override fun onMapReady(p0: GoogleMap) {
        googleMap = p0
        if (participante != null) {
            if(!participante!!.latitud.isNullOrEmpty() && !participante!!.longitud.isNullOrEmpty()) {
                val latLng = LatLng(participante!!.latitud!!.toDouble(), participante!!.longitud!!.toDouble())
                addMarker(latLng)
                googleMap?.moveCamera(
                    CameraUpdateFactory.newLatLngZoom(
                        latLng, 17f
                    )
                )
            }
        }
        LocPermission.launch(
            success = {
                googleMap?.isMyLocationEnabled = true
            },
            error = {

            }
        )
        googleMap?.setOnMapClickListener {
            addMarker(it)
        }
    }


    override fun onResume() {
        super.onResume()
        binding?.mapViewSelect?.onResume()
    }

    override fun onStart() {
        super.onStart()
        binding?.mapViewSelect?.onStart()
        if(locationCallback!=null) {
            LocationServices.getFusedLocationProviderClient(requireContext())
                .removeLocationUpdates(locationCallback!!)
        }
    }

    override fun onPause() {
        super.onPause()
        binding?.mapViewSelect?.onPause()
    }

    override fun onStop() {
        super.onStop()
        binding?.mapViewSelect?.onStop()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        binding?.mapViewSelect?.onLowMemory()
    }

    override fun onDestroy() {
        binding?.mapViewSelect?.onDestroy()
        super.onDestroy()
    }

    private fun addMarker(latLng: LatLng) {
        if (marker != null) {
            marker?.position = latLng
        } else {
            marker = googleMap?.addMarker(
                MarkerOptions().position(latLng)
            )
        }
    }
}