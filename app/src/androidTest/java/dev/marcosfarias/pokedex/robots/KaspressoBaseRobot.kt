package dev.marcosfarias.pokedex.robots

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers

/**
 * Базовый класс для Kaspresso тестов
 * Предоставляет удобные методы для работы с UI элементами
 */
interface KaspressoBaseRobot {

    /**
     * Проверяет, что текст элемента соответствует ожидаемому
     */
    fun testViewText(id: Int, text: String): ViewInteraction =
        Espresso.onView(ViewMatchers.withId(id))
            .check(ViewAssertions.matches(ViewMatchers.withText(text)))

    /**
     * Проверяет, что элемент отображается
     */
    fun isViewDisplayed(id: Int): ViewInteraction =
        Espresso.onView(ViewMatchers.withId(id))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

    /**
     * Проверяет, что элемент не отображается (Оставить ли такие методы ?)
     */
    fun isViewNotDisplayed(id: Int): ViewInteraction =
        Espresso.onView(ViewMatchers.withId(id))
            .check(ViewAssertions.matches(ViewMatchers.withEffectiveVisibility(ViewMatchers.Visibility.GONE)))

    /**
     * Проверяет, что элемент не существует в иерархии
     */
    fun viewDoesNotExist(id: Int): ViewInteraction =
        Espresso.onView(ViewMatchers.withId(id))
            .check(ViewAssertions.doesNotExist())

    /**
     * Клик по элементу
     */
    fun onClick(id: Int): ViewInteraction =
        Espresso.onView(ViewMatchers.withId(id))
            .perform(ViewActions.click())

