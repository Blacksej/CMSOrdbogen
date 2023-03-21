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
                name = "Ny ordbog",
                body = "Der er en ny ordbog ude lige nu, tjek den ud",
                type = "Nyhed",
                uri = null,
                uris = emptyList<Uri>(),
                startDate = LocalDate.now(),
                endDate = LocalDate.now().plusDays(2)
            ),
            PostModel(
                name = "Nyt event",
                body = "Vi spiser pizza og kører gokart",
                type = "Event",
                uri = null,
                uris = emptyList<Uri>(),
                startDate = LocalDate.now(),
                endDate = LocalDate.now().plusDays(2)
            ),
            PostModel(
                name = "Møde med partner",
                body = "Vores partner kommer på besøg, og derfor skal der holdes møde",
                type = "Annoncering",
                uri = null,
                uris = emptyList<Uri>(),
                startDate = LocalDate.now(),
                endDate = LocalDate.now().plusDays(2)
            ),
            PostModel(
                name = "Ny ordbog",
                body = "Der er en ny ordbog ude lige nu, tjek den ud",
                type = "Nyhed",
                uri = null,
                uris = emptyList<Uri>(),
                startDate = LocalDate.now(),
                endDate = LocalDate.now().plusDays(2)
            ),
            PostModel(
                name = "Nyt event",
                body = "Vi spiser pizza og kører gokart",
                type = "Event",
                uri = null,
                uris = emptyList<Uri>(),
                startDate = LocalDate.now(),
                endDate = LocalDate.now().plusDays(2)
            ),
            PostModel(
                name = "Møde med partner",
                body = "Vores partner kommer på besøg, og derfor skal der holdes møde",
                type = "Annoncering",
                uri = null,
                uris = emptyList<Uri>(),
                startDate = LocalDate.now(),
                endDate = LocalDate.now().plusDays(2)
            ),
            PostModel(
                name = "Ny ordbog JAA",
                body = "Der er en ny ordbog ude lige nu, tjek den ud",
                type = "Nyhed",
                uri = null,
                uris = emptyList<Uri>(),
                startDate = LocalDate.now(),
                endDate = LocalDate.now().plusDays(2)
            ),
            PostModel(
                name = "Nyt event",
                body = "Vi spiser pizza og kører gokart",
                type = "Event",
                uri = null,
                uris = emptyList<Uri>(),
                startDate = LocalDate.now(),
                endDate = LocalDate.now().plusDays(2)
            ),
            PostModel(
                name = "Møde med partner",
                body = "Vores partner kommer på besøg, og derfor skal der holdes møde",
                type = "Annoncering",
                uri = null,
                uris = emptyList<Uri>(),
                startDate = LocalDate.now(),
                endDate = LocalDate.now().plusDays(2)
            ),
            PostModel(
                name = "Ny ordbog",
                body = "Der er en ny ordbog ude lige nu, tjek den ud",
                type = "Nyhed",
                uri = null,
                uris = emptyList<Uri>(),
                startDate = LocalDate.now(),
                endDate = LocalDate.now().plusDays(2)
            ),
            PostModel(
                name = "Nyt event",
                body = "Vi spiser pizza og kører gokart",
                type = "Event",
                uri = null,
                uris = emptyList<Uri>(),
                startDate = LocalDate.now(),
                endDate = LocalDate.now().plusDays(2)
            ),
            PostModel(
                name = "Møde med partner",
                body = "Vores partner kommer på besøg, og derfor skal der holdes møde",
                type = "Annoncering",
                uri = null,
                uris = emptyList<Uri>(),
                startDate = LocalDate.now(),
                endDate = LocalDate.now().plusDays(2)
            ),
            PostModel(
                name = "Ny ordbog",
                body = "Der er en ny ordbog ude lige nu, tjek den ud",
                type = "Nyhed",
                uri = null,
                uris = emptyList<Uri>(),
                startDate = LocalDate.now(),
                endDate = LocalDate.now().plusDays(2)
            ),
            PostModel(
                name = "Nyt event",
                body = "Vi spiser pizza og kører gokart",
                type = "Event",
                uri = null,
                uris = emptyList<Uri>(),
                startDate = LocalDate.now(),
                endDate = LocalDate.now().plusDays(2)
            ),
            PostModel(
                name = "Møde med partner",
                body = "Vores partner kommer på besøg, og derfor skal der holdes møde",
                type = "Annoncering",
                uri = null,
                uris = emptyList<Uri>(),
                startDate = LocalDate.now(),
                endDate = LocalDate.now().plusDays(2)
            ),
            PostModel(
                name = "Ny ordbog",
                body = "Der er en ny ordbog ude lige nu, tjek den ud",
                type = "Nyhed",
                uri = null,
                uris = emptyList<Uri>(),
                startDate = LocalDate.now(),
                endDate = LocalDate.now().plusDays(2)
            ),
            PostModel(
                name = "Nyt event",
                body = "Vi spiser pizza og kører gokart",
                type = "Event",
                uri = null,
                uris = emptyList<Uri>(),
                startDate = LocalDate.now(),
                endDate = LocalDate.now().plusDays(2)
            ),
            PostModel(
                name = "Møde med partner",
                body = "Vores partner kommer på besøg, og derfor skal der holdes møde",
                type = "Annoncering",
                uri = null,
                uris = emptyList<Uri>(),
                startDate = LocalDate.now(),
                endDate = LocalDate.now().plusDays(2)
            ),
        )
        return posts
    }

    override fun getRecentPosts() : List<PostModel> {
        TODO("Not yet implemented")
    }

    override fun createPost() {
        TODO("Not yet implemented")
    }

    override fun updatePost() {
        TODO("Not yet implemented")
    }

}