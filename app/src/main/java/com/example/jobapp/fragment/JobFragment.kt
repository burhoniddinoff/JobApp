package com.example.jobapp.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.jobapp.MainActivity
import com.example.jobapp.R
import com.example.jobapp.adapter.JobAdapter
import com.example.jobapp.databinding.FragmentDetailBinding
import com.example.jobapp.databinding.FragmentJobBinding
import com.example.jobapp.utils.Resource
import com.example.jobapp.viewmodel.MainViewModel

class JobFragment : Fragment(R.layout.fragment_job) {
    private var _binding: FragmentJobBinding? = null
    private val binding get() = _binding!!
    private val jobAdapter by lazy { JobAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentJobBinding.bind(view)

        initViews()
    }

    private fun initViews() {
        val viewModel = (activity as MainActivity).mainViewModel
        binding.rvRemoteJobs.adapter = jobAdapter
        binding.rvRemoteJobs.layoutManager = LinearLayoutManager(requireContext())
        binding.swipeContainer.setOnRefreshListener {
            jobAdapter.submitList(emptyList())
            viewModel.getAllRemoteJobs()
        }
        setupViewModel(viewModel)
        jobAdapter.onClick = {
            val bundle = bundleOf("job" to it)
            findNavController().navigate(R.id.action_mainFragment_to_detailFragment, bundle)
        }
    }

    private fun setupViewModel(viewModel: MainViewModel) {
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