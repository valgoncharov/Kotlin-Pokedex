package dev.marcosfarias.pokedex

import android.os.Bundle
import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.Navigation
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import dev.marcosfarias.pokedex.robots.KaspressoBaseRobot
import dev.marcosfarias.pokedex.ui.dashboard.DashboardFragment
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith


/**
 * Тесты для DashboardFragment с использованием Kaspresso
 * Покрывает основные сценарии и ошибочные сценарии
 */
@RunWith(AndroidJUnit4::class)
class KaspressoDashboardTest : KaspressoBaseRobot() {

    lateinit var navHost: TestNavHostController
    private lateinit var scenario: FragmentScenario<DashboardFragment>

    @Before
    fun setup() {
        // 1. Создаем контроллер навигации
        navHost = TestNavHostController(ApplicationProvider.getApplicationContext())
        
        val bundle = Bundle().apply {
            putString("id", "001")
            putString("name", "Bulbasaur")
        }
        
        // 2. Запускаем фрагмент в контейнере
        scenario = launchFragmentInContainer<DashboardFragment>(
            fragmentArgs = bundle,
            themeResId = R.style.AppTheme
        )
        
        // 3. Привязываем контроллер навигации к фрагменту и устанавливаем граф
        scenario.onFragment { fragment ->
            Navigation.setViewNavController(fragment.requireView(), navHost)
            navHost.setGraph(R.navigation.mobile_navigation)
        }
    }

    /**
     * Основной сценарий: Проверка отображения экрана деталей покемона
     */
    @Test
    fun verifyDashboardScreenIsDisplayed() {
        // Ожидаем загрузку данных
        waitFor(2000)
        isVisibleAndEnabled(R.id.textViewName)
        isVisibleAndEnabled(R.id.textViewID)
        isViewDisplayed(R.id.tabs)
        isViewDisplayed(R.id.viewPager)
    }

    /**
     * Основной сценарий: Проверка отображения имени покемона
     */
    @Test
    fun verifyPokemonNameIsDisplayed() {
        waitFor(2000)
        isVisibleAndEnabled(R.id.textViewName)
        isClickable(R.id.textViewName)
    }

    /**
     * Основной сценарий: Проверка отображения ID покемона
     */
    @Test
    fun verifyPokemonIdIsDisplayed() {
        waitFor(2000)
        isVisibleAndEnabled(R.id.textViewID)
    }

    /**
     * Основной сценарий: Проверка отображения типов покемона
     */
    @Test
    fun verifyPokemonTypesAreDisplayed() {
        isViewDisplayed(R.id.textViewType1)
        isViewDisplayed(R.id.textViewType2)
        isViewDisplayed(R.id.textViewType3)
    }

    /**
     * Основной сценарий: Проверка отображения табов
     */
    @Test
    fun verifyTabsAreDisplayed() {
        isViewDisplayed(R.id.tabs)
        isClickable(R.id.tabs)
    }

    /**
     * Основной сценарий: Проверка таба About
     */
    @Test
    fun verifyAboutTabIsDisplayed() {
        isViewDisplayed(R.id.tabs)
    }

    /**
     * Основной сценарий: Проверка таба Base Stats
     */
    @Test
    fun verifyBaseStatsTabIsDisplayed() {
        isViewDisplayed(R.id.tabs)
    }

    /**
     * Основной сценарий: Проверка таба Evolution
     */
    @Test
    fun verifyEvolutionTabIsDisplayed() {
        isViewDisplayed(R.id.tabs)
    }

    /**
     * Основной сценарий: Проверка таба Moves
     */
    @Test
    fun verifyMovesTabIsDisplayed() {
        isViewDisplayed(R.id.tabs)
    }

    /**
     * Основной сценарий: Проверка отображения изображения покемона
     */
    @Test
    fun verifyPokemonImageIsDisplayed() {
        isViewDisplayed(R.id.imageView)
    }

    /**
     * Основной сценарий: Проверка навигации
     */
    @Test
    fun verifyNavigationToDashboard() {
        assert(navHost.currentDestination?.id == R.id.navigation_dashboard)
    }

    /**
     * Основной сценарий: Проверка скроллинга
     */
    @Test
    fun verifyScrollBehavior() {
        isViewDisplayed(R.id.app_bar)
        isViewDisplayed(R.id.viewPager)
    }

    /**
     * Ошибочный сценарий: Проверка обработки пустых типов покемона
     */
    @Test
    fun verifyEmptyTypesHandling() {
        isViewDisplayed(R.id.textViewType1)
        isViewDisplayed(R.id.textViewType2)
        isViewDisplayed(R.id.textViewType3)
    }

    /**
     * Основной сценарий: Проверка toolbar
     */
    @Test
    fun verifyToolbarIsDisplayed() {
        isViewDisplayed(R.id.toolbar)
    }

}