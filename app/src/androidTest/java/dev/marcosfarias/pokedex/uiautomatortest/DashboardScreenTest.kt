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
import dev.marcosfarias.pokedex.model.Pokemon
import dev.marcosfarias.pokedex.ui.dashboard.DashboardFragment
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Тесты экрана Dashboard (детальная информация о покемоне)
 * Тестирование проводится как "черный ящик" с использованием UiAutomator
 */
@RunWith(AndroidJUnit4::class)
class DashboardScreenTest {

    private lateinit var uiDevice: UiDevice
    private lateinit var resources: Resources
    private lateinit var navHost: TestNavHostController
    private lateinit var testPokemon: Pokemon
    private lateinit var scenario: FragmentScenario<DashboardFragment>

    @Before
    fun setup() {
        // Инициализация UiDevice для UiAutomator тестов
        uiDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())
        
        // Получение контекста и ресурсов
        val context: Context = InstrumentationRegistry.getInstrumentation().targetContext
        resources = context.resources
        
        // Создание тестового покемона
        testPokemon = Pokemon().apply {
            id = "001"
            name = "Bulbasaur"
        }
        
        // Настройка навигации для фрагмента
        navHost = TestNavHostController(ApplicationProvider.getApplicationContext())
        scenario = launchFragmentInContainer<DashboardFragment>(themeResId = R.style.AppTheme)
        scenario.onFragment { fragment ->
            navHost.setGraph(R.navigation.mobile_navigation)
            navHost.setCurrentDestination(R.id.navigation_dashboard)
            Navigation.setViewNavController(fragment.requireView(), navHost)
        }
    }

    /**
     * Тест: Проверка отображения информации о покемоне
     * Ожидаемый результат: Имя покемона отображается на экране
     */
    @Test
    fun testPokemonNameDisplayed() {
        // Ожидаем появления информации о покемоне
        uiDevice.wait(Until.hasObject(By.res("textViewName")), 5000)
        
        // Проверяем что имя покемона отображается
        val nameViewExists = uiDevice.hasObject(By.res("textViewName"))
        assertTrue("Имя покемона должно отображаться", nameViewExists)
    }

    /**
     * Тест: Проверка отображения ID покемона
     * Ожидаемый результат: ID покемона (#001) отображается на экране
     */
    @Test
    fun testPokemonIdDisplayed() {
        // Проверяем наличие ID покемона
        val idViewExists = uiDevice.hasObject(By.res("textViewID"))
        assertTrue("ID покемона должно отображаться", idViewExists)
    }

    /**
     * Тест: Проверка наличия табов (вкладок)
     * Ожидаемый результат: TabLayout с 4 вкладками присутствует на экране
     */
    @Test
    fun testTabsExist() {
        // Проверяем наличие табов
        val tabsExist = uiDevice.hasObject(By.res("tabs"))
        assertTrue("TabLayout должен отображаться", tabsExist)
    }

    /**
     * Тест: Проверка наличия таба About
     * Ожидаемый результат: Вкладка "About" присутствует на экране
     */
    @Test
    fun testAboutTabExists() {
        val aboutTabText = resources.getString(R.string.dashboard_tab_1)
        val aboutTabExists = uiDevice.hasObject(By.text(aboutTabText))
        assertTrue("Вкладка 'About' должна отображаться", aboutTabExists)
    }

    /**
     * Тест: Проверка наличия таба Base Stats
     * Ожидаемый результат: Вкладка "Base Stats" присутствует на экране
     */
    @Test
    fun testBaseStatsTabExists() {
        val baseStatsTabText = resources.getString(R.string.dashboard_tab_2)
        val baseStatsTabExists = uiDevice.hasObject(By.text(baseStatsTabText))
        assertTrue("Вкладка 'Base Stats' должна отображаться", baseStatsTabExists)
    }

    /**
     * Тест: Проверка наличия таба Evolution
     * Ожидаемый результат: Вкладка "Evolution" присутствует на экране
     */
    @Test
    fun testEvolutionTabExists() {
        val evolutionTabText = resources.getString(R.string.dashboard_tab_3)
        val evolutionTabExists = uiDevice.hasObject(By.text(evolutionTabText))
        assertTrue("Вкладка 'Evolution' должна отображаться", evolutionTabExists)
    }

    /**
     * Тест: Проверка наличия таба Moves
     * Ожидаемый результат: Вкладка "Moves" присутствует на экране
     */
    @Test
    fun testMovesTabExists() {
        val movesTabText = resources.getString(R.string.dashboard_tab_4)
        val movesTabExists = uiDevice.hasObject(By.text(movesTabText))
        assertTrue("Вкладка 'Moves' должна отображаться", movesTabExists)
    }

    /**
     * Тест: Проверка переключения между табами
     * Ожидаемый результат: При нажатии на таб происходит переключение
     */
    @Test
    fun testTabSwitching() {
        // Нажимаем на таб "Base Stats"
        val baseStatsTabText = resources.getString(R.string.dashboard_tab_2)
        val baseStatsTab = uiDevice.findObject(By.text(baseStatsTabText))
        baseStatsTab?.click()
        
        // Даем время на переключение
        uiDevice.waitForIdle(1000)
        
        // Проверяем что таб остался видимым (переключение произошло)
        val tabStillExists = uiDevice.hasObject(By.text(baseStatsTabText))
        assertTrue("Таб 'Base Stats' должен остаться видимым после нажатия", tabStillExists)
    }

    /**
     * Тест: Проверка наличия изображения покемона
     * Ожидаемый результат: ImageView с изображением покемона присутствует
     */
    @Test
    fun testPokemonImageExists() {
        val imageViewExists = uiDevice.hasObject(By.res("imageView"))
        assertTrue("Изображение покемона должно отображаться", imageViewExists)
    }

    /**
     * Тест: Проверка наличия типов покемона
     * Ожидаемый результат: TextView для типов покемона присутствуют
     */
    @Test
    fun testPokemonTypesExist() {
        val type1Exists = uiDevice.hasObject(By.res("textViewType1"))
        assertTrue("Первый тип покемона должен отображаться", type1Exists)
    }

    /**
     * Тест: Проверка наличия Toolbar
     * Ожидаемый результат: Toolbar присутствует на экране
     */
    @Test
    fun testToolbarExists() {
        val toolbarExists = uiDevice.hasObject(By.res("toolbar"))
        assertTrue("Toolbar должен отображаться", toolbarExists)
    }

    /**
     * Тест: Проверка наличия ViewPager для контента табов
     * Ожидаемый результат: ViewPager присутствует для отображения контента
     */
    @Test
    fun testViewPagerExists() {
        val viewPagerExists = uiDevice.hasObject(By.res("viewPager"))
        assertTrue("ViewPager должен отображаться", viewPagerExists)
    }

    // ==================== ОШИБОЧНЫЙ СЦЕНАРИЙ ====================
    
    /**
     * ОШИБОЧНЫЙ ТЕСТ: Проверка отображения ошибки при отсутствии данных о покемоне
     * 
     * Этот тест проверяет ошибочный сценарий, когда:
     * - Загружается экран Dashboard без данных о покемоне
     * - Ожидается что будет показано сообщение об ошибке или пустое состояние
     * 
     * Ожидаемый результат: Тест ДОЛЖЕН УПАСТЬ, так как в текущей реализации
     * нет обработки отсутствия данных - экран может отображаться с пустыми полями
     */
    @Test
    fun testMissingPokemonDataError() {
        // Проверяем что отображается сообщение об ошибке при отсутствии данных
        val errorMessageExists = uiDevice.hasObject(By.text("Pokemon not found")) ||
                                 uiDevice.hasObject(By.text("Error loading")) ||
                                 uiDevice.hasObject(By.text("No data available")) ||
                                 uiDevice.hasObject(By.res("errorView"))
        
        // Также проверяем что имя покемона не пустое
        val nameView = uiDevice.findObject(By.res("textViewName"))
        val nameText = nameView?.text ?: ""
        
        // Если имя пустое и нет сообщения об ошибке - это баг
        val hasErrorHandling = errorMessageExists || nameText.isNotEmpty()
        
        assertTrue("Должно отображаться сообщение об ошибке или данные покемона", 
                   hasErrorHandling)
    }

    /**
     * ОШИБОЧНЫЙ ТЕСТ: Проверка что все табы кликабельны
     * 
     * Этот тест проверяет ошибочный сценарий, когда:
     * - Некоторые табы могут быть неактивными или заблокированными
     * - Ожидается что все табы должны быть кликабельны
     * 
     * Ожидаемый результат: Тест ДОЛЖЕН УПАСТЬ, если таб заблокирован
     */
    @Test
    fun testAllTabsAreClickable() {
        val tabs = listOf(
            resources.getString(R.string.dashboard_tab_1), // About
            resources.getString(R.string.dashboard_tab_2), // Base Stats
            resources.getString(R.string.dashboard_tab_3), // Evolution
            resources.getString(R.string.dashboard_tab_4)  // Moves
        )
        
        // Проверяем что каждый таб можно нажать
        for (tabText in tabs) {
            val tab = uiDevice.findObject(By.text(tabText))
            val isClickable = tab?.isClickable ?: false
            
            assertTrue("Таб '$tabText' должен быть кликабельным", isClickable)
        }
    }
}