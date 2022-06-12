package com.nexthotel.ui.setting

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.nexthotel.databinding.FragmentSettingBinding

class SettingFragment : Fragment() {

    private var _binding: FragmentSettingBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.cvChangeLanguage.setOnClickListener {
            Toast.makeText(requireActivity(), "Change Language", Toast.LENGTH_SHORT).show()
        }

        binding.cvAboutUs.setOnClickListener {
            Toast.makeText(requireActivity(), "About Us", Toast.LENGTH_SHORT).show()
        }

        binding.switch2.setOnClickListener {
            Toast.makeText(requireActivity(), "Dark Theme", Toast.LENGTH_SHORT).show()
        }

    }

}