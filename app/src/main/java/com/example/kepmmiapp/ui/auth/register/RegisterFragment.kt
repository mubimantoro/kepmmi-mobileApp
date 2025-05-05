package com.example.kepmmiapp.ui.auth.register

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
import com.example.kepmmiapp.databinding.FragmentRegisterBinding
import com.example.kepmmiapp.ui.AuthViewModelFactory


class RegisterFragment : Fragment() {
    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!

    private val viewModel: RegisterViewModel by viewModels {
        AuthViewModelFactory.getInstance(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupObserver()
        setupListener()
    }

    private fun setupListener() {
        binding.registerBtn.setOnClickListener {
            val namaLengkap = binding.namaLengkapEt.text.toString().trim()
            val email = binding.emailEt.text.toString().trim()
            val password = binding.passwordEt.text.toString().trim()

            when {
                namaLengkap.isEmpty() -> {
                    binding.namaLengkapEt.error = getString(R.string.error_field_fullname)
                    return@setOnClickListener
                }

                email.isEmpty() -> {
                    binding.emailEt.error = getString(R.string.error_field_email)
                    return@setOnClickListener
                }

                password.isEmpty() -> {
                    binding.passwordEt.error = getString(R.string.error_field_password)
                    return@setOnClickListener
                }

                else -> viewModel.register(namaLengkap, email, password)

            }
        }

        binding.loginTv.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun setupObserver() {
        viewModel.registerResult.observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Loading -> {
                    binding.progressbar.visibility = View.VISIBLE
                }

                is Result.Success -> {
                    binding.progressbar.visibility = View.GONE

                    showToast("Registrasi berhasil")

                    findNavController().navigateUp()
                }

                is Result.Error -> {
                    binding.progressbar.visibility = View.GONE
                    showToast(result.error)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }
}