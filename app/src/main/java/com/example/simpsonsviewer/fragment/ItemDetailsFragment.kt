package com.example.simpsonsviewer.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.simpsonsviewer.BuildConfig
import com.example.simpsonsviewer.R
import com.example.simpsonsviewer.data.CharacterInfo
import com.example.simpsonsviewer.databinding.FragmentItemDetailsBinding
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ItemDetailsFragment : Fragment() {

    private var characterInfo: CharacterInfo? = null
    private lateinit var binding: FragmentItemDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            characterInfo = it.getParcelable(CHARACTER_INFO_PARAM) as? CharacterInfo
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       binding = FragmentItemDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        characterInfo?.let { updateDescription(it) }
        super.onViewCreated(view, savedInstanceState)
    }


    fun updateDescription(info: CharacterInfo){
        binding.tvCharacterName.text = info.characterInfo.substringBefore("-", "Name not found")
        binding.tvCharacterDetails.text = info.characterInfo.substringAfter("-", "Description not found")
        if (info.imageInfo.url.isNullOrEmpty())
            binding.ivCharacterImage.setImageResource(R.drawable.ic_launcher_foreground)
        else
            Picasso.get().load("${BuildConfig.IMAGE_BASE_URL}${info.imageInfo.url}").placeholder(R.drawable.ic_launcher_foreground).into(binding.ivCharacterImage)
    }

    companion object {

        const val DETAILS_FRAGMENT_TAG = "Character Details Fragment tag"
        const val CHARACTER_INFO_PARAM = "characterInfoParam"

        @JvmStatic
        fun newInstance(info: CharacterInfo ) =
            ItemDetailsFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(CHARACTER_INFO_PARAM, info)
                }
            }
    }

}