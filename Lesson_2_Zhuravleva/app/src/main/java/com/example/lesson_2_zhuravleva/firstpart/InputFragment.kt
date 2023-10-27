package com.example.lesson_2_zhuravleva.firstpart

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.setFragmentResultListener
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.lesson_2_zhuravleva.R
import com.example.lesson_2_zhuravleva.databinding.FragmentInputBinding
import com.example.lesson_2_zhuravleva.firstpart.utils.BUNDLE_KEY
import com.example.lesson_2_zhuravleva.firstpart.utils.REQUEST_KEY

class InputFragment : Fragment() {

    private var _binding: FragmentInputBinding? = null
    private val binding get() = _binding!!
    private val args: InputFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentInputBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            edtInputText.setText(args.text)
            btnSave.setOnClickListener {
                val text = edtInputText.text.toString()
                setFragmentResult(REQUEST_KEY, bundleOf(BUNDLE_KEY to text))
                findNavController().popBackStack()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
