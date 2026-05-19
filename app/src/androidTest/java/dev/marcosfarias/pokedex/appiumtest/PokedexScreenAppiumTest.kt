package dev.marcosfarias.pokedex.appiumtest

import io.appium.java_client.AppiumDriver
import io.appium.java_client.MobileElement
import io.appium.java_client.android.AndroidDriver
import io.appium.java_client.remote.AndroidMobileCapabilityType
import io.appium.java_client.remote.AutomationName
import io.appium.java_client.remote.MobileCapabilityType
import org.junit.After
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.openqa.selenium.By
import org.openqa.selenium.Dimension
import org.openqa.selenium.remote.DesiredCapabilities
import java.net.MalformedURLException
import java.net.URL

/**
 * Appium тесты для экрана Pokedex (список покемонов)
 * Тестирование проводится как "черный ящик" с использованием Appium
 * Тесты могут работать как на Android, так и на iOS
 */
class PokedexScreenAppiumTest {

    private var driver: AppiumDriver<MobileElement>? = null
    private val appPackage = "dev.marcosfarias.pokedex"
    private val appActivity = ".MainActivity"

    @Before
    @Throws(MalformedURLException::class)
    fun setUp() {
        val capabilities = DesiredCapabilities()
        
        // Настройка для Android
        capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, "Android")
        capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, AutomationName.ANDROID_UIAUTOMATOR2)
        capabilities.setCapability(AndroidMobileCapabilityType.APP_PACKAGE, appPackage)
        capabilities.setCapability(AndroidMobileCapabilityType.APP_ACTIVITY, appActivity)
        capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "Android Emulator")
        capabilities.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, 60)
        
        // Инициализация драйвера
        driver = AndroidDriver(URL("http://127.0.0.1:4723/wd/hub"), capabilities)
    }

    @After
    fun tearDown() {
        driver?.quit()
    }

    /**
     * Тест: Проверка отображения экрана Pokedex
     * Ожидаемый результат: Экран загружается и отображает заголовок "Pokedex"
     */
    @Test
    fun testPokedexScreenDisplayed() {
        // Ожидаем загрузки экрана
        Thread.sleep(2000)
        
        // Проверяем наличие заголовка Pokedex
        val titleElement = driver?.findElement(By.xpath("//android.widget.TextView[@text='Pokedex']"))
        assertTrue("Экран Pokedex должен отображаться", titleElement != null)
    }

    /**
     * Тест: Проверка наличия списка покемонов
     * Ожидаемый результат: RecyclerView со списком покемонов присутствует на экране
     */
    @Test
    fun testPokemonListDisplayed() {
        // Проверяем наличие списка покемонов
        val recyclerView = driver?.findElement(By.id("recyclerView"))
        assertTrue("Список покемонов должен отображаться", recyclerView != null)
    }

    /**
     * Тест: Проверка наличия FAB кнопки фильтрации
     * Ожидаемый результат: FAB кнопка присутствует на экране
     */
    @Test
    fun testFilterFabExists() {
        // Проверяем наличие FAB кнопки
        val fab = driver?.findElement(By.id("speedDial"))
        assertTrue("FAB кнопка фильтрации должна отображаться", fab != null)
    }

    /**
     * Тест: Проверка раскрытия FAB меню
     * Ожидаемый результат: При нажатии на FAB появляется меню с опциями
     */
    @Test
    fun testFabMenuExpansion() {
        // Находим и нажимаем на FAB кнопку
        val fab = driver?.findElement(By.id("speedDial"))
        fab?.click()
        
        // Ожидаем появления меню
        Thread.sleep(1000)
        
        // Проверяем что меню раскрылось (ищем кнопку закрытия)
        val closeButton = driver?.findElement(By.id("sdFabClose"))
        assertTrue("FAB меню должно раскрыться", closeButton != null)
    }

    /**
     * Тест: Проверка скролла списка покемонов
     * Ожидаемый результат: Список можно прокручивать
     */
    @Test
    fun testPokemonListScroll() {
        // Получаем размер экрана
        val dimension: Dimension? = driver?.manage()?.window()?.size
        
        if (dimension != null) {
            // Выполняем свайп вверх для прокрутки списка
            val startX = dimension.width / 2
            val startY = (dimension.height * 0.8).toInt()
            val endY = (dimension.height * 0.2).toInt()
            
            driver?.swipe(startX, startY, startX, endY, 500)
            
            // Даем время на прокрутку
            Thread.sleep(1000)
        }
        
        // Проверяем что список остался видимым после прокрутки
        val recyclerView = driver?.findElement(By.id("recyclerView"))
        assertTrue("Список покемонов должен оставаться видимым после прокрутки", recyclerView != null)
    }

    /**
     * Тест: Проверка наличия ProgressBar
     * Ожидаемый результат: ProgressBar присутствует на экране
     */
    @Test
    fun testProgressBarExists() {
        val progressBar = driver?.findElement(By.id("progressBar"))
        assertTrue("ProgressBar должен отображаться", progressBar != null)
    }

    /**
     * Тест: Проверка навигации к деталям покемона
     * Ожидаемый результат: При нажатии на покемона происходит переход к экрану деталей
     */
    @Test
    fun testNavigateToPokemonDetails() {
        // Ожидаем загрузки списка
        Thread.sleep(3000)
        
        // Пытаемся найти первый элемент списка покемонов
        val firstPokemon = driver?.findElement(By.xpath("//androidx.recyclerview.widget.RecyclerView/android.widget.LinearLayout[1]"))
        
        if (firstPokemon != null) {
            firstPokemon.click()
            Thread.sleep(2000)
            
            // Проверяем что произошел переход (должен появиться экран деталей)
            val dashboardTitle = driver?.findElement(By.xpath("//android.widget.TextView[@text='Bulbasaur']"))
                ?: driver?.findElement(By.xpath("//android.widget.TextView[contains(@text, '#')]"))
            
            assertTrue("Должен произойти переход к экрану деталей покемона", dashboardTitle != null)
        }
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
     */
    @Test
    fun testEmptyStateHandling() {
        // Проверяем что отображается индикатор пустого состояния
        // Ожидаем сообщение типа "No Pokemon found" или пустой индикатор
        val emptyStateExists = driver?.findElement(By.xpath("//android.widget.TextView[contains(@text, 'No Pokemon')]")) != null
            || driver?.findElement(By.xpath("//android.widget.TextView[contains(@text, 'No data')]")) != null
            || driver?.findElement(By.xpath("//android.widget.TextView[contains(@text, 'No results')]")) != null
            || driver?.findElement(By.id("emptyStateView")) != null
        
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
        val progressBar = driver?.findElement(By.id("progressBar"))
        
        // Также проверяем что нет сообщения об ошибке таймаута
        val timeoutMessage = driver?.findElement(By.xpath("//android.widget.TextView[contains(@text, 'timeout')]"))
            ?: driver?.findElement(By.xpath("//android.widget.TextView[contains(@text, 'Failed to load')]"))
        
        // Если ProgressBar не отображается и нет сообщения об ошибке - баг
        // Текущая реализация может зависнуть в состоянии загрузки
        assertTrue("ProgressBar должен отображаться во время загрузки или быть сообщение об ошибке",
                   progressBar != null || timeoutMessage != null)
    }
}