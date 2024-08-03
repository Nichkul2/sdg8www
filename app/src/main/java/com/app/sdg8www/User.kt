package com.app.sdg8www

class User {
    var bio: String? = null
    var firstName: String? = null
    var lastName: String? = null
    var image: String? = null
    constructor(bio: String, firstName: String, lastName: String, image: String) {
        this.bio = bio
        this.firstName = firstName
        this.lastName = lastName
        this.image = image
    }
}
