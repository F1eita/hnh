package com.example.lesson_2_zhuravleva.thirdpart

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.lesson_2_zhuravleva.databinding.FragmentOneBinding

class OneFragment : Fragment() {

    private var _binding: FragmentOneBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("fragment lifecycle", "OnCreate()")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d("fragment lifecycle", "OnCreateView()")
        _binding = FragmentOneBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        Log.d("fragment lifecycle", "OnStart()")
    }

    override fun onResume() {
        super.onResume()
        Log.d("fragment lifecycle", "OnResume()")
    }

    override fun onPause() {
        super.onPause()
        Log.d("fragment lifecycle", "OnPause()")
    }

    override fun onStop() {
        super.onStop()
        Log.d("fragment lifecycle", "OnStop()")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("fragment lifecycle", "OnDestroy()")
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        Log.d("fragment lifecycle", "OnAttach()")
    }

    override fun onDetach() {
        super.onDetach()
        Log.d("fragment lifecycle", "OnDetach()")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d("fragment lifecycle", "OnDestroyView()")
        _binding = null
    }
}
