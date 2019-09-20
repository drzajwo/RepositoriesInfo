package com.test.denis.repositoriesinfo


import com.test.denis.repositoriesinfo.helpers.JUnitSpecHelper
import org.junit.Test

class APIGithubIntegrationTest {

    private val specHelper = JUnitSpecHelper()

    @Test
    fun githubApiCall_repoOverviewStructure_isCorrect() {
        val response = specHelper.makeAPICall("RepositoriesInfo", 1, 5)
        assert(response != null) { print("Error: Api call returned no response") }
        assert(response!!.total > 0)  { print("Error: Api call returned empty response")}
        val myRepo = response.items[1]
        assert(myRepo.size > 0) { print("Error: Repo size is ${myRepo.size}")}
        assert(myRepo.name == "RepositoriesInfo")  { print("Error: Repo name ${myRepo.name} is not expected")}
        assert((myRepo.hasWiki)) { print("Error: This repo is expected to contain wiki and it's not")}
    }

    @Test
    fun githubApiCall_repoOwnerStructure_isCorrect() {
        val response = specHelper.makeAPICall("RepositoriesInfo", 1, 5)
        assert(response != null) { print("Error: Api call returned no response") }
        assert(response!!.total > 0)  { print("Error: Api call returned empty response")}
        val myRepo = response.items[1]
        val owner = myRepo.owner
        assert(owner.login == "drzajwo")  { print("Error: Repo owner ${owner.login} is not expected")}
    }
}