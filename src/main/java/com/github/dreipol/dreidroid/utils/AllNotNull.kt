package com.github.dreipol.dreidroid.utils

/**
 * Execute [block] if and only if all inputs are not null.
 * Not null parameters are passed to block as input parameters.
 */
inline fun <R, S, X> allNotNull(a: R?, b: S?, block: (R, S) -> X): X? {
    return a?.let { aNotNull ->
        b?.let { bNotNull ->
            block(aNotNull, bNotNull)
        }
    }
}

/**
 * Execute [block] if and only if all inputs are not null.
 * Not null parameters are passed to block as input parameters.
 */
inline fun <R, S, T, X> allNotNull(a: R?, b: S?, c: T?, block: (R, S, T) -> X): X? {
    return a?.let { aNotNull ->
        b?.let { bNotNull ->
            c?.let { cNotNull ->
                block(aNotNull, bNotNull, cNotNull)
            }
        }
    }
}