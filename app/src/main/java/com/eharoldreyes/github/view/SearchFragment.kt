package com.eharoldreyes.github.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import com.eharoldreyes.github.R
import com.eharoldreyes.github.databinding.FragmentSearchBinding
import com.eharoldreyes.github.util.viewBindingLifecycle

class SearchFragment : Fragment(R.layout.fragment_search) {

    private var binding by viewBindingLifecycle<FragmentSearchBinding>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSearchBinding.bind(view)
    }
}