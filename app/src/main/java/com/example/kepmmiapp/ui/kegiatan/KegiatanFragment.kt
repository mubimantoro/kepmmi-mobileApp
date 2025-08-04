package com.example.kepmmiapp.ui.kegiatan

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kepmmiapp.adapter.CategoryAdapter
import com.example.kepmmiapp.adapter.KegiatanAdapter
import com.example.kepmmiapp.data.local.entity.KegiatanEntity
import com.example.kepmmiapp.databinding.FragmentKegiatanBinding
import com.example.kepmmiapp.ui.KepmmiViewModelFactory
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class KegiatanFragment : Fragment() {

    private var _binding: FragmentKegiatanBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<KegiatanViewModel> {
        KepmmiViewModelFactory.getInstance(requireContext())
    }

    private val categoryAdapter by lazy {
        CategoryAdapter()
    }


    private val kegiatanAdapter by lazy {
        KegiatanAdapter({ kegiatan ->
            detailKegiatan(kegiatan)
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentKegiatanBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupSwipeRefresh()
        setupRv()
        setupObserver()

    }

    private fun setupSwipeRefresh() {
        binding.swipeRefresh.setOnRefreshListener {
            refreshData()
        }
    }

    private fun refreshData() {
        categoryAdapter.refresh()
        kegiatanAdapter.refresh()

        binding.swipeRefresh.isRefreshing = false
    }

    private fun setupRv() {

        binding.apply {

            categoryRv.apply {
                adapter = categoryAdapter
                setHasFixedSize(false)
                layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            }

            kegiatanRv.apply {
                adapter = kegiatanAdapter
                setHasFixedSize(false)
                layoutManager = LinearLayoutManager(requireContext())
            }
        }
    }

    private fun setupObserver() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.category.collectLatest {
                categoryAdapter.submitData(it)
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.kegiatan.collectLatest {
                kegiatanAdapter.submitData(it)
            }
        }
    }


    private fun detailKegiatan(kegiatan: KegiatanEntity) {
        val action =
            KegiatanFragmentDirections.actionNavigationKegiatanToDetailKegiatanFragment(kegiatan)
        findNavController().navigate(action)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}