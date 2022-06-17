package com.nexthotel.ui.setting

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.nexthotel.BuildConfig
import com.nexthotel.R
import com.nexthotel.core.utils.Utils.toast
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

        binding.apply {
            versionNumber.text = BuildConfig.VERSION_NAME
            themeButton.setOnClickListener {
                startActivity(Intent(Settings.ACTION_DISPLAY_SETTINGS))
            }
            languageButton.setOnClickListener {
                startActivity(Intent(Settings.ACTION_LOCALE_SETTINGS))
            }
            aboutUsButton.setOnClickListener {
                toast(requireActivity(), getString(R.string.about_us))
            }
        }
    }
}