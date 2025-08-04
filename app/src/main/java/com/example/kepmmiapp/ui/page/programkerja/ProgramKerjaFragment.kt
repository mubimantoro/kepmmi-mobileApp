package com.example.kepmmiapp.ui.page.programkerja

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kepmmiapp.adapter.ProgramKerjaAdapter
import com.example.kepmmiapp.data.Result
import com.example.kepmmiapp.databinding.FragmentProgramKerjaBinding
import com.example.kepmmiapp.ui.KepmmiViewModelFactory


class ProgramKerjaFragment : Fragment() {

    private var _binding: FragmentProgramKerjaBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ProgramKerjaViewModel by viewModels {
        KepmmiViewModelFactory.getInstance(requireContext())
    }

    private lateinit var programKerjaAdapter: ProgramKerjaAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentProgramKerjaBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
        setupRV()
        setupObserver()
        viewModel.getProgramKerja()
    }

    private fun setupUI() {
        binding.apply {
            swipeRefreshLayout.setOnRefreshListener {
                loadData()
            }
        }

    }

    private fun setupRV() {
        programKerjaAdapter = ProgramKerjaAdapter()
        binding.programKerjaRv.apply {
            adapter = programKerjaAdapter
            layoutManager = LinearLayoutManager(requireContext())

        }
    }

    private fun setupObserver() {
        viewModel.programKerja.observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Loading -> {

                    if (!binding.swipeRefreshLayout.isRefreshing) {
                        showLoading(true)
                    }
                }

                is Result.Success -> {
                    showLoading(false)
                    val data = result.data
                    programKerjaAdapter.submitList(data)
                }

                is Result.Error -> {
                    showLoading(false)
                }
            }
        }
    }


    private fun loadData() {
        viewModel.getProgramKerja()
        viewModel.getBidang()
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