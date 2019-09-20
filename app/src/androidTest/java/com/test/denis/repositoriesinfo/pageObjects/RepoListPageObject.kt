package com.test.denis.repositoriesinfo.pageObjects

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.NoMatchingViewException
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.rule.ActivityTestRule
import com.test.denis.repositoriesinfo.R
import com.test.denis.repositoriesinfo.ui.RepositoryListActivity
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnHolderItem
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import android.widget.TextView
import androidx.test.espresso.PerformException
import androidx.test.espresso.matcher.BoundedMatcher
import com.test.denis.repositoriesinfo.helpers.RecyclerViewMatcher
import com.test.denis.repositoriesinfo.helpers.SpecHelper
import com.test.denis.repositoriesinfo.ui.RepoItemViewHolder
import org.hamcrest.Description
import org.hamcrest.Matcher


/**
 * Page object class to do actions on RepoList view
 */
class RepoListPageObject(private val activityRule: ActivityTestRule<RepositoryListActivity>) {
    private val specHelper = SpecHelper()

    /**
     * Method checks if search bar element is visible
     */
    fun checkIfSearchBarIsDisplayed(): Boolean {
        try {
            onView(withId(R.id.inputSearch)).perform(click()).check(matches(isDisplayed()))
        } catch (ex: NoMatchingViewException) {
            return false
        }
        return true
    }

    /**
     * Method checks if repository list element is visible
     */
    fun checkIfReposListIsDisplayed(): Boolean {
        try {
            onView(withId(R.id.contentList)).check(matches(isDisplayed()))
        } catch (ex: NoMatchingViewException) {
            return false
        }
        return true
    }

    /**
     * Method types tetris to search bar and clicks search button
     */
    fun loadTetrisRepos() {
        typeTextToSearchBar("tetris")
        specHelper.tapSearch()
        specHelper.waitUntilGoneProgressBar(5000)
    }

    /**
     * Method to type text to search bar
     */
    fun typeTextToSearchBar(text: String) {
        onView(withId(R.id.inputSearch)).perform(typeText(text))
    }

    /**
     * Method returns false if there is no repo with name as text given as param in repositories list
     * true otherwise
     */
    fun checkRepositoryNameItem(text: String): Boolean {
        try {
            onView(withId(R.id.contentList)).perform(
                actionOnHolderItem<RecyclerView.ViewHolder>(
                    withRepoHolderView(text, R.id.repoName), click()
                )
            )
        } catch (ex: PerformException) {
            return false
        }
        return true
    }

    /**
     * Method returns false if there is no repo with repo owner as text given as param in repositories list
     * true otherwise
     */
    fun checkRepositoryOwnerItem(text: String): Boolean {
        try {
            onView(withId(R.id.contentList)).perform(
                actionOnHolderItem<RecyclerView.ViewHolder>(
                    withRepoHolderView(text, R.id.ownerName), click()
                )
            )
        } catch (ex: PerformException) {
            return false
        }
        return true
    }

    /**
     * Method returns false if there is no repo with size as text given as param in repositories list
     * true otherwise
     */
    fun checkRepositorySizeItem(text: String): Boolean {
        try {
            onView(withId(R.id.contentList)).perform(
                actionOnHolderItem<RecyclerView.ViewHolder>(
                    withRepoHolderView(text, R.id.repoSize), click()
                )
            )
        } catch (ex: PerformException) {
            return false
        }
        return true
    }

    /**
     * Method checks single cell of repositories list on given position with given element id
     * and compares it with given text
     */
    fun checkTextOfRecyclerChildItemAtPosition(position: Int, id: Int, text: String){
        onView( RecyclerViewMatcher(R.id.contentList)
            .atPositionOnView(position, id))
            .check(matches(withText(text)))
    }

    /**
     * Method returns count of repo elements in list
     */
    fun getReposListCount(): Int {
        val recyclerView = activityRule.activity.findViewById<RecyclerView>(R.id.contentList)
        return recyclerView.adapter!!.itemCount
    }

    /**
     * Method swipes to given position in list.
     * Element at given position must be rendered
     */
    fun swipeToNElement(n: Int) {
        onView(withId(R.id.contentList)).perform(
            actionOnItemAtPosition<RecyclerView.ViewHolder>(
                n,
                click()
            )
        )
    }
}

/**
 * Method to check if RecyclerView list contains given text
 */
fun withRepoHolderView(text: String, id: Int): Matcher<RecyclerView.ViewHolder> {
    return object :
        BoundedMatcher<RecyclerView.ViewHolder, RepoItemViewHolder>(RepoItemViewHolder::class.java) {

        override fun describeTo(description: Description) {
            description.appendText("No Repository item found with text: $text")
        }

        override fun matchesSafely(item: RepoItemViewHolder): Boolean {
            val repoItemText =
                item.itemView.findViewById(id) as TextView
            return repoItemText.text.toString().contains(text)
        }
    }
}

