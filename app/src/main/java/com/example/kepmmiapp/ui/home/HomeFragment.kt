package com.example.kepmmiapp.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.kepmmiapp.R
import com.example.kepmmiapp.adapter.KegiatanHomeAdapter
import com.example.kepmmiapp.adapter.SliderAdapter
import com.example.kepmmiapp.data.Result
import com.example.kepmmiapp.data.interceptor.LogoutEvent
import com.example.kepmmiapp.data.remote.response.PeriodeRekrutmenAnggotaResponseItem
import com.example.kepmmiapp.databinding.FragmentHomeBinding
import com.example.kepmmiapp.ui.KepmmiViewModelFactory
import com.example.kepmmiapp.utils.DateFormatter
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<HomeViewModel> {
        KepmmiViewModelFactory.getInstance(requireContext())
    }

    private lateinit var sliderAdapter: SliderAdapter
    private lateinit var kegiatanHomeAdapter: KegiatanHomeAdapter


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

        setupAdapter()
        setupObserver()
        setupEventBus()
        setupSwipeRefresh()
        setupActivePeriodeListener()

    }

    private fun setupAdapter() {

        kegiatanHomeAdapter = KegiatanHomeAdapter { kegiatan ->

        }

        sliderAdapter = SliderAdapter()

        binding.apply {
            bannerVp.adapter = sliderAdapter
            dotsIndicator.attachTo(bannerVp)


            kegiatanRv.layoutManager = GridLayoutManager(context, 2)
            kegiatanRv.adapter = kegiatanHomeAdapter

        }
    }


    private fun setupObserver() {
        viewModel.apply {
            sliderResult.observe(viewLifecycleOwner) {
                sliderAdapter.submitList(it)
            }

            kegiatanHomeResult.observe(viewLifecycleOwner) { result ->
                when (result) {
                    is Result.Loading -> {
                        binding.progressbar.visibility = View.VISIBLE
                        binding.emptyKegiatanTv.visibility = View.GONE
                    }

                    is Result.Success -> {
                        binding.progressbar.visibility = View.GONE

                        if (result.data.isEmpty()) {
                            binding.kegiatanRv.visibility = View.GONE
                            binding.emptyKegiatanTv.visibility = View.VISIBLE
                        } else {
                            binding.emptyKegiatanTv.visibility = View.GONE
                            kegiatanHomeAdapter.submitList(result.data)
                        }
                    }

                    is Result.Error -> {
                        binding.progressbar.visibility = View.GONE
                        binding.emptyKegiatanTv.visibility = View.VISIBLE
                        binding.emptyKegiatanTv.text = result.error
                    }
                }
            }

            periodeRekrutmenResult.observe(viewLifecycleOwner) { result ->
                when (result) {
                    is Result.Loading -> {
                        binding.itemRekrutmenAnggota.daftarSekarangBtn.isEnabled = false
                    }

                    is Result.Success -> {
                        val periodeData = result.data

                        binding.itemRekrutmenAnggota.apply {
                            statusPeriodeTv.text =
                                if (periodeData.isAktif) getString(R.string.periode_aktif) else getString(
                                    R.string.periode_nonaktif
                                )
                            periodeDaftarTv.text =
                                "${DateFormatter.formatDate(periodeData.tanggalMulai)} - ${
                                    DateFormatter.formatDate(periodeData.tanggalSelesai)
                                }"
                            daftarSekarangBtn.isEnabled = periodeData.isAktif
                        }
                    }

                    is Result.Error -> {
                        binding.itemRekrutmenAnggota.apply {
                            statusPeriodeTv.text = getString(R.string.error)
                            periodeDaftarTv.text = result.error
                            daftarSekarangBtn.isEnabled = false

                        }
                    }
                }

            }

            getSession().observe(viewLifecycleOwner) { user ->
                if (!user.isLogin) {
                    findNavController().navigate(R.id.action_navigation_home_to_nagivation_login)
                }
            }
        }
    }

    private fun setupEventBus() {
        EventBus.getDefault().register(this)
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onLogoutEvent(event: LogoutEvent) {
        navigateToLogin()
    }

    private fun navigateToLogin() {
        findNavController().navigate(R.id.action_navigation_home_to_nagivation_login)
    }

    private fun setupSwipeRefresh() {
        binding.swipeRefresh.setOnRefreshListener {
            refreshData()
        }
    }

    private fun setupActivePeriodeListener() {
        binding.itemRekrutmenAnggota.daftarSekarangBtn.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_home_to_registrasiAnggotaFragment)
        }
    }


    private fun refreshData() {
        viewModel.apply {
            getSliders()
            getKegiatanHome()
            getActivePeriode()

            binding.swipeRefresh.isRefreshing = false
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        EventBus.getDefault().unregister(this)
        _binding = null
    }
}