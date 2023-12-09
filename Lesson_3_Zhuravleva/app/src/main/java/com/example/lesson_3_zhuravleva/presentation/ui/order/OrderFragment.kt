package com.example.lesson_3_zhuravleva.presentation.ui.order

import android.app.Activity.RESULT_OK
import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.createViewModelLazy
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.MultiTransformation
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.lesson_3_zhuravleva.R
import com.example.lesson_3_zhuravleva.data.responsemodel.ResponseStates
import com.example.lesson_3_zhuravleva.databinding.FragmentOrderBinding
import com.example.lesson_3_zhuravleva.domain.order.Order
import com.example.lesson_3_zhuravleva.domain.order.OrderProduct
import com.example.lesson_3_zhuravleva.presentation.MapActivity
import com.google.android.material.snackbar.Snackbar
import dagger.android.support.AndroidSupportInjection
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import java.util.TimeZone
import javax.inject.Inject

class OrderFragment : Fragment() {

    private var _binding: FragmentOrderBinding? = null
    private val binding: FragmentOrderBinding get() = _binding!!

    private val args: OrderFragmentArgs by navArgs()

    private var activityResultLauncher: ActivityResultLauncher<Intent>? = null
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val dateFormat = SimpleDateFormat("", Locale.getDefault())

    private val viewModel by createViewModelLazy(
        OrderViewModel::class,
        { this.viewModelStore },
        factoryProducer = { viewModelFactory })

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentOrderBinding.inflate(inflater, container, false)
        requireActivity().window.statusBarColor = resources.getColor(R.color.blue_status_bar)
        activityResultLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ){
            if (it.resultCode == RESULT_OK){
                binding.tvHouse.setText(it.data?.getStringExtra("address") ?: "")
            }
        }
        binding.toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        AndroidSupportInjection.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val product = args.product
        addObserver()
        binding.apply {
            tvSizeAndTitle.text = resources.getString(R.string.size_and_title, product.size,
                product.title)
            tvDepartment.text = product.department
            Glide.with(imageView)
                .load(product.preview)
                .transform(
                    MultiTransformation(
                        CenterCrop(),
                        RoundedCorners(resources
                            .getDimension(R.dimen.corners_radius_50).toInt())
                    )
                ).into(imageView)
            btnBuyFor.buttonText = resources.getString(R.string.buy_for_rubles,
                product.price.toString())

            counter.callback = {
                btnBuyFor.buttonText = resources.getString(R.string.buy_for_rubles,
                    (product.price * binding.counter.getCount()).toString())
            }

            tvDate.setOnClickListener {
                val calendar = Calendar.getInstance()
                var year = calendar.get(Calendar.YEAR)
                var month = calendar.get(Calendar.MONTH)
                var day = calendar.get(Calendar.DAY_OF_MONTH)
                val datePickerDialog = DatePickerDialog(requireContext(),
                    { view, selectedYear, selectedMonth, selectedDay ->
                        year = selectedYear
                        month = selectedMonth
                        day = selectedDay
                        calendar.set(year, month, day)
                        dateFormat.applyLocalizedPattern("d MMMM")
                        tvDate.setText(dateFormat.format(calendar.time))
                }, year, month, day)
                datePickerDialog.datePicker.minDate = calendar.timeInMillis
                datePickerDialog.show()
            }

            tvHouse.setOnClickListener {
                activityResultLauncher!!.launch(MapActivity.createStartIntent(requireActivity()))
            }

            btnBuyFor.setOnClickListener{
                if (tvHouse.text.isNullOrEmpty() || tvApartment.text.isNullOrEmpty()
                    || tvDate.text.isNullOrEmpty()){
                    val snackbar = Snackbar.make(
                        requireView(), resources.getString(R.string.fields_is_empty),
                        Snackbar.LENGTH_SHORT)
                    snackbar.setBackgroundTint(resources.getColor(R.color.error_red))
                    snackbar.show()
                }
                else {
                    val order = Order(house = tvHouse.text.toString(),
                        apartment = tvApartment.text.toString(),
                        dateDelivery = convertDateToServer(tvDate.text.toString()),
                        products = listOf(
                            OrderProduct(productId = args.product.id,
                                size = args.product.size,
                                quantity = counter.getCount())))
                    viewModel.makeOrder(order)
                }
            }

        }
    }

    private fun addObserver(){
        viewModel.orderLiveData.observe(viewLifecycleOwner) { result ->
            when (result) {
                is ResponseStates.Success -> {
                    binding.btnBuyFor.isLoading = false
                    val snackbar = Snackbar.make(
                        requireView(),
                        resources.getString(R.string.success_order_processing,
                            result.data.number.toString(),
                            convertDateToView(result.data.createdDelivery)),
                        Snackbar.LENGTH_LONG)
                    snackbar.setBackgroundTint(resources.getColor(R.color.dark_blue))
                    snackbar.show()
                }

                is ResponseStates.Failure -> {
                    binding.btnBuyFor.isLoading = false
                    val snackbar = Snackbar.make(
                        requireView(), resources.getString(R.string.error_order_processing),
                        Snackbar.LENGTH_LONG)
                    snackbar.setBackgroundTint(resources.getColor(R.color.error_red))
                    snackbar.show()
                }

                is ResponseStates.Loading -> {
                    binding.btnBuyFor.isLoading = true
                }
            }
        }
    }

    private fun convertDateToView(inputDateString: String): String{
        val inputFormat = dateFormat.apply{
            applyPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
            timeZone = TimeZone.getTimeZone("UTC")
        }
        val date = inputFormat.parse(inputDateString)
        val outputFormat = dateFormat.apply{
            applyPattern("dd.MM.yy HH:mm")
            timeZone = TimeZone.getDefault()
        }
        return outputFormat.format(date)
    }

    private fun convertDateToServer(inputDateString: String): String{
        val inputFormat = dateFormat.apply{
            applyLocalizedPattern("d MMMM")
            timeZone = TimeZone.getTimeZone("UTC")
        }
        val date = inputFormat.parse(inputDateString)
        val outputFormat = dateFormat.apply{
            applyPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
            timeZone = TimeZone.getTimeZone("UTC")
        }
        return outputFormat.format(date)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}