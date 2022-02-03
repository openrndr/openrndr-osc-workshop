package examples

import org.openrndr.application
import org.openrndr.extra.compositor.*
import org.openrndr.extra.fx.blur.GaussianBloom
import org.openrndr.extra.fx.distort.Lenses
import org.openrndr.extra.osc.OSC
import org.openrndr.math.Vector3

fun main() {
    application {
        configure {
            width = 1280
            height = 720
        }
        program {

            val accel = DoubleArray(3)

            val osc = OSC(portIn = 9000)
            osc.listen("/linear_acceleration/x") { address, message ->
                accel[0] = message[0].toString().toDouble()
            }
            osc.listen("/linear_acceleration/y") { address, message ->
                accel[1] = message[0].toString().toDouble()
            }
            osc.listen("/linear_acceleration/z") { address, message ->
                accel[2] = message[0].toString().toDouble()
            }



            val composition = compose {
                layer {
                    layer {
                        clearColor = null
                        draw {
                            drawer.circle(width / 2.0 + accel[0] * width/2.0, height/2.0 + accel[1] * height/2.0, 10.0)
                        }
                    }
//                    post(Lenses()) {
//                        scale = 1.2
//                    }
                }
            }

            extend {
                composition.draw(drawer)
            }


        }

    }
}