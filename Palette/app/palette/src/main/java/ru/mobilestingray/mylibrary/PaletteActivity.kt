package ru.mobilestingray.mylibrary

import android.app.Activity
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.Matrix
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.widget.ImageView
import androidx.cardview.widget.CardView
import androidx.palette.graphics.Palette
import kotlin.math.floor

/**
 * Цель задачи: знакомство с [Palette]
 *
 * Приложение позволяет получить дипазон цветов в соответствии с выбранной областью.
 * Область определяется квадратом, полученным из начальных и конечных координат touch event.
 *
 * Достаточно нарисовать диагональ квадрата.
 *
 * В примере не были обработаны различные углы touch event и отрисовка выбранной области.
 */
class PaletteActivity : Activity(), View.OnTouchListener {

    companion object {
        private const val DEFAULT_COLOR = Color.TRANSPARENT
    }

    private var paletteBuilder: Palette.Builder? = null
    private var startXTouchCoordinate = 0
    private var startYTouchCoordinate = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_palette)

        findViewById<ImageView>(R.id.image).setImageResource(R.drawable.monsted_flowers)
        definePalette()
        findViewById<ImageView>(R.id.image).setOnTouchListener(this)
    }

    /**
     * Устанавливаем Bitmap изображения с которого хотим получить цвета.
     */
    private fun definePalette() {
        val bitmap = BitmapFactory.decodeResource(resources, R.drawable.monsted_flowers)
        paletteBuilder = Palette.Builder(bitmap)
    }

    override fun onTouch(v: View, event: MotionEvent): Boolean {

        val matrix = Matrix()
        findViewById<ImageView>(R.id.image).imageMatrix.invert(matrix)

        when (event.action) {
            MotionEvent.ACTION_DOWN // нажатие
            -> {
                getCurrentCoordinates(event, matrix).apply {
                    startXTouchCoordinate = first
                    startYTouchCoordinate = second
                }
            }
            MotionEvent.ACTION_MOVE -> {
            }
            MotionEvent.ACTION_UP // отпускание
                , MotionEvent.ACTION_CANCEL -> {

                getCurrentCoordinates(event, matrix).apply {
                    paletteBuilder?.setRegion(
                        startXTouchCoordinate,
                        startYTouchCoordinate,
                        first,
                        second
                    )
                    generatePalette()
                }
            }
        }

        return true
    }

    /**
     * Получаем начальные и конечные координаты touch event относительно текущего изображения.
     */
    private fun getCurrentCoordinates(event: MotionEvent, matrix: Matrix): Pair<Int, Int> {
        val touchEventCoordinates = floatArrayOf(event.x, event.y)
        matrix.mapPoints(touchEventCoordinates)
        return Pair(
            floor(touchEventCoordinates[0].toDouble()).toInt(),
            floor(touchEventCoordinates[1].toDouble()).toInt()
        )
    }

    /**
     * Асинхронно генерируем цвета из выбранной части изображения.
     */
    private fun generatePalette() {
        paletteBuilder?.generate { palette: Palette? ->

            palette?.getDominantColor(Color.TRANSPARENT)?.let {
                findViewById<CardView>(R.id.card_color_1).findViewById<ImageView>(R.id.card_color).setBackgroundColor(it)
            }

            palette?.getMutedColor(DEFAULT_COLOR)?.let {
                findViewById<CardView>(R.id.card_color_2).findViewById<ImageView>(R.id.card_color).setBackgroundColor(it)
            }

            palette?.getLightVibrantColor(DEFAULT_COLOR)?.let {
                findViewById<CardView>(R.id.card_color_3).findViewById<ImageView>(R.id.card_color).setBackgroundColor(it)
            }

            palette?.getLightMutedColor(DEFAULT_COLOR)?.let {
                findViewById<CardView>(R.id.card_color_4).findViewById<ImageView>(R.id.card_color).setBackgroundColor(it)
            }

            palette?.getDarkVibrantColor(DEFAULT_COLOR)?.let {
                findViewById<CardView>(R.id.card_color_5).findViewById<ImageView>(R.id.card_color).setBackgroundColor(it)
            }

            palette?.getDarkMutedColor(DEFAULT_COLOR)?.let {
                findViewById<CardView>(R.id.card_color_6).findViewById<ImageView>(R.id.card_color).setBackgroundColor(it)
            }
        }
    }
}
