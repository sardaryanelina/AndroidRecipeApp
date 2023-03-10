package ca.elina.recipesapp.data

import ca.elina.recipesapp.data.network.FoodRecipesApi
import ca.elina.recipesapp.models.FoodRecipe
import retrofit2.Response
import javax.inject.Inject

// Constructor Injection tells which constructor to use to provide instances
// and which dependencies the type has.
class RemoteDataSource @Inject constructor(private val foodRecipesApi: FoodRecipesApi) {

    suspend fun getRecipes(queries: Map<String, String>): Response<FoodRecipe> {
        return foodRecipesApi.getRecipes(queries)
    }

    suspend fun searchRecipes(searchQuery: Map<String, String>): Response<FoodRecipe> {
        return foodRecipesApi.searchRecipes(searchQuery)
    }
}