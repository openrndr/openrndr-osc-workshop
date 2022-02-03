package monitor

import library.getLocalIP
import org.openrndr.application
import org.openrndr.draw.loadFont
import org.openrndr.extra.osc.OSC

fun main() {
    application {
        configure {
            width = 1280
            height = 720
        }
        program {
            val ip = getLocalIP()
            val port = 9000
            val osc = OSC(portIn = port)
            val messages = mutableMapOf<String, String>()
            osc.listen("//") { address, message ->
                messages[address] = message.joinToString(", ")
            }

            val font = loadFont("data/fonts/default.otf", 20.0)
            extend {
                drawer.fontMap = font
                drawer.translate(20.0, 20.0)
                drawer.text("monitoring $ip : $port")
                messages.keys.sorted().forEach { address ->
                    drawer.translate(0.0, 20.0)
                    drawer.text("$address: ${messages[address]}")
                }
            }
        }
    }
}