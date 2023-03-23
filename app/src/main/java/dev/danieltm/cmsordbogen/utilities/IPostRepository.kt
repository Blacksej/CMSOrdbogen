package dev.danieltm.cmsordbogen.utilities

import dev.danieltm.cmsordbogen.Models.PostModel
import java.time.LocalDate

interface IPostRepository {

    fun getAllPosts() : List<PostModel>

    fun getRecentPosts() : List<PostModel>

    fun createNewsPost(type: String, title: String, body: String,
                       startDate: LocalDate, endDate: LocalDate,
                       creationDate: LocalDate, author: String)

    fun updatePost()
}