package com.test.denis.repositoriesinfo


import com.test.denis.repositoriesinfo.helpers.JUnitSpecHelper
import junit.framework.Assert.fail
import org.junit.Test
import retrofit2.HttpException

/**
 * Test class to test integration between application and contend provider which is GitHub API
 */
class APIGithubIntegrationTest {

    private val specHelper = JUnitSpecHelper()

    @Test
    fun githubApiCall_standardCallSize_isCorrect() {
        val response = specHelper.makeAPICall("tetris", 1, 10)
        assert(response != null) { print("Error: Api call returned no response") }
        assert(response!!.total > 0)  { print("Error: Api call returned empty response")}
        assert(response.items.size == 10){ print("Error: Api call returned wrong number (${response.items.size} of responses")}
    }

    @Test
    fun githubApiCall_nonExistingRepo_isEmpty() {
        val response = specHelper.makeAPICall("dsdsdsdsdsdsdsdsdsds", 1, 10)
        assert(response != null) { print("Error: Api call returned no response") }
        assert(response!!.total == 0)  { print("Error: Api call returned non empty response ${response.total} size")}
        assert(response.items.isEmpty())  { print("Error: Api call returned non empty response ${response.items}")}
    }

    @Test
    fun githubApiCall_emptyQuery_isEmpty() {
        try {
            specHelper.makeAPICall("", 1, 10)
        } catch (e: HttpException){
            fail("Empty API call raised unhandled exception")
        }
    }

    @Test
    fun githubApiCall_zeroItemsPerPage_isEmpty() {
        val response = specHelper.makeAPICall("tetris", 1, 0)
        assert(response != null) { print("Error: Api call returned no response") }
        assert(response!!.items.isEmpty()) { print("Error: Expected to have 0 items but instead there are ${response.items.size}")}
    }

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