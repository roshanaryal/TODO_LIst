package com.aarappstudios.todofromskillshare.fragments.add

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.aarappstudios.todofromskillshare.R
import com.aarappstudios.todofromskillshare.data.models.TodoData
import com.aarappstudios.todofromskillshare.data.viewmodal.ToDoViewModal
import com.aarappstudios.todofromskillshare.fragments.SharedViewModal
import kotlinx.android.synthetic.main.fragment_create.*
import kotlinx.android.synthetic.main.fragment_create.view.*


class AddFragment : Fragment() {

    private val toDoViewModal: ToDoViewModal by viewModels()
    private val sharedViewModal:SharedViewModal by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_create, container, false)
        setHasOptionsMenu(true)
        view.spinner_upd.onItemSelectedListener=sharedViewModal.listner
        return view
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.add_menu, menu)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_save_add) {
            addDataToDatabase()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun addDataToDatabase() {
        val title = title_et_upd.text.toString()
        var priority = spinner_upd.selectedItem.toString()
        val description = description_et_upd.text.toString()

        if (sharedViewModal.verifyData(title, description)) {

            var todoData = TodoData(
                0,
                title,
                sharedViewModal.getPriority(priority),
                description
            )
            toDoViewModal.insertData(todoData)
            Toast.makeText(requireContext(), "Todo saved succesfully", Toast.LENGTH_SHORT).show()
           description_et_upd.isFocusable=false
            findNavController().navigate(R.id.action_createFragment_to_listFragment)
        } else {
            Toast.makeText(requireContext(), "Enter requirefield", Toast.LENGTH_SHORT).show()

        }


    }



}