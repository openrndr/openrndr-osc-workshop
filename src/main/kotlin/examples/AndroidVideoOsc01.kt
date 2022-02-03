package examples

import org.openrndr.application
import org.openrndr.color.ColorRGBa
import org.openrndr.draw.loadFont
import org.openrndr.extra.osc.OSC

/**
 * This is a demonstration for using the VideoOsc+ app on Android
 */

fun main() {
    application {
        configure {
            width = 1280
            height = 720
        }
        program {
            val port = 57120
            val osc = OSC(portIn = 57120)

            val horizontalResolution = 7
            val verticalResolution = 5

            val blues = DoubleArray(horizontalResolution * verticalResolution)
            val reds = DoubleArray(horizontalResolution * verticalResolution)
            val greens = DoubleArray(horizontalResolution * verticalResolution)

            // listen and decode osc messages. don't let this intimidate you :)
            osc.listen("/vosc/blue*") { address, message ->
                val indexRegex = Regex("([0-9]+)$")
                val index = indexRegex.find(address)?.groupValues?.getOrNull(1)?.toInt() ?: error("no number")
                blues[index - 1] = (message[0] as Double) / 255.0
            }
            osc.listen("/vosc/red*") { address, message ->
                val indexRegex = Regex("([0-9]+)$")
                val index = indexRegex.find(address)?.groupValues?.getOrNull(1)?.toInt() ?: error("no number")
                reds[index - 1] = (message[0] as Double) / 255.0
            }
            osc.listen("/vosc/green*") { address, message ->
                val indexRegex = Regex("([0-9]+)$")
                val index = indexRegex.find(address)?.groupValues?.getOrNull(1)?.toInt() ?: error("no number")
                greens[index -1] = (message[0] as Double) / 255.0
            }

            extend {
                val rwidth = width.toDouble() / horizontalResolution
                val rheight = height.toDouble() / verticalResolution
                for (y in 0 until verticalResolution) {
                    for (x in 0 until horizontalResolution) {
                        val i = y * horizontalResolution + x
                        drawer.fill = ColorRGBa(reds[i], greens[i], blues[i])
                        drawer.stroke = null
                        drawer.rectangle(x * rwidth, y * rheight, rwidth, rheight)
                    }
                }
            }
        }
    }
}