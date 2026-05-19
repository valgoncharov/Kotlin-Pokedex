package dev.marcosfarias.pokedex.appiumtest

import io.appium.java_client.AppiumDriver
import io.appium.java_client.android.AndroidDriver
import org.junit.After
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.openqa.selenium.By
import org.openqa.selenium.remote.DesiredCapabilities
import java.net.MalformedURLException
import java.net.URL

/**
 * Appium тесты для экрана Home (главный экран приложения)
 * Тестирование проводится как "черный ящик" с использованием Appium
 * Тесты могут работать как на Android, так и на iOS
 */
class HomeScreenAppiumTest {

    private var driver: AppiumDriver? = null
    private val appPackage = "dev.marcosfarias.pokedex"
    private val appActivity = ".MainActivity"

    @Before
    @Throws(MalformedURLException::class)
    fun setUp() {
        val capabilities = DesiredCapabilities()
        
        // Настройка для Android
        capabilities.setCapability("platformName", "Android")
        capabilities.setCapability("automationName", "UiAutomator2")
        capabilities.setCapability("appPackage", appPackage)
        capabilities.setCapability("appActivity", appActivity)
        capabilities.setCapability("deviceName", "Android Emulator")
        capabilities.setCapability("newCommandTimeout", 60)
        
        // Инициализация драйвера
        driver = AndroidDriver(URL("http://127.0.0.1:4723/wd/hub"), capabilities)
    }

    @After
    fun tearDown() {
        driver?.quit()
    }

    /**
     * Тест: Проверка отображения главного экрана
     * Ожидаемый результат: Экран загружается и отображает заголовок
     */
    @Test
    fun testHomeScreenDisplayed() {
        // Ожидаем загрузки экрана
        Thread.sleep(2000)
        
        // Проверяем наличие заголовка
        val titleElement = driver?.findElement(By.xpath("//android.widget.TextView[@text='What Pokémon\\nare you looking for?']"))
        assertTrue("Главный экран должен отображаться", titleElement != null)
    }

    /**
     * Тест: Проверка наличия строки поиска
     * Ожидаемый результат: Поле поиска присутствует на экране
     */
    @Test
    fun testSearchBarExists() {
        // Проверяем наличие строки поиска
        val searchElement = driver?.findElement(By.xpath("//android.widget.TextView[@text='Search Pokemon']"))
        assertTrue("Строка поиска должна отображаться", searchElement != null)
    }

    /**
     * Тест: Проверка наличия меню на главном экране
     * Ожидаемый результат: Меню с пунктами (Pokedex, Moves и т.д.) отображается
     */
    @Test
    fun testMenuItemsDisplayed() {
        // Проверяем наличие пункта меню Pokedex
        val pokedexMenu = driver?.findElement(By.xpath("//android.widget.TextView[@text='Pokedex']"))
        assertTrue("Пункт меню 'Pokedex' должен отображаться", pokedexMenu != null)
    }

    /**
     * Тест: Проверка навигации к экрану Pokedex через меню
     * Ожидаемый результат: При нажатии на Pokedex в меню происходит переход
     */
    @Test
    fun testNavigateToPokedex() {
        // Находим и нажимаем на пункт меню Pokedex
        val pokedexMenu = driver?.findElement(By.xpath("//android.widget.TextView[@text='Pokedex']"))
        pokedexMenu?.click()
        
        // Ожидаем перехода
        Thread.sleep(2000)
        
        // Проверяем что экран Pokedex отображается
        val pokedexScreen = driver?.findElement(By.xpath("//android.widget.TextView[@text='Pokedex']"))
        assertTrue("Должен произойти переход к экрану Pokedex", pokedexScreen != null)
    }

    /**
     * Тест: Проверка раздела новостей
     * Ожидаемый результат: Секция новостей отображается на экране
     */
    @Test
    fun testNewsSectionDisplayed() {
        val newsTitle = driver?.findElement(By.xpath("//android.widget.TextView[@text='Pokémon News']"))
        assertTrue("Раздел 'Pokémon News' должен отображаться", newsTitle != null)
    }

    // ==================== ОШИБОЧНЫЙ СЦЕНАРИЙ ====================
    
    /**
     * ОШИБОЧНЫЙ ТЕСТ: Проверка обработки ошибки сети при загрузке данных
     * 
     * Этот тест проверяет ошибочный сценарий, когда:
     * - Приложение запускается без интернет-соединения
     * - Ожидается что будет показано сообщение об ошибке сети
     * 
     * Ожидаемый результат: Тест ДОЛЖЕН УПАСТЬ, так как в текущей реализации
     * может не быть корректной обработки ошибок сети
     */
    @Test
    fun testNetworkErrorHandling() {
        // Проверяем наличие индикатора ошибки сети
        val networkError = driver?.findElement(By.xpath("//android.widget.TextView[contains(@text, 'No internet')]")) 
            ?: driver?.findElement(By.xpath("//android.widget.TextView[contains(@text, 'Network error')]"))
            ?: driver?.findElement(By.xpath("//android.widget.TextView[contains(@text, 'Connection failed')]"))
        
        // Проверяем наличие кнопки повтора
        val retryButton = driver?.findElement(By.xpath("//android.widget.Button[contains(@text, 'Retry')]"))
        
        // Если нет ни ошибки сети, ни кнопки повтора - это потенциальный баг
        // Приложение должно показывать пользователю информацию об отсутствии сети
        assertTrue("Должно отображаться сообщение об ошибке сети или кнопка повтора", 
                   networkError != null || retryButton != null)
    }
}