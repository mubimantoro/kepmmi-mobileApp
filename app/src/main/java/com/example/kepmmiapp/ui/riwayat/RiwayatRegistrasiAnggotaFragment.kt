package com.example.kepmmiapp.ui.riwayat

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kepmmiapp.adapter.RiwayatRegistrasiAnggotaAdapter
import com.example.kepmmiapp.data.Result
import com.example.kepmmiapp.databinding.FragmentRiwayatRegistrasiAnggotaBinding
import com.example.kepmmiapp.ui.KepmmiViewModelFactory

class RiwayatRegistrasiAnggotaFragment : Fragment() {
    private var _binding: FragmentRiwayatRegistrasiAnggotaBinding? = null
    private val binding get() = _binding!!

    private val viewModel: RiwayatRegistrasiAnggotaViewModel by viewModels {
        KepmmiViewModelFactory.getInstance(requireContext())
    }

    private lateinit var riwayatRegistrasiAnggotaAdapter: RiwayatRegistrasiAnggotaAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentRiwayatRegistrasiAnggotaBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRV()
        setupObserver()
        setupSwipeRefresh()
        viewModel.getRiwayatRegistrasiAnggota()
    }



    private fun setupRV() {
        riwayatRegistrasiAnggotaAdapter = RiwayatRegistrasiAnggotaAdapter()

        binding.riwayatRegistrasiAnggotaRv.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = riwayatRegistrasiAnggotaAdapter
        }
    }

    private fun setupObserver() {
        viewModel.riwayatPendaftaranResult.observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Loading -> {
                    showLoading(true)
                }

                is Result.Success -> {
                    showLoading(false)
                    riwayatRegistrasiAnggotaAdapter.submitList(result.data)


                }

                is Result.Error -> {
                    showLoading(false)
                }
            }

        }
    }

    private fun setupSwipeRefresh() {
        binding.swipeRefreshLayout.setOnRefreshListener {
            viewModel.getRiwayatRegistrasiAnggota()
        }
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