package com.example.todo.ui.fragment

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import com.example.todo.R
import com.example.todo.databinding.FragmentTodoSettingsBinding
import java.util.Locale

class TodoSettingsFragment : Fragment() {
    private var _binding: FragmentTodoSettingsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTodoSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
    }

    private fun initViews() {
        displayCurrentLanguage()
        displayCurrentMode()
        selectLanguages()
        selectModes()
    }

    private fun displayCurrentLanguage() {
        if (Locale.getDefault().language == ENGLISH_LANGUAGE_CODE)
            binding.languageInput.setText(resources.getString(R.string.english))
        else
            binding.languageInput.setText(resources.getString(R.string.arabic))
    }

    private fun displayCurrentMode() {
        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES)
            binding.modeInput.setText(resources.getString(R.string.dark_mode))
        else
            binding.modeInput.setText(resources.getString(R.string.light_mode))
    }

    private fun selectLanguages() {
        initLanguageExposedDropDownMenuAdapter()
        binding.languageInput.setOnItemClickListener { parent, view, position, id ->
            when (position) {
                0 -> selectLanguageToEnglish(this)
                1 -> selectLanguageToArabic(this)
            }
        }
    }

    private fun initLanguageExposedDropDownMenuAdapter() {
        val languages = resources.getStringArray(R.array.language_options)
        val adapter = ArrayAdapter(
            requireContext(),
            R.layout.item_drop_down_menu,
            languages
        )
        binding.languageInput.setAdapter(adapter)
    }

    private fun selectLanguageToEnglish(fragment: Fragment) {
        val currentLanguage = setLocal(ENGLISH_LANGUAGE_CODE)
        val configuration = fragment.requireActivity().resources.configuration
        configuration.setLocale(currentLanguage)
        fragment.requireActivity().resources.updateConfiguration(
            configuration,
            resources.displayMetrics
        )
    }

    private fun selectLanguageToArabic(fragment: Fragment) {
        val currentLanguage = setLocal(ARABIC_LANGUAGE_CODE)
        val configuration = fragment.requireActivity().resources.configuration
        configuration.setLocale(currentLanguage)
        configuration.setLayoutDirection(currentLanguage)
        fragment.requireActivity().resources.updateConfiguration(
            configuration,
            resources.displayMetrics
        )
    }

    private fun setLocal(language: String): Locale {
        val local = Locale(language)
        Locale.setDefault(local)
        requireActivity().recreate()
        return local
    }

    private fun selectModes() {
        initModeExposedDropDownMenuAdapter()
        binding.modeInput.setOnItemClickListener { parent, view, position, id ->
            when (position) {
                0 -> selectLightMode(this)
                1 -> selectDarkMode(this)
            }
        }
    }

    private fun initModeExposedDropDownMenuAdapter() {
        val modes = resources.getStringArray(R.array.mode_options)
        val adapter = ArrayAdapter(
            requireContext(),
            R.layout.item_drop_down_menu,
            modes
        )
        binding.modeInput.setAdapter(adapter)
    }


    private fun selectLightMode(fragment: Fragment) {
        val currentMode = fragment.requireActivity().resources.configuration.uiMode
        val lightMode =
            if (currentMode == Configuration.UI_MODE_NIGHT_YES)
                AppCompatDelegate.MODE_NIGHT_NO
            else
                AppCompatDelegate.MODE_NIGHT_NO
        AppCompatDelegate.setDefaultNightMode(lightMode)
    }

    private fun selectDarkMode(fragment: Fragment) {
        val currentMode = fragment.requireActivity().resources.configuration.uiMode
        val darkMode = if (currentMode == Configuration.UI_MODE_NIGHT_NO) {
            AppCompatDelegate.MODE_NIGHT_YES
        } else {
            AppCompatDelegate.MODE_NIGHT_YES
        }
        AppCompatDelegate.setDefaultNightMode(darkMode)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.apply {
            languageInput.setAdapter(null)
            modeInput.setAdapter(null)
            languageInput.onItemClickListener = null
            modeInput.onItemClickListener = null
        }
        _binding = null
    }

    ////////////////////////////////////////////
    companion object {
        const val ARABIC_LANGUAGE_CODE = "ar"
        const val ENGLISH_LANGUAGE_CODE = "en"
    }
}