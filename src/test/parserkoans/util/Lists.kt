@file:Suppress("PackageDirectoryMismatch")

package parserkoans

data class List1<T1>(val value1: T1) {
    operator fun <T1> plus(value1: T1): List1<T1> =
        List1(value1)
}
data class List2<T1, T2>(val value1: T1, val value2: T2) {
    operator fun <T2> plus(value2: T2): List2<T1, T2> =
        List2(value1, value2)
}
data class List3<T1, T2, T3>(val value1: T1, val value2: T2, val value3: T3) {
    operator fun <T3> plus(value3: T3): List3<T1, T2, T3> =
        List3(value1, value2, value3)
}
data class List4<T1, T2, T3, T4>(val value1: T1, val value2: T2, val value3: T3, val value4: T4) {
    operator fun <T4> plus(value4: T4): List4<T1, T2, T3, T4> =
        List4(value1, value2, value3, value4)
}
data class List5<T1, T2, T3, T4, T5>(val value1: T1, val value2: T2, val value3: T3, val value4: T4, val value5: T5) {
    operator fun <T5> plus(value5: T5): List5<T1, T2, T3, T4, T5> =
        List5(value1, value2, value3, value4, value5)
}
data class List6<T1, T2, T3, T4, T5, T6>(val value1: T1, val value2: T2, val value3: T3, val value4: T4, val value5: T5, val value6: T6) {
    operator fun <T6> plus(value6: T6): List6<T1, T2, T3, T4, T5, T6> =
        List6(value1, value2, value3, value4, value5, value6)
}
data class List7<T1, T2, T3, T4, T5, T6, T7>(val value1: T1, val value2: T2, val value3: T3, val value4: T4, val value5: T5, val value6: T6, val value7: T7) {
    operator fun <T7> plus(value7: T7): List7<T1, T2, T3, T4, T5, T6, T7> =
        List7(value1, value2, value3, value4, value5, value6, value7)
}
data class List8<T1, T2, T3, T4, T5, T6, T7, T8>(val value1: T1, val value2: T2, val value3: T3, val value4: T4, val value5: T5, val value6: T6, val value7: T7, val value8: T8) {
    operator fun <T8> plus(value8: T8): List8<T1, T2, T3, T4, T5, T6, T7, T8> =
        List8(value1, value2, value3, value4, value5, value6, value7, value8)
}
