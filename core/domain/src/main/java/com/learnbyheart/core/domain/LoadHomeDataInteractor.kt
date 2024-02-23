package com.learnbyheart.core.domain

import com.learnbyheart.core.data.HomeDataType
import com.learnbyheart.core.data.model.HomeDataUiState
import com.learnbyheart.core.data.model.HomeDisplayData
import com.learnbyheart.core.data.repository.home.HomeDataRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
class LoadHomeDataInteractor @Inject constructor(
    private val getAccessTokenInteractor: GetAccessTokenInteractor,
    private val homeDataRepository: HomeDataRepository,
) {

    operator fun invoke(): Flow<MutableList<HomeDataUiState>> {

        val homeDataList = mutableListOf<HomeDataUiState>()

        return getAccessTokenInteractor()
            .flatMapLatest { bearerToken ->
                val token = bearerToken.value

                combine(
                    homeDataRepository.getRecommendationTracks(token),
                    homeDataRepository.getPopularTracks(token),
                    homeDataRepository.getFeaturedPlaylist(token),
                    homeDataRepository.getNewReleaseAlbums(token),
                ) { flow1, flow2, flow3, flow4 ->

                    val recommendedTrackList = mutableListOf<HomeDisplayData>()
                    val recommendedAlbumList = mutableListOf<HomeDisplayData>()
                    val popularTrackList = mutableListOf<HomeDisplayData>()
                    val featuredPlayList = mutableListOf<HomeDisplayData>()
                    val newReleaseList = mutableListOf<HomeDisplayData>()

                    flow1.tracks.forEach {
                        recommendedTrackList.add(
                            HomeDisplayData(
                                id = it.id,
                                name = it.name,
                                image = it.album.images[0].url,
                            )
                        )
                        homeDataList.add(
                            HomeDataUiState(
                                musicType = HomeDataType.RECOMMENDATION_TRACK,
                                items = recommendedTrackList
                            )
                        )

                        recommendedAlbumList.add(
                            HomeDisplayData(
                                id = it.album.id,
                                name = it.album.name,
                                image = it.album.images[0].url,
                            )
                        )
                        homeDataList.add(
                            HomeDataUiState(
                                musicType = HomeDataType.RECOMMENDATION_ALBUM,
                                items = recommendedAlbumList
                            )
                        )
                    }

                    popularTrackList.addAll(
                        flow2.tracks.map {
                            HomeDisplayData(
                                id = it.id,
                                name = it.name,
                                image = it.album.images[0].url,
                            )
                        }
                    )
                    homeDataList.add(
                        HomeDataUiState(
                            musicType = HomeDataType.POPULAR_TRACK,
                            items = recommendedTrackList
                        )
                    )

                    featuredPlayList.addAll(
                        flow3.playlistMetadata.playLists.map {
                            HomeDisplayData(
                                id = it.id,
                                name = it.name,
                                image = it.images[0].url,
                            )
                        }
                    )
                    homeDataList.add(
                        HomeDataUiState(
                            musicType = HomeDataType.FEATURED_PLAYLIST,
                            items = featuredPlayList
                        )
                    )

                    newReleaseList.addAll(
                        flow4.albumMetadata.albums.map {
                            HomeDisplayData(
                                id = it.id,
                                name = it.name,
                                image = it.images[0].url,
                            )
                        }
                    )
                    homeDataList.add(
                        HomeDataUiState(
                            musicType = HomeDataType.NEW_RELEASE_ALBUM,
                            items = newReleaseList
                        )
                    )

                    homeDataList
                }
            }
    }
}
