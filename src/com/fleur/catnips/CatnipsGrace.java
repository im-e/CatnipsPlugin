package com.fleur.catnips;

import org.bukkit.plugin.java.JavaPlugin;

import java.util.Date;
import java.util.HashMap;
import java.util.UUID;

public class CatnipsGrace {

    private HashMap<UUID, Date> graceTimes = new HashMap<>();

    public Date getGraceTime(UUID uuid) {
        return graceTimes.get(uuid);
    }

    public void setGraceTime(UUID uuid, Date date) {
        graceTimes.put(uuid, date);
    }

    public void setGraceTimeFromCurrent(UUID uuid, Date date) {
        Date grace = new Date(date.getTime()+1000*60*60); //milli->second->minute->hour (1 hour grace)
        graceTimes.put(uuid, grace);
    }

    public Boolean dateInGrace(UUID uuid, Date date) {
        Date grace = getGraceTime(uuid);
        int result =  date.compareTo(grace);
        if (result <= 0) {
            return true;
        }
        else {
            return false;
        }
    }

}
