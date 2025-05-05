package com.example.kepmmiapp.ui.page.profilorganisasi

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.net.toUri
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.example.kepmmiapp.data.Result
import com.example.kepmmiapp.data.remote.response.ProfilOrganisasiResponseItem
import com.example.kepmmiapp.databinding.FragmentProfilOrganisasiBinding
import com.example.kepmmiapp.ui.KepmmiViewModelFactory


class ProfilOrganisasiFragment : Fragment() {
    private var _binding: FragmentProfilOrganisasiBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ProfilOrganisasiViewModel by viewModels {
        KepmmiViewModelFactory.getInstance(requireContext())
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentProfilOrganisasiBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupObserver()
        viewModel.getProfilOrganisasi()
    }

    private fun setupObserver() {
        viewModel.profilOrganisasiResult.observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Loading -> {
                    showLoading(true)
                    showError(false, "")
                    showContent(false)
                }

                is Result.Success -> {
                    showLoading(false)
                    showError(false, "")

                    if (result.data.isNotEmpty()) {
                        val profilData = result.data[0]
                        displayProfilOrganisasi(profilData)
                        showContent(true)
                    } else {
                        showError(true, "Tidak ada data Profil Organisasi")
                    }
                }

                is Result.Error -> {
                    showLoading(false)
                    showContent(false)
                    showError(true, result.error)
                }
            }
        }
    }

    private fun displayProfilOrganisasi(profil: ProfilOrganisasiResponseItem) {
        Glide.with(this)
            .load(profil.logo)
            .into(binding.logoOrganisasiIv)

        binding.ringkasanTv.text = profil.ringkasan

        binding.bukuSakuBtn.setOnClickListener {
            openUrlInBrowser(profil.bukuSaku)
        }

        binding.pedomanInternBtn.setOnClickListener {
            openUrlInBrowser(profil.pedomanIntern)
        }
    }

    private fun openUrlInBrowser(url: String?) {
        try {
            val intent = Intent(Intent.ACTION_VIEW, url?.toUri())
            startActivity(intent)
        } catch (e: Exception) {
            Toast.makeText(
                requireContext(),
                "Tidak dapat membuka tautan: ${e.message}",
                Toast.LENGTH_SHORT
            ).show()

        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.isVisible = isLoading
    }

    private fun showContent(isVisible: Boolean) {
        binding.contentGroup.isVisible = isVisible
    }

    private fun showError(isError: Boolean, errorMessage: String) {
        binding.errorTv.isVisible = isError
        binding.errorTv.text = errorMessage
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}