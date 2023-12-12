package com.example.lesson_3_zhuravleva.presentation.ui.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.navigation.fragment.findNavController
import com.example.lesson_3_zhuravleva.databinding.FragmentAddPhotoBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class AddPhotoFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentAddPhotoBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddPhotoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply{
            tvCamera.setOnClickListener {
                setFragmentResult(IMAGE_REQUEST_KEY, bundleOf(IMAGE_BUNDLE to CAMERA))
                findNavController().popBackStack()
            }
            tvGallery.setOnClickListener {
                setFragmentResult(IMAGE_REQUEST_KEY, bundleOf(IMAGE_BUNDLE to GALLERY))
                findNavController().popBackStack()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object{
        const val IMAGE_REQUEST_KEY = "image_key"
        const val IMAGE_BUNDLE = "image_bundle"
        const val GALLERY = 1
        const val CAMERA = 2
    }
}