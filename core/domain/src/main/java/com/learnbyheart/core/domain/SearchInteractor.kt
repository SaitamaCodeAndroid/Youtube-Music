package com.learnbyheart.core.domain

import com.learnbyheart.core.data.repository.search.SearchRepository
import com.learnbyheart.core.model.MusicDisplayData
import com.learnbyheart.core.network.model.SearchType
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
class SearchInteractor @Inject constructor(
    private val getAccessTokenInteractor: GetAccessTokenInteractor,
    private val searchRepository: SearchRepository
) {

    operator fun invoke(searchQuery: String, searchType: String): Flow<List<SearchDataState>> {
        return getAccessTokenInteractor().flatMapLatest { bearerToken ->
            searchRepository.searchAllType(
                token = bearerToken.value,
                query = searchQuery,
                type = searchType
            ).map { searchResult ->
                val searchDataList = mutableListOf<SearchDataState>()
                searchResult.apply {
                    if (tracks.isNotEmpty()) {
                        searchDataList.add(
                            SearchDataState(
                                type = SearchType.TRACK,
                                data = tracks.map {
                                    val track = it.toTrackDisplayData()
                                    MusicDisplayData(
                                        id = track.id,
                                        name = track.name,
                                        image = track.image,
                                        owner = track.artists,
                                        duration = track.duration
                                    )
                                }
                            )
                        )
                    }
                    if (albums.isNotEmpty()) {
                        searchDataList.add(
                            SearchDataState(
                                type = SearchType.ALBUM,
                                data = albums.map {
                                    val album = it.toAlbumDisplayData()
                                    MusicDisplayData(
                                        id = album.id,
                                        name = album.name,
                                        image = album.image,
                                        owner = album.artists,
                                        albumType = album.type
                                    )
                                }
                            )
                        )
                    }
                    if (artists.isNotEmpty()) {
                        searchDataList.add(
                            SearchDataState(
                                type = SearchType.ARTIST,
                                data = artists.map {
                                    val artist = it.toArtistDisplayData()
                                    MusicDisplayData(
                                        id = artist.id,
                                        name = artist.name,
                                        image = artist.image,
                                        totalSubscriber = artist.totalSubscriber
                                    )
                                }
                            )
                        )
                    }
                    if (playlists.isNotEmpty()) {
                        searchDataList.add(
                            SearchDataState(
                                type = SearchType.PLAYLIST,
                                data = playlists.map {
                                    val playlist = it.toPlaylistDisplayData()
                                    MusicDisplayData(
                                        id = playlist.id,
                                        name = playlist.name,
                                        image = playlist.image,
                                        owner = playlist.owner.name,
                                        totalTrack = playlist.totalTrack
                                    )
                                }
                            )
                        )
                    }
                }
                searchDataList
            }
        }
    }
}

data class SearchDataState(
    val type: SearchType,
    val data: List<MusicDisplayData>
)
