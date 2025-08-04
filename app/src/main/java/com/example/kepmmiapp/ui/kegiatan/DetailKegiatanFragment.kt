package com.example.kepmmiapp.ui.kegiatan

import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.kepmmiapp.R
import com.example.kepmmiapp.data.Result
import com.example.kepmmiapp.data.remote.response.KegiatanResponseItem
import com.example.kepmmiapp.databinding.FragmentDetailKegiatanBinding
import com.example.kepmmiapp.ui.KepmmiViewModelFactory

class DetailKegiatanFragment : Fragment() {

    private var _binding: FragmentDetailKegiatanBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<KegiatanViewModel> {
        KepmmiViewModelFactory.getInstance(requireContext())
    }

    private val args: DetailKegiatanFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentDetailKegiatanBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
        setupObserver()
    }

    private fun setupObserver() {
        viewModel.kegiatanDetail.observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Loading -> {

                }

                is Result.Success -> {
                    showKegiatanDetail(result.data)

                }

                is Result.Error -> {

                }
            }
        }
    }

    private fun showKegiatanDetail(item: KegiatanResponseItem) {
        Glide.with(this)
            .load(item.gambar)
            .placeholder(R.drawable.ic_broken_image)
            .into(binding.gambarKegiatanTv)

        binding.apply {
            judulTv.text = item.judul
            categoryTv.text = item.kategori?.nama
            viewsTv.text = item.viewsCount.toString()
            kontenTv.text = Html.fromHtml(item.konten, Html.FROM_HTML_MODE_LEGACY)
        }
    }

    private fun setupUI() {

    }

    private fun showLoading(isLoading: Boolean) {

    }
}