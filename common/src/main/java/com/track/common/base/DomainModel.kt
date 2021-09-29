package com.track.common.base

import com.track.common.base.dto.LocalDto
import com.track.common.base.dto.RemoteDto

interface DomainModel {
    fun toLocalDto(): LocalDto
    fun toRemoteDto(): RemoteDto
}