package ca.elina.recipesapp.data

import ca.elina.recipesapp.data.network.FoodRecipesApi
import com.example.foody.models.FoodRecipe
import retrofit2.Response
import javax.inject.Inject

// Constructor Injection tells which constructor to use to provide instances
// and which dependencies the type has.
class RemoteDataSource @Inject constructor(private val foodRecipesApi: FoodRecipesApi) {

    suspend fun getRecipes(queries: Map<String, String>): Response<FoodRecipe> {
        return foodRecipesApi.getRecipes(queries)
    }

}