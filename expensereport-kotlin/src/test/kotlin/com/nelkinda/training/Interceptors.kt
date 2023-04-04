package com.nelkinda.training

import java.io.ByteArrayOutputStream
import java.io.PrintStream

fun interceptStdout(block: () -> Unit): String {
    val originalStdout = System.out
    val interceptedStdout = ByteArrayOutputStream()
    System.setOut(PrintStream(interceptedStdout))
    try {
        block()
    } finally {
        System.setOut(originalStdout)
    }
    return interceptedStdout.toString()
}
