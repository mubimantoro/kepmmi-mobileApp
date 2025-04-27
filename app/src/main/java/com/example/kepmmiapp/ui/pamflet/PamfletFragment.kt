package com.example.kepmmiapp.ui.pamflet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kepmmiapp.adapter.PamfletAdapter
import com.example.kepmmiapp.databinding.FragmentPamfletBinding
import com.example.kepmmiapp.ui.ViewModelFactory
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


class PamfletFragment : Fragment() {

    private var _binding: FragmentPamfletBinding? = null
    private val binding get() = _binding!!

    private val viewModel: PamfletViewModel by viewModels {
        ViewModelFactory.getInstance(requireContext())
    }

    private lateinit var pamfletAdapter: PamfletAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentPamfletBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRv()
        setupObserver()
    }

    private fun setupRv() {
        pamfletAdapter = PamfletAdapter()
        binding.pamfletRv.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = pamfletAdapter
        }
    }

    private fun setupObserver() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.pamflet.collectLatest {
                pamfletAdapter.submitData(it)
            }
        }
    }

}