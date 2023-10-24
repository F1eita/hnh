package com.example.lesson_2_zhuravleva.secondpart

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.lesson_2_zhuravleva.R
import com.example.lesson_2_zhuravleva.databinding.FragmentThirdBinding

class ThirdFragment : Fragment() {

    private lateinit var binding: FragmentThirdBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentThirdBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            btnToFirstFragment.setOnClickListener {
                parentFragmentManager.beginTransaction().apply {
                    replace(R.id.fragmentContainerView, FirstFragment())
                    commit()
                    addToBackStack(null)
                }
            }
            btnToSecondFragment.setOnClickListener {
                parentFragmentManager.beginTransaction().apply {
                    replace(R.id.fragmentContainerView, SecondFragment())
                    commit()
                    addToBackStack(null)
                }
            }
            btnToThirdFragment.setOnClickListener {
                Toast.makeText(requireActivity(), getString(R.string.error_msg), Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }
}
