package ca.elina.recipesapp.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import ca.elina.recipesapp.util.Constants.Companion.FAVORITE_RECIPES_TABLE
import ca.elina.recipesapp.models.Result

@Entity(tableName = FAVORITE_RECIPES_TABLE)
class FavoritesEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    var result: Result
)