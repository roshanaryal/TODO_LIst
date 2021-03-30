package com.aarappstudios.todofromskillshare.fragments.list

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.aarappstudios.todofromskillshare.R
import com.aarappstudios.todofromskillshare.data.viewmodal.ToDoViewModal
import com.aarappstudios.todofromskillshare.databinding.FragmentListBinding
import com.aarappstudios.todofromskillshare.fragments.SharedViewModal
import com.aarappstudios.todofromskillshare.fragments.list.adapter.ListAdapter
import com.aarappstudios.todofromskillshare.hideKeyboard
import com.aarappstudios.todofromskillshare.observeOnce
import com.google.android.material.snackbar.Snackbar
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator

class ListFragment : Fragment(), SearchView.OnQueryTextListener {

    private val listAdapter: ListAdapter by lazy { ListAdapter() }
    private val toDoViewModal: ToDoViewModal by viewModels()
    private val sharedViewModal: SharedViewModal by viewModels()

    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.mSharedViewModal = sharedViewModal

        setupRecyclerView()
        toDoViewModal.getAllData.observe(viewLifecycleOwner, Observer { data ->
            listAdapter.addData(data)
            sharedViewModal.checkDataIfempty(data)
        })



        //set menu
        setHasOptionsMenu(true)
        hideKeyboard(requireActivity())
        return binding.root
    }

    private fun swipeToDelete(recyclerView: RecyclerView) {
        val onswipecallback = object : SwipeToDelete() {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val itemtodelete = listAdapter.dataList[viewHolder.adapterPosition]
                toDoViewModal.deleteData(itemtodelete.id)
                listAdapter.notifyItemRemoved(viewHolder.adapterPosition)


                var snackbar = Snackbar.make(
                    recyclerView,
                    "Restore ${itemtodelete.title} ",
                    Snackbar.LENGTH_LONG
                )
                snackbar.setAction("Yes") {
                    toDoViewModal.insertData(itemtodelete)
                    Toast.makeText(
                        requireContext(),
                        "${itemtodelete.title}restored succesfully",
                        Toast.LENGTH_SHORT
                    ).show()
                    //listAdapter.notifyItemInserted(viewHolder.adapterPosition)
                }
                snackbar.show()
            }
        }
        val itemTouchHelper = ItemTouchHelper(onswipecallback)
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }

    private fun setupRecyclerView() {
        var recyclerView = binding.recyclerviewListFragment
        recyclerView.adapter = listAdapter
        recyclerView.itemAnimator = SlideInUpAnimator().apply {
            addDuration = 300
        }
        recyclerView.layoutManager =
            StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        swipeToDelete(recyclerView)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.list_menu, menu)
        val search = menu.findItem(R.id.menu_search)
        val searchView = search.actionView as? SearchView
        searchView?.isSubmitButtonEnabled = true
        searchView?.setOnQueryTextListener(this)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.delete_all -> {
                confirmRemoval()
            }
            R.id.menu_priority_high->{
              toDoViewModal.sortByHighPriority.observe(viewLifecycleOwner, {
                  listAdapter.addData(it)
              })
            }
            R.id.menu_priority_low->{
                toDoViewModal.sortByLowPriority.observe(viewLifecycleOwner, {
                    listAdapter.addData(it)
                })
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun confirmRemoval() {
        val builder = androidx.appcompat.app.AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Yes") { _, _ ->
            toDoViewModal.deleteAll()
            Toast.makeText(
                requireContext(), "all  data deleted succesfully",
                Toast.LENGTH_SHORT
            ).show()

        }
            .setNegativeButton("No") { _, _ ->

            }
            .setTitle("Delete all list ?")
            .setMessage("Are you sure to delete all data from list ?")
            .create().show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        if (query != null) {
            searchThroughDatabase(query)
        }
        return true
    }

    private fun searchThroughDatabase(query: String) {
        var searchQuery = query
        searchQuery = "%$searchQuery%"
        toDoViewModal.searchAll(searchQuery).observeOnce(viewLifecycleOwner, { list ->
            list?.let {
                listAdapter.addData(it)
            }
        })
    }

    override fun onQueryTextChange(query: String?): Boolean {
        if (query != null) {
            searchThroughDatabase(query)
        }
        return true
    }


}