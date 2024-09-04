package com.example.lab2version2

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.lab2version2.databinding.FragmentDrawBinding

class DrawFragment : Fragment() {

    private var _binding: FragmentDrawBinding? = null
    private val binding get() = _binding!!
    private val viewModel: SimpleViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDrawBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val customView = binding.customView

// ---- saved bitmap!!
        viewModel.bitmap?.let { savedBitmap ->
            customView.setBitmap(savedBitmap)
        }

        binding.backButton.setOnClickListener {
            saveDrawingState()
            parentFragmentManager.popBackStack()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        saveDrawingState()
        _binding = null
    }

    private fun saveDrawingState() {
        val customView = binding.customView
        viewModel.bitmap = customView.getBitmap()
    }
}
