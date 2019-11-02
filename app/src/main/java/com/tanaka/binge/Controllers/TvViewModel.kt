package com.tanaka.binge.Controllers

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.tanaka.binge.Models.TvShowResult
import com.tanaka.binge.TvShowRepository
import javax.inject.Inject

//class UserProfileViewModel @Inject constructor(
//   savedStateHandle: SavedStateHandle,
//   userRepository: UserRepository
//) : ViewModel() {
//   val userId : String = savedStateHandle["uid"] ?:
//          throw IllegalArgumentException("missing user id")
//   val user : LiveData<User> = userRepository.getUser(userId)
//}
class TvViewModel @Inject constructor(tvShowRepository: TvShowRepository) : ViewModel() {
    var page: Int = 1
    val tvShows: LiveData<List<TvShowResult>> = tvShowRepository.getAllTvShows(page)
}