package com.track.trackhabit.habit.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import com.track.common.base.data.remote.util.NetworkBoundResource
import com.track.common.base.data.remote.util.Resource
import com.track.trackhabit.habit.data.local.dao.InspectionDao
import com.track.trackhabit.habit.data.remote.services.InspectionDataSource
import com.track.trackhabit.habit.domain.entity.Inspection
import com.track.trackhabit.habit.domain.entity.remote.InspectionDto
import com.track.trackhabit.habit.domain.repository.InspectionRepository
import retrofit2.Response
import javax.inject.Inject

class InspectionRepositoryImpl @Inject constructor(
    private val inspectionDao: InspectionDao,
    private val inspectionDataSource: InspectionDataSource): InspectionRepository {
    override suspend fun addInspection(inspection: Inspection) {
        inspectionDao.insertInspection(inspection.toLocalDto())
    }

    override suspend fun addInspectionToHabit(inspection: Inspection, habitId: Int) {
        inspectionDao.insertInspection(inspection.toLocalDto().apply {
            id = habitId
        })
    }
//    override suspend fun getInspection(): LiveData<List<Inspection>> =
//        Transformations.map(inspectionDao.getInspection()) { list ->
//            list.map { it.mapToDomainModel() }
//        }

    override suspend fun getInspection(): LiveData<Resource<List<Inspection>>> {
        return object : NetworkBoundResource<List<Inspection>, List<InspectionDto>>() {
            override fun processResponse(response: Response<List<InspectionDto>>): List<Inspection> {
                Log.d("checkListcheckRes","--$response")
                return response.body()?.map {
                    it.mapToDomainModel()
                }?: emptyList()
            }

            override suspend fun saveCallResults(items: List<Inspection>) {
                Log.d("checkListcheckRes","--checked save")

                inspectionDao.insertListInspection(items.map {
                    it.toLocalDto()
                })
            }

            override suspend fun shouldFetch(data: List<Inspection>?): Boolean {
                return true
            }

            override suspend fun loadFromDb(): List<Inspection>? {
                Log.d("checkListcheckRes","--checked")

                return inspectionDao.getListInspection().map {
                    it.mapToDomainModel()
                }
            }

            override suspend fun createCallAsync(): Response<List<InspectionDto>> {
                Log.d("checkListcheckCallAsync","--checked")
                return inspectionDataSource.getInspection()
            }

        }.build().asLiveData()
    }

    override suspend fun updateInspectionToHabit(inspection: Inspection, habitId: Int) {
        inspectionDao.updateInspection(inspection.toLocalDto().apply {
            id = habitId
        })
    }
}