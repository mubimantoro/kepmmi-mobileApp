package com.example.kepmmiapp.ui.pamflet

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.kepmmiapp.R
import com.example.kepmmiapp.data.Result
import com.example.kepmmiapp.data.remote.response.PamfletResponseItem
import com.example.kepmmiapp.databinding.FragmentDetailPamfletBinding
import com.example.kepmmiapp.ui.KepmmiViewModelFactory
import java.net.URLEncoder


class DetailPamfletFragment : Fragment() {

    private var _binding: FragmentDetailPamfletBinding? = null
    private val binding get() = _binding!!

    private val args: DetailPamfletFragmentArgs by navArgs()

    private val viewModel: PamfletViewModel by viewModels {
        KepmmiViewModelFactory.getInstance(requireContext())
    }

    private var currentPamflet: PamfletResponseItem? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentDetailPamfletBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
        observeData()
        viewModel.getPamfletDetail(args.pamfletId)
    }

    private fun observeData() {
        viewModel.pamfletDetail.observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Loading -> {
                    showLoading(true)
                }

                is Result.Success -> {
                    showLoading(false)
                    showPamfletDetail(result.data)
                }

                is Result.Error -> {

                }
            }
        }
    }

    private fun showPamfletDetail(pamflet: PamfletResponseItem) {
        currentPamflet = pamflet

        // Load image
        Glide.with(this)
            .load(pamflet.gambar)
            .placeholder(R.drawable.ic_broken_image)
            .error(R.drawable.ic_broken_image)
            .into(binding.pamfletImageIv)

        binding.captionTv.text = pamflet.caption
    }

    private fun setupUI() {
        binding.shareWhatsappBtn.setOnClickListener {
            shareToWhatsApp()
        }
    }

    private fun shareToWhatsApp() {
        currentPamflet?.let { pamflet ->
            val shareText = buildString {
                append("*${pamflet.caption}*\n\n")
                append("Gambar: ${pamflet.gambar}\n\n")
                append("Shared via KEPEMMI App")
            }

            val intent = Intent(Intent.ACTION_SEND).apply {
                type = "text/plain"
                setPackage("com.whatsapp")
                putExtra(Intent.EXTRA_TEXT, shareText)
            }

            try {
                startActivity(intent)
            } catch (e: ActivityNotFoundException) {
                shareToWhatsAppWeb(shareText)
            }
        } ?: run {
            Toast.makeText(requireContext(), "Data pamflet tidak tersedia", Toast.LENGTH_SHORT)
                .show()

        }

    }

    private fun shareToWhatsAppWeb(text: String) {
        val encodedText = URLEncoder.encode(text, "UTF-8")
        val whatsappWebUrl = "https://web.whatsapp.com/send?text=$encodedText"

        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(whatsappWebUrl))
        try {
            startActivity(intent)
        } catch (e: ActivityNotFoundException) {
            val shareIntent = Intent(Intent.ACTION_SEND).apply {
                type = "text/plain"
                putExtra(Intent.EXTRA_TEXT, text)
            }
            startActivity(Intent.createChooser(shareIntent, "Share via"))
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
