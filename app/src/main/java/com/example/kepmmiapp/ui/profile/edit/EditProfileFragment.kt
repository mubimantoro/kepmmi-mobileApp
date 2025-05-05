package com.example.kepmmiapp.ui.profile.edit

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.kepmmiapp.data.Result
import com.example.kepmmiapp.databinding.FragmentEditProfileBinding
import com.example.kepmmiapp.ui.KepmmiViewModelFactory
import com.example.kepmmiapp.ui.profile.ProfileViewModel
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale


class EditProfileFragment : Fragment() {

    private var _binding: FragmentEditProfileBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ProfileViewModel by viewModels {
        KepmmiViewModelFactory.getInstance(requireContext())
    }

    private val args: EditProfileFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentEditProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        populateFormWithData()
        setupClickListener()
        setupObserver()
    }

    private fun setupObserver() {
        viewModel.updateProfileResult.observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Loading -> {
                    showLoading(true)
                }

                is Result.Success -> {
                    showLoading(false)
                    Toast.makeText(context, "Profil berhasil diperbarui", Toast.LENGTH_SHORT).show()
                    findNavController().navigateUp()
                }

                is Result.Error -> {
                    showLoading(false)
                    Toast.makeText(context, result.error, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun setupClickListener() {
        binding.saveBtn.setOnClickListener {
            updateProfile()
        }

        binding.cancelBtn.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun populateFormWithData() {
        binding.namaLengkapEt.setText(args.namaLengkap)
        binding.emailEt.setText(args.email)
        binding.emailEt.isEnabled = false

        binding.alamatEt.setText(args.alamat ?: "")
        binding.tempatLahirEt.setText(args.tempatLahir ?: "")
        binding.tanggalLahirEt.setText(args.tanggalLahir ?: "")
        binding.asalKampusEt.setText(args.asalKampus ?: "")
        binding.jurusanEt.setText(args.jurusan ?: "")
        binding.angkatanAkademikEt.setText(args.angkatanAkademik ?: "")
        binding.asalDaerahEt.setText(args.asalDaerah ?: "")

        binding.tanggalLahirEt.setOnClickListener {
            showDatePicker()
        }
    }

    private fun updateProfile() {
        val profileData = mapOf(
            "nama_lengkap" to binding.namaLengkapEt.text.toString().trim(),
            "alamat" to binding.alamatEt.text.toString().trim(),
            "tempat_lahir" to binding.tempatLahirEt.text.toString().trim(),
            "tanggal_lahir" to binding.tanggalLahirEt.text.toString().trim(),
            "asal_kampus" to binding.asalKampusEt.text.toString().trim(),
            "jurusan" to binding.jurusanEt.text.toString().trim(),
            "angkatan_akademik" to binding.angkatanAkademikEt.text.toString().trim(),
            "asal_daerah" to binding.asalDaerahEt.text.toString().trim()
        )

        viewModel.updateProfile(profileData)
    }

    private fun showDatePicker() {
        val currentData = binding.tanggalLahirEt.text.toString()
        val calendar = Calendar.getInstance()

        if (currentData.isNotEmpty()) {
            try {
                val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                val date = dateFormat.parse(currentData)
                date?.let {
                    calendar.time = it
                }
            } catch (e: Exception) {
            }
        }

        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            requireContext(),
            { _, selectedYear, selectedMonth, selectedDay ->
                val selectedDate =
                    String.format("%d-%02d-%02d", selectedYear, selectedMonth + 1, selectedDay)
                binding.tanggalLahirEt.setText(selectedDate)
            },
            year, month, day
        )
        datePickerDialog.show()

    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressbar.visibility = if (isLoading) View.VISIBLE else View.GONE
        binding.saveBtn.isEnabled = !isLoading
        binding.cancelBtn.isEnabled = !isLoading
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}