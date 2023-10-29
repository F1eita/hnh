package com.example.lesson_3_zhuravleva.presentation.ui.sign_in

import android.os.Bundle
import android.view.KeyEvent
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.navigation.fragment.findNavController
import com.example.lesson_3_zhuravleva.R
import com.example.lesson_3_zhuravleva.databinding.FragmentSignInBinding

class SignInFragment : Fragment() {

    private var _binding: FragmentSignInBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSignInBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            btnSignIn.setOnClickListener {
                if (isLoginCorrect() and isPasswordCorrect()) {
                    findNavController().navigate(R.id.catalogFragment)
                }
            }
            edtPassword.setOnEditorActionListener { _, actionId, event ->
                if (actionId == EditorInfo.IME_ACTION_DONE ||
                    event.action == KeyEvent.ACTION_DOWN &&
                    event.keyCode == KeyEvent.KEYCODE_ENTER
                ) {
                    if (isLoginCorrect() and isPasswordCorrect()) {
                        findNavController().navigate(R.id.catalogFragment)
                    }
                    true
                } else {
                    false

                }
            }
        }
    }

    //временная реализация обработки ошибок для наглядности работы textInputLayout
    //проверяется только наличие введенного текста
    private fun isLoginCorrect(): Boolean{
        val text = binding.edtLogin.text.toString()
        binding.textBoxLogin.apply{
            if (text == ""){
                isErrorEnabled = true
                error = getString(R.string.error_input_msg)
                return false
            }
            else{
                isErrorEnabled = false
                return true
            }
        }
    }

    private fun isPasswordCorrect(): Boolean{
        val text = binding.edtPassword.text.toString()
        binding.textBoxPassword.apply{
            if (text == ""){
                isErrorEnabled = true
                error = getString(R.string.error_input_msg)
                return false
            }
            else{
                isErrorEnabled = false
                return true
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}