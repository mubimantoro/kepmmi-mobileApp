package com.example.kepmmiapp.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.kepmmiapp.R
import com.example.kepmmiapp.data.Result
import com.example.kepmmiapp.data.remote.response.UserResponseItem
import com.example.kepmmiapp.databinding.FragmentProfileBinding
import com.example.kepmmiapp.ui.KepmmiViewModelFactory
import com.google.android.material.dialog.MaterialAlertDialogBuilder


class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ProfileViewModel by viewModels {
        KepmmiViewModelFactory.getInstance(requireContext())
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupObserver()
        setupClickListener()
        setupSwipeRefresh()
    }

    private fun setupSwipeRefresh() {
        binding.swipeRefreshLayout.setOnRefreshListener {
            viewModel.apply {
                getProfile()
                getStatusAnggota()
            }
            binding.swipeRefreshLayout.isRefreshing = false
        }
    }

    private fun setupClickListener() {
        binding.logoutBtn.setOnClickListener {
            showLogoutConfirmDialog()
        }

        binding.editProfileBtn.setOnClickListener {
            navigateToEditProfile()
        }

        binding.riwayatRegistrasiAnggotaBtn.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_profile_to_navigation_riwayat_registrasi_anggota)
        }
    }

    private fun navigateToEditProfile() {

        val currentProfile = viewModel.profileResult.value
        if (currentProfile is Result.Success) {
            val userData = currentProfile.data

            val action = ProfileFragmentDirections.actionNavigationProfileToNavigationEditProfile(
                userData.id,
                userData.namaLengkap,
                userData.email,
                userData.profile?.alamat,
                userData.profile?.tempatLahir,
                userData.profile?.tanggalLahir,
                userData.profile?.asalKampus,
                userData.profile?.jurusan,
                userData.profile?.angkatanAkademik,
                userData.profile?.asalDaerah
            )

            findNavController().navigate(action)
        } else {
            Toast.makeText(context, "Gagal memuat data profil", Toast.LENGTH_SHORT).show()
        }

    }

    private fun showLogoutConfirmDialog() {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Konfirmasi Logout")
            .setMessage("Apakah Anda yakin ingin keluar?")
            .setPositiveButton("Ya") { _, _ ->
                viewModel.logout()
                navigateToLogin()
            }
            .setNegativeButton("Tidak", null)
            .show()
    }

    private fun navigateToLogin() {
        findNavController().navigate(R.id.action_navigation_profile_to_nagivation_login)
    }

    private fun setupObserver() {
        viewModel.profileResult.observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Loading -> {
                    binding.progressbar.visibility = View.VISIBLE
                }

                is Result.Success -> {
                    binding.progressbar.visibility = View.GONE
                    populateProfileData(result.data)
                }

                is Result.Error -> {}
            }
        }

        viewModel.statusAnggotaResult.observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Loading -> {}
                is Result.Success -> {
                    val statusAnggota =
                        result.data.anggota?.jenisAnggota?.nama ?: "Belum Terverifikasi"
                    binding.itemCardAnggota.statusAnggotaTv.text = statusAnggota
                }

                is Result.Error -> {}
            }

        }

    }

    private fun populateProfileData(userData: UserResponseItem) {
        binding.itemCardAnggota.apply {
            namaLengkapTv.text = userData.namaLengkap
            emailTv.text = userData.email
        }

        binding.itemCardProfile.apply {
            userData.profile?.let { profile ->
                alamatTv.text = profile.alamat ?: "-"
                tempatLahirTv.text = profile.tempatLahir ?: "-"
                tanggalLahirTv.text = profile.tanggalLahir ?: "-"
                asalKampusTv.text = profile.asalKampus ?: "-"
                jurusanTv.text = profile.jurusan ?: "-"
                angkatanAkademikTv.text = profile.angkatanAkademik ?: "-"
                asalDaerahTv.text = profile.asalDaerah ?: "-"
            } ?: run {
                val profileFields = arrayOf(
                    alamatTv,
                    tempatLahirTv,
                    tanggalLahirTv,
                    asalKampusTv,
                    jurusanTv,
                    angkatanAkademikTv,
                    asalDaerahTv
                )
                profileFields.forEach { fieldId ->
                    fieldId.text = "-"
                }
            }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}