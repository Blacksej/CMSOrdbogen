package dev.danieltm.cmsordbogen.Models.Repositories

import android.net.Uri
import dev.danieltm.cmsordbogen.Models.PostModel
import dev.danieltm.cmsordbogen.utilities.IPostRepository
import dev.danieltm.cmsordbogen.utilities.PostsService
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class PostRepository(
    //Dependency injection
    private val client: HttpClient
) : PostsService
{


    // Function to fetch ALL posts from the API
    override suspend fun getAllPosts(): List<PostModel> {

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
        TODO("Not yet implemented")
    }

    override suspend fun createNewsPost(postModel: PostModel)
    {
        val reponse: HttpResponse = client.post("https://10.0.2.2:44316/api/Contents"){
            contentType(ContentType.Application.Json)
            setBody(postModel)
        }

    }

    override suspend fun updatePost(postModel: PostModel) {
        TODO("Not yet implemented")
    }

}