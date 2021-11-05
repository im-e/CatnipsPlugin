package com.fleur.catnips;


import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.BanList;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.plugin.java.JavaPlugin;


import java.util.Date;


public class CatnipsEvents extends JavaPlugin implements Listener {

    static CatnipsGrace catnipsGrace = new CatnipsGrace();  //Init our Grace Class

    @EventHandler
    public static void onPlayerJoin(PlayerJoinEvent event) { //When a player joins the server

        Player player = event.getPlayer();                   //Get player
        Date joinDate = new Date(System.currentTimeMillis()); //Get current date/time

        player.sendMessage(ChatColor.LIGHT_PURPLE + "Welcome to Catnips Hardcore Server! :)"); //welcome player

        if(!player.hasPlayedBefore()) { //if user has not played before
            catnipsGrace.setGraceTimeFromCurrent(player, joinDate); //set their grace time
            player.sendMessage(ChatColor.RED    + "In this server, you are banned from playing for 24 hours when you die!");
            player.sendMessage(ChatColor.GREEN  + "You have a one hour grace period where you cannot be banned upon death.");
            player.sendMessage(ChatColor.GREEN  + "Use the grace period to set yourself up safely, have fun!.");
            player.sendMessage(ChatColor.RED    + "Your grace ends at: " + catnipsGrace.getGraceTime(player));
        }
        else { //player has played before
            player.sendMessage(ChatColor.RED + "You have played before.");
            player.sendMessage(ChatColor.RED + "Your grace ends at: " + catnipsGrace.getGraceTime(player));

            /*
            if (catnipsGrace.dateInGrace(player, joinDate)) { //if played joined within their grace period
                player.sendMessage(ChatColor.RED + "Your grace ends at: " + catnipsGrace.getGraceTime(player));
            }
            else {  //if player joined when their grace period has ended
                player.sendMessage(ChatColor.RED + "Your grace ended at: " + catnipsGrace.getGraceTime(player));
            }*/
        }
    }

    @EventHandler
    public static void onPlayerRespawn(PlayerRespawnEvent event) { //when a player respawns

        Player player = event.getPlayer(); //get player
        Date respawnDate = new Date(System.currentTimeMillis()); //get current date/time

        if(catnipsGrace.dateInGrace(player,respawnDate)) { //If player respawned within their grace period
            player.sendMessage(ChatColor.RED + "Your grace ends at: " + catnipsGrace.getGraceTime(player));
        }
        else { //player died outside their grace period
            catnipsGrace.setGraceTime(player,respawnDate); //set them their new grace period
            player.sendMessage(ChatColor.RED + "Your new grace ends at: " + catnipsGrace.getGraceTime(player));
        }

    }

    @EventHandler
    public static void onPlayerDeath(PlayerDeathEvent event) { //when a player dies

        Player player = event.getEntity(); //get the player
        Date deathDate = new Date(System.currentTimeMillis()); //get the date of death

        if(!catnipsGrace.dateInGrace(player, deathDate)) { //if the player did not die in their grace period
            Date banDate = new Date(deathDate.getTime()+1000*60*60*24); //set the time for the ban - milli->second->minute->hour->24h ban
            player.kickPlayer(ChatColor.RED + "You died and are now banned!"); //kick the player from the server
            Bukkit.getBanList(BanList.Type.IP).addBan(player.getAddress().getHostName(), "You Died!", banDate, null); //update the ban list
        }
        else { //if the player died within the grace period
            player.sendMessage(ChatColor.GREEN + "You died within the grace period: " + catnipsGrace.getGraceTime(player));
        }
    }



}
