package dev.danieltm.cmsordbogen.Models.Repositories

import android.net.Uri
import androidx.compose.ui.text.toLowerCase
import dev.danieltm.cmsordbogen.Models.PostModel
import dev.danieltm.cmsordbogen.utilities.IPostRepository
import dev.danieltm.cmsordbogen.utilities.PostsService
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

class PostRepository(
    //Dependency injection
    private val client: HttpClient
) : PostsService
{


    // Function to fetch ALL posts from the API
    override suspend fun getAllPosts(): List<PostModel> {

        /*var get = client.get{ url("https://ascendance.hrmoller.com/api/contents") }
        var body = get.body<List<PostModel>>()

        body.forEach(){ post ->
            if(post.type.lowercase() == "news"){
                post.type = "NYHED"
            }
        }

        return body*/

        // Dummy data until i get access to an API
        var formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")
        var posts = listOf(
            PostModel(
                id = 2,
                title = "Ny ordbog",
                body = "Der er en ny ordbog ude lige nu, tjek den ud",
                type = "NYHED",
                sites = emptyList<String>(),
                //author = "Daniel",
                image = null,
                startDate = LocalDate.now().toString(),
                endDate = LocalDate.now().plusDays(2).toString(),
                //creationTime = LocalDate.now()
            ),
            PostModel(
                id = 3,
                title = "Ny ordbog",
                body = "Der er en ny ordbog ude lige nu, tjek den ud",
                type = "NYHED",
                sites = emptyList<String>(),
                //author = "Daniel",
                image = null,
                startDate = LocalDate.now().toString(),
                endDate = LocalDate.now().plusDays(2).toString(),
                //creationTime = LocalDate.now()
            ),
            PostModel(
                id = 4,
                title = "Ny ordbog",
                body = "Der er en ny ordbog ude lige nu, tjek den ud",
                type = "NYHED",
                sites = emptyList<String>(),
                //author = "Daniel",
                image = null,
                startDate = LocalDate.now().toString(),
                endDate = LocalDate.now().plusDays(2).toString(),
                //creationTime = LocalDate.now()
            ),
            PostModel(
                id = 5,
                title = "Ny ordbog",
                body = "Der er en ny ordbog ude lige nu, tjek den ud",
                type = "NYHED",
                sites = emptyList<String>(),
                //author = "Daniel",
                image = null,
                startDate = LocalDate.now().toString(),
                endDate = LocalDate.now().plusDays(2).toString(),
                //creationTime = LocalDate.now()
            )

        )
        return posts
    }

    override suspend fun getRecentPosts() : List<PostModel> {

        return emptyList()
        //var get = client.get{ url("https://ascendance.hrmoller.com/api/contents") }
        //var body = get.body<List<PostModel>>()
        //return body
    }

    override suspend fun createNewsPost(postModel: PostModel)
    {
        val tempPosts = getAllPosts()
        val biggestIdObject = tempPosts.maxByOrNull { it.id }

        if(biggestIdObject != null){

            postModel.id = biggestIdObject.id + 1

            val response: HttpResponse = client.post("https://ascendance.hrmoller.com/api/contents"){
                contentType(ContentType.Application.Json)
                setBody(postModel)
            }
        }

    }

    override suspend fun updatePost(postModel: PostModel) {
        TODO("Not yet implemented")
    }

    override suspend fun deletePost(postId: Int) {
        val response: HttpResponse = client.delete("https://ascendance.hrmoller.com/api/contents/${postId}")
        var response1 = response.toString()
    }

}