package com.example.todo.ui.fragment

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.app.ActivityCompat.recreate
import androidx.fragment.app.Fragment
import com.example.todo.R
import com.example.todo.databinding.FragmentTodoSettingsBinding
import java.util.Locale

class TodoSettingsFragment : Fragment() {
    private lateinit var binding: FragmentTodoSettingsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTodoSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupExposedDropdownMenu()


//        binding.languageAcTv.doOnTextChanged { text, start, before, count ->
//            activeArabicLanguage(
//                when (text.toString()) {
//                    resources.getString(R.string.arabic) -> true
//                    else -> false
//                }
//            )
//        }
    }

    private fun setupExposedDropdownMenu() {
        initLanguagesExposedDropdownMenu()
        initModesExposedDropdownMenu()
        //initPopulationMenu()
    }

    private fun initLanguagesExposedDropdownMenu() {
        // Set up the language dropdown menu
        val languages = resources.getStringArray(R.array.language_options)
        val languageAdapter = ArrayAdapter(
            requireContext().applicationContext,
            R.layout.item_settings_language_dropdown_menu,
            languages
        )
        binding.languageInput.setAdapter(languageAdapter)
    }

    private fun initModesExposedDropdownMenu() {
        // Set up the mode dropdown menu
        val modes = resources.getStringArray(R.array.mode_options)
        val modeAdapter = ArrayAdapter(
            requireContext().applicationContext,
            R.layout.item_settings_language_dropdown_menu,
            modes
        )
        binding.languageInput.setAdapter(modeAdapter)
    }

    private fun initPopulationMenu() {
        if (isArabic()) {
            binding.languageInput.setText(resources.getString(R.string.arabic))
        } else {
            binding.languageInput.setText(resources.getString(R.string.english))
        }
    }

    private fun activeArabicLanguage(action: Boolean) {
        setLocale(if (action) "ar" else "en")
        activity?.let { recreate(it) }
    }

    private fun isArabic(): Boolean {
        return context?.resources?.configuration?.locales?.get(0)?.language == "ar"
    }

    private fun setLocale(language: String) {
        val locale = Locale(language)
        Locale.setDefault(locale)
        val config = Configuration()
        config.setLocale(locale)
        activity?.let {
            requireActivity().baseContext.resources.updateConfiguration(
                config,
                requireActivity().baseContext.resources.displayMetrics
            )
        }
    }
}