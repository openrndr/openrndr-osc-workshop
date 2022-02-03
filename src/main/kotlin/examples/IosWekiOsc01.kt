package examples

import org.openrndr.application
import org.openrndr.color.ColorRGBa
import org.openrndr.extra.noclear.NoClear
import org.openrndr.extra.osc.OSC
import org.openrndr.math.Vector3

fun main() {
    application {
        configure {
            width = 720
            height = 720
        }
        program {
            var acc0 = Vector3(0.0, 0.0, 0.0)
            var acc1 = Vector3(0.0, 0.0, 0.0)

            val osc0 = OSC(portIn = 9000)
            osc0.listen("/wek/inputs") { address, message ->
                acc0 = Vector3(message[0].toString().toDouble() - 0.5, message[1].toString().toDouble() - 0.5, message[2].toString().toDouble() - 0.5)
            }

            val osc1 = OSC(portIn = 9001)
            osc1.listen("/wek/inputs") { address, message ->
                acc1 = Vector3(message[0].toString().toDouble() - 0.5, message[1].toString().toDouble() - 0.5, message[2].toString().toDouble() - 0.5)
            }

            extend(NoClear())
            extend {
                drawer.fill = ColorRGBa.WHITE
                drawer.stroke = ColorRGBa.BLACK
                drawer.circle(width/2.0 + acc0.x * width, height/2.0 + acc0.y * height, 20.0)

                drawer.fill = ColorRGBa.BLACK
                drawer.stroke = ColorRGBa.WHITE
                drawer.circle(width/2.0 + acc1.x * width, height/2.0 + acc1.y * height, 20.0)
            }
        }
    }
}