package com.github.skosvall.nextlvl

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.view.isVisible
import com.google.firebase.firestore.FirebaseFirestore

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_TITLE = "title"
private const val ARG_DESCRIPTION = "description"
private const val ARG_EQUIPMENT_TITLE = "equipmentTitle"
private const val ARG_EQUIPMENT = "equipment"
private const val ARG_SETUP_TITLE = "setupTitle"
private const val ARG_SETUP = "setup"
private const val ARG_HOWTOPLAY_TITLE = "howToPlayTitle"
private const val ARG_HOWTOPLAY = "howToPlay"

/**
 * A simple [Fragment] subclass.
 * Use the [CardGamesFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CardGamesFragment : Fragment() {
    // TODO: Rename and change types of parameters
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

        val titleTextView = view?.findViewById<TextView>(R.id.cardGameTitle)?.setText(title)
        val descriptionTextView = view?.findViewById<TextView>(R.id.cardGameDescription)?.setText(title)
        val titleEquipmentTextView = view?.findViewById<TextView>(R.id.cardGameSectionOneTitle)?.setText(equipmentTitle)
        val equipmentTextView = view?.findViewById<TextView>(R.id.cardGameEquipment)?.setText(equipment)
        val titleSetupTextView = view?.findViewById<TextView>(R.id.cardGameSectionTwoTitle)?.setText(setupTitle)
        val setupTextView = view?.findViewById<TextView>(R.id.cardGameSetup)?.setText(setup)
        val titleHowToPlayTextView = view?.findViewById<TextView>(R.id.cardGameSectionThreeTitle)?.setText(howToPlayTitle)
        val howToPlayTextView = view?.findViewById<TextView>(R.id.cardGameHowToPlay)?.setText(howToPlay)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_card_games, container, false) as View

        view.findViewById<TextView>(R.id.cardGameTitle)?.text = title
        view.findViewById<TextView>(R.id.cardGameDescription)?.text = description
        view.findViewById<TextView>(R.id.cardGameSectionOneTitle)?.text = equipmentTitle
        view.findViewById<TextView>(R.id.cardGameEquipment)?.text = equipment
        view.findViewById<TextView>(R.id.cardGameSectionTwoTitle)?.text = setupTitle
        view.findViewById<TextView>(R.id.cardGameSetup)?.text = setup
        view.findViewById<TextView>(R.id.cardGameSectionThreeTitle)?.text = howToPlayTitle
        view.findViewById<TextView>(R.id.cardGameHowToPlay)?.text = howToPlay

        return view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param title The title of the game.
         * @param equipment Equipment needed to play.
         * @param setup Instructions on how to setup the game.
         * @param howToPlay Instructions on how to play the game.
         * @return A new instance of fragment CardGamesFragment.
         */
        @JvmStatic
        fun newInstance(title: String, description: String, equipmentTitle: String, equipment: String, setupTitle: String, setup: String, howToPlayTitle: String, howToPlay: String) =
            CardGamesFragment().apply {
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