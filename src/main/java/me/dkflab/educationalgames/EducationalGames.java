package me.dkflab.educationalgames;

import me.dkflab.educationalgames.commands.MainCommand;
import me.dkflab.educationalgames.listeners.ChatListener;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public final class EducationalGames extends JavaPlugin {

    private MainCommand command;
    private List<Player> maths = new ArrayList<>();
    private List<Player> english = new ArrayList<>();
    @Override
    public void onEnable() {
        command = new MainCommand(this);
        getCommand("education").setExecutor(command);
        getServer().getPluginManager().registerEvents(new ChatListener(this),this);
    }

    public MainCommand getCommand() {
        return this.command;
    }

    public List<Player> getMathsPlayers() {
        return maths;
    }

    public void addPlayerToEnglish(Player p) {
        english.add(p);
    }

    public void addPlayerToMaths(Player p) {
        maths.add(p);
    }

    public void removePlayerFromMaths(Player p) {
        maths.remove(p);
    }

    public void removePlayerFromEnglish(Player p) {
        english.remove(p);
    }

    public boolean isPlayerInMaths(Player p) {
        return maths.contains(p);
    }

    public boolean isPlayerInEnglish(Player p) {
        return english.contains(p);
    }
}
