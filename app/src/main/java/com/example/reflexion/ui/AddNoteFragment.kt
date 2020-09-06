package com.example.reflexion.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.reflexion.R
import com.example.reflexion.databinding.AddNoteFragmentBinding

class AddNoteFragment : Fragment() {

    companion object {
        fun newInstance() = AddNoteFragment()
    }

    private val viewModel: AddNoteViewModel by viewModels()
    private lateinit var binding: AddNoteFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(layoutInflater, R.layout.add_note_fragment, container, false)


        binding.apply {
            if (etDescription.length() > 0) {
                mbtnReset.isEnabled = true
                mbtnSave.isEnabled = true
            }

            mbtnReset.setOnClickListener {
                etDescription.text = null
            }

            mbtnSave.setOnClickListener {
                saveNote()
            }

        }






        return binding.root
    }

    private fun saveNote() {

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


    }

}
