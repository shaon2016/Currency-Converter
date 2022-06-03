package com.lastblade.paypaycorpcurrencyexchanger.view.fragment.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.lastblade.paypaycorpcurrencyexchanger.databinding.FragmentHomeBinding
import com.lastblade.paypaycorpcurrencyexchanger.view.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import java.util.HashMap

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>() {
    private val vm: HomeViewModel by viewModels()
    private lateinit var adapter: CurrencyExchangeRatesAdapter

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentHomeBinding
        get() = FragmentHomeBinding::inflate

    override fun viewRelatedTask() {
        setRecyclerView()

        binding.evAmount.doOnTextChanged { text, start, before, count ->
            if (text.toString().isNotEmpty())
                adapter.updateAmount(text.toString().toDouble())
        }

        vm.convertedCurrencyList.observe(this) {
            it?.let {
                adapter.updateCurrencyList(it)
            }
        }

        vm.currenciesAsObserve.observe(this) {
            it?.let {
                it.currencies?.let { currencies ->
                    setCurrenciesSpinner(currencies)
                }
            }
        }

        vm.isLoading.observe(this) {
            it?.let {
                if (it) {
                    progressBarHandler.show()
                } else
                    progressBarHandler.hide()
            }
        }
        vm.errorMessage.observe(this) {
            it?.let {
                Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setRecyclerView() {
        adapter = CurrencyExchangeRatesAdapter()

        binding.rvExchangeRates.layoutManager = GridLayoutManager(requireContext(), 3)
        binding.rvExchangeRates.adapter = adapter
    }

    private fun setCurrenciesSpinner(currencies: HashMap<String, String>) {
        val toMutableList = currencies.keys.toMutableList()

        val arrayAdapter =
            ArrayAdapter(requireContext(),
                android.R.layout.simple_spinner_dropdown_item, toMutableList)

        binding.spinCurrency.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, position: Int, p3: Long) {
                vm.selectedCurrency = toMutableList[position]
                vm.convertUnitRate()
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
            }
        }

        binding.spinCurrency.adapter = arrayAdapter
    }


}