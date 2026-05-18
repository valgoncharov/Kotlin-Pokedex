package dev.marcosfarias.pokedex.robots

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import dev.marcosfarias.pokedex.R

/**
 * Базовый класс для Kaspresso тестов
 * Предоставляет удобные методы для работы с UI элементами
 */
open class KaspressoBaseRobot {

    /**
     * Проверяет, что текст элемента соответствует ожидаемому
     */
    protected fun testViewText(id: Int, text: String): ViewInteraction =
        Espresso.onView(ViewMatchers.withId(id))
            .check(ViewAssertions.matches(ViewMatchers.withText(text)))

    /**
     * Проверяет, что элемент отображается
     */
    protected fun isViewDisplayed(id: Int): ViewInteraction =
        Espresso.onView(ViewMatchers.withId(id))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

    /**
     * Проверяет, что элемент не отображается
     */
    protected fun isViewNotDisplayed(id: Int): ViewInteraction =
        Espresso.onView(ViewMatchers.withId(id))
            .check(ViewAssertions.matches(ViewMatchers.withEffectiveVisibility(ViewMatchers.Visibility.GONE)))

    /**
     * Проверяет, что элемент не существует в иерархии
     */
    protected fun viewDoesNotExist(id: Int): ViewInteraction =
        Espresso.onView(ViewMatchers.withId(id))
            .check(ViewAssertions.doesNotExist())

    /**
     * Клик по элементу
     */
    protected fun onClick(id: Int): ViewInteraction =
        Espresso.onView(ViewMatchers.withId(id))
            .perform(ViewActions.click())

    /**
     * Клик по элементу списка по позиции
     */
    protected fun onClickItemAtPosition(recyclerViewId: Int, position: Int): ViewInteraction =
        Espresso.onView(ViewMatchers.withId(recyclerViewId))
            .perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(position, ViewActions.click())
            )

    /**
     * Клик по первому элементу списка
     */
    protected fun onClickFirstItem(recyclerViewId: Int): ViewInteraction =
        onClickItemAtPosition(recyclerViewId, 0)

    /**
     * Клик по элементу списка с определенным текстом
     */
    protected fun onClickItemWithText(recyclerViewId: Int, text: String): ViewInteraction =
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
    protected fun isRecyclerViewItemDisplayed(viewId: Int, text: String): ViewInteraction =
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
    protected fun scrollToPosition(recyclerViewId: Int, position: Int): ViewInteraction =
        Espresso.onView(ViewMatchers.withId(recyclerViewId))
            .perform(
                RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(position)
            )

    /**
     * Вводит текст в поле ввода
     */
    protected fun typeText(id: Int, text: String): ViewInteraction =
        Espresso.onView(ViewMatchers.withId(id))
            .perform(ViewActions.typeText(text))

    /**
     * Очищает поле ввода и вводит текст
     */
    protected fun clearAndTypeText(id: Int, text: String): ViewInteraction =
        Espresso.onView(ViewMatchers.withId(id))
            .perform(ViewActions.clearText(), ViewActions.typeText(text))

    /**
     * Закрывает клавиатуру
     */
    protected fun closeKeyboard() {
        Espresso.closeSoftKeyboard()
    }

    /**
     * Нажимает кнопку "назад"
     */
    protected fun pressBack() {
        Espresso.pressBack()
    }

    /**
     * Проверяет, что элемент содержит текст
     */
    protected fun viewContainsText(id: Int, text: String): ViewInteraction =
        Espresso.onView(ViewMatchers.withId(id))
            .check(ViewAssertions.matches(ViewMatchers.withText(text)))

    /**
     * Проверяет, что элемент кликабелен
     */
    protected fun isClickable(id: Int): ViewInteraction =
        Espresso.onView(ViewMatchers.withId(id))
            .check(ViewAssertions.matches(ViewMatchers.isClickable()))

    /**
     * Проверяет, что элемент не кликабелен
     */
    protected fun isNotClickable(id: Int): ViewInteraction =
        Espresso.onView(ViewMatchers.withId(id))
            .check(ViewAssertions.matches(ViewMatchers.isNotClickable()))

    /**
     * Проверяет, что элемент имеет фокус
     */
    protected fun hasFocus(id: Int): ViewInteraction =
        Espresso.onView(ViewMatchers.withId(id))
            .check(ViewAssertions.matches(ViewMatchers.hasFocus()))

    /**
     * Проверяет, что элемент выбран
     */
    protected fun isSelected(id: Int): ViewInteraction =
        Espresso.onView(ViewMatchers.withId(id))
            .check(ViewAssertions.matches(ViewMatchers.isSelected()))

    /**
     * Проверяет, что элемент не выбран
     */
    protected fun isNotSelected(id: Int): ViewInteraction =
        Espresso.onView(ViewMatchers.withId(id))
            .check(ViewAssertions.matches(ViewMatchers.isNotSelected()))

    /**
     * Проверяет, что элемент включен
     */
    protected fun isEnabled(id: Int): ViewInteraction =
        Espresso.onView(ViewMatchers.withId(id))
            .check(ViewAssertions.matches(ViewMatchers.isEnabled()))

    /**
     * Проверяет, что элемент выключен
     */
    protected fun isDisabled(id: Int): ViewInteraction =
        Espresso.onView(ViewMatchers.withId(id))
            .check(ViewAssertions.matches(ViewMatchers.withEffectiveVisibility(ViewMatchers.Visibility.INVISIBLE)))

    /**
     * Проверяет, что элемент виден и включен
     */
    protected fun isVisibleAndEnabled(id: Int): ViewInteraction =
        Espresso.onView(ViewMatchers.withId(id))
            .check(ViewAssertions.matches(ViewMatchers.isCompletelyDisplayed()))

    /**
     * Проверяет, что элемент виден и выключен
     */
    protected fun isVisibleAndDisabled(id: Int): ViewInteraction =
        Espresso.onView(ViewMatchers.withId(id))
            .check(ViewAssertions.matches(ViewMatchers.withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)))

    /**
     * Помощник для поиска view по id
     */
    protected fun onView(id: Int): ViewInteraction =
        Espresso.onView(ViewMatchers.withId(id))

    /**
     * Помощник для поиска view по тексту
     */
    protected fun onViewWithText(text: String): ViewInteraction =
        Espresso.onView(ViewMatchers.withText(text))

    /**
     * Помощник для поиска view по ресурсу строки
     */
    protected fun onViewWithStringResource(stringResId: Int): ViewInteraction =
        Espresso.onView(ViewMatchers.withText(stringResId))

    /**
     * Ожидает указанное время в миллисекундах
     */
    protected fun waitFor(millis: Long) {
        Thread.sleep(millis)
    }

    /**
     * Swipe вверх
     */
    protected fun swipeUp(id: Int): ViewInteraction =
        Espresso.onView(ViewMatchers.withId(id))
            .perform(ViewActions.swipeUp())

    /**
     * Swipe вниз
     */
    protected fun swipeDown(id: Int): ViewInteraction =
        Espresso.onView(ViewMatchers.withId(id))
            .perform(ViewActions.swipeDown())

    /**
     * Swipe влево
     */
    protected fun swipeLeft(id: Int): ViewInteraction =
        Espresso.onView(ViewMatchers.withId(id))
            .perform(ViewActions.swipeLeft())

    /**
     * Swipe вправо
     */
    protected fun swipeRight(id: Int): ViewInteraction =
        Espresso.onView(ViewMatchers.withId(id))
            .perform(ViewActions.swipeRight())

    /**
     * Проверяет, что ProgressBar отображается
     */
    protected fun isProgressBarVisible(id: Int): ViewInteraction =
        Espresso.onView(ViewMatchers.withId(id))
            .check(ViewAssertions.matches(ViewMatchers.withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)))

    /**
     * Проверяет, что ProgressBar не отображается
     */
    protected fun isProgressBarNotVisible(id: Int): ViewInteraction =
        Espresso.onView(ViewMatchers.withId(id))
            .check(ViewAssertions.matches(ViewMatchers.withEffectiveVisibility(ViewMatchers.Visibility.GONE)))
}