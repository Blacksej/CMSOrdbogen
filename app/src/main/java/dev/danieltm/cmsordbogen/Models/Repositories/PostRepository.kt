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

    override suspend fun getAllPosts(): List<PostModel> {
        var formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")
        var posts = listOf(
            PostModel(
                title = "Ny ordbog",
                body = "Der er en ny ordbog ude lige nu, tjek den ud",
                type = "NYHED",
                site = emptyList<String>(),
                author = "Daniel",
                uri = null,
                startDate = LocalDate.now(),
                endDate = LocalDate.now().plusDays(2),
                creationTime = LocalDate.now()
            ),
            PostModel(
                title = "Ny ordbog",
                body = "Der er en ny ordbog ude lige nu, tjek den ud",
                type = "NYHED",
                site = emptyList<String>(),
                author = "Daniel",
                uri = null,
                startDate = LocalDate.now(),
                endDate = LocalDate.now().plusDays(2),
                creationTime = LocalDate.now()
            ),
            PostModel(
                title = "Ny ordbog",
                body = "Der er en ny ordbog ude lige nu, tjek den ud",
                type = "NYHED",
                site = emptyList<String>(),
                author = "Daniel",
                uri = null,
                startDate = LocalDate.now(),
                endDate = LocalDate.now().plusDays(2),
                creationTime = LocalDate.now()
            ),
            PostModel(
                title = "Ny ordbog",
                body = "Der er en ny ordbog ude lige nu, tjek den ud",
                type = "NYHED",
                site = emptyList<String>(),
                author = "Daniel",
                uri = null,
                startDate = LocalDate.now(),
                endDate = LocalDate.now().plusDays(2),
                creationTime = LocalDate.now()
            ),
            PostModel(
                title = "Gokart med gutterne",
                body = "Vi kører gokart, jaaaaaa",
                type = "EVENT",
                site = emptyList<String>(),
                author = "Daniel",
                uri = null,
                startDate = LocalDate.now(),
                endDate = LocalDate.now().plusDays(2),
                creationTime = LocalDate.now()
            ),
            PostModel(
                title = "Gokart med gutterne",
                body = "Vi kører gokart, jaaaaaa",
                type = "EVENT",
                site = emptyList<String>(),
                author = "Daniel",
                uri = null,
                startDate = LocalDate.now(),
                endDate = LocalDate.now().plusDays(2),
                creationTime = LocalDate.now()
            ),
            PostModel(
                title = "Gokart med gutterne",
                body = "Vi kører gokart, jaaaaaa",
                type = "EVENT",
                site = emptyList<String>(),
                author = "Daniel",
                uri = null,
                startDate = LocalDate.now(),
                endDate = LocalDate.now().plusDays(2),
                creationTime = LocalDate.now()
            ),
            PostModel(
                title = "Gokart med gutterne",
                body = "Vi kører gokart, jaaaaaa",
                type = "EVENT",
                site = emptyList<String>(),
                author = "Daniel",
                uri = null,
                startDate = LocalDate.now(),
                endDate = LocalDate.now().plusDays(2),
                creationTime = LocalDate.now()
            ),
            PostModel(
                title = "Der er vedligeholdelse på siden snart",
                body = "Siden vil være nede lidt",
                type = "ANNONCERING",
                site = emptyList<String>(),
                author = "Daniel",
                uri = null,
                startDate = LocalDate.now(),
                endDate = LocalDate.now().plusDays(2),
                creationTime = LocalDate.now()
            ),
            PostModel(
                title = "Der er vedligeholdelse på siden snart",
                body = "Siden vil være nede lidt",
                type = "ANNONCERING",
                site = emptyList<String>(),
                author = "Daniel",
                uri = null,
                startDate = LocalDate.now(),
                endDate = LocalDate.now().plusDays(2),
                creationTime = LocalDate.now()
            ),
            PostModel(
                title = "Der er vedligeholdelse på siden snart",
                body = "Siden vil være nede lidt",
                type = "ANNONCERING",
                site = emptyList<String>(),
                author = "Daniel",
                uri = null,
                startDate = LocalDate.now(),
                endDate = LocalDate.now().plusDays(2),
                creationTime = LocalDate.now()
            ),
            PostModel(
                title = "Abonner på vores nyhedsbrev",
                body = "Klik her for at abonnere",
                type = "PUSH",
                site = emptyList<String>(),
                author = "Daniel",
                uri = null,
                startDate = LocalDate.now(),
                endDate = LocalDate.now().plusDays(2),
                creationTime = LocalDate.now()
            ),
            PostModel(
                title = "Abonner på vores nyhedsbrev",
                body = "Klik her for at abonnere",
                type = "PUSH",
                site = emptyList<String>(),
                author = "Daniel",
                uri = null,
                startDate = LocalDate.now(),
                endDate = LocalDate.now().plusDays(2),
                creationTime = LocalDate.now()
            ),
            PostModel(
                title = "Abonner på vores nyhedsbrev",
                body = "Klik her for at abonnere",
                type = "PUSH",
                site = emptyList<String>(),
                author = "Daniel",
                uri = null,
                startDate = LocalDate.now(),
                endDate = LocalDate.now().plusDays(2),
                creationTime = LocalDate.now()
            ),
            PostModel(
                title = "Abonner på vores nyhedsbrev",
                body = "Klik her for at abonnere",
                type = "PUSH",
                site = emptyList<String>(),
                author = "Daniel",
                uri = null,
                startDate = LocalDate.now(),
                endDate = LocalDate.now().plusDays(2),
                creationTime = LocalDate.now()
            ),
            PostModel(
                title = "Abonner på vores nyhedsbrev",
                body = "Klik her for at abonnere",
                type = "PUSH",
                site = emptyList<String>(),
                author = "Daniel",
                uri = null,
                startDate = LocalDate.now(),
                endDate = LocalDate.now().plusDays(2),
                creationTime = LocalDate.now()
            ),

        )
        return posts
    }

    override suspend fun getRecentPosts() : List<PostModel> {
        TODO("Not yet implemented")
    }

    override suspend fun createNewsPost(postModel: PostModel)
    {
        val reponse: HttpResponse = client.post(""){
            contentType(ContentType.Application.Json)
            setBody(postModel)
        }

    }

    override suspend fun updatePost(postModel: PostModel) {
        TODO("Not yet implemented")
    }

}