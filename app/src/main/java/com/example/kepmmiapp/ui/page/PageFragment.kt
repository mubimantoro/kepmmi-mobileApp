package com.example.kepmmiapp.ui.page

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kepmmiapp.R
import com.example.kepmmiapp.adapter.PageMenuAdapter
import com.example.kepmmiapp.databinding.FragmentPageBinding
import com.example.kepmmiapp.utils.DestinationType
import com.example.kepmmiapp.utils.PageMenu


class PageFragment : Fragment() {

    private var _binding: FragmentPageBinding? = null
    private val binding get() = _binding!!

    private lateinit var pageMenuAdapter: PageMenuAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentPageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRv()
    }

    private fun setupRv() {
        val pageMenu = listOf(
            PageMenu(
                id = 1,
                title = "Profil Organisasi",
                destinationType = DestinationType.PROFIL_ORGANISASI
            ),
            PageMenu(
                id = 2,
                title = "Program Kerja",
                destinationType = DestinationType.PROGRAM_KERJA
            ),
            PageMenu(
                id = 3,
                title = "Struktur Organisasi",
                destinationType = DestinationType.STRUKTUR_ORGANISASI
            )

        )

        pageMenuAdapter = PageMenuAdapter {
            navigateToDestination(it.destinationType)

        }

        binding.pageMenuRv.apply {
            layoutManager = LinearLayoutManager(requireActivity())
            adapter = pageMenuAdapter
        }

        pageMenuAdapter.submitList(pageMenu)
    }

    private fun navigateToDestination(destinationType: DestinationType) {
        when (destinationType) {
            DestinationType.PROFIL_ORGANISASI -> {
                findNavController().navigate(R.id.action_navigation_page_to_navigation_profil_organisasi)
            }

            DestinationType.PROGRAM_KERJA -> {
                findNavController().navigate(R.id.action_navigation_page_to_navigation_program_kerja)
            }

            DestinationType.STRUKTUR_ORGANISASI -> {
                findNavController().navigate(R.id.action_navigation_page_to_navigation_struktur_organisasi)
            }
        }
    }


}