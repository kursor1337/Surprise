package com.kursor.surprise.fragments

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.kursor.surprise.BATTLE
import com.kursor.surprise.objects.Factions
import com.kursor.surprise.objects.Factions.STAR_EMPIRE
import com.kursor.surprise.R
import com.kursor.surprise.entities.Battle
import com.kursor.surprise.entities.Faction
import com.kursor.surprise.entities.Province
import com.kursor.surprise.objects.War
import com.kursor.surprise.views.MapMenuView

class MainFragment : Fragment() {

    lateinit var mapMenuView: MapMenuView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main, LinearLayout(activity), false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        if (War.currentTurn != STAR_EMPIRE) {
            buildMessageOurTerritoryUnderAttack(War.aiNextBattle())
        }
        mapMenuView = view.findViewById<MapMenuView>(R.id.map_view).apply {
            addObserver(object : MapMenuView.MapObserver {
                override fun onProvinceClicked(faction: Faction, province: Province) {
                    when (faction.relationship) {
                        Faction.Relationship.ALLY -> buildMessageOurTerritory(province)
                        else -> buildMessageGoIntoBattle(province)
                    }
                }
            })
        }
    }

    fun buildMessageGoIntoBattle(province: Province) {
        AlertDialog.Builder(activity)
            .setCancelable(true)
            .setTitle(province.localizedName(requireContext()))
            .setMessage(R.string.message_go_into_battle)
            .setPositiveButton(getString(R.string.yes)) { dialog, which ->
                val faction = province.findOwner()
                faction.relationship = Faction.Relationship.WAR
                findNavController().navigate(R.id.gameFragment, Bundle().apply {
                    putString(
                        BATTLE,
                        Battle(
                            Factions.FACTIONS[STAR_EMPIRE]!!,
                            faction,
                            province
                        ).serialize()
                    )
                })
            }
            .setNegativeButton(R.string.no) { dialog, which ->
                dialog.dismiss()
            }
            .create().show()
    }

    fun buildMessageOurTerritory(province: Province) {
        AlertDialog.Builder(activity)
            .setCancelable(true)
            .setTitle(province.localizedName(requireContext()))
            .setMessage(getString(R.string.message_our_territory))
            .setPositiveButton(R.string.okay) { dialog, which ->
                dialog.dismiss()
            }
            .create().show()
    }

    fun buildMessageOurTerritoryUnderAttack(battle: Battle) {
        AlertDialog.Builder(activity)
            .setCancelable(false)
            .setTitle("Our province under attack!")
            .setMessage(
                "${battle.attacker.localizedName(requireContext())} attacked our province " +
                        battle.province.localizedName(requireContext())
            ).setPositiveButton("CHAAARGE!") { dialog, which ->
                battle.attacker.relationship = Faction.Relationship.WAR
                findNavController().navigate(R.id.gameFragment, Bundle().apply {
                    putString(
                        BATTLE,
                        battle.serialize()
                    )
                })
            }.setNegativeButton("Do nothing") { dialog, which ->
                battle.lost()
                dialog.dismiss()
                mapMenuView.invalidate()
            }.create().show()
    }

}