package com.lastblade.payseracurrencyexchanger.view.fragment.home

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.lastblade.payseracurrencyexchanger.R
import com.lastblade.payseracurrencyexchanger.data.model.CalculatedValueModel
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

        vm.myBalances.observe(this) {
            it?.let {
                val adapter = MyRvAdapter(it)
                binding.lvMyBalances.layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
                binding.lvMyBalances.adapter = adapter
            }
        }

        vm.calculatedValueModel.observe(this) {
            it?.let {
                showCalculatedRateDialog(it)
                binding.tvReceiveCurrency.text = it.convertedToValue.toString()
            }
        }

        binding.btnSubmit.setOnClickListener {
            val selectedConvertedCurrency =
                resources.getStringArray(R.array.currency2)[binding.spinReceive.selectedItemPosition]
            vm.calculateCurrency(binding.evSell.text.toString(), selectedConvertedCurrency)
        }
    }

    private fun showCalculatedRateDialog(calculatedValue: CalculatedValueModel) {
        val dialog = AlertDialog.Builder(requireContext())
        dialog.setTitle("Currency converted")

        val msg = if (calculatedValue.commissionFee == null)
            "You have converted ${calculatedValue.convertedFromValue} USD to ${calculatedValue.convertedToValue}."
        else "You have converted ${calculatedValue.convertedFromValue} USD to ${calculatedValue.convertedToValue}. Commission Fee - ${calculatedValue.commissionFee}"
        dialog.setMessage(msg)

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