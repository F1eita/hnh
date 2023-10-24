package com.example.lesson_2_zhuravleva.secondpart

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.lesson_2_zhuravleva.R
import com.example.lesson_2_zhuravleva.databinding.FragmentSecondBinding

class SecondFragment : Fragment() {

    private lateinit var binding: FragmentSecondBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSecondBinding.inflate(inflater, container, false)
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
                Toast.makeText(requireActivity(), getString(R.string.error_msg), Toast.LENGTH_SHORT)
                    .show()
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
}
