package com.kursor.surprise.fragments

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.kursor.surprise.R
import com.kursor.surprise.TERR_NAME
import com.kursor.surprise.entities.Territory
import com.kursor.surprise.views.MapMenuView

class MainFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main, LinearLayout(activity), false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val mapView = view.findViewById<MapMenuView>(R.id.map_view).apply {
            addObserver(object : MapMenuView.MapObserver {
                override fun onTerritoryClicked(territory: Territory) {
                    when (territory.affiliation) {
                        Territory.Affiliation.ENEMY -> buildMessageGoIntoBattle(territory)
                        Territory.Affiliation.ALLY -> buildMessageOurTerritory(territory)
                    }
                }
            })
        }
    }

    fun buildMessageGoIntoBattle(territory: Territory) {
        AlertDialog.Builder(activity)
            .setCancelable(true)
            .setTitle(territory.name)
            .setMessage(R.string.message_go_into_battle)
            .setPositiveButton(getString(R.string.yes)) { dialog, which ->
                findNavController().navigate(R.id.gameFragment, Bundle().apply {
                    putString(TERR_NAME, territory.name)
                })
            }
            .setNegativeButton(R.string.no) { dialog, which ->
                dialog.dismiss()
            }
            .create().show()
    }

    fun buildMessageOurTerritory(territory: Territory) {
        AlertDialog.Builder(activity)
            .setCancelable(true)
            .setTitle(territory.name)
            .setMessage(getString(R.string.message_our_territory))
            .setPositiveButton(R.string.okay) { dialog, which ->
                dialog.dismiss()
            }
            .create().show()
    }

}