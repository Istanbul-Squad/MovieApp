package com.karrar.movieapp.ui.actorDetails

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.annotation.RequiresApi
import androidx.core.net.toUri
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.karrar.movieapp.R
import com.karrar.movieapp.databinding.FragmentActorDetailsBinding
import com.karrar.movieapp.domain.enums.AllMediaType
import com.karrar.movieapp.ui.base.BaseFragment
import com.karrar.movieapp.utilities.collectLast
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ActorDetailsFragment : BaseFragment<FragmentActorDetailsBinding>() {

    override val layoutIdFragment = R.layout.fragment_actor_details
    override val viewModel: ActorViewModel by viewModels()

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setTitle(false)
        binding.relatedMovieRecycler.adapter = ActorMoviesAdapter(mutableListOf(), viewModel)

        collectLast(viewModel.actorDetailsUIEvent) {
            it.getContentIfNotHandled()?.let { onEvent(it) }
        }
    }

    private fun onEvent(event: ActorDetailsUIEvent) {
        when (event) {
            ActorDetailsUIEvent.BackEvent -> {
                removeFragment()
            }
            is ActorDetailsUIEvent.ClickMovieEvent -> {
                seeMovieDetails(event.movieID)
            }
            ActorDetailsUIEvent.SeeAllMovies -> {
                navigateToActorMovies()
            }
            is ActorDetailsUIEvent.OpenSocialMediaLinkEvent -> {
                openLinkSocialMediaLink(event.link)
            }

            ActorDetailsUIEvent.SeeMoreGallery -> {
                navigateToGallery()
            }

        }
    }
    private fun navigateToGallery() {
        if (findNavController().currentDestination?.id == R.id.actorDetailsFragment) {
            Navigation.findNavController(binding.root)
                .navigate(
                    ActorDetailsFragmentDirections.actionActorDetailsFragmentToGalleryActorFragment(
                        viewModel.args.id,
                        viewModel.actorDetailsUIState.value.name
                    )
                )
        }
    }
    private fun openLinkSocialMediaLink(link: String) {
        val intent = when {
            link.contains("youtube", true) -> Intent(Intent.ACTION_VIEW, "vnd.youtube:${link.substringAfterLast("/")}".toUri())
            link.contains("facebook", true) -> Intent(Intent.ACTION_VIEW, "fb://facewebmodal/f?href=$link".toUri())
            link.contains("instagram", true) -> Intent(Intent.ACTION_VIEW, "http://instagram.com/_u/${link.substringAfterLast("/")}".toUri())
            link.contains("twitter", true) -> Intent(Intent.ACTION_VIEW, "twitter://user?screen_name=${link.substringAfterLast("/")}".toUri())
            link.contains("tiktok", true) -> Intent(Intent.ACTION_VIEW, "snssdk1233://user/profile/${link.substringAfterLast("/")}".toUri())
            else -> Intent(Intent.ACTION_VIEW, link.toUri())
        }
        startActivity(intent)
    }
    private fun navigateToActorMovies() {
        Navigation.findNavController(binding.root)
            .navigate(
                ActorDetailsFragmentDirections.actionActorDetailsFragmentToAllMovieOfActorFragment(
                    viewModel.args.id,
                    AllMediaType.ACTOR_MOVIES
                )
            )
    }

    private fun seeMovieDetails(movieID: Int) {
        findNavController().navigate(
            ActorDetailsFragmentDirections.actionActorDetailsFragmentToMovieDetailFragment(
                movieID
            )
        )
    }

    private fun removeFragment() {
        findNavController().popBackStack()
    }

}