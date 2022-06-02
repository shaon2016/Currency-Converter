package com.lastblade.payseracurrencyexchanger.view.fragment.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.viewModels
import com.lastblade.payseracurrencyexchanger.R
import com.lastblade.payseracurrencyexchanger.data.db.currencies.Currencies
import com.lastblade.payseracurrencyexchanger.databinding.FragmentHomeBinding
import com.lastblade.payseracurrencyexchanger.view.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import java.util.HashMap

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>() {
    private val vm: HomeViewModel by viewModels()

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentHomeBinding
        get() = FragmentHomeBinding::inflate

    override fun viewRelatedTask() {
        vm.loadCurrencies()

        vm.currencies.observe(this) {
            it.currencies?.let {
                setCurrenciesSpinner(it)
            }
        }


    }

    private fun setCurrenciesSpinner(currencies: HashMap<String, String>) {
        val arrayAdapter =
            ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, currencies.keys.toMutableList())

        binding.spinCurrency.onItemSelectedListener = object  : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, position: Int, p3: Long) {

            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
            }
        }

        binding.spinCurrency.adapter = arrayAdapter
    }


    private fun loadExchangeRateIntoSpinner() {
        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.currency, android.R.layout.simple_spinner_item
        ).also { arrayAdapter ->
            arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.spinCurrency.adapter = arrayAdapter
        }

    }

}