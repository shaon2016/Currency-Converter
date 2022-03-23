package com.lastblade.payseracurrencyexchanger.view.fragment.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.lastblade.payseracurrencyexchanger.databinding.FragmentHomeBinding
import com.lastblade.payseracurrencyexchanger.view.base.BaseFragment


class HomeFragment : BaseFragment<FragmentHomeBinding>() {
    private val vm : HomeViewModel by viewModels()

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentHomeBinding
        get() = FragmentHomeBinding::inflate

    override fun viewRelatedTask() {
        vm.currencyRateResponse.observe(viewLifecycleOwner) {
            it?.let {
                loadExchangeRateIntoSpinnerSell(it.rates)
            }
        }
    }

}