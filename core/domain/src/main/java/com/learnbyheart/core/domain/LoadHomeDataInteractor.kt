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
                    homeDataRepository.getRecommendationTracks(bearerToken.value),
                    homeDataRepository.getPopularTracks(token),
                    homeDataRepository.getFeaturedPlaylist(token),
                    homeDataRepository.getNewReleaseAlbums(token),
                    homeDataRepository.getNewReleasePlaylist(token)
                ) { flow1, flow2, flow3, flow4, flow5 ->

                    val recommendedTrackList = mutableListOf<HomeDisplayData>()
                    val recommendedAlbumList = mutableListOf<HomeDisplayData>()
                    val popularTrackList = mutableListOf<HomeDisplayData>()
                    val featuredPlayList = mutableListOf<HomeDisplayData>()
                    val newReleaseAlbums = mutableListOf<HomeDisplayData>()
                    val newReleasePlaylist = mutableListOf<HomeDisplayData>()

                    flow1.tracks.forEach {
                        recommendedTrackList.add(
                            HomeDisplayData(
                                id = it.id,
                                name = it.name,
                                image = it.album.images[0].url,
                                artists = it.artists.joinToString(" & ") { artist ->
                                    artist.name
                                }
                            )
                        )

                        recommendedAlbumList.add(
                            HomeDisplayData(
                                id = it.album.id,
                                name = it.album.name,
                                image = it.album.images[0].url,
                                artists = it.artists.joinToString(" & ") { artist ->
                                    artist.name
                                }
                            )
                        )
                    }
                    homeDataList.add(
                        HomeDataUiState(
                            musicType = HomeDataType.RECOMMENDATION_TRACK,
                            items = recommendedTrackList
                        )
                    )
                    homeDataList.add(
                        HomeDataUiState(
                            musicType = HomeDataType.RECOMMENDATION_ALBUM,
                            items = recommendedAlbumList
                        )
                    )

                    popularTrackList.addAll(
                        flow2.tracks.map {
                            HomeDisplayData(
                                id = it.id,
                                name = it.name,
                                image = it.album.images[0].url,
                                artists = it.artists.joinToString(" & ") { artist ->
                                    artist.name
                                }
                            )
                        }
                    )
                    homeDataList.add(
                        HomeDataUiState(
                            musicType = HomeDataType.POPULAR_TRACK,
                            items = recommendedTrackList
                        )
                    )

                    newReleasePlaylist.addAll(
                        flow5.playlistMetadata.playLists.map {
                            HomeDisplayData(
                                id = it.id,
                                name = it.name,
                                image = it.images[0].url,
                            )
                        }
                    )
                    homeDataList.add(
                        HomeDataUiState(
                            musicType = HomeDataType.NEW_RELEASE_PLAYLIST,
                            items = featuredPlayList
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

                    newReleaseAlbums.addAll(
                        flow4.albumMetadata.albums.map {
                            HomeDisplayData(
                                id = it.id,
                                name = it.name,
                                image = it.images[0].url,
                                artists = it.artists.joinToString(" & ") { artist ->
                                    artist.name
                                }
                            )
                        }
                    )
                    homeDataList.add(
                        HomeDataUiState(
                            musicType = HomeDataType.NEW_RELEASE_ALBUM,
                            items = newReleaseAlbums
                        )
                    )

                    homeDataList
                }
            }
    }
}
