package ca.elina.recipesapp.data.database

import androidx.room.*
import ca.elina.recipesapp.data.database.entities.RecipesEntity

import kotlinx.coroutines.flow.Flow

@Dao
interface RecipesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE) // whenever we fetch data from APi, we are going to replace the old one
    suspend fun insertRecipes(recipesEntity: RecipesEntity)

    @Query("SELECT * FROM recipes_table ORDER BY id ASC")
    fun readRecipes(): Flow<List<RecipesEntity>>
}