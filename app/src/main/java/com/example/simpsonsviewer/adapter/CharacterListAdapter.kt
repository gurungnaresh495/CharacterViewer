package com.example.simpsonsviewer.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.simpsonsviewer.data.CharacterInfo
import com.example.simpsonsviewer.databinding.RecyclerItemCharacterBinding
import okhttp3.internal.filterList

/*
    Custom adapter class to view items in recyclerview
 */
class CharacterListAdapter(private val listener: ItemClickListener): RecyclerView.Adapter<CharacterListAdapter.CharacterListViewHolder>() {

    // Original character list to display items once search query is cleared or search query is changed
    private var originalCharactersInfoList: MutableList<CharacterInfo> = mutableListOf()
    // List to display current items in recycler view
    private var charactersInfoList: MutableList<CharacterInfo> = mutableListOf()

    inner class CharacterListViewHolder(private val binding: RecyclerItemCharacterBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind( character: CharacterInfo){
            binding.tvCharacterName.text = character.characterInfo.substringBefore("-", "Name not found")
            binding.root.setOnClickListener {
                listener.onItemClicked(charactersInfoList[adapterPosition])
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterListViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = RecyclerItemCharacterBinding.inflate(layoutInflater, parent, false)
        return CharacterListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CharacterListViewHolder, position: Int) {
        holder.bind(charactersInfoList[position])
    }

    override fun getItemCount(): Int = charactersInfoList.size

    fun updateList(list: List<CharacterInfo>){

        val diffResult = DiffUtil.calculateDiff(DiffUtilCallback(this.charactersInfoList, list))
        diffResult.dispatchUpdatesTo(this)

        this.charactersInfoList.clear()
        originalCharactersInfoList = list.toMutableList()
        charactersInfoList.addAll(list)
    }

    interface ItemClickListener {
        fun onItemClicked(character: CharacterInfo)
    }

    fun filterList(searchText: String){
        val tempList = originalCharactersInfoList.filterList {
            this.characterInfo.substringBefore("-").contains(searchText, ignoreCase = true) ||
                    this.characterInfo.substringAfter("-").contains(searchText, ignoreCase = true)
        }.toMutableList()

        val diffResult = DiffUtil.calculateDiff(DiffUtilCallback(this.charactersInfoList, tempList))
        diffResult.dispatchUpdatesTo(this)

        this.charactersInfoList.clear()
        charactersInfoList.addAll(tempList
        )
    }

    /*
        Custom DiffUtilCallback class to calculate minimum changes required to change from one list to another

        Used to avoid using notifyDataSetChanged()
     */
    inner class DiffUtilCallback(private val oldList: List<CharacterInfo>, private val newList: List<CharacterInfo>) : DiffUtil.Callback(){
        override fun getOldListSize(): Int =oldList.size
        override fun getNewListSize(): Int = newList.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            val oldItem = oldList[oldItemPosition]
            val newItem = newList[newItemPosition]
            return newItem.characterInfo.substringBefore("-") == oldItem.characterInfo.substringBefore("-")
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {

            val oldItem = oldList[oldItemPosition]
            val newItem = newList[newItemPosition]
            return when {
                oldItem.imageInfo != newItem.imageInfo -> false
                oldItem.characterInfo.substringAfter("-") != newItem.characterInfo.substringAfter("-") -> false
                else -> true
            }
        }

    }
}