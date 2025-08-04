package com.example.kepmmiapp.ui.page.strukturorganisasi

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.example.kepmmiapp.R
import com.example.kepmmiapp.data.Result
import com.example.kepmmiapp.databinding.FragmentStrukturOrganisasiBinding
import com.example.kepmmiapp.ui.KepmmiViewModelFactory


class StrukturOrganisasiFragment : Fragment() {


    private var _binding: FragmentStrukturOrganisasiBinding? = null
    private val binding get() = _binding!!

    private val viewModel: StrukturOrganisasiViewModel by viewModels {
        KepmmiViewModelFactory.getInstance(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentStrukturOrganisasiBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
        setupObserver()
        viewModel.getStrukturOrganisasi()
    }

    private fun setupObserver() {
        viewModel.strukturOrganisasi.observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Loading -> {
                    showLoading(true)
                }

                is Result.Success -> {
                    showLoading(false)
                    Glide.with(this@StrukturOrganisasiFragment)
                        .load(result.data.gambar)
                        .placeholder(R.drawable.ic_broken_image)
                        .into(binding.gambarStrukturOrganisasiIv)
                }

                is Result.Error -> {
                    showLoading(false)
                }
            }
        }
    }

    private fun setupUI() {

    }

    private fun showLoading(isLoading: Boolean) {
        binding.apply {
            progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
            swipeRefreshLayout.isRefreshing = false
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}