package com.example.kepmmiapp.ui.page.profilorganisasi

import android.Manifest
import android.app.DownloadManager
import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.example.kepmmiapp.R
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

    private var currentProfil: ProfilOrganisasiResponseItem? = null
    private var downloadManager: DownloadManager? = null


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
        setupUI()
        setupObserver()
        viewModel.getProfilOrganisasi()

        downloadManager =
            requireContext().getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
    }

    private fun setupUI() {
        binding.apply {
            bukuSakuBtn.setOnClickListener {
                downloadPDF(currentProfil?.bukuSaku, "Buku_Saku_KEPMMI.pdf")
            }
            pedomanInternBtn.setOnClickListener {
                downloadPDF(currentProfil?.pedomanIntern, "Pedoman_Intern_KEPEMMI.pdf")
            }
            swipeRefreshLayout.setOnRefreshListener {
                viewModel.getProfilOrganisasi()
            }
        }
    }

    private fun setupObserver() {
        viewModel.profilOrganisasi.observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Loading -> {
                    showLoading(true)
                    showError(false, "")
                }

                is Result.Success -> {
                    showLoading(false)
                    showError(false, "")
                    showProfilOrganisasi(result.data)

                }

                is Result.Error -> {
                    showLoading(false)
                    showError(true, result.error)
                }
            }
        }
    }

    private fun downloadPDF(url: String?, fileName: String) {
        if (url.isNullOrEmpty()) {
            Toast.makeText(requireContext(), "File tidak tersedia", Toast.LENGTH_SHORT).show()
            return
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                requestPermissionLauncher.launch(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                return
            }
        }

        try {
            val request = DownloadManager.Request(Uri.parse(url))
                .setTitle("KEPEMMI - $fileName")
                .setDescription("Mengunduh dokumen...")
                .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, fileName)
                .setAllowedOverMetered(true)
                .setAllowedOverRoaming(true)

            downloadManager?.enqueue(request)
            Toast.makeText(requireContext(), "Mulai mengunduh $fileName", Toast.LENGTH_SHORT).show()

        } catch (e: Exception) {
            Toast.makeText(requireContext(), "Gagal mengunduh file", Toast.LENGTH_SHORT).show()
        }
    }


    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            // Retry download after permission granted
            Toast.makeText(requireContext(), "Silakan coba unduh lagi", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(
                requireContext(),
                "Izin diperlukan untuk mengunduh file",
                Toast.LENGTH_SHORT
            ).show()
        }
    }


    private fun showProfilOrganisasi(profil: ProfilOrganisasiResponseItem) {
        currentProfil = profil

        binding.apply {
            // Load logo
            Glide.with(this@ProfilOrganisasiFragment)
                .load(profil.logo)
                .placeholder(R.drawable.ic_broken_image)
                .into(logoOrganisasiIv)

            // Set ringkasan
            ringkasanTv.text = profil.ringkasan

        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.apply {
            progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
            swipeRefreshLayout.isRefreshing = false
        }
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