package com.example.lesson_2_zhuravleva.secondpart

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.lesson_2_zhuravleva.R
import com.example.lesson_2_zhuravleva.databinding.FragmentFirstBinding

class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            btnToFirstFragment.setOnClickListener {
                Toast.makeText(requireActivity(), getString(R.string.error_msg), Toast.LENGTH_SHORT)
                    .show()
            }
            btnToSecondFragment.setOnClickListener {
                parentFragmentManager.beginTransaction().apply {
                    replace(R.id.fragmentContainerView, SecondFragment())
                    commit()
                    addToBackStack(null)
                }
            }
            btnToThirdFragment.setOnClickListener {
                parentFragmentManager.beginTransaction().apply {
                    replace(R.id.fragmentContainerView, ThirdFragment())
                    commit()
                    addToBackStack(null)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
