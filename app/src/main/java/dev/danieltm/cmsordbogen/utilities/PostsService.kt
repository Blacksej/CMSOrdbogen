package dev.danieltm.cmsordbogen.utilities

import dev.danieltm.cmsordbogen.Models.Repositories.PostRepository
import io.ktor.client.*
import io.ktor.client.engine.android.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json

interface PostsService {

    //suspend fun getPosts(lat: String, long: String): PostRepository
    //suspend fun getPostsFromSearch(city: String): WeatherModelResponse.Welcome

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