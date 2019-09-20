package com.test.denis.repositoriesinfo.helpers

import android.view.KeyEvent
import androidx.test.InstrumentationRegistry
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.closeSoftKeyboard
import androidx.test.espresso.action.ViewActions.pressKey
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.uiautomator.UiDevice
import androidx.test.uiautomator.UiSelector
import com.test.denis.repositoriesinfo.R
import com.test.denis.repositoriesinfo.model.RepositoryResponse
import com.test.denis.repositoriesinfo.network.BASE_URL
import com.test.denis.repositoriesinfo.network.RepositoryApi
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory

/**
 * Helper class for functional tests
 */
class SpecHelper {

    fun pressDeleteInSearch(){
        Espresso.onView(ViewMatchers.withId(R.id.inputSearch)).perform(pressKey(KeyEvent.KEYCODE_DEL))
    }

    fun tapSearch(){
        Espresso.onView(ViewMatchers.withId(R.id.inputSearch))
            .perform(ViewActions.pressImeActionButton())
    }

    fun hideKeybard(){
        Espresso.onView(ViewMatchers.withId(R.id.contentList)).perform(closeSoftKeyboard())
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

    fun makeAPICall(query: String, page: Int, perPage: Int): RepositoryResponse? {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(OkHttpClient.Builder().build())
            .addConverterFactory(MoshiConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
            .build()
        val api = retrofit.create(RepositoryApi::class.java)
        return api.getRepositories(query, page, perPage).blockingSingle()
    }
}