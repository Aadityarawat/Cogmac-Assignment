package com.example.cogmacassignment.ui

import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.PopupMenu
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.cogmacassignment.R
import com.example.cogmacassignment.databinding.FragmentBookBinding
import com.example.cogmacassignment.room.entities.Book
import com.example.cogmacassignment.ui.adapter.BooksAdapter
import com.example.cogmacassignment.viewModel.MainViewModel
import com.google.android.material.search.SearchView
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.Locale

@AndroidEntryPoint
class BookFragment : Fragment(), BooksAdapter.NoteItemClickListener, PopupMenu.OnMenuItemClickListener {

    private var ascSort = true
    lateinit var selectedBook : Book
    private val binding by lazy { FragmentBookBinding.inflate(layoutInflater) }
    lateinit var viewModel : MainViewModel
    lateinit var itemList : List<Book>
    private lateinit var booksAdapter: BooksAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]
        onClick()
        onObserve()
        onSetup()
        return binding.root
    }

    private fun onSetup() {
        val searchView = binding.searchView
        val searchIcon = searchView.findViewById<ImageView>(androidx.appcompat.R.id.search_mag_icon)
        searchIcon.setColorFilter(ContextCompat.getColor(requireContext(), R.color.black), PorterDuff.Mode.SRC_IN)

        val searchAutoComplete = searchView.findViewById<TextView>(androidx.appcompat.R.id.search_src_text)
        searchAutoComplete.setTextColor(ContextCompat.getColor(requireContext(), R.color.black)) // Text color



        binding.recyclerview.layoutManager = LinearLayoutManager(requireContext())
        booksAdapter = BooksAdapter(this)
        binding.recyclerview.adapter = booksAdapter
    }

    private fun onObserve() {
        viewModel.getBooks().observe(viewLifecycleOwner){
            if (!it.isNullOrEmpty()){
                binding.noData.visibility = View.GONE
                binding.recyclerview.visibility = View.VISIBLE
                itemList = it
                booksAdapter.sortBooksByPublicationDate(it)
            }else{
                binding.noData.visibility = View.VISIBLE
                binding.recyclerview.visibility = View.GONE
            }

        }
    }


    private fun onClick() {
        binding.sortingIV.setOnClickListener {
            if (ascSort){
                ascSort = false
                binding.sortingIV.setImageResource(R.drawable.desc)
                booksAdapter.sortBooksByPublicationDateDescending(itemList)

            }else{
                ascSort = true
                binding.sortingIV.setImageResource(R.drawable.asc)
                booksAdapter.sortBooksByPublicationDate(itemList)
            }
        }

        binding.addBtn.setOnClickListener{
            activity?.supportFragmentManager?.beginTransaction()?.replace(R.id.container, AddBookFragment())?.addToBackStack(null)?.commit()
        }

        binding.searchView.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText != null){
                    booksAdapter.filterList(newText)
                }
                return true
            }

        })
    }

    override fun onItemClicked(book: Book) {
        //viewModel.bookData = book
        val fragment = AddBookFragment()
        val bundle = Bundle()
        bundle.putSerializable("book",book)
        fragment.arguments = bundle
        activity?.supportFragmentManager
            ?.beginTransaction()
            ?.replace(R.id.container, fragment)
            ?.addToBackStack(null)
            ?.commit()
    }

    override fun onLongItemClicked(book: Book, cardView: CardView) {
        selectedBook = book
        popupMenu(cardView)
    }

    private fun popupMenu(cardView: CardView){
        val popup = PopupMenu(requireContext(), cardView)
        popup.setOnMenuItemClickListener(this@BookFragment)
        popup.inflate(R.menu.pop_up_menu)
        popup.show()
    }

    override fun onMenuItemClick(item: MenuItem?): Boolean {
        if (item?.itemId == R.id.delete_note){

            viewModel.deleteBook(selectedBook)
            return true
        }
        return false
    }

}