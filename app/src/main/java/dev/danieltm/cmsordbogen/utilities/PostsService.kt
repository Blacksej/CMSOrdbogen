package dev.danieltm.cmsordbogen.utilities

import dev.danieltm.cmsordbogen.Models.PostModel
import dev.danieltm.cmsordbogen.Models.Repositories.PostRepository
import io.ktor.client.*
import io.ktor.client.engine.android.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json

interface PostsService {
    suspend fun getAllPosts() : List<PostModel>
    suspend fun getRecentPosts() : List<PostModel>
    suspend fun createNewsPost(postModel: PostModel)
    suspend fun updatePost(postModel: PostModel)

    companion object{
        fun create() : PostsService{
            return PostRepository (
                client = HttpClient(Android){
                    install(ContentNegotiation){
                        json(Json{
                            prettyPrint = true
                            isLenient = true
                        })
                    }
                }
            )
        }
    }
}