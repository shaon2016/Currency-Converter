package com.lastblade.payseracurrencyexchanger.view.fragment.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.viewModels
import com.lastblade.payseracurrencyexchanger.R
import com.lastblade.payseracurrencyexchanger.databinding.FragmentHomeBinding
import com.lastblade.payseracurrencyexchanger.view.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>() {
    private val vm: HomeViewModel by viewModels()

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentHomeBinding
        get() = FragmentHomeBinding::inflate

    override fun viewRelatedTask() {
        loadExchangeRateIntoSpinner()

        vm.myBalance.observe(this) {
            it?.let {
                binding.tvMyBalances.text = "$it USD"
            }
        }
        vm.selectedCurrencyValue.observe(this) {
            it?.let {
                binding.tvReceiveCurrency.text = it.toString()
            }
        }

        vm.calculatedValue.observe(this) {
            it?.let {
                showCalculatedRateDialog(it)
            }
        }

        binding.btnSubmit.setOnClickListener {
            val selectedConvertedCurrency =
                resources.getStringArray(R.array.currency2)[binding.spinReceive.selectedItemPosition]
            vm.calculateCurrency(binding.evSell.text.toString(), selectedConvertedCurrency)
        }
    }

    private fun showCalculatedRateDialog(calculatedValue: Double) {
        val dialog = AlertDialog.Builder(requireContext())
        dialog.setMessage("Calculated value $calculatedValue")

        dialog.show()
    }

    private fun loadExchangeRateIntoSpinner() {
        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.currency, android.R.layout.simple_spinner_item
        ).also { arrayAdapter ->
            arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.spinSell.adapter = arrayAdapter
        }
        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.currency2, android.R.layout.simple_spinner_item
        ).also { arrayAdapter ->
            arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.spinReceive.adapter = arrayAdapter
        }
    }

}