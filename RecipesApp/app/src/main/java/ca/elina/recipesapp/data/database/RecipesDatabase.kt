package ca.elina.recipesapp.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import ca.elina.recipesapp.data.database.entities.RecipesEntity

@Database(
    entities = [RecipesEntity::class],
    version = 1, // whenever we change the database schema, we need to increase the version number
    exportSchema = false // tell the room do not export the database schema into a folder,
    // it is optional and good for version control system, but we do not use it here
)

@TypeConverters(RecipesTypeConverter::class)
abstract class RecipesDatabase : RoomDatabase() {

    abstract fun recipesDao(): RecipesDao

}