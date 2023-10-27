package com.example.lesson_2_zhuravleva.firstpart

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResultListener
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.lesson_2_zhuravleva.R
import com.example.lesson_2_zhuravleva.databinding.FragmentOutputBinding
import com.example.lesson_2_zhuravleva.firstpart.utils.BUNDLE_KEY
import com.example.lesson_2_zhuravleva.firstpart.utils.REQUEST_KEY

class OutputFragment : Fragment() {

    private var _binding: FragmentOutputBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentOutputBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            setFragmentResultListener(REQUEST_KEY){ _, bundle ->
                tvOutputText.text = bundle.getString(BUNDLE_KEY)
            }
            btnToNext.setOnClickListener {
                val text = tvOutputText.text.toString()
                val action = OutputFragmentDirections.actionOutputFragmentToInputFragment(text)
                findNavController().navigate(action)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
