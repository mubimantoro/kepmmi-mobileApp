package com.example.kepmmiapp.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kepmmiapp.R
import com.example.kepmmiapp.adapter.KategoriAdapter
import com.example.kepmmiapp.adapter.KegiatanAdapter
import com.example.kepmmiapp.adapter.SliderAdapter
import com.example.kepmmiapp.data.Result
import com.example.kepmmiapp.data.local.entity.KegiatanEntity
import com.example.kepmmiapp.databinding.FragmentHomeBinding
import com.example.kepmmiapp.ui.ViewModelFactory
import com.example.kepmmiapp.ui.auth.login.LoginActivity
import com.example.kepmmiapp.utils.DateFormatter
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<HomeViewModel> {
        ViewModelFactory.getInstance(requireActivity())
    }

    private val sliderAdapter by lazy {
        SliderAdapter()
    }

    private val kategoriAdapter by lazy {
        KategoriAdapter()
    }

    private val kegiatanAdapter by lazy {
        KegiatanAdapter {
            detailKegiatan(it)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupUi()

        viewModel.getSession().observe(requireActivity()) { user ->
            if (!user.isLogin) {
                startActivity(Intent(requireActivity(), LoginActivity::class.java))
            }
        }

        setupObserver()
    }

    private fun setupObserver() {
        viewModel.slider.observe(viewLifecycleOwner) {
            sliderAdapter.submitList(it)
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.category.collectLatest {
                kategoriAdapter.submitData(it)
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.kegiatan.collectLatest {
                kegiatanAdapter.submitData(it)
            }
        }


        viewModel.periodeRekrutmenResult.observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Loading -> {

                }

                is Result.Success -> {


                    binding.itemRekrutmenAnggota.periodeDaftarTv.text =
                        getString(
                            R.string.periode_daftar,
                            DateFormatter.formatDate(result.data.tanggalMulai),
                            DateFormatter.formatDate(result.data.tanggalSelesai)

                        )

                    val statusText =
                        if (result.data.status) getString(R.string.aktif) else getString(R.string.nonaktif)
                    val statusColor = if (result.data.status)
                        ContextCompat.getColor(
                            requireContext(),
                            R.color.green_500
                        ) else ContextCompat.getColor(requireContext(), R.color.red_500)

                    binding.itemRekrutmenAnggota.statusPeriodeTv.apply {
                        text = statusText
                        background.setTint(statusColor)
                    }

                    binding.itemRekrutmenAnggota.DaftarSekarangBtn.apply {
                        isEnabled = result.data.status
                        alpha = if (isEnabled) 1.0f else 0.5f

                        setOnClickListener {
                            findNavController().navigate(R.id.action_navigation_home_to_registrasiAnggotaFragment)
                        }
                    }
                }

                is Result.Error -> {

                }
            }
        }
    }

    private fun setupUi() = with(binding) {
        bannerVp.adapter = sliderAdapter
        kategoriRv.apply {
            adapter = kategoriAdapter
            setHasFixedSize(true)
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        }

        kegiatanRv.apply {
            adapter = kegiatanAdapter
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireContext())
        }

    }

    private fun detailKegiatan(kegiatan: KegiatanEntity) {
        val action = HomeFragmentDirections.actionNavigationHomeToDetailKegiatanFragment(kegiatan)
        findNavController().navigate(action)
    }

    private fun message(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}