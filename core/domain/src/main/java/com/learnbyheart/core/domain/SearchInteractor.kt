package com.learnbyheart.core.domain

import com.learnbyheart.core.data.repository.search.SearchRepository
import com.learnbyheart.core.model.MusicDisplayData
import com.learnbyheart.core.network.model.SearchType
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import javax.inject.Inject

private const val SEARCH_DEBOUNCE_TIME = 200L

@OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
class SearchInteractor @Inject constructor(
    private val getAccessTokenInteractor: GetAccessTokenInteractor,
    private val searchRepository: SearchRepository
) {

    operator fun invoke(searchQuery: String, searchType: String): Flow<List<SearchDisplayData>> {
        return getAccessTokenInteractor().flatMapLatest { bearerToken ->
            searchRepository.search(
                token = bearerToken.value,
                query = searchQuery,
                type = searchType
            ).debounce(SEARCH_DEBOUNCE_TIME)
                .map { searchResult ->
                val searchDataList = mutableListOf<SearchDisplayData>()
                searchResult.apply {
                    searchDataList.add(
                        SearchDisplayData(
                            type = SearchType.TRACK,
                            data = tracks.map {
                                val track = it.toTrackDisplayData()
                                MusicDisplayData(
                                    id = track.id,
                                    name = track.name,
                                    image = track.image,
                                    owner = track.artists
                                )
                            }
                        )
                    )
                    searchDataList.add(
                        SearchDisplayData(
                            type = SearchType.ALBUM,
                            data = albums.map {
                                val album = it.toAlbumDisplayData()
                                MusicDisplayData(
                                    id = album.id,
                                    name = album.name,
                                    image = album.image,
                                    owner = album.artists
                                )
                            }
                        )
                    )
                    searchDataList.add(
                        SearchDisplayData(
                            type = SearchType.ARTIST,
                            data = artists.map {
                                val artist = it.toArtistDisplayData()
                                MusicDisplayData(
                                    id = artist.id,
                                    name = artist.name,
                                    image = artist.image,
                                )
                            }
                        )
                    )
                    searchDataList.add(
                        SearchDisplayData(
                            type = SearchType.PLAYLIST,
                            data = playlists.map {
                                val playlist = it.toPlaylistDisplayData()
                                MusicDisplayData(
                                    id = playlist.id,
                                    name = playlist.name,
                                    image = playlist.image,
                                    owner = playlist.owner.name
                                )
                            }
                        )
                    )
                }
                searchDataList
            }
        }
    }
}

data class SearchDisplayData(
    val type: SearchType,
    val data: List<MusicDisplayData>
)
