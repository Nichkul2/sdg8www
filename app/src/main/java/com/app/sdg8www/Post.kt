package com.app.sdg8www

class Post {
    var author: String? = null
    var content: String? = null
    var date: String? = null
    var likeCount: Int? = 0
    constructor(author: String, content: String, date: String, likeCount: Int ) {
        this.author = author
        this.content = content
        this.date = date
        this.likeCount = likeCount
    }
}