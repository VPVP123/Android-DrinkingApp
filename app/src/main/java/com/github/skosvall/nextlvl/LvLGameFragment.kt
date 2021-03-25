package com.github.skosvall.nextlvl

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

class LvLGameFragment : Fragment() {
    private var title: String? = null
    private var description: String? = null
    private var equipmentTitle: String? = null
    private var equipment: String? = null
    private var setupTitle: String? = null
    private var setup: String? = null
    private var howToPlayTitle: String? = null
    private var howToPlay: String? = null

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
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_lvl_game, container, false) as View

        view.findViewById<TextView>(R.id.lvl_game_title).text = title
        view.findViewById<TextView>(R.id.lvl_game_description).text = description
        view.findViewById<TextView>(R.id.lvl_game_section_one_title).text = equipmentTitle
        view.findViewById<TextView>(R.id.lvl_game_equipment).text = equipment
        view.findViewById<TextView>(R.id.lvl_game_section_two_title).text = setupTitle
        view.findViewById<TextView>(R.id.lvl_game_setup).text = setup
        view.findViewById<TextView>(R.id.lvl_game_section_three_title).text = howToPlayTitle
        view.findViewById<TextView>(R.id.lvl_game_how_to_play).text = howToPlay

        return view
    }

    companion object {
        private const val ARG_TITLE = "title"
        private const val ARG_DESCRIPTION = "description"
        private const val ARG_EQUIPMENT_TITLE = "equipmentTitle"
        private const val ARG_EQUIPMENT = "equipment"
        private const val ARG_SETUP_TITLE = "setupTitle"
        private const val ARG_SETUP = "setup"
        private const val ARG_HOWTOPLAY_TITLE = "howToPlayTitle"
        private const val ARG_HOWTOPLAY = "howToPlay"

        /**
         *
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param title The title of the game.
         * @param equipment Equipment needed to play.
         * @param setup Instructions on how to setup the game.
         * @param howToPlay Instructions on how to play the game.
         * @return A new instance of fragment LvlGamesFragment.
         */
        @JvmStatic
        fun newInstance(title: String, description: String, equipmentTitle: String, equipment: String, setupTitle: String, setup: String, howToPlayTitle: String, howToPlay: String) =
            LvLGameFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_TITLE, title)
                    putString(ARG_DESCRIPTION, description)
                    putString(ARG_EQUIPMENT_TITLE, equipmentTitle)
                    putString(ARG_EQUIPMENT, equipment)
                    putString(ARG_SETUP_TITLE, setupTitle)
                    putString(ARG_SETUP, setup)
                    putString(ARG_HOWTOPLAY_TITLE, howToPlayTitle)
                    putString(ARG_HOWTOPLAY, howToPlay)
                }
            }
    }
}