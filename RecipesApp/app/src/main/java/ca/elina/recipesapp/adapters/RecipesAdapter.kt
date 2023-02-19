package ca.elina.recipesapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import ca.elina.recipesapp.databinding.RecipesRowLayoutBinding
import ca.elina.recipesapp.models.FoodRecipe
import ca.elina.recipesapp.models.Result
import ca.elina.recipesapp.util.RecipesDiffUtil


class RecipesAdapter : RecyclerView.Adapter<RecipesAdapter.MyViewHolder>() {

    private var recipes = emptyList<Result>()

    class MyViewHolder(private val binding: RecipesRowLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(result: Result) {
            binding.result = result
            binding.executePendingBindings() // this will update our layout whenever there is a change inside our data
        }

        companion object {
            fun from(parent: ViewGroup): MyViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = RecipesRowLayoutBinding.inflate(layoutInflater, parent, false)
                return MyViewHolder(binding)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentRecipe = recipes[position]
        holder.bind(currentRecipe) // update recycler view every time we receive new data from API
    }

    override fun getItemCount(): Int {
        return recipes.size
    }

    fun setData(newData: FoodRecipe) {
        val recipesDiffUtil =
            RecipesDiffUtil(recipes, newData.results)
        val diffUtilResult = DiffUtil.calculateDiff(recipesDiffUtil)
        recipes = newData.results
        // this will update only the views that are not the same
        // and it will increase the performance of the application
        diffUtilResult.dispatchUpdatesTo(this)
    }
}