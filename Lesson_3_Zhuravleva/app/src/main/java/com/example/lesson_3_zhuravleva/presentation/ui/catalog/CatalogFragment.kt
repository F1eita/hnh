package com.example.lesson_3_zhuravleva.presentation.ui.catalog

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.createViewModelLazy
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.example.lesson_3_zhuravleva.R
import com.example.lesson_3_zhuravleva.data.responsemodel.ResponseStates
import com.example.lesson_3_zhuravleva.databinding.FragmentCatalogBinding
import com.example.lesson_3_zhuravleva.domain.catalog.Product
import com.example.lesson_3_zhuravleva.domain.order.SelectedProduct
import com.example.lesson_3_zhuravleva.presentation.ui.exception.getError
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

class CatalogFragment : Fragment(), CatalogAdapter.Listener {
    private var _binding: FragmentCatalogBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var catalogAdapter: CatalogAdapter

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel by createViewModelLazy(
        CatalogViewModel::class,
        { this.viewModelStore },
        factoryProducer = { viewModelFactory })

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCatalogBinding.inflate(inflater, container, false)
        requireActivity().window.statusBarColor = resources.getColor(R.color.blue_status_bar)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        addObserver()
        val catalogRecyclerView = binding.rvCatalog
        catalogRecyclerView.adapter = catalogAdapter
        catalogRecyclerView.addItemDecoration(
            DividerItemDecoration(
                context,
                RecyclerView.VERTICAL
            )
        )
        binding.errorScreen.btnUpdateData.setOnClickListener {
            viewModel.getProductsList()
        }
        binding.toolbar.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.profile -> {
                    findNavController().navigate(R.id.settingsFragment)
                }
            }
            true
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        AndroidSupportInjection.inject(this)
    }

    private fun addObserver(){
        viewModel.productsLiveData.observe(viewLifecycleOwner) { result ->
            when (result) {
                is ResponseStates.Success -> {
                    catalogAdapter.submitList(result.data)
                    binding.viewFlipper.displayedChild = CATALOG_SCREEN
                }

                is ResponseStates.Failure -> {
                    binding.errorScreen.tvErrorText.text = result.e.getError() ?: ""
                    binding.viewFlipper.displayedChild = ERROR_SCREEN
                }

                is ResponseStates.Loading -> {
                    binding.viewFlipper.displayedChild = LOADING_SCREEN
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object{
        const val CATALOG_SCREEN = 0
        const val ERROR_SCREEN = 2
        const val LOADING_SCREEN = 1
    }

    override fun onClick(product: Product) {
        val action = CatalogFragmentDirections
            .actionCatalogFragmentToProductFragment(id = product.id)
        findNavController().navigate(action)
    }

    override fun onClickButton(product: Product) {
        val selectedProduct = SelectedProduct(id = product.id,
            title = product.title,
            department = product.department,
            preview = product.preview,
            size = resources.getString(R.string.xs),
            price = product.price)
        val action = CatalogFragmentDirections
            .actionCatalogFragmentToOrderFragment(product = selectedProduct)
        findNavController().navigate(action)
    }

}