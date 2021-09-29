package com.track.common.base.dto

import com.track.common.base.DomainModel

interface LocalDto {
    fun mapToDomainModel(): DomainModel
    fun mapToRemoteDto(): RemoteDto
}