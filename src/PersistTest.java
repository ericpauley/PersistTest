import java.sql.Time;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.Persist;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

public class PersistTest extends JavaPlugin implements Listener {

    @Persist
    public Map<String, Vector> lastTimes = new HashMap<String, Vector>();
    @Persist
    public Time lastStart;

    public void onEnable() {
        this.getServer().getPluginManager().registerEvents(this, this);
        if(lastStart != null){
            getLogger().info("This plugin was last started at "+lastStart);
        }else{
            getLogger().info("This plugin has never been started before!");
        }
        lastStart = new Time(System.currentTimeMillis());
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        if (lastTimes.containsKey(p.getName())) {
            Vector v = lastTimes.get(p.getName());
            p.sendMessage("You last logged in " + v.distance(p.getLocation().toVector()) + " blocks from here.");
        }
        lastTimes.put(p.getName(), p.getLocation().toVector());
    }

}
