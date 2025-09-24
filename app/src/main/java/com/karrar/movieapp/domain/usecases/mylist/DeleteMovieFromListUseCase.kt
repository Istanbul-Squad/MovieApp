package com.karrar.movieapp.domain.usecases.mylist

import com.karrar.movieapp.data.repository.AccountRepository
import com.karrar.movieapp.data.repository.MovieRepository
import com.karrar.movieapp.utilities.ErrorUI
import javax.inject.Inject

class DeleteMovieFromListUseCase @Inject constructor(
    private val accountRepository: AccountRepository,
    private val movieRepository: MovieRepository
) {
    suspend operator fun invoke(listID: Int, mediaId: Int): String {
        val sessionID = accountRepository.getSessionId()
        return sessionID?.let {
            movieRepository.deleteMovieFromCollection(
                sessionId = it,
                listId = listID,
                movieId = mediaId
            )
            "Success: The movie has been deleted"
        } ?: throw Throwable(ErrorUI.NO_LOGIN)
    }
}