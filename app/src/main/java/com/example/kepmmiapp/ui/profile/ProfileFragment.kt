package com.example.kepmmiapp.ui.profile

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
import com.example.kepmmiapp.data.remote.response.UserResponse
import com.example.kepmmiapp.databinding.FragmentProfileBinding
import com.example.kepmmiapp.ui.ViewModelFactory
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.coroutines.launch


class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ProfileViewModel by viewModels {
        ViewModelFactory.getInstance(requireContext())
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

        viewModel.getProfile()
    }

    private fun setupClickListener() {
        binding.logoutBtn.setOnClickListener {
            showLogoutConfirmDialog()
        }
    }

    private fun showLogoutConfirmDialog() {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Konfirmasi Logout")
            .setMessage("Apakah Anda yakin ingin keluar?")
            .setPositiveButton("Ya") { _, _ ->
                viewModel.logout()
            }
            .setNegativeButton("Tidak", null)
            .show()
    }

    private fun setupObserver() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.profileResult.collect { result ->
                    when (result) {
                        is Result.Loading -> {
                            binding.progressbar.visibility = View.VISIBLE
                        }

                        is Result.Success -> {
                            binding.progressbar.visibility = View.GONE
                            updateProfileUI(result.data)
                        }

                        is Result.Error -> {
                            binding.progressbar.visibility = View.GONE
                        }
                    }
                }
            }
        }
    }

    private fun updateProfileUI(userData: UserResponse) {
        binding.itemCardAnggota.namaLengkapTv.text = userData.namaLengkap
        binding.itemCardAnggota.emailTv.text = userData.email

        userData.profile.let {
            binding.itemCardProfile.apply {
                alamatTv.text = it?.alamat ?: "-"
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}