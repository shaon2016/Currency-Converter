package com.lastblade.payseracurrencyexchanger.view.fragment.home

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ArrayAdapter
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