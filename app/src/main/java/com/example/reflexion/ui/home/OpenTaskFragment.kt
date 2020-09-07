package com.example.reflexion.ui.home

import android.content.Intent
import android.os.Bundle
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


        binding.tvShare.setOnClickListener {
            shareNote()

        }

        binding.tvDelete.setOnClickListener {
            deleteThisTask()
        }


        return binding.root
    }

    private fun shareNote() {

        val shareIntent = Intent(Intent.ACTION_SEND)
        val args = arguments

        val str = args?.getString("task")!!
        shareIntent.type = "text/plain"

        shareIntent.putExtra(Intent.EXTRA_TEXT, str)
        startActivity(shareIntent)

    }

    private fun deleteThisTask() {
        TODO("Not yet implemented")
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

    }

}
