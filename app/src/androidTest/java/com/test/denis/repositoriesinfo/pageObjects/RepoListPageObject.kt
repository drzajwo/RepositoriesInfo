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
import com.test.denis.repositoriesinfo.helpers.SpecHelper
import com.test.denis.repositoriesinfo.ui.RepoItemViewHolder
import org.hamcrest.Description
import org.hamcrest.Matcher


class RepoListPageObject(private val activityRule: ActivityTestRule<RepositoryListActivity>) {
    val specHelper = SpecHelper()

    fun checkIfSearchBarIsDisplayed(): Boolean{
        try {
            onView(withId(R.id.inputSearch)).perform(click()).check(matches(isDisplayed()))
        } catch (ex: NoMatchingViewException) {
            return false
        }
        return true
    }

    fun checkIfReposListIsDisplayed(): Boolean{
        try {
            onView(withId(R.id.contentList)).check(matches(isDisplayed()))
        } catch (ex: NoMatchingViewException) {
            return false
        }
        return true
    }

    fun loadTetrisRepos(){
        typeTextToSearchBar("tetris")
        specHelper.tapSearch()
        specHelper.waitUntilGoneProgressBar(5000)
    }

    fun typeTextToSearchBar(text: String){
        onView(withId(R.id.inputSearch)).perform(typeText(text))
    }

    fun checkRepositoryNameItem(text: String): Boolean {
        try {
            onView(withId(R.id.contentList)).perform(
                actionOnHolderItem<RecyclerView.ViewHolder>(
                    withRepoHolderView(text, R.id.repoName), click()
                )
            )
        }catch (ex: PerformException) {
                return false
        }
        return true
    }

    fun checkRepositoryOwnerItem(text: String): Boolean{
        try {
            onView(withId(R.id.contentList)).perform(
                actionOnHolderItem<RecyclerView.ViewHolder>(
                    withRepoHolderView(text, R.id.ownerName), click()
                )
            )
        }catch (ex: PerformException) {
            return false
        }
        return true
    }

    fun checkRepositorySizeItem(text: String): Boolean{
        try {
            onView(withId(R.id.contentList)).perform(
                actionOnHolderItem<RecyclerView.ViewHolder>(
                    withRepoHolderView(text, R.id.repoSize), click()
                )
            )
        }catch (ex: PerformException) {
            return false
        }
        return true
    }

    fun getReposListCount(): Int{
        val recyclerView = activityRule.activity.findViewById<RecyclerView>(R.id.contentList)
        return recyclerView.adapter!!.itemCount
    }

    fun swipeToNElement(n: Int) {
        onView(withId(R.id.contentList)).perform(actionOnItemAtPosition<RecyclerView.ViewHolder>(n, click()))
    }
}

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