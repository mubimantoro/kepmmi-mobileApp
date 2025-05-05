package com.example.kepmmiapp.ui.registrasianggota

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.kepmmiapp.R
import com.example.kepmmiapp.data.Result
import com.example.kepmmiapp.data.remote.response.RegistrasiAnggotaResponseItem
import com.example.kepmmiapp.databinding.FragmentRegistrasiAnggotaBinding
import com.example.kepmmiapp.ui.KepmmiViewModelFactory
import com.example.kepmmiapp.utils.DateFormatter
import com.google.android.material.dialog.MaterialAlertDialogBuilder


class RegistrasiAnggotaFragment : Fragment() {

    private var _binding: FragmentRegistrasiAnggotaBinding? = null
    private val binding get() = _binding!!

    private val viewModel: RegistrasiAnggotaViewModel by viewModels {
        KepmmiViewModelFactory.getInstance(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentRegistrasiAnggotaBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupObserver()
        setupClickListener()

    }

    private fun setupObserver() {
        viewModel.profileResult.observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Loading -> {


                }

                is Result.Success -> {
                    val userData = result.data
                    binding.itemDataDiri.apply {
                        namaLengkapTv.text = userData.namaLengkap

                        userData.profile?.let {
                            alamatTv.text = it.alamat ?: "Belum diisi"
                            tempatLahirTv.text = it.tempatLahir ?: "Belum diisi"
                            tanggalLahirTv.text =
                                DateFormatter.formatDate(it.tanggalLahir ?: "Belum diisi")
                            asalKampusTv.text = it.asalKampus
                            jurusanTv.text = it.jurusan
                            angkatanAkademikTv.text = it.angkatanAkademik
                            asalDaerahTv.text = it.asalDaerah
                        }
                    }
                }

                is Result.Error -> {

                }
            }
        }

        viewModel.registrasiAnggotaResult.observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Loading -> {
                    binding.progressbar.visibility = View.VISIBLE
                }

                is Result.Success -> {
                    binding.progressbar.visibility = View.GONE
                    showSuccessDialog(result.data)
                }

                is Result.Error -> {
                    binding.progressbar.visibility = View.GONE
                    showErrorDialog(result.error)

                }
            }
        }
    }

    private fun setupClickListener() {
        binding.daftarBtn.setOnClickListener {
            viewModel.registrasiAnggota()
        }

        binding.editProfileBtn.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_registrasi_anggota_to_navigation_edit_profile)
        }
    }

    private fun showErrorDialog(errorMessage: String) {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Registrasi gagal")
            .setMessage(errorMessage)
            .setPositiveButton("OK") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }

    private fun showSuccessDialog(registrasiData: RegistrasiAnggotaResponseItem) {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Registrasi Berhasil")
            .setMessage("Anda berhasil mendaftar")
            .setPositiveButton("OK") { dialog, _ ->
                dialog.dismiss()
                findNavController().navigateUp()
            }
            .show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}