    /**
     * Клик по элементу списка по позиции
     */
    fun onClickItemAtPosition(recyclerViewId: Int, position: Int): ViewInteraction =
        Espresso.onView(ViewMatchers.withId(recyclerViewId))
            .perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(position, ViewActions.click())
            )

    /**
     * Клик по первому элементу списка
     */
    fun onClickFirstItem(recyclerViewId: Int): ViewInteraction =
        onClickItemAtPosition(recyclerViewId, 0)

    /**
     * Клик по элементу списка с определенным текстом
     */
    fun onClickItemWithText(recyclerViewId: Int, text: String): ViewInteraction =
        Espresso.onView(ViewMatchers.withId(recyclerViewId))
            .perform(
                RecyclerViewActions.actionOnItem<RecyclerView.ViewHolder>(
                    ViewMatchers.hasDescendant(ViewMatchers.withText(text)),
                    ViewActions.click()
                )
            )

    /**
     * Проверяет наличие элемента с текстом в списке
     */
    fun isRecyclerViewItemDisplayed(viewId: Int, text: String): ViewInteraction =
        Espresso.onView(ViewMatchers.withId(viewId))
            .perform(
                RecyclerViewActions.actionOnItem<RecyclerView.ViewHolder>(
                    ViewMatchers.hasDescendant(ViewMatchers.withText(text)),
                    ViewActions.click()
                )
            )

    /**
     * Скроллит список к позиции
     */
    fun scrollToPosition(recyclerViewId: Int, position: Int): ViewInteraction =
        Espresso.onView(ViewMatchers.withId(recyclerViewId))
            .perform(
                RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(position)
            )

    /**
     * Вводит текст в поле ввода
     */
    fun typeText(id: Int, text: String): ViewInteraction =
        Espresso.onView(ViewMatchers.withId(id))
            .perform(ViewActions.typeText(text))

    /**
     * Очищает поле ввода и вводит текст
     */
    fun clearAndTypeText(id: Int, text: String): ViewInteraction =
        Espresso.onView(ViewMatchers.withId(id))
            .perform(ViewActions.clearText(), ViewActions.typeText(text))

    /**
     * Закрывает клавиатуру
     */
    fun closeKeyboard() {
        Espresso.closeSoftKeyboard()
    }

    /**
     * Нажимает кнопку "назад"
     */
    fun pressBack() {
        Espresso.pressBack()
    }

    /**
     * Проверяет, что элемент содержит текст
     */
    fun viewContainsText(id: Int, text: String): ViewInteraction =
        Espresso.onView(ViewMatchers.withId(id))
            .check(ViewAssertions.matches(ViewMatchers.withText(text)))

    /**
     * Проверяет, что элемент кликабелен
     */
    fun isClickable(id: Int): ViewInteraction =
        Espresso.onView(ViewMatchers.withId(id))
            .check(ViewAssertions.matches(ViewMatchers.isClickable()))

    /**
     * Проверяет, что элемент не кликабелен
     */
    fun isNotClickable(id: Int): ViewInteraction =
        Espresso.onView(ViewMatchers.withId(id))
            .check(ViewAssertions.matches(ViewMatchers.isNotClickable()))

    /**
     * Проверяет, что элемент имеет фокус
     */
    fun hasFocus(id: Int): ViewInteraction =
        Espresso.onView(ViewMatchers.withId(id))
            .check(ViewAssertions.matches(ViewMatchers.hasFocus()))

    /**
     * Проверяет, что элемент выбран
     */
    fun isSelected(id: Int): ViewInteraction =
        Espresso.onView(ViewMatchers.withId(id))
            .check(ViewAssertions.matches(ViewMatchers.isSelected()))

    /**
     * Проверяет, что элемент не выбран
     */
    fun isNotSelected(id: Int): ViewInteraction =
        Espresso.onView(ViewMatchers.withId(id))
            .check(ViewAssertions.matches(ViewMatchers.isNotSelected()))

    /**
     * Проверяет, что элемент включен
     */
    fun isEnabled(id: Int): ViewInteraction =
        Espresso.onView(ViewMatchers.withId(id))
            .check(ViewAssertions.matches(ViewMatchers.isEnabled()))

    /**
     * Проверяет, что элемент выключен
     */
    fun isDisabled(id: Int): ViewInteraction =
        Espresso.onView(ViewMatchers.withId(id))
            .check(ViewAssertions.matches(ViewMatchers.withEffectiveVisibility(ViewMatchers.Visibility.INVISIBLE)))

    /**
     * Проверяет, что элемент виден и включен
     */
    fun isVisibleAndEnabled(id: Int): ViewInteraction =
        Espresso.onView(ViewMatchers.withId(id))
            .check(ViewAssertions.matches(ViewMatchers.isCompletelyDisplayed()))

    /**
     * Проверяет, что элемент виден и выключен
     */
    fun isVisibleAndDisabled(id: Int): ViewInteraction =
        Espresso.onView(ViewMatchers.withId(id))
            .check(ViewAssertions.matches(ViewMatchers.withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)))

    /**
     * Помощник для поиска view по id
     */
    fun onView(id: Int): ViewInteraction =
        Espresso.onView(ViewMatchers.withId(id))

    /**
     * Помощник для поиска view по тексту
     */
    fun onViewWithText(text: String): ViewInteraction =
        Espresso.onView(ViewMatchers.withText(text))

    /**
     * Помощник для поиска view по ресурсу строки
     */
    fun onViewWithStringResource(stringResId: Int): ViewInteraction =
        Espresso.onView(ViewMatchers.withText(stringResId))

    /**
     * Ожидает указанное время в миллисекундах
     */
    fun waitFor(millis: Long) {
        Thread.sleep(millis)
    }

    /**
     * Swipe вверх
     */
    fun swipeUp(id: Int): ViewInteraction =
        Espresso.onView(ViewMatchers.withId(id))
            .perform(ViewActions.swipeUp())

    /**
     * Swipe вниз
     */
    fun swipeDown(id: Int): ViewInteraction =
        Espresso.onView(ViewMatchers.withId(id))
            .perform(ViewActions.swipeDown())

    /**
     * Swipe влево
     */
    fun swipeLeft(id: Int): ViewInteraction =
        Espresso.onView(ViewMatchers.withId(id))
            .perform(ViewActions.swipeLeft())

    /**
     * Swipe вправо
     */
    fun swipeRight(id: Int): ViewInteraction =
        Espresso.onView(ViewMatchers.withId(id))
            .perform(ViewActions.swipeRight())

    /**
     * Проверяет, что ProgressBar отображается
     */
    fun isProgressBarVisible(id: Int): ViewInteraction =
        Espresso.onView(ViewMatchers.withId(id))
            .check(ViewAssertions.matches(ViewMatchers.withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)))

    /**
     * Проверяет, что ProgressBar не отображается
     */
    fun isProgressBarNotVisible(id: Int): ViewInteraction =
        Espresso.onView(ViewMatchers.withId(id))
            .check(ViewAssertions.matches(ViewMatchers.withEffectiveVisibility(ViewMatchers.Visibility.GONE)))
}

object KaspressoBaseRobotImpl : KaspressoBaseRobot
