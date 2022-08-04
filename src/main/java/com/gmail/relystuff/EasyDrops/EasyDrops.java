package com.gmail.relystuff.EasyDrops;

import com.gmail.relystuff.EasyDrops.Commands.Drops;
import com.gmail.relystuff.EasyDrops.Listeners.BlockBreak;
import com.gmail.relystuff.EasyDrops.Storage.BlockDrops;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

public class EasyDrops extends JavaPlugin {

    private static EasyDrops instance = new EasyDrops();

    {
        ConfigurationSerialization.registerClass(BlockDrops.class);
    }

    private ArrayList<BlockDrops> bd;
    private File blocks;
    private FileConfiguration blocksConfig;

    public ArrayList<BlockDrops> getBd() {
        return bd;
    }

    @Override
    public void onEnable() {
        this.getCommand("drops").setExecutor(new Drops(this));
        getServer().getPluginManager().registerEvents(new BlockBreak(), this);
        initiateBlocksConfig();
        fetchBlocks();
        getLogger().info("EasyDrops has been enabled.");
    }

    public static EasyDrops getInstance() {
        return instance;
    }

    private void fetchBlocks() {
        bd = new ArrayList<>();

        ConfigurationSection blocks = blocksConfig.getConfigurationSection("loottable.blocks");
        if(blocks == null) {
            blocksConfig.createSection("loottable.blocks");
            blocks = blocksConfig.getConfigurationSection("loottable.blocks");
        }

        if(blocks != null) {
            for(String s : blocks.getKeys(false)) {
                BlockDrops current = blocks.getObject(s + ".items", BlockDrops.class);
                bd.add(current);
            }
        }

    }

    private void saveBlocks() {
        ConfigurationSection blocks = blocksConfig.getConfigurationSection("loottable.blocks");

        for(String s : blocks.getKeys(false)) {
            Material m = Material.matchMaterial(s);
            if(getMaterial(m) == null) {
                blocksConfig.set("loottable.blocks." + m.name().toLowerCase(), null);
            }
        }

        for(BlockDrops b : bd) {
            blocks.set(b.getM().name().toLowerCase() + ".items", b);
        }

        save();
    }

    public BlockDrops getMaterial(Material m) {
        for(BlockDrops b : bd) {
            if(b.getM() == m) {
                return b;
            }
        }
        return null;
    }

    private void initiateBlocksConfig() {
        blocks = new File(getDataFolder(), "easydrops.yml");

        if(!blocks.exists()) {
            blocks.getParentFile().mkdirs();
            saveResource("easydrops.yml", false);
        }

        blocksConfig = new YamlConfiguration();

        try {
            blocksConfig.load(blocks);
        } catch (InvalidConfigurationException | IOException e) {
            e.printStackTrace();
        }

    }

    public boolean save() {
        try {
            blocksConfig.save(blocks);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public FileConfiguration getBlocksConfig() {
        return blocksConfig;
    }

    @Override
    public void onDisable() {
        saveBlocks();
        getLogger().info("EasyDrops has been disabled.");
    }

    public void removeMaterial(Material m) {
        BlockDrops remove = null;
        for(BlockDrops b : bd) {
            if(b.getM() == m) {
                remove = b;
            }
        }
        bd.remove(remove);
    }

//    public ArrayList<ItemStack> getDrops(Block b) {
//        ConfigurationSection i = blocksConfig.getConfigurationSection("loottable.blocks." + b.getType().name().toLowerCase() + ".items");
//
//        if(i != null) {
//
//            BlockDrops d = new BlockDrops(b.getType(), i.getInt("loottable.blocks." + b.getType().name().toLowerCase() + ".iterations"));
//
//
//            i.getObject("d.d", ItemStack.class);
//
//            for(Map.Entry e : values.entrySet()) {
//                d.addDrop(i.getItemStack((String) e.getKey());
//            }
//        } else {
//            return null;
//        }
//        i.getValues(false);
//        if(i == null) {
//
//        } else {
//
//        }
//    }
}
