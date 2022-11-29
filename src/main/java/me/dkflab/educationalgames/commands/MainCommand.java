package me.dkflab.educationalgames.commands;

import me.dkflab.educationalgames.EducationalGames;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static me.dkflab.educationalgames.Utils.*;

public class MainCommand implements CommandExecutor {

    private EducationalGames main;
    public MainCommand(EducationalGames main) {
        this.main = main;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (command.getName().equalsIgnoreCase("education")) {
            if (!(sender instanceof Player)) {
                notPlayer(sender);
                return true;
            }
            Player p = (Player) sender;
            if (args.length != 1) {
                error(sender, "Invalid usage.");
                return true;
            }
            if (main.isPlayerInMaths(p)||main.isPlayerInEnglish(p)||args[0].equalsIgnoreCase("leave")) {
                removePlayerFromGame(p);
                if (args[0].equalsIgnoreCase("leave")) {
                    success(p, "Left the game.");
                }
                return true;
            }
            if (args[0].equalsIgnoreCase("math")) {
                broadcastInstructions(p);
                math(p);
            }
            if (args[0].equalsIgnoreCase("english")) {
                broadcastInstructions(p);
                english(p);
            }
            if (args[0].equalsIgnoreCase("science")) {
                science(p);
            }
        }
        return true;
    }


    private void broadcastInstructions(Player p) {
        for (int i = 0; i < 10; i++) {
            sendMessage(p, "");
        }
        sendMessage(p, "&c&lEducational Instructions");
        sendMessage(p, "&7Press 't' to open chat and type");
        sendMessage(p, "&7your answer.");
        sendMessage(p, "");
        sendMessage(p, "&7Leave the game at any time by running");
        sendMessage(p, "&c/education leave &7or typing");
        sendMessage(p, "&cleave &7in chat.");
    }
    public void removePlayerFromGame(Player p) {
        mathsAnswers.put(p, null);
        count.put(p, null);
        main.removePlayerFromEnglish(p);
        main.removePlayerFromMaths(p);
        removePotionEffects(p);
        p.sendTitle("","",0,1,0);
    }

    private HashMap<Player, Integer> mathsAnswers = new HashMap<>();
    private HashMap<Player, Integer> count = new HashMap<>();

    public void math(Player p) {
        main.addPlayerToMaths(p);
        count.put(p, 0);
        newMathsQuestion(p);
        addPotionEffects(p);
    }

    public void addPotionEffects(Player p) {
        p.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, Integer.MAX_VALUE, 255));
        p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, Integer.MAX_VALUE, 255));
    }

    public void removePotionEffects(Player p) {
        p.removePotionEffect(PotionEffectType.BLINDNESS);
        p.removePotionEffect(PotionEffectType.SLOW);
    }

    public void checkMathsAnswer(Player p, int input) {
        if (mathsAnswers.get(p) == input) {
            // Answer is correct
            success(p, "Correct answer!");
            // Next question
            newMathsQuestion(p);
        } else {
            // Answer is incorrect
            error(p, "Wrong answer! Try again.");
        }
    }

    private List<String> symbols = new ArrayList<>();
    public void newMathsQuestion(Player p) {
        if (count.get(p) >= 10) {
            // Game over
            p.sendTitle("","",0,1,0);
            main.removePlayerFromMaths(p);
            success(p, "You finished the game!");
            removePotionEffects(p);
            return;
        }
        if (symbols.isEmpty()) {
            symbols.add("+");
            symbols.add("*");
            symbols.add("/");
            symbols.add("-");
        }
        // Increase count by one
        count.put(p, count.get(p)+1);
        String symbol = randomItemFromList(symbols);
        int one = randInt(0, 20);
        int two = randInt(0, 20);
        switch (symbol) {
            case "+":
                mathsAnswers.put(p, one + two);
                break;
            case "-":
                mathsAnswers.put(p, one - two);
                break;
            case "*":
                mathsAnswers.put(p, one * two);
                break;
            case "/":
                mathsAnswers.put(p, one / two);
                break;
        }
        // Send title
        p.sendTitle(color("&e" + one + " " + symbol + " " + two),color("&7Type your answer in chat."), 0, Integer.MAX_VALUE, 0);
    }

    public void english(Player p) {
        main.addPlayerToEnglish(p);
        count.put(p, 0);
        newEnglishQuestion(p);
        addPotionEffects(p);
    }

    private List<String> words = new ArrayList<>();
    public void newEnglishQuestion(Player p) {
        if (count.get(p) >= 10) {
            // Game over
            p.sendTitle("","",0,1,0);
            main.removePlayerFromEnglish(p);
            success(p, "You finished the game!");
            removePotionEffects(p);
            return;
        }
        // Increase count by one
        count.put(p, count.get(p)+1);
        // Send question
        if (words.isEmpty()) {
            words.add("frog");
            words.add("cheese");
            words.add("crackers");
            words.add("mouse");
            words.add("command");
            words.add("education");
            words.add("plugin");
            words.add("target");
            words.add("english");
            words.add("time");
            words.add("complicated");
            words.add("mathematics");
        }
        String answer = randomItemFromList(words);
        englishAnswers.put(p, answer);
        p.sendTitle(color("&e" + shuffleString(answer)), color("&7Unscramble and type your answer in chat."), 0, Integer.MAX_VALUE, 0);
    }

    private HashMap<Player, String> englishAnswers = new HashMap<>();
    public void checkEnglishAnswer(Player player, String message) {
        if (englishAnswers.get(player).equalsIgnoreCase(message)) {
            // Correct
            success(player,"Correct Answer!");
            // Next question
            newEnglishQuestion(player);
        } else {
            // Incorrect
            error(player, "Incorrect answer!");
        }
    }

    public void science(Player p) {

    }
}
