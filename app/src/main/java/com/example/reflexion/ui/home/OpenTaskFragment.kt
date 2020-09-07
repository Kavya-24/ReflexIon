package com.example.reflexion.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.reflexion.R
import com.example.reflexion.databinding.OpenTaskFragmentBinding
import com.example.reflexion.viewmodels.OpenTaskViewModel

class OpenTaskFragment : Fragment() {

    companion object {
        fun newInstance() = OpenTaskFragment()
    }

    private val viewModel: OpenTaskViewModel by viewModels()
    private lateinit var binding: OpenTaskFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(layoutInflater, R.layout.open_task_fragment, container, false)

        //This will get arguments from the home fragment
        //This will get the rating from argumnets as well

        //Extract the arguments
        val stars = arguments?.getString("stars")!!
        val task = arguments?.getString("task")!!


        binding.rating.rating = stars.toFloat()
        binding.tvDescriptionOpen.text = task


        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

    }

}
