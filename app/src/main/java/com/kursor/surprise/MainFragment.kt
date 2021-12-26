package com.kursor.surprise

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment

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
            .setMessage("Do you want to go into battle")
            .setPositiveButton("Yes") { dialog, which ->
                TODO("Not yet implemented")
            }
            .setNegativeButton("No") { dialog, which ->
                TODO("Not yet implemented")
            }
            .create().show()
    }

    fun buildMessageOurTerritory(territory: Territory) {
        AlertDialog.Builder(activity)
            .setCancelable(true)
            .setTitle(territory.name)
            .setMessage("This is our territory")
            .setPositiveButton("Okay") { dialog, which ->
                TODO("Not yet implemented")
            }
            .create().show()
    }

}