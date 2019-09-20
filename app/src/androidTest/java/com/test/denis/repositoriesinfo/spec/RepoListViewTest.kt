package com.test.denis.repositoriesinfo.spec

import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import com.test.denis.repositoriesinfo.R
import com.test.denis.repositoriesinfo.helpers.SpecHelper
import com.test.denis.repositoriesinfo.pageObjects.RepoListPageObject
import com.test.denis.repositoriesinfo.ui.RepositoryListActivity
import junit.framework.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
@LargeTest
class RepoListViewTest {

    @get:Rule
    val activityRule = ActivityTestRule(RepositoryListActivity::class.java)

    // Declaration of page objects and helpers
    private val reposListPageObject = RepoListPageObject(activityRule)
    private val specHelper = SpecHelper()

    // Basic check for content in app
    @Test
    fun searchBarIsDisplayedTest() {
        assertTrue(reposListPageObject.checkIfSearchBarIsDisplayed())
    }

    @Test
    fun repoListIsDisplayedTest() {
        assertTrue(reposListPageObject.checkIfReposListIsDisplayed())
    }

    // Tests of auto search trigger - AC1
    @Test
    fun autoUpdateAfter3CharsTest(){
        specHelper.waitUntilGoneProgressBar(5000)
        reposListPageObject.typeTextToSearchBar("xam")
        specHelper.waitUntilGoneProgressBar(5000)
        assertTrue(reposListPageObject.checkRepositoryOwnerItem("XLabs"))
    }

    @Test
    fun autoUpdateAfter3CharsRetriggerTest(){
        specHelper.waitUntilGoneProgressBar(5000)
        reposListPageObject.typeTextToSearchBar("xam")
        specHelper.waitUntilGoneProgressBar(5000)
        assertTrue(reposListPageObject.checkRepositoryOwnerItem("XLabs"))
        specHelper.pressDeleteInSearch()
        reposListPageObject.typeTextToSearchBar("l")
        specHelper.waitUntilGoneProgressBar(5000)
        assertTrue(reposListPageObject.checkRepositoryOwnerItem("qfish"))
    }

    @Test
    fun typeTextAndHitSearchTest() {
        specHelper.waitUntilGoneProgressBar(5000)
        assertTrue(reposListPageObject.checkRepositoryOwnerItem("chvin"))
        assertTrue(reposListPageObject.checkRepositorySizeItem("1248"))
        reposListPageObject.typeTextToSearchBar("Appium")
        specHelper.tapSearch()
        specHelper.waitUntilGoneProgressBar(5000)
        // Ensure list is visible
        assertTrue(reposListPageObject.checkIfReposListIsDisplayed())
        val repoCount = reposListPageObject.getReposListCount()
        // Check if 10 repos are displayed
        assertEquals(10, repoCount)
        assertTrue(reposListPageObject.checkRepositoryOwnerItem("Appium"))
    }

    // UI Tests of Repositories list - AC2 - Test of elements count in list view
    @Test
    fun repoListElementsCountScrollAndAgainTest(){
        // Wait for progress bar to disappear
        reposListPageObject.loadTetrisRepos()
        specHelper.waitUntilGoneProgressBar(5000)
        // Ensure list is visible
        specHelper.hideKeybard()
        assertTrue(reposListPageObject.checkIfReposListIsDisplayed())
        var repoCount = reposListPageObject.getReposListCount()
        // Check if 10 repos are displayed
        assertEquals(10, repoCount)
        // Scroll to 9th element and wait for content to load
        reposListPageObject.swipeToNElement(9)
        specHelper.waitUntilGoneProgressBar(5000)
        reposListPageObject.swipeToNElement(11)
        repoCount = reposListPageObject.getReposListCount()
        // Check if 20 repos are displayed
        assertEquals(20, repoCount)
    }

    @Test
    fun failingSearchTest(){
        specHelper.waitUntilGoneProgressBar(5000)
        reposListPageObject.typeTextToSearchBar("drdrdrdrdrdrdrdrdr")
        specHelper.tapSearch()
        specHelper.waitUntilGoneProgressBar(5000)
        val repoCount = reposListPageObject.getReposListCount()
        // Check if 0 repos are displayed
        assertEquals(0, repoCount)
    }

    // UI Tests of Repositories list - AC3 - If repo contains a wiki displays different cell color
    @Test
    fun itemWithWikiWhiteBackgroundTest(){
        fail("Not implemented test")
        reposListPageObject.loadTetrisRepos()
        val response = specHelper.makeAPICall("tetris", 1, 10)
        assert(response != null) { print("Error: Api call returned no response") }
        assert(response!!.total > 0)  { print("Error: Api call returned empty response")}
        reposListPageObject.checkTextOfRecyclerChildItemAtPosition(0, R.id.ownerName, "chvis")
        assert(response.items[0].hasWiki) { print("Error: Expected first element contain wiki")}
        // TODO: Add mechanism to retrieve background color of RecyclerView child at given position (Complex)
    }

    @Test
    fun itemWithoutWikiDarkBackgroundTest(){
        fail("Not implemented test")
        // TODO: Add mechanism to retrieve background color of RecyclerView child at given position (Complex)
    }

    // Test of Repository cell render properties - AC4
    @Test
    fun tetrisCompleteCellDataDisplayTest() {
        reposListPageObject.typeTextToSearchBar("tetris")
        specHelper.tapSearch()
        specHelper.waitUntilGoneProgressBar(5000)
        assertTrue(reposListPageObject.checkRepositoryOwnerItem("chvin"))
        assertTrue(reposListPageObject.checkRepositorySizeItem("4319"))
        assertTrue(reposListPageObject.checkRepositoryNameItem("react-tetris"))
    }







}