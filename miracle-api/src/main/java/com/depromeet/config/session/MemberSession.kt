package com.depromeet.config.session

import java.io.Serializable

class MemberSession(
    val memberId: Long
) : Serializable {

    companion object {
        @JvmStatic
        fun of(memberId: Long): MemberSession {
            return MemberSession(memberId)
        }
    }
}
