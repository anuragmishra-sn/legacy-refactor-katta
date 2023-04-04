package com.nelkinda.training

import org.junit.jupiter.api.Assertions.assertEquals

fun assertStdout(expected: String, block: () -> Unit) {
    val actual = interceptStdout(block)
    assertEquals(expected, actual)
}
