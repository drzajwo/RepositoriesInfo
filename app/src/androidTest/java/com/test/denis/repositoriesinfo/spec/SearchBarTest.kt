package com.test.denis.repositoriesinfo

import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import com.test.denis.repositoriesinfo.pageObjects.RepoListPageObject
import com.test.denis.repositoriesinfo.ui.RepositoryListActivity
import junit.framework.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
@LargeTest
class SearchBarTest {

    @get:Rule
    val activityRule = ActivityTestRule(RepositoryListActivity::class.java)

    private val reposListPageObject = RepoListPageObject(activityRule)

    @Test
    fun checkIfSearchBarIsDisplayed() {
        assertTrue(reposListPageObject.checkIfSearchBarIsDisplayed())
    }

    @Test
    fun checkIfRepoListIsDisplayed() {
        assertTrue(reposListPageObject.checkIfReposListIsDisplayed())
    }

    @Test
    fun checkRepoListElementsCount(){
        reposListPageObject.waitUntilGoneProgressBar(5000)
        assertTrue(reposListPageObject.checkIfReposListIsDisplayed())
        val repoCount = reposListPageObject.getReposListCount()
        assertEquals(10, repoCount)
    }

}