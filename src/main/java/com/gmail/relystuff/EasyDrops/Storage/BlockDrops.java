package com.gmail.relystuff.EasyDrops.Storage;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class BlockDrops implements ConfigurationSerializable {
    private final Material m;
    private int iterations = 1;
    private HashMap<ItemStack, Double> drops;

    public BlockDrops(Material m, int iterations) {
        this.drops = new HashMap<>();
        this.iterations = iterations;
        this.m = m;
    }

    public ArrayList<ItemStack> getItems() {

        ArrayList<ItemStack> temp = new ArrayList<>();

        for(Map.Entry<ItemStack, Double> e : drops.entrySet()) {
            temp.add(e.getKey());
        }

        return temp;
    }

    public Material getM() {
        return m;
    }

    public void setIterations(int iterations) {
        this.iterations = iterations;
    }

    public void addDrop(ItemStack drop, Double weight) {
        drops.put(drop, weight);
    }

    public void removeDrop(ItemStack drop) {
        drops.remove(drop);
    }

    public boolean hasItem(ItemStack item) {

        for(Map.Entry<ItemStack, Double> e : drops.entrySet()) {
            if(e.getKey().equals(item)) {
                return true;
            }
        }

        return false;

    }

    public ArrayList<ItemStack> getDrops() {
        ArrayList<ItemStack> theseDrops = new ArrayList<>();

        double totalWeight = 0;

        for(Map.Entry<ItemStack, Double> e : drops.entrySet()) {
            totalWeight += e.getValue();
        }

        for(int x = 0; x < iterations; x++) {
            double additiveWeight = 0;
            double r = (new Random()).nextDouble() * totalWeight;
            for(Map.Entry<ItemStack, Double> e : drops.entrySet()) {
                additiveWeight += e.getValue();
//                Bukkit.broadcastMessage("Random:" + r + ", additiveweight:" + additiveWeight + ", itemweight:" + e.getValue() + ", totalWeight:" + totalWeight);
                if(additiveWeight >= r) {
                    theseDrops.add(e.getKey().clone());
                    break;
                }
            }
        }
        return theseDrops;
    }

    public int getIterations() {
        return iterations;
    }

    public static BlockDrops deserialize(Map<String, Object> d) {
        BlockDrops b = new BlockDrops(Material.getMaterial((String) d.get("m")), (Integer) d.get("iterations"));
        HashMap<ItemStack, Double> i = (HashMap<ItemStack, Double>) d.get("drops");
        for(Map.Entry<ItemStack, Double> e : i.entrySet()) {
            b.addDrop(e.getKey(), e.getValue());
        }
        return b;
    }

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> s = new HashMap<>();
        s.put("m", m.name());
        s.put("iterations", iterations);
        s.put("drops", drops);
        return s;
//        return null;
    }
}
