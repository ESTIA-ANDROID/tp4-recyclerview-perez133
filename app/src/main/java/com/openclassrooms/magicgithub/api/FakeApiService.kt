package com.openclassrooms.magicgithub.api

import com.openclassrooms.magicgithub.model.User

class FakeApiService : ApiService {
    private val users = FakeApiServiceGenerator.FAKE_USERS

    /**
     * Return a list of [User]
     * Those users must be generated by [FakeApiServiceGenerator]
     */
    override fun getUsers(): List<User> {
        return users
    }

    /**
     * Generate a random [User] and add it [FakeApiService.users] list.
     * This user must be get from the [FakeApiServiceGenerator.FAKE_USERS_RANDOM] list.
     */
    override fun addRandomUser() {
        // Get a random user from FAKE_USERS_RANDOM
        val randomUser = FakeApiServiceGenerator.FAKE_USERS_RANDOM.random()
        users.add(randomUser)
    }

    /**
     * Delete a [User] from the [FakeApiService.users] list.
     */
    override fun deleteUser(user: User) {
        users.remove(user)
    }
}