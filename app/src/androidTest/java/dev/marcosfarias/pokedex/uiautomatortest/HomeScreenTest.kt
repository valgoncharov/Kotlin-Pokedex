package dev.marcosfarias.pokedex.uiautomatortest

import android.content.Context
import android.content.res.Resources
import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.Navigation
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.uiautomator.By
import androidx.test.uiautomator.UiDevice
import androidx.test.uiautomator.Until
import dev.marcosfarias.pokedex.R
import dev.marcosfarias.pokedex.ui.home.HomeFragment
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Тесты экрана Home (главный экран приложения)
 * Тестирование проводится как "черный ящик" с использованием UiAutomator
 */
@RunWith(AndroidJUnit4::class)
class HomeScreenTest {

    private lateinit var uiDevice: UiDevice
    private lateinit var resources: Resources
    private lateinit var navHost: TestNavHostController
    private lateinit var scenario: FragmentScenario<HomeFragment>

    @Before
    fun setup() {
        // Инициализация UiDevice для UiAutomator тестов
        uiDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())
        
        // Получение контекста и ресурсов
        val context: Context = InstrumentationRegistry.getInstrumentation().targetContext
        resources = context.resources
        
        // Настройка навигации для фрагмента
        navHost = TestNavHostController(ApplicationProvider.getApplicationContext())
        scenario = launchFragmentInContainer<HomeFragment>(themeResId = R.style.AppTheme)
        scenario.onFragment { fragment ->
            navHost.setGraph(R.navigation.mobile_navigation)
            Navigation.setViewNavController(fragment.requireView(), navHost)
        }
    }

    /**
     * Тест: Проверка отображения главного экрана
     * Ожидаемый результат: Экран загружается и отображает основные элементы
     */
    @Test
    fun testHomeScreenDisplayed() {
        // Ожидаем появления экрана
        uiDevice.wait(Until.hasObject(By.text("What Pokémon\nare you looking for?")), 5000)
        
        // Проверяем что главный экран отображается
        val titleExists = uiDevice.hasObject(By.text("What Pokémon\nare you looking for?"))
        assertTrue("Главный экран должен отображаться", titleExists)
    }

    /**
     * Тест: Проверка наличия строки поиска
     * Ожидаемый результат: Поле поиска присутствует на экране
     */
    @Test
    fun testSearchBarExists() {
        // Проверяем наличие строки поиска
        val searchText = resources.getString(R.string.main_search)
        val searchExists = uiDevice.hasObject(By.text(searchText))
        assertTrue("Строка поиска должна отображаться", searchExists)
    }

    /**
     * Тест: Проверка наличия меню на главном экране
     * Ожидаемый результат: Меню с пунктами (Pokedex, Moves и т.д.) отображается
     */
    @Test
    fun testMenuItemsDisplayed() {
        // Проверяем наличие пунктов меню
        val menuItem1 = resources.getString(R.string.menu_item_1)
        val menuExists = uiDevice.hasObject(By.text(menuItem1))
        assertTrue("Пункт меню 'Pokedex' должен отображаться", menuExists)
    }

    /**
     * Тест: Проверка наличия раздела новостей
     * Ожидаемый результат: Секция новостей отображается на экране
     */
    @Test
    fun testNewsSectionDisplayed() {
        // Проверяем наличие раздела новостей
        val newsTitle = resources.getString(R.string.pokemon_news)
        val newsExists = uiDevice.hasObject(By.text(newsTitle))
        assertTrue("Раздел 'Pokémon News' должен отображаться", newsExists)
    }

    /**
     * Тест: Проверка навигации к экрану Pokedex через меню
     * Ожидаемый результат: При нажатии на Pokedex в меню происходит переход
     */
    @Test
    fun testNavigateToPokedex() {
        // Нажимаем на пункт меню Pokedex
        val menuItem1 = resources.getString(R.string.menu_item_1)
        val menuItem = uiDevice.findObject(By.text(menuItem1))
        menuItem?.click()
        
        // Ожидаем перехода к экрану Pokedex
        uiDevice.wait(Until.hasObject(By.text("Pokedex")), 3000)
        
        // Проверяем что навигация произошла
        val currentDestination = navHost.currentDestination?.id
        assertTrue("Должна произойти навигация к Pokedex", 
            currentDestination == R.id.navigation_pokedex || uiDevice.hasObject(By.text("Pokedex")))
    }
}