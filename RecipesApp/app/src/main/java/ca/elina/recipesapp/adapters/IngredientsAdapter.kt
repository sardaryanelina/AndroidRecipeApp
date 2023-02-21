package ca.elina.recipesapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import ca.elina.recipesapp.R
import ca.elina.recipesapp.databinding.IngredientsRowLayoutBinding
import ca.elina.recipesapp.models.ExtendedIngredient
import ca.elina.recipesapp.util.Constants.Companion.BASE_IMAGE_URL
import ca.elina.recipesapp.util.RecipesDiffUtil
import coil.load
import java.util.*

class IngredientsAdapter : RecyclerView.Adapter<IngredientsAdapter.MyViewHolder>() {

    private var ingredientsList = emptyList<ExtendedIngredient>()

    class MyViewHolder(val binding: IngredientsRowLayoutBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            IngredientsRowLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.binding.ingredientImageView.load(BASE_IMAGE_URL + ingredientsList[position].image) {
            crossfade(600)
            error(R.drawable.ic_error_placeholder)
        }
        holder.binding.ingredientName.text = ingredientsList[position].name.replaceFirstChar {
            if (it.isLowerCase()) it.titlecase(
                Locale.ROOT
            ) else it.toString()
        }
        holder.binding.ingredientAmount.text = ingredientsList[position].amount.toString()
        holder.binding.ingredientUnit.text = ingredientsList[position].unit
        holder.binding.ingredientConsistency.text = ingredientsList[position].consistency
        holder.binding.ingredientOriginal.text = ingredientsList[position].original
    }

    override fun getItemCount(): Int {
        return ingredientsList.size
    }

    fun setData(newIngredients: List<ExtendedIngredient>) {
        val ingredientsDiffUtil =
            RecipesDiffUtil(ingredientsList, newIngredients)
        val diffUtilResult = DiffUtil.calculateDiff(ingredientsDiffUtil)
        ingredientsList = newIngredients
        diffUtilResult.dispatchUpdatesTo(this)
    }

}