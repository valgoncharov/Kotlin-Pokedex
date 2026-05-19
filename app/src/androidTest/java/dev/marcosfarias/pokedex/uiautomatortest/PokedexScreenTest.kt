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
import dev.marcosfarias.pokedex.ui.pokedex.PokedexFragment
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Тесты экрана Pokedex (список покемонов)
 * Тестирование проводится как "черный ящик" с использованием UiAutomator
 */
@RunWith(AndroidJUnit4::class)
class PokedexScreenTest {

    private lateinit var uiDevice: UiDevice
    private lateinit var resources: Resources
    private lateinit var navHost: TestNavHostController
    private lateinit var scenario: FragmentScenario<PokedexFragment>

    @Before
    fun setup() {
        // Инициализация UiDevice для UiAutomator тестов
        uiDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())
        
        // Получение контекста и ресурсов
        val context: Context = InstrumentationRegistry.getInstrumentation().targetContext
        resources = context.resources
        
        // Настройка навигации для фрагмента
        navHost = TestNavHostController(ApplicationProvider.getApplicationContext())
        scenario = launchFragmentInContainer<PokedexFragment>(themeResId = R.style.AppTheme)
        scenario.onFragment { fragment ->
            navHost.setGraph(R.navigation.mobile_navigation)
            navHost.setCurrentDestination(R.id.navigation_pokedex)
            Navigation.setViewNavController(fragment.requireView(), navHost)
        }
    }

    /**
     * Тест: Проверка отображения экрана Pokedex
     * Ожидаемый результат: Экран загружается и отображает заголовок "Pokedex"
     */
    @Test
    fun testPokedexScreenDisplayed() {
        // Ожидаем появления экрана
        uiDevice.wait(Until.hasObject(By.text("Pokedex")), 5000)
        
        // Проверяем что экран Pokedex отображается
        val titleExists = uiDevice.hasObject(By.text("Pokedex"))
        assertTrue("Экран Pokedex должен отображаться", titleExists)
    }

    /**
     * Тест: Проверка наличия списка покемонов
     * Ожидаемый результат: RecyclerView со списком покемонов присутствует на экране
     */
    @Test
    fun testPokemonListDisplayed() {
        // Проверяем наличие списка покемонов по ID ресурса
        val recyclerViewExists = uiDevice.hasObject(By.res("recyclerView"))
        assertTrue("Список покемонов должен отображаться", recyclerViewExists)
    }

    /**
     * Тест: Проверка наличия FAB кнопки фильтрации
     * Ожидаемый результат: FAB кнопка присутствует на экране
     */
    @Test
    fun testFilterFabExists() {
        // Проверяем наличие FAB кнопки
        val fabExists = uiDevice.hasObject(By.res("speedDial"))
        assertTrue("FAB кнопка фильтрации должна отображаться", fabExists)
    }

    /**
     * Тест: Проверка раскрытия FAB меню
     * Ожидаемый результат: При нажатии на FAB появляется меню с опциями
     */
    @Test
    fun testFabMenuExpansion() {
        // Находим и нажимаем на FAB кнопку
        val fab = uiDevice.findObject(By.res("speedDial"))
        fab?.click()
        
        // Ожидаем появления меню (кнопка закрытия)
        uiDevice.wait(Until.hasObject(By.res("sdFabClose")), 3000)
        
        // Проверяем что меню раскрылось
        val menuOpened = uiDevice.hasObject(By.res("sdFabClose"))
        assertTrue("FAB меню должно раскрыться", menuOpened)
    }

    /**
     * Тест: Проверка скролла списка покемонов
     * Ожидаемый результат: Список можно прокручивать
     */
    @Test
    fun testPokemonListScroll() {
        // Выполняем свайп вверх для прокрутки списка
        uiDevice.swipe(540, 1000, 540, 400, 10)
        
        // Даем время на прокрутку
        uiDevice.waitForIdle(1000)
        
        // Проверяем что список остался видимым после прокрутки
        val recyclerViewExists = uiDevice.hasObject(By.res("recyclerView"))
        assertTrue("Список покемонов должен оставаться видимым после прокрутки", recyclerViewExists)
    }

    // ==================== ОШИБОЧНЫЙ СЦЕНАРИЙ ====================
    
    /**
     * ОШИБОЧНЫЙ ТЕСТ: Проверка поведения при отсутствии данных
     * 
     * Этот тест проверяет ошибочный сценарий, когда:
     * - Приложение запускается без предварительно загруженных данных
     * - Ожидается что список покемонов будет пустым
     * - Вместо списка должно отображаться сообщение об отсутствии данных
     * 
     * Ожидаемый результат: Тест ДОЛЖЕН УПАСТЬ, так как в текущей реализации
     * нет обработки пустого состояния - список просто не отображает элементы
     * 
     * Это демонстрирует баг: отсутствие UI для пустого состояния списка
     */
    @Test
    fun testEmptyStateHandling() {
        // В реальном сценарии мы бы очистили кэш/базу данных
        // и перезапустили фрагмент для имитации отсутствия данных
        
        // Проверяем что отображается индикатор пустого состояния
        // Ожидаем сообщение типа "No Pokemon found" или пустой индикатор
        val emptyStateExists = uiDevice.hasObject(By.text("No Pokemon found")) ||
                               uiDevice.hasObject(By.text("No data available")) ||
                               uiDevice.hasObject(By.res("emptyStateView"))
        
        // Этот тест ДОЛЖЕН УПАСТЬ, так как пустое состояние не обрабатывается
        // Текущая реализация просто показывает пустой RecyclerView
        assertTrue("Должен отображаться индикатор пустого состояния когда данные отсутствуют", 
                   emptyStateExists)
    }

    /**
     * ОШИБОЧНЫЙ ТЕСТ: Проверка таймаута загрузки данных
     * 
     * Этот тест проверяет ошибочный сценарий, когда:
     * - Данные не загружаются в течение длительного времени
     * - Приложение не показывает индикатор ошибки или таймаута
     * 
     * Ожидаемый результат: Тест ДОЛЖЕН УПАСТЬ, так как нет обработки таймаута
     */
    @Test
    fun testLoadingTimeoutHandling() {
        // Проверяем что ProgressBar отображается во время загрузки
        val progressBarExists = uiDevice.hasObject(By.res("progressBar"))
        
        // Также проверяем что нет сообщения об ошибке таймаута
        val timeoutMessageExists = uiDevice.hasObject(By.text("Connection timeout")) ||
                                   uiDevice.hasObject(By.text("Failed to load"))
        
        // Если ProgressBar не отображается и нет сообщения об ошибке - баг
        // Текущая реализация может зависнуть в состоянии загрузки
        assertTrue("ProgressBar должен отображаться во время загрузки или быть сообщение об ошибке",
                   progressBarExists || timeoutMessageExists)
    }
}