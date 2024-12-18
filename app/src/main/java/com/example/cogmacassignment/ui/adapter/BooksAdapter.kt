package com.example.cogmacassignment.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.cogmacassignment.R
import com.example.cogmacassignment.room.entities.Book
import java.text.SimpleDateFormat
import java.util.Locale

class BooksAdapter(val noteListener : NoteItemClickListener) : RecyclerView.Adapter<BooksAdapter.BooksViewHolder>(){

    private val itemList = ArrayList<Book>()
    private val fullList = ArrayList<Book>()

    class BooksViewHolder(item : View): RecyclerView.ViewHolder(item){
        val title = item.findViewById<TextView>(R.id.title)
        val auther = item.findViewById<TextView>(R.id.auther)
        val genre = item.findViewById<TextView>(R.id.genre)
        val publishDate = item.findViewById<TextView>(R.id.publication_date)
        val readStatus = item.findViewById<TextView>(R.id.readStatus)
        val edit = item.findViewById<ImageView>(R.id.edit)
        val layout = item.findViewById<CardView>(R.id.cardview)
    }

    interface NoteItemClickListener{
        fun onItemClicked(book: Book)
        fun onLongItemClicked(book: Book, cardView: CardView)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BooksViewHolder {
        return BooksViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.book_item_list, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: BooksViewHolder, position: Int) {
        val item = itemList[position]

        holder.title.text = "Title: ${item.title}"
        holder.auther.text = "Auther: ${item.author}"
        holder.genre.text = "Genre: ${item.genre}"
        holder.publishDate.text = "Publication Date: ${item.publicationDate}"

        if (item.readStatus){
            holder.readStatus.text = "Read Status: ON"
        }else{
            holder.readStatus.text = "Read Status: OFF"
        }

        holder.edit.setOnClickListener {
            noteListener.onItemClicked(itemList[holder.adapterPosition])
        }

        holder.layout.setOnLongClickListener{
            noteListener.onLongItemClicked(itemList[holder.adapterPosition], holder.layout)
            true
        }

    }

    fun filterList(search : String){
        itemList.clear()
        for (item in fullList){
            if (item.title?.lowercase()?.contains(search.lowercase()) == true ||
                item.author?.lowercase()?.contains(search.lowercase()) == true){
                itemList.add(item)
            }
        }

        notifyDataSetChanged()
    }

    fun sortBooksByPublicationDate(books: List<Book>) {
        val dateFormat = SimpleDateFormat("dd/MMM/yyyy", Locale.getDefault())

        val newList = books.sortedBy { book ->
            dateFormat.parse(book.publicationDate)
        }

        fullList.clear()
        fullList.addAll(newList)

        itemList.clear()
        itemList.addAll(fullList)

        notifyDataSetChanged()
    }

     fun sortBooksByPublicationDateDescending(books: List<Book>) {
        val dateFormat = SimpleDateFormat("dd/MMM/yyyy", Locale.getDefault())

        val newList = books.sortedByDescending { book ->
            dateFormat.parse(book.publicationDate)
        }

        fullList.clear()
        fullList.addAll(newList)

        itemList.clear()
        itemList.addAll(fullList)

        notifyDataSetChanged()
    }
}