package com.test.denis.repositoriesinfo.pageObjects

import android.app.PendingIntent.getActivity
import android.os.SystemClock
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.NoMatchingViewException
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.rule.ActivityTestRule
import com.test.denis.repositoriesinfo.R
import com.test.denis.repositoriesinfo.ui.RepositoryListActivity
import androidx.test.InstrumentationRegistry.getTargetContext
import androidx.annotation.StringRes
import androidx.annotation.IdRes
import androidx.test.InstrumentationRegistry.getInstrumentation
import androidx.test.uiautomator.UiObject
import androidx.test.uiautomator.UiDevice
import androidx.test.uiautomator.Until.findObject
import androidx.test.uiautomator.UiSelector





class RepoListPageObject(private val activityRule: ActivityTestRule<RepositoryListActivity>) {

    fun checkIfSearchBarIsDisplayed(): Boolean{
        try {
            onView(withId(R.id.inputSearch)).perform(click()).check(matches(isDisplayed()))
            return true
        } catch (ex: NoMatchingViewException) {
            // do nothing
        }
        return false
    }

    fun checkIfReposListIsDisplayed(): Boolean{
        try {
            onView(withId(R.id.contentList)).check(matches(isDisplayed()))
            return true
        } catch (ex: NoMatchingViewException) {
            // do nothing
        }
        return false
    }

    fun getReposListCount(): Int{
        val recyclerView = activityRule.activity.findViewById<RecyclerView>(R.id.contentList)
        return recyclerView.adapter!!.itemCount
    }

    fun waitUntilGoneProgressBar(timeout: Long) {
        val resourceId = getTargetContext().resources.getResourceName(R.id.progressBar)
        val selector = UiSelector().resourceId(resourceId)
        UiDevice.getInstance(getInstrumentation()).findObject(selector)
        UiDevice.getInstance(getInstrumentation()).findObject(selector).waitUntilGone(timeout)
    }

}