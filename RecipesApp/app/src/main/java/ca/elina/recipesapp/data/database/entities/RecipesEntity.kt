package ca.elina.recipesapp.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import ca.elina.recipesapp.models.FoodRecipe
import ca.elina.recipesapp.util.Constants.Companion.RECIPES_TABLE

@Entity(tableName = RECIPES_TABLE)
class RecipesEntity(
    var foodRecipe: FoodRecipe
) {
    @PrimaryKey(autoGenerate = false)
    var id: Int = 0 // default value is zero
}