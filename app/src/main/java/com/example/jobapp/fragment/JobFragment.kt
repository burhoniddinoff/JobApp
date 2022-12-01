package com.example.jobapp.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.jobapp.R
import com.example.jobapp.databinding.FragmentDetailBinding
import com.example.jobapp.databinding.FragmentJobBinding

class JobFragment : Fragment(R.layout.fragment_job) {
    private var _binding: FragmentJobBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentJobBinding.bind(view)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}