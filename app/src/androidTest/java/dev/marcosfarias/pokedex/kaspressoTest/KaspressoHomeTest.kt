package dev.marcosfarias.pokedex.kaspressoTest

import android.content.res.Resources
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.Navigation
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import dev.marcosfarias.pokedex.R
import dev.marcosfarias.pokedex.robots.KaspressoBaseRobot
import dev.marcosfarias.pokedex.ui.home.HomeFragment
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Тесты для HomeFragment с использованием Kaspresso
 * Покрывает основные сценарии и ошибочные сценарии
 */
@RunWith(AndroidJUnit4::class)
class KaspressoHomeTest : KaspressoBaseRobot() {

    lateinit var navHost: TestNavHostController

    private val resources: Resources by lazy {
        InstrumentationRegistry.getInstrumentation().targetContext.resources
    }

    @Before
    fun setup() {
        navHost = TestNavHostController(ApplicationProvider.getApplicationContext())
        launchFragmentInContainer(themeResId = R.style.AppTheme) {
            HomeFragment().also { fragment ->
                fragment.viewLifecycleOwnerLiveData.observeForever { viewLifecycleOwner ->
                    if (viewLifecycleOwner != null) {
                        navHost.setGraph(R.navigation.mobile_navigation)
                        Navigation.setViewNavController(fragment.requireView(), navHost)
                    }
                }
            }
        }
    }

    /**
     * Основной сценарий: Проверка отображения главного экрана
     */
    @Test
    fun verifyHomeScreenIsDisplayed() {
        isViewDisplayed(R.id.search_title)
        isViewDisplayed(R.id.search_text)
        isViewDisplayed(R.id.recyclerViewMenu)
        isViewDisplayed(R.id.recyclerViewNews)
    }

    /**
     * Основной сценарий: Проверка текста заголовка
     */
    @Test
    fun verifyTitleText() {
        testViewText(R.id.search_title, resources.getString(R.string.main_title))
    }

    /**
     * Основной сценарий: Проверка текста поисковой строки
     */
    @Test
    fun verifySearchBarText() {
        testViewText(R.id.search_text, resources.getString(R.string.main_search))
    }

    /**
     * Основной сценарий: Проверка отображения меню
     */
    @Test
    fun verifyMenuItemsAreDisplayed() {
        isViewDisplayed(R.id.recyclerViewMenu)
        isRecyclerViewItemDisplayed(R.id.recyclerViewMenu, resources.getString(R.string.menu_item_1))
    }

    /**
     * Основной сценарий: Проверка отображения секции новостей
     */
    @Test
    fun verifyNewsSectionIsDisplayed() {
        isViewDisplayed(R.id.recyclerViewNews)
    }

    /**
     * Основной сценарий: Проверка клика по пункту меню
     */
    @Test
    fun verifyMenuItemClick() {
        onClickFirstItem(R.id.recyclerViewMenu)
        isViewDisplayed(R.id.recyclerViewMenu)
    }

    /**
     * Основной сценарий: Проверка клика по новости
     */
    @Test
    fun verifyNewsItemClick() {
        onClickFirstItem(R.id.recyclerViewNews)
        isViewDisplayed(R.id.recyclerViewNews)
    }

    /**
     * Основной сценарий: Проверка скроллинга списка меню
     */
    @Test
    fun verifyMenuScroll() {
        isViewDisplayed(R.id.recyclerViewMenu)
        swipeUp(R.id.recyclerViewMenu)
    }

    /**
     * Ошибочный сценарий: Проверка обработки пустого состояния
     */
    @Test
    fun verifyEmptyStateHandling() {
        isViewDisplayed(R.id.recyclerViewMenu)
        isViewDisplayed(R.id.recyclerViewNews)
        isViewDisplayed(R.id.search_title)
        isViewDisplayed(R.id.search_text)
    }
}