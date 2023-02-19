package ca.elina.recipesapp.util

import androidx.recyclerview.widget.DiffUtil

class RecipesDiffUtil<T>(
    private val oldList: List<T>,
    private val newList: List<T>
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    // Called by DiffUtil to decide whether two objects represent the same item
    // in the old and new list.
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] === newList[newItemPosition]
    }

    // Checks whether two items have the same data.
    // You can change its behaviour depending on your UI.
    // This method is called by DiffUtil only if areItemsTheSame returns true.
    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }
}