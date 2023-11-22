package com.example.lesson_3_zhuravleva.presentation.ui.product.size

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.lesson_3_zhuravleva.databinding.FragmentSizesBottomSheetBinding
import com.example.lesson_3_zhuravleva.domain.product.Size
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

class SizesBottomSheetFragment : BottomSheetDialogFragment(), SizeAdapter.Listener {

    private var _binding: FragmentSizesBottomSheetBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var sizeAdapter: SizeAdapter

    private val args: SizesBottomSheetFragmentArgs by navArgs()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        AndroidSupportInjection.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSizesBottomSheetBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val sizeRecyclerView = binding.root
        sizeRecyclerView.adapter = sizeAdapter
        val sizeList = mutableListOf<Size>()
        for (i in args.sizelist){
            sizeList.add(i)
        }
        sizeAdapter.submitList(sizeList)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object{
        const val REQUEST_KEY = "requestKey"
        const val BUNDLE_KEY = "bundle"
    }

    override fun onClick(size: Size) {
        setFragmentResult(REQUEST_KEY, bundleOf(BUNDLE_KEY to size))
        findNavController().popBackStack()
    }

}