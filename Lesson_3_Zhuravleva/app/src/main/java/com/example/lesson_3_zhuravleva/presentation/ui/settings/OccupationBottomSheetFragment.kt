package com.example.lesson_3_zhuravleva.presentation.ui.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.navigation.fragment.findNavController
import com.example.lesson_3_zhuravleva.databinding.FragmentOccupationBottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class OccupationBottomSheetFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentOccupationBottomSheetBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentOccupationBottomSheetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            tvDeveloper.setOnClickListener {
                onClick(tvDeveloper.text.toString())
            }
            tvManager.setOnClickListener {
                onClick(tvManager.text.toString())
            }
            tvTester.setOnClickListener {
                onClick(tvTester.text.toString())
            }
            tvOther.setOnClickListener {
                onClick(tvOther.text.toString())
            }
        }
    }

    private fun onClick(occupation: String){
        setFragmentResult(OCCUPATION_REQUEST_KEY, bundleOf(OCCUPATION_BUNDLE to occupation))
        findNavController().popBackStack()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object{
        const val OCCUPATION_REQUEST_KEY = "occupation_key"
        const val OCCUPATION_BUNDLE = "occupation_bundle"
    }

}