package com.test.denis.repositoriesinfo.helpers

import android.view.KeyEvent
import androidx.test.InstrumentationRegistry
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.pressKey
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.uiautomator.UiDevice
import androidx.test.uiautomator.UiSelector
import com.test.denis.repositoriesinfo.R

class SpecHelper {

    fun pressDeleteInSearch(){
        Espresso.onView(ViewMatchers.withId(R.id.inputSearch)).perform(pressKey(KeyEvent.KEYCODE_DEL))
    }

    fun pressBackButton(){
        Espresso.onView(ViewMatchers.withId(R.id.contentList)).perform(ViewActions.pressBack())
    }

    fun waitUntilGoneProgressBar(timeout: Long) {
        val resourceId = InstrumentationRegistry.getTargetContext().resources.getResourceName(R.id.progressBar)
        val selector = UiSelector().resourceId(resourceId)
        UiDevice.getInstance(InstrumentationRegistry.getInstrumentation()).findObject(selector)
        UiDevice.getInstance(InstrumentationRegistry.getInstrumentation()).findObject(selector).waitUntilGone(timeout)
    }
}