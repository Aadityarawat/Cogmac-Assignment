package com.example.cogmacassignment.ui

import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.cogmacassignment.R
import com.example.cogmacassignment.databinding.FragmentAddBookBinding
import com.example.cogmacassignment.room.entities.Book
import com.example.cogmacassignment.viewModel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

@AndroidEntryPoint
class AddBookFragment : Fragment() {

    private val binding by lazy{ FragmentAddBookBinding.inflate(layoutInflater) }
    lateinit var mainViewModel : MainViewModel
    private val calendar = Calendar.getInstance()
    var isUpdate = false
    lateinit var newBook : Book
    var oldBook: Book? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mainViewModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]
        try {
            oldBook = arguments?.getSerializable("book") as Book
        }catch (e : Exception){
            println(e)
        }

        setup()
        return binding.root
    }

    private fun setup() {
        binding.readET.setText("OFF")
        try {
            //oldBook = mainViewModel.bookData!!
            binding.titleET.setText(oldBook!!.title)
            binding.autherET.setText(oldBook!!.author)
            binding.genreET.setText(oldBook!!.genre)
            if (oldBook!!.readStatus){
                binding.readSwitch.isChecked = true
                binding.readET.setText("ON")
            }else{
                binding.readSwitch.isChecked = false
                binding.readET.setText("OFF")
            }

            if (oldBook != null){
                binding.calendarIV.visibility = View.GONE
                binding.dateContainer.visibility = View.GONE
            }else{
                binding.calendarIV.visibility = View.VISIBLE
                binding.dateContainer.visibility = View.VISIBLE
            }

            binding.inputForm.text = "Update Book"

            isUpdate = true

        }catch (e : Exception){
            binding.inputForm.text = "Add Book"
            e.printStackTrace()
        }

        binding.calendarIV.setOnClickListener {
            showDatePicker()
        }

        binding.readSwitch.setOnClickListener {
            binding.readET.setText("OFF")
            if(binding.readSwitch.isChecked){
                binding.readET.setText("ON")
            }
        }

        binding.submitBtn.setOnClickListener {
            val title = binding.titleET.text.toString()
            val auther = binding.autherET.text.toString()
            val genre = binding.genreET.text.toString()
            val readStatus = binding.readSwitch.isChecked
            val publishedDate = binding.dateET.text.toString()

            if (isUpdate){
                newBook = Book(
                    oldBook!!.id,
                    title,
                    author = auther,
                    genre = genre,
                    publicationDate = oldBook!!.publicationDate,
                    readStatus = readStatus
                )

                isUpdate = false
                if (title.isNotBlank() && auther.isNotBlank() && genre.isNotBlank()){
                    mainViewModel.updateBook(newBook)
                    activity?.onBackPressedDispatcher?.onBackPressed()
                }else{
                    Toast.makeText(requireContext(), "Please fill all the details",Toast.LENGTH_SHORT).show()
                }

                mainViewModel.bookData = null
            }
            else{
                newBook = Book(
                    0,
                    title,
                    author = auther,
                    genre = genre,
                    publicationDate = publishedDate,
                    readStatus = true
                )
                if (title.isNotBlank() && auther.isNotBlank() && genre.isNotBlank() && publishedDate.isNotBlank()){
                    mainViewModel.insertBook(newBook)
                    activity?.onBackPressedDispatcher?.onBackPressed()
                }else{
                    Toast.makeText(requireContext(), "Please fill all the details",Toast.LENGTH_SHORT).show()
                }

            }

        }
    }

    private fun showDatePicker() {
        val datePicker = DatePickerDialog(
            requireContext(), { DatePicker, year: Int, monthOfYear: Int, dayOfMonth: Int ->

                val selectedDate = Calendar.getInstance()
                selectedDate.set(year, monthOfYear, dayOfMonth)
                val dateFormat = SimpleDateFormat("dd/MMM/yyyy", Locale.getDefault())
                val formattedDate = dateFormat.format(selectedDate.time)
                binding.dateET.setText(formattedDate)

            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
        datePicker.show()
    }

}