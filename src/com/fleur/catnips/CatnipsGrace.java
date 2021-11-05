package com.fleur.catnips;

import org.bukkit.entity.Player;

import java.util.Date;
import java.util.HashMap;

public class CatnipsGrace {

    private final HashMap<Player, Date> playerSpawnTime = new HashMap<>();

    public Date getGraceTime(Player player) {
        return playerSpawnTime.get(player);

    }

    public void setGraceTime(Player player, Date date) {
        playerSpawnTime.put(player, date);
    }

    public void setGraceTimeFromCurrent(Player player, Date date) {
        Date grace = new Date(date.getTime()+1000*60*60); //milli->second->minute->hour (1 hour grace)
        playerSpawnTime.put(player, grace);
    }

    public Boolean dateInGrace(Player player, Date date) {
        Date grace = getGraceTime(player);
        int result =  date.compareTo(grace);
        if (result <= 0) {
            return true;
        }
        else {
            return false;
        }
    }
}
