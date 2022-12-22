package com.example.mathfunlab3

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.example.mathfunlab3.ui.theme.MathFunLab3Theme
import com.himanshoe.charty.line.LineChart
import com.himanshoe.charty.line.model.LineData
import kotlin.math.pow
import kotlin.math.round

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MathFunLab3Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Greeting("Android")
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
    val lineData = eulerMethod(x0 = 0.0, y0 = 1.0, xn = 1.0, h = 0.1)
    LineChart(
        modifier = Modifier,
        colors = listOf(Color.Red, Color.Green),
        lineData = lineData
    )
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MathFunLab3Theme {
        Greeting("Android")
    }
}

fun eulerMethod(x0: Double, y0: Double, xn: Double, h: Double): List<LineData> {
    var x: Double = x0
    var y: Double = y0
    val lineData: MutableList<LineData> = mutableListOf(LineData(x, y.toFloat()))
    while (x <= xn) {
        val f = Expressions().define("x", x)
            .define("y", y)
            .eval("x^2 - 2*y")
            .toDouble()
        Log.i("euler: ", "x = $x, y = $y, f = $f, hf = ${h * f}")
        y += h * f
        x += h

        x = x.round(2)
        lineData.add(LineData(x, y.toFloat()))
    }
    return lineData
}

fun actualFunction(x: Double, y: Double): Double {
    return x.pow(2) - 2 * y
}

fun Double.round(decimals: Int): Double {
    var multiplier = 1.0
    repeat(decimals) { multiplier *= 10 }
    return round(this * multiplier) / multiplier
}
