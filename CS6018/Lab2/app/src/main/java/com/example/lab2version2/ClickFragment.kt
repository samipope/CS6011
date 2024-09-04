package com.example.lab2version2

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.lab2version2.databinding.FragmentClickBinding

class ClickFragment : Fragment() {

    private var _binding: FragmentClickBinding? = null
    private val binding get() = _binding!!
    private val viewModel: SimpleViewModel by activityViewModels()

    private var buttonFunction: () -> Unit = {}

    fun setButtonFunction(newFunc: () -> Unit) {
        buttonFunction = newFunc
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentClickBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.clickMe.setOnClickListener {
            buttonFunction()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}