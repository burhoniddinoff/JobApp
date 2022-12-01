package com.example.jobapp.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.jobapp.MainActivity
import com.example.jobapp.R
import com.example.jobapp.adapter.JobAdapter
import com.example.jobapp.databinding.FragmentDetailBinding
import com.example.jobapp.databinding.FragmentJobBinding
import com.example.jobapp.utils.Resource

class JobFragment : Fragment(R.layout.fragment_job) {
    private var _binding: FragmentJobBinding? = null
    private val jobAdapter by lazy { JobAdapter() }
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentJobBinding.bind(view)

        initViews()
    }

    private fun initViews() {
        val viewModel = (activity as MainActivity).mainViewModel
        binding.rvRemoteJobs.adapter = jobAdapter
        binding.rvRemoteJobs.layoutManager = LinearLayoutManager(requireContext())

        viewModel.remoteJobs.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Loading -> {
                    binding.swipeContainer.isRefreshing = true
                }
                is Resource.Error -> {
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                }
                is Resource.Success -> {
                    binding.swipeContainer.isRefreshing = false
                    jobAdapter.submitList(it.data?.jobs)
                }
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}