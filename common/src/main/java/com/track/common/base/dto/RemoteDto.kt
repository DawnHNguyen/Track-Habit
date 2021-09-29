package com.track.common.base.dto

import com.track.common.base.DomainModel

interface RemoteDto {
    fun mapToDomainModel(): DomainModel
    fun mapToLocalDto(): LocalDto
}