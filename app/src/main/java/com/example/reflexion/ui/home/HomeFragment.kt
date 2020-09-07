package com.example.reflexion.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.reflexion.R
import com.example.reflexion.adapters.OnItemClickListener
import com.example.reflexion.adapters.TasksAdapter
import com.example.reflexion.databinding.FragmentHomeBinding
import com.example.reflexion.models.Task
import com.example.reflexion.viewmodels.HomeViewModel
import com.google.android.gms.tasks.OnFailureListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

class HomeFragment : Fragment(), OnItemClickListener {

    private val homeViewModel: HomeViewModel by viewModels()
    lateinit var binding: FragmentHomeBinding

    //Get reference to the document reference
    private val database = FirebaseFirestore.getInstance()

    //Get user id reference
    private val userId = FirebaseAuth.getInstance().currentUser?.uid
    private val TAG = HomeFragment::class.java.simpleName

    private lateinit var adapter: TasksAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(layoutInflater, R.layout.fragment_home, container, false)


        binding.fabAdd.setOnClickListener {
            this.findNavController().navigate(R.id.action_nav_home_to_addNoteFragment)
        }

        adapter = TasksAdapter(this)

        //Init the recycler view
        binding.apply {
            val layoutManager = LinearLayoutManager(context)
            rvNotes.layoutManager = layoutManager
            rvNotes.adapter = adapter
        }

        getDocuments()


        return binding.root
    }

    private fun getDocuments() {

        //Create a list
        val adapterList: MutableList<Task> = mutableListOf()
        val collectionReference =
            database.collection(userId!!)
        val id = database.collection("myTasksCollection").document().id

        //Get them is descending order
        collectionReference.orderBy("DATE", Query.Direction.DESCENDING)

        collectionReference.get()
            .addOnSuccessListener { documentSnapshot ->


                if (documentSnapshot.documents.isEmpty()) {
                    binding.tvNoTask.visibility = View.VISIBLE
                    binding.rvNotes.visibility = View.GONE
                } else {

                    binding.tvNoTask.visibility = View.GONE
                    binding.rvNotes.visibility = View.VISIBLE


                    for (i in documentSnapshot.documents) {
                        if (i.exists()) {
                            //Get the data from i
                            val map = i.data
                            val sKey = map?.get("STARS").toString()
                            val tKey = map?.get("TASKS").toString()
                            val __id = i.id
                            val date = map?.get("DATE").toString()
                            val task = Task(tKey, sKey, __id, date)
                            adapterList.add(task)

                        } else {
                            Log.e(TAG, "Unable to load the tasks")
                        }
                    }


                    adapter.lst = adapterList
                    binding.rvNotes.adapter = adapter
                    adapter.notifyDataSetChanged()

                }


            }
            .addOnFailureListener(object : OnFailureListener {
                override fun onFailure(p0: java.lang.Exception) {
                    Toast.makeText(context, p0.message, Toast.LENGTH_SHORT).show()
                }
            })


    }

    override fun clickThisItem(_listItem: Task) {

        val bundle = bundleOf(
            "stars" to _listItem.numStars,
            "task" to _listItem.description,
            "id" to _listItem._id,
            "date" to _listItem.date
        )


        findNavController().navigate(
            R.id.action_nav_home_to_openTaskFragment, bundle
        )


    }
}
