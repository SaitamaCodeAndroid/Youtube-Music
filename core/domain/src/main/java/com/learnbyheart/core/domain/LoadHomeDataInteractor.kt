package com.learnbyheart.core.domain

import com.learnbyheart.core.data.HomeDataType
import com.learnbyheart.core.data.model.HomeDataUiState
import com.learnbyheart.core.data.repository.home.HomeDataRepository
import com.learnbyheart.core.model.MusicDisplayData
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
                    homeDataRepository.getNewReleasePlaylist(token)
                ) { flow1, flow2, flow3, flow4, flow5 ->

                    val recommendedTrackList = mutableListOf<MusicDisplayData>()
                    val recommendedAlbumList = mutableListOf<MusicDisplayData>()
                    val popularTrackList = mutableListOf<MusicDisplayData>()
                    val featuredPlayList = mutableListOf<MusicDisplayData>()
                    val newReleaseAlbums = mutableListOf<MusicDisplayData>()
                    val newReleasePlaylist = mutableListOf<MusicDisplayData>()

                    flow1.tracks.forEach {
                        val track = it.toTrackDisplayData()
                        recommendedTrackList.add(
                            MusicDisplayData(
                                id = track.id,
                                name = track.name,
                                image = track.image,
                                owner = track.artists
                            )
                        )

                        val album = it.album.toAlbumDisplayData()
                        recommendedAlbumList.add(
                            MusicDisplayData(
                                id = album.id,
                                name = album.name,
                                image = album.image,
                                owner = album.artists
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
                            val track = it.toTrackDisplayData()
                            MusicDisplayData(
                                id = track.id,
                                name = track.name,
                                image = track.image,
                                owner = track.artists
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
                            val playlist = it.toPlaylistDisplayData()
                            MusicDisplayData(
                                id = playlist.id,
                                name = playlist.name,
                                image = playlist.image,
                                owner = playlist.owner.name
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
                            val playlist = it.toPlaylistDisplayData()
                            MusicDisplayData(
                                id = playlist.id,
                                name = playlist.name,
                                image = playlist.image,
                                owner = playlist.owner.name
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
                            val album = it.toAlbumDisplayData()
                            MusicDisplayData(
                                id = album.id,
                                name = album.name,
                                image = album.image,
                                owner = album.artists
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
