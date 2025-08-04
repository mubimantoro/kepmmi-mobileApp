package com.example.kepmmiapp.ui.page.pengurus

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.example.kepmmiapp.adapter.PengurusAdapter
import com.example.kepmmiapp.databinding.FragmentPengurusBinding
import com.example.kepmmiapp.ui.KepmmiViewModelFactory
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


class PengurusFragment : Fragment() {

    private var _binding: FragmentPengurusBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<PengurusViewModel> {
        KepmmiViewModelFactory.getInstance(requireContext())
    }

    private val pengurusAdapter by lazy {
        PengurusAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentPengurusBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRv()
        setupObserver()
    }

    private fun setupObserver() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.pengurus.collectLatest {
                pengurusAdapter.submitData(it)
            }
        }
    }

    private fun setupRv() {
        binding.pengurusRv.apply {
            layoutManager = GridLayoutManager(context, 2)
            adapter = pengurusAdapter
        }
    }


}