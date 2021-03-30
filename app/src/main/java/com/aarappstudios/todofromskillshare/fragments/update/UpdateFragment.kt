package com.aarappstudios.todofromskillshare.fragments.update

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.aarappstudios.todofromskillshare.R
import com.aarappstudios.todofromskillshare.data.models.TodoData
import com.aarappstudios.todofromskillshare.data.viewmodal.ToDoViewModal
import com.aarappstudios.todofromskillshare.databinding.FragmentUpdateBinding
import com.aarappstudios.todofromskillshare.fragments.SharedViewModal
import kotlinx.android.synthetic.main.fragment_update.*
import kotlinx.android.synthetic.main.fragment_update.view.*


class UpdateFragment : Fragment() {


    private val args by navArgs<UpdateFragmentArgs>()
    private val sharedViewModal: SharedViewModal by viewModels()
    private val toDoViewModal: ToDoViewModal by viewModels()

    private var _binding: FragmentUpdateBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentUpdateBinding.inflate(inflater, container, false)
       // var view = inflater.inflate(R.layout.fragment_update, container, false)
        binding.args=args.currentitem

        // Inflate the layout for this fragment
        setHasOptionsMenu(true)
        binding.spinnerUpd.onItemSelectedListener=sharedViewModal.listner


        return binding.root
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.update_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_save_update) {
            updateDataToDatabase()
        } else if (item.itemId == R.id.delete_menu) {
            confirmRemoval()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun confirmRemoval() {
        val builder = androidx.appcompat.app.AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Yes") { _, _ ->
            toDoViewModal.deleteData(args.currentitem.id)
            Toast.makeText(
                requireContext(),
                "Item ${args.currentitem.title} deleted succesfully",
                Toast.LENGTH_SHORT
            ).show()
            findNavController().navigate(R.id.action_updateFragment_to_listFragment)
        }
            .setNegativeButton("No") { _, _ ->

            }
            .setTitle("Delete ?")
            .setMessage("Are you sure to delete ${args.currentitem.title} ?")
            .create().show()
    }


    private fun updateDataToDatabase() {
        val title = title_et_upd.text.toString()
        var priority = spinner_upd.selectedItem.toString()
        val description = description_et_upd.text.toString()

        if (sharedViewModal.verifyData(title, description)) {

            var todoData = TodoData(
                args.currentitem.id,
                title,
                sharedViewModal.getPriority(priority),
                description
            )
            toDoViewModal.updateData(todoData)
            Toast.makeText(requireContext(), "Todo saved succesfully", Toast.LENGTH_SHORT).show()
            description_et_upd.isFocusable = false
            findNavController().navigate(R.id.action_updateFragment_to_listFragment)
        } else {
            Toast.makeText(requireContext(), "Enter requirefield", Toast.LENGTH_SHORT).show()

        }


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
    }


}