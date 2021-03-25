package com.github.skosvall.nextlvl

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

private const val ARG_TITLE = "title"
private const val ARG_DESCRIPTION = "description"
private const val ARG_EQUIPMENT_TITLE = "equipmentTitle"
private const val ARG_EQUIPMENT = "equipment"
private const val ARG_SETUP_TITLE = "setupTitle"
private const val ARG_SETUP = "setup"
private const val ARG_HOWTOPLAY_TITLE = "howToPlayTitle"
private const val ARG_HOWTOPLAY = "howToPlay"
private const val ARG_SECTION_FOUR_TITLE = "sectionFourTitle"
private const val ARG_SECTION_FOUR_TEXT = "sectionFourText"

class RingOfFireGameFragment : Fragment() {
    private var title: String? = null
    private var description: String? = null
    private var equipmentTitle: String? = null
    private var equipment: String? = null
    private var setupTitle: String? = null
    private var setup: String? = null
    private var howToPlayTitle: String? = null
    private var howToPlay: String? = null
    private var sectionFourTitle: String? = null
    private var sectionFourText: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            title = it.getString(ARG_TITLE)
            description = it.getString(ARG_DESCRIPTION)
            equipmentTitle = it.getString(ARG_EQUIPMENT_TITLE)
            equipment = it.getString(ARG_EQUIPMENT)
            setupTitle = it.getString(ARG_SETUP_TITLE)
            setup = it.getString(ARG_SETUP)
            howToPlayTitle = it.getString(ARG_HOWTOPLAY_TITLE)
            howToPlay = it.getString(ARG_HOWTOPLAY)
            sectionFourTitle = it.getString(ARG_SECTION_FOUR_TITLE)
            sectionFourText = it.getString(ARG_SECTION_FOUR_TEXT)

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_card_games, container, false) as View

        view.findViewById<TextView>(R.id.card_game_title)?.text = title
        view.findViewById<TextView>(R.id.card_game_description)?.text = description
        view.findViewById<TextView>(R.id.card_game_section_one_title)?.text = equipmentTitle
        view.findViewById<TextView>(R.id.card_game_equipment)?.text = equipment
        view.findViewById<TextView>(R.id.card_game_section_two_title)?.text = setupTitle
        view.findViewById<TextView>(R.id.card_game_setup)?.text = setup
        view.findViewById<TextView>(R.id.card_game_section_three_title)?.text = howToPlayTitle
        view.findViewById<TextView>(R.id.card_game_how_to_play)?.text = howToPlay
        view.findViewById<TextView>(R.id.card_game_section_four_title)?.text = sectionFourTitle
        view.findViewById<TextView>(R.id.card_game_section_four_text)?.text = sectionFourText

        return view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param title The title of the game.
         * @param description Short introduction about the game.
         * @param equipmentTitle Title for equipment.
         * @param equipment Equipment needed to play.
         * @param setupTitle Title for setup.
         * @param setup Instructions on how to setup the game.
         * @param howToPlayTitle Title for how to play.
         * @param howToPlay Instructions on how to play the game.
         * @param sectionFourTitle Title for section four.
         * @param sectionFourText Instructions for rules available in section four.
         * @return A new instance of fragment CardGamesFragment.
         */
        @JvmStatic
        fun newInstance(
            title: String,
            description: String,
            equipmentTitle: String,
            equipment: String,
            setupTitle: String,
            setup: String,
            howToPlayTitle: String,
            howToPlay: String,
            sectionFourTitle: String,
            sectionFourText: String
        ) =
            RingOfFireGameFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_TITLE, title)
                    putString(ARG_DESCRIPTION, description)
                    putString(ARG_EQUIPMENT_TITLE, equipmentTitle)
                    putString(ARG_EQUIPMENT, equipment)
                    putString(ARG_SETUP_TITLE, setupTitle)
                    putString(ARG_SETUP, setup)
                    putString(ARG_HOWTOPLAY_TITLE, howToPlayTitle)
                    putString(ARG_HOWTOPLAY, howToPlay)
                    putString(ARG_SECTION_FOUR_TITLE, sectionFourTitle)
                    putString(ARG_SECTION_FOUR_TEXT, sectionFourText)
                }
            }
    }
}