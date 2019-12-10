package fr.appsolute.tp.data.networking.datasource

import android.util.Log
import androidx.paging.DataSource
import androidx.paging.PageKeyedDataSource
import fr.appsolute.tp.data.model.Character
import fr.appsolute.tp.data.networking.api.CharacterApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * DataSource use for the paginated api ([CharacterApi.getAllCharacter])
 */
class CharacterDataSource private constructor(
    private val api: CharacterApi,
    private val scope: CoroutineScope
) : PageKeyedDataSource<Int, Character>() {

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, Character>
    ) {
        scope.launch(Dispatchers.IO) {
            try {
                val response = api.getAllCharacter(page = FIRST_KEY).run {
                    if (this.isSuccessful) this.body()
                        ?: throw IllegalStateException("Body is null")
                    else throw IllegalStateException("Response is not successful : code = ${this.code()}")
                }
                if (params.placeholdersEnabled) callback.onResult(
                    response.results,
                    0,
                    response.information.count,
                    null,
                    if (response.information.next.isNotEmpty()) FIRST_KEY + 1 else null
                ) else callback.onResult(
                    response.results,
                    null,
                    if (response.information.next.isNotEmpty()) FIRST_KEY + 1 else null
                )

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Character>) {
        scope.launch(Dispatchers.IO) {
            try {
                val response = api.getAllCharacter(page = params.key).run {
                    if (this.isSuccessful) this.body()
                        ?: throw IllegalStateException("Body is null")
                    else throw IllegalStateException("Response is not successful : code = ${this.code()}")
                }
                callback.onResult(
                    response.results,
                    if (response.information.next.isNotEmpty()) params.key + 1 else null
                )
            } catch (e: Exception) {
                Log.e(TAG, "loadInitial: ", e)
            }
        }
    }

    // This method will not be used in this app
    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Character>) = Unit

    class Factory(
        private val api: CharacterApi,
        private val scope: CoroutineScope
    ) : DataSource.Factory<Int, Character>() {
        override fun create(): DataSource<Int, Character> =
            CharacterDataSource(
                api, scope
            )
    }

    companion object {
        private const val TAG: String = "CharacterDataSource"
        private const val FIRST_KEY = 1
    }

}