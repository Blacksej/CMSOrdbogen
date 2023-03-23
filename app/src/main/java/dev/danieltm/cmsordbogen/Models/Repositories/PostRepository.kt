package dev.danieltm.cmsordbogen.Models.Repositories

import android.net.Uri
import dev.danieltm.cmsordbogen.Models.PostModel
import dev.danieltm.cmsordbogen.utilities.IPostRepository
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class PostRepository : IPostRepository {

    override fun getAllPosts(): List<PostModel> {
        var formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")
        var posts = listOf(
            PostModel(
                title = "Ny ordbog",
                body = "Der er en ny ordbog ude lige nu, tjek den ud",
                type = "NYHED",
                site = emptyList<String>(),
                author = "Daniel",
                uris = emptyList<Uri>(),
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
                uris = emptyList<Uri>(),
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
                uris = emptyList<Uri>(),
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
                uris = emptyList<Uri>(),
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
                uris = emptyList<Uri>(),
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
                uris = emptyList<Uri>(),
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
                uris = emptyList<Uri>(),
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
                uris = emptyList<Uri>(),
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
                uris = emptyList<Uri>(),
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
                uris = emptyList<Uri>(),
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
                uris = emptyList<Uri>(),
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
                uris = emptyList<Uri>(),
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
                uris = emptyList<Uri>(),
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
                uris = emptyList<Uri>(),
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
                uris = emptyList<Uri>(),
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
                uris = emptyList<Uri>(),
                startDate = LocalDate.now(),
                endDate = LocalDate.now().plusDays(2),
                creationTime = LocalDate.now()
            ),

        )
        return posts
    }

    override fun getRecentPosts() : List<PostModel> {
        TODO("Not yet implemented")
    }

    override fun createNewsPost(type: String, title: String, body: String,
                                startDate: LocalDate, endDate: LocalDate,
                                creationDate: LocalDate, author: String)
    {
        TODO("Not yet implemented")
    }

    override fun updatePost() {
        TODO("Not yet implemented")
    }

}