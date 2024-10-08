package com.gotasoft.mosojkausay_mobile.view.momentos_magicos.fotos_mm.foto_mm_add

import android.Manifest
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.gotasoft.mosojkausay_mobile.StateData
import com.gotasoft.mosojkausay_mobile.databinding.FragmentAddFotoMmBinding
import com.gotasoft.mosojkausay_mobile.utils.getToken
import com.gotasoft.mosojkausay_mobile.view.load.LoadDialog
import java.io.File
import android.provider.MediaStore
import com.github.dhaval2404.imagepicker.ImagePicker
import com.gotasoft.mosojkausay_mobile.utils.FetchPath
import com.gotasoft.mosojkausay_mobile.view.MessageDialog
import dev.matt2393.utils.permission.ReqPermission


class FotoMMAddFragment: Fragment() {
    private val viewModel: FotoMMAddViewModel by viewModels()
    companion object {
        val TAG = FotoMMAddFragment::class.java.name
        private const val MM_ID = "MMID"
        fun newInstance(mmId: Int) = FotoMMAddFragment().apply {
            arguments = Bundle().apply {
                putInt(MM_ID, mmId)
            }
        }
    }
    private var token = ""
    private var mm_id = 0

    private var loadDialog: LoadDialog? = null

    private var foto: File? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentAddFotoMmBinding.inflate(inflater, container, false)
        requireActivity().title = "Fotografias"
        ReqPermission.init(this)
        val register = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if(it.data!=null && it.data?.data!=null) {
                val uriData = it.data?.data!!
                binding.imageAddFotoMM.setImageURI(uriData)
                val path = FetchPath.getPath(requireContext(), uriData)
                foto = File(path)

            }
            /*if(it!=null) {
                val uriReal = getRealPathFromURI(it)
                if (uriReal!=null) {
                    val path = uriReal.path
                    foto = File(path!!)
                    binding.imageAddFotoMM.setImageURI(uriReal)
                }
            }*/

            //val contentResolver = requireActivity().contentResolver
            //val type = MimeTypeMap.getSingleton().getExtensionFromMimeType(contentResolver.getType(it!!))

            //foto = File.createTempFile()
               /* Log.e("FOTO", it.data?.data.toString())
            val resultCode = it.resultCode
            val data = it.data

            if (resultCode == Activity.RESULT_OK) {
                binding.imageAddFotoMM.setImageURI(it.data?.data)
                val fileUri = data?.data!!

                foto = fileUri.toFile()
            }*/
        }
        arguments?.let {
            mm_id = it.getInt(MM_ID, 0)
            with(binding) {
                cardAddFotoMM.setOnClickListener {
                    ReqPermission.launch(
                        permission = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE),
                        result = { p->
                            if(p[Manifest.permission.READ_EXTERNAL_STORAGE] == true && p[Manifest.permission.WRITE_EXTERNAL_STORAGE] == true) {
                                //val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                                //intent.type = "image/*"
                                //intent.action = Intent.ACTION_OPEN_DOCUMENT
                                //intent.addCategory(Intent.CATEGORY_OPENABLE)
                                //intent.flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
                                //register.launch(Intent.createChooser(intent, "Selecciones una imagen"))

                                ImagePicker.with(this@FotoMMAddFragment)
                                    .createIntent { intent->
                                        register.launch(intent)
                                    }
                                //register.launch("image/*")
                            } else {
                                Toast.makeText(requireContext(), "Se necesita permisos.", Toast.LENGTH_SHORT).show()
                            }
                        }
                    )

                }
                buttonGuardarFotoMM.setOnClickListener {
                    val des = editDescAddFotoMM.text.toString()
                    if(foto==null) {
                        Toast.makeText(requireContext(), "Seleccione una Foto", Toast.LENGTH_SHORT).show()
                        return@setOnClickListener
                    }
                    if(des.isEmpty()) {
                        Toast.makeText(requireContext(), "Escriba una descripción", Toast.LENGTH_SHORT).show()
                        return@setOnClickListener
                    }

                    viewModel.addFotos(token, des, mm_id, foto!!)

                }
            }
        }
        flowScopes()
        return binding.root
    }

    private fun flowScopes() {
        lifecycleScope.launchWhenStarted {
            getToken(requireContext()).collect {
                if(it!=null) {
                    token = "Bearer $it"
                }
            }
        }
        lifecycleScope.launchWhenStarted {
            viewModel.foto.collect {
                when(it) {
                    is StateData.Success -> {
                        val mess = MessageDialog.newInstance("Éxito", "Se guardo con éxito", "Aceptar", {
                            requireActivity().onBackPressed()
                        })
                        mess.isCancelable = false
                        mess.show(childFragmentManager, MessageDialog.TAG)
                    }
                    is StateData.Error -> {
                        val mess = MessageDialog.newInstance("Error", "Ocurrio un error inesperado, intente nuevamente", "Aceptar", { d ->
                            d.dismiss()
                        })
                        mess.isCancelable = true
                        mess.show(childFragmentManager, MessageDialog.TAG)
                    }
                    StateData.Loading -> {
                        loadDialog = LoadDialog()
                        loadDialog?.isCancelable = false
                        loadDialog?.show(childFragmentManager, LoadDialog.TAG)
                    }
                    StateData.None -> {
                        if(loadDialog!=null) {
                            loadDialog?.dismiss()
                            loadDialog = null
                        }
                    }
                }
            }
        }
    }

    private fun getRealPathFromURI(contentURI: Uri): Uri? {
        var result: Uri? = null

        val column = arrayOf(MediaStore.Images.Media._ID)
        val contentResolver = requireActivity().contentResolver
        val cur = contentResolver.query(contentURI, column, null, null, null)
        if(cur!=null) {
            val col = cur.getColumnIndexOrThrow(MediaStore.Images.Media._ID)
            while (cur.moveToNext()) {
                val id = cur.getLong(col)
                result = Uri.withAppendedPath(contentURI, id.toString())

            }


        }

        cur?.close()

        return result
    }
}