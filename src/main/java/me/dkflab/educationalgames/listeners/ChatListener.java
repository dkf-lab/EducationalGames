package me.dkflab.educationalgames.listeners;

import me.dkflab.educationalgames.EducationalGames;
import me.dkflab.educationalgames.Utils;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChatEvent;

public class ChatListener implements Listener {

    private EducationalGames main;
    public ChatListener(EducationalGames main) {
        this.main = main;
    }

    @EventHandler
    public void chat(PlayerChatEvent e) {
        if (main.isPlayerInMaths(e.getPlayer())) {
            // Check if message is an integer
            e.setCancelled(true);
            Bukkit.getLogger().info(e.getMessage());
            if (Utils.parseInt(e.getPlayer(), e.getMessage())) {
                main.getCommand().checkMathsAnswer(e.getPlayer(), Integer.parseInt(e.getMessage()));
            }
        }

        if (main.isPlayerInEnglish(e.getPlayer())) {
            e.setCancelled(true);
            // Send message
            main.getCommand().checkEnglishAnswer(e.getPlayer(), e.getMessage());
        }
    }
}
