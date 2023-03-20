package dev.danieltm.cmsordbogen.utilities

import dev.danieltm.cmsordbogen.Models.PostModel

interface IPostRepository {

    fun getAllPosts() : List<PostModel>

    fun getRecentPosts() : List<PostModel>

    fun createPost()

    fun updatePost()
}