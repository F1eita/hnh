package com.example.lesson_3_zhuravleva.presentation.ui.settings

import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.core.view.isVisible
import androidx.fragment.app.setFragmentResultListener
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.lesson_3_zhuravleva.BuildConfig
import com.example.lesson_3_zhuravleva.R
import com.example.lesson_3_zhuravleva.databinding.FragmentSettingsBinding
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Date

class SettingsFragment : Fragment() {

    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!

    private var cameraResultLauncher: ActivityResultLauncher<Uri>? = null
    private var galleryResultLauncher: ActivityResultLauncher<String>? = null

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) {}

    private var imageByCameraUri: Uri? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        requireActivity().window.statusBarColor = resources.getColor(R.color.gray)
        cameraResultLauncher = registerForActivityResult(
            ActivityResultContracts.TakePicture()
        ){ success ->
            if (success) setPhoto(imageByCameraUri)
        }
        galleryResultLauncher = registerForActivityResult(
            ActivityResultContracts.GetContent()
        ){
            val imageUri = it
            setPhoto(imageUri)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }

        setFragmentResultListener(OCCUPATION_REQUEST_KEY){ _, bundle ->
            val occupationResult = bundle.getString(OCCUPATION_BUNDLE) ?: ""
            binding.edtOccupation.setText(occupationResult)
            binding.edtAnotherOccupation.isVisible = (occupationResult == getString(R.string.other))
        }

        setFragmentResultListener(IMAGE_REQUEST_KEY){ _, bundle ->
            when (bundle.getInt(IMAGE_BUNDLE)){
                CAMERA -> takePhotoFromCamera()
                GALLERY -> choosePhotoFromGallery()
            }
        }
        binding.edtOccupation.setOnClickListener {
            findNavController().navigate(R.id.occupationBottomSheetFragment)
        }
        binding.imgProfile.apply {
            Glide.with(this)
                .load(R.drawable.profile_without_photo)
                .circleCrop()
                .into(this)
            setOnClickListener {
                findNavController().navigate(R.id.addPhotoFragment)
            }
        }
    }

    private fun choosePhotoFromGallery(){
        if ((ContextCompat.checkSelfPermission(requireContext(),
                Manifest.permission.READ_EXTERNAL_STORAGE) !=
                    PackageManager.PERMISSION_GRANTED)
        ) {
            requestPermissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
        }
        else {
            galleryResultLauncher!!.launch("image/*")
        }
    }

    private fun takePhotoFromCamera(){
        if ((ContextCompat.checkSelfPermission(requireContext(),
                Manifest.permission.CAMERA) !=
                    PackageManager.PERMISSION_GRANTED)
        ) {
            requestPermissionLauncher.launch(Manifest.permission.CAMERA)
        }
        else {
            val photoFile: File? = try {
                createImageFile()
            } catch (ex: IOException) {
                null
            }
            photoFile?.also {
                imageByCameraUri = FileProvider.getUriForFile(
                            requireContext(),
                            "${BuildConfig.APPLICATION_ID}.provider",
                            it
                )
                cameraResultLauncher!!.launch(imageByCameraUri)
            }
        }
    }

    private fun createImageFile(): File {
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val imageFileName = "JPEG_" + timeStamp + "_"
        val storageDir = requireContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(
            imageFileName, /* prefix */
            ".jpg", /* suffix */
            storageDir /* directory */
        )
    }

    private fun setPhoto(uri: Uri?){
        Glide.with(binding.imgProfile)
            .load(uri ?: R.drawable.profile_without_photo)
            .circleCrop()
            .into(binding.imgProfile)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object{
        const val OCCUPATION_REQUEST_KEY = "occupation_key"
        const val OCCUPATION_BUNDLE = "occupation_bundle"
        const val IMAGE_REQUEST_KEY = "image_key"
        const val IMAGE_BUNDLE = "image_bundle"
        const val GALLERY = 1
        const val CAMERA = 2
    }
}