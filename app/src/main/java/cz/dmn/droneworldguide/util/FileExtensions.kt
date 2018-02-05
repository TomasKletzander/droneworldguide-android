package cz.dmn.droneworldguide.util

import java.io.File

fun File.appendPath(path: String, create: Boolean = false): File {
    val file = File(this, path)
    if (create && !file.exists()) {
        file.mkdirs()
    }
    return file
}