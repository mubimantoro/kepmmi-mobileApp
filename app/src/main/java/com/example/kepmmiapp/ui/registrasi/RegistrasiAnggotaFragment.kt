package com.example.kepmmiapp.ui.registrasi

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.kepmmiapp.data.Result
import com.example.kepmmiapp.data.remote.response.RegistrasiAnggotaRequest
import com.example.kepmmiapp.data.remote.response.UserResponse
import com.example.kepmmiapp.databinding.FragmentRegistrasiAnggotaBinding
import com.example.kepmmiapp.ui.ViewModelFactory
import com.example.kepmmiapp.ui.profile.ProfileViewModel
import kotlinx.coroutines.launch


class RegistrasiAnggotaFragment : Fragment() {

    private var _binding: FragmentRegistrasiAnggotaBinding? = null
    private val binding get() = _binding!!

    private val viewModel: RegistrasiAnggotaViewModel by viewModels {
        ViewModelFactory.getInstance(requireContext())
    }

    private val profileViewModel: ProfileViewModel by viewModels {
        ViewModelFactory.getInstance(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentRegistrasiAnggotaBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupObserver()
        setupClickListener()

        profileViewModel.getProfile()
    }

    private fun setupObserver() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                profileViewModel.profileResult.collect { result ->
                    when (result) {
                        is Result.Loading -> {

                        }

                        is Result.Success -> {
                            populateFormWithProfileData(result.data)
                        }

                        is Result.Error -> {

                        }
                    }

                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.registrasiResult.collect { result ->
                    when (result) {
                        is Result.Loading -> {}
                        is Result.Success -> {
                            binding.submitPendaftaranBtn.isEnabled = true

                        }

                        is Result.Error -> {}
                    }
                }
            }
        }
    }

    private fun populateFormWithProfileData(userData: UserResponse) {
        userData.profile?.let {
            binding.alamatEdt.setText(it.alamat ?: "")
            binding.tempatLahirEdt.setText(it.tempatLahir ?: "")
            binding.tanggalLahirEdt.setText(it.tanggalLahir ?: "")
            binding.asalKampusEdt.setText(it.asalKampus ?: "")
            binding.jurusanEdt.setText(it.jurusan ?: "")
            binding.angkatanAkademikEdt.setText(it.angkatanAkademik ?: "")
            binding.asalDaerahEdt.setText(it.asalDaerah ?: "")
        }

    }

    private fun setupClickListener() {

    }

    private fun submitRegistrasi() {
        val registrasiData = RegistrasiAnggotaRequest(
            alamat = binding.alamatEdt.text.toString(),
            tempatLahir = binding.tempatLahirEdt.text.toString(),
            tanggalLahir = binding.tanggalLahirEdt.text.toString(),
            asalKampus = binding.asalKampusEdt.text.toString(),
            jurusan = binding.jurusanEdt.text.toString(),
            angkatanAkademik = binding.angkatanAkademikEdt.text.toString(),
            asalDaerah = binding.asalDaerahEdt.text.toString(),
        )

        // viewModel.registrasiAnggota(registrasiData)


    }


}