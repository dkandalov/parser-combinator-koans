package parserkoans.util

import kotlin.test.assertEquals

infix fun Any?.shouldEqual(expected: Any?) =
    assertEquals(expected = expected, actual = this)
