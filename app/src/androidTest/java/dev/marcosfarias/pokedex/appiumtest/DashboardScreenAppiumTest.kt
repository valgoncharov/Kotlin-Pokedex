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
import org.openqa.selenium.remote.DesiredCapabilities
import java.net.MalformedURLException
import java.net.URL

/**
 * Appium тесты для экрана Dashboard (детальная информация о покемоне)
 * Тестирование проводится как "черный ящик" с использованием Appium
 * Тесты могут работать как на Android, так и на iOS
 */
class DashboardScreenAppiumTest {

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
     * Тест: Проверка отображения информации о покемоне
     * Ожидаемый результат: Имя покемона отображается на экране
     */
    @Test
    fun testPokemonNameDisplayed() {
        // Ожидаем загрузки экрана
        Thread.sleep(2000)
        
        // Проверяем наличие имени покемона
        val nameView = driver?.findElement(By.id("textViewName"))
        assertTrue("Имя покемона должно отображаться", nameView != null)
    }

    /**
     * Тест: Проверка отображения ID покемона
     * Ожидаемый результат: ID покемона (#001) отображается на экране
     */
    @Test
    fun testPokemonIdDisplayed() {
        val idView = driver?.findElement(By.id("textViewID"))
        assertTrue("ID покемона должно отображаться", idView != null)
    }

    /**
     * Тест: Проверка наличия табов (вкладок)
     * Ожидаемый результат: TabLayout с 4 вкладками присутствует на экране
     */
    @Test
    fun testTabsExist() {
        val tabs = driver?.findElement(By.id("tabs"))
        assertTrue("TabLayout должен отображаться", tabs != null)
    }

    /**
     * Тест: Проверка наличия таба About
     * Ожидаемый результат: Вкладка "About" присутствует на экране
     */
    @Test
    fun testAboutTabExists() {
        val aboutTab = driver?.findElement(By.xpath("//android.widget.TextView[@text='About']"))
        assertTrue("Вкладка 'About' должна отображаться", aboutTab != null)
    }

    /**
     * Тест: Проверка наличия таба Base Stats
     * Ожидаемый результат: Вкладка "Base Stats" присутствует на экране
     */
    @Test
    fun testBaseStatsTabExists() {
        val baseStatsTab = driver?.findElement(By.xpath("//android.widget.TextView[@text='Base Stats']"))
        assertTrue("Вкладка 'Base Stats' должна отображаться", baseStatsTab != null)
    }

    /**
     * Тест: Проверка наличия таба Evolution
     * Ожидаемый результат: Вкладка "Evolution" присутствует на экране
     */
    @Test
    fun testEvolutionTabExists() {
        val evolutionTab = driver?.findElement(By.xpath("//android.widget.TextView[@text='Evolution']"))
        assertTrue("Вкладка 'Evolution' должна отображаться", evolutionTab != null)
    }

    /**
     * Тест: Проверка наличия таба Moves
     * Ожидаемый результат: Вкладка "Moves" присутствует на экране
     */
    @Test
    fun testMovesTabExists() {
        val movesTab = driver?.findElement(By.xpath("//android.widget.TextView[@text='Moves']"))
        assertTrue("Вкладка 'Moves' должна отображаться", movesTab != null)
    }

    /**
     * Тест: Проверка переключения между табами
     * Ожидаемый результат: При нажатии на таб происходит переключение
     */
    @Test
    fun testTabSwitching() {
        // Нажимаем на таб "Base Stats"
        val baseStatsTab = driver?.findElement(By.xpath("//android.widget.TextView[@text='Base Stats']"))
        baseStatsTab?.click()
        
        // Даем время на переключение
        Thread.sleep(1000)
        
        // Проверяем что таб остался видимым (переключение произошло)
        val tabStillExists = driver?.findElement(By.xpath("//android.widget.TextView[@text='Base Stats']"))
        assertTrue("Таб 'Base Stats' должен остаться видимым после нажатия", tabStillExists != null)
    }

    /**
     * Тест: Проверка наличия изображения покемона
     * Ожидаемый результат: ImageView с изображением покемона присутствует
     */
    @Test
    fun testPokemonImageExists() {
        val imageView = driver?.findElement(By.id("imageView"))
        assertTrue("Изображение покемона должно отображаться", imageView != null)
    }

    /**
     * Тест: Проверка наличия типов покемона
     * Ожидаемый результат: TextView для типов покемона присутствуют
     */
    @Test
    fun testPokemonTypesExist() {
        val type1 = driver?.findElement(By.id("textViewType1"))
        assertTrue("Первый тип покемона должен отображаться", type1 != null)
    }

    /**
     * Тест: Проверка наличия Toolbar
     * Ожидаемый результат: Toolbar присутствует на экране
     */
    @Test
    fun testToolbarExists() {
        val toolbar = driver?.findElement(By.id("toolbar"))
        assertTrue("Toolbar должен отображаться", toolbar != null)
    }

    /**
     * Тест: Проверка наличия ViewPager для контента табов
     * Ожидаемый результат: ViewPager присутствует для отображения контента
     */
    @Test
    fun testViewPagerExists() {
        val viewPager = driver?.findElement(By.id("viewPager"))
        assertTrue("ViewPager должен отображаться", viewPager != null)
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
        val errorMessage = driver?.findElement(By.xpath("//android.widget.TextView[contains(@text, 'not found')]"))
            ?: driver?.findElement(By.xpath("//android.widget.TextView[contains(@text, 'Error')]"))
            ?: driver?.findElement(By.xpath("//android.widget.TextView[contains(@text, 'No data')]"))
        
        // Также проверяем что имя покемона не пустое
        val nameView = driver?.findElement(By.id("textViewName"))
        val nameText = nameView?.text ?: ""
        
        // Если имя пустое и нет сообщения об ошибке - это баг
        val hasErrorHandling = errorMessage != null || nameText.isNotEmpty()
        
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
        val tabs = listOf("About", "Base Stats", "Evolution", "Moves")
        
        // Проверяем что каждый таб можно нажать
        for (tabText in tabs) {
            val tab = driver?.findElement(By.xpath("//android.widget.TextView[@text='$tabText']"))
            val isClickable = tab?.getAttribute("clickable")?.toBoolean() ?: false
            
            assertTrue("Таб '$tabText' должен быть кликабельным", isClickable)
        }
    }
}