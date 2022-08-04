package com.gmail.relystuff.EasyDrops.Commands;

import com.gmail.relystuff.EasyDrops.EasyDrops;
import com.gmail.relystuff.EasyDrops.Storage.BlockDrops;
import org.apache.commons.lang3.StringUtils;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class Drops implements CommandExecutor {

    private final EasyDrops pl;
    public Drops(EasyDrops pl) {
        this.pl = pl;
    }

    @Override
    public boolean onCommand(CommandSender cs, Command cmd, String s, String[] args) {

        if(cs.hasPermission("easydrops.drops")) {

            if(cs instanceof Player) {

                Player player = (Player) cs;

                if(args.length > 0) {
                    if(args[0].equalsIgnoreCase("new")) {
                        if(args.length < 3) {
                            cs.sendMessage(ColorUtils.colorize("&#D39FF0&l> &r&#A39E7CIncorrect syntax. Usage: &#F0E6AA/drops new <block> <iterations>"));
                        } else {
                            Material m = findBlock(args[1]);
                            if(m != null) {

                                if(StringUtils.isNumeric(args[2])) {

                                    int iterations = Integer.parseInt(args[2]);

                                    if(pl.getMaterial(m) == null) {

                                        BlockDrops items = new BlockDrops(m, iterations);

                                        pl.getBd().add(items);

                                        cs.sendMessage(ColorUtils.colorize("&#D39FF0&l> &r&#A39E7CSuccessfully inserted new block to configuration."));

                                    } else {
                                        cs.sendMessage(ColorUtils.colorize("&#D39FF0&l> &r&#A39E7CThis block already has custom drops."));
                                    }


                                } else {
                                    cs.sendMessage(ColorUtils.colorize("&#D39FF0&l> &r&#A39E7CCould not interpret iterations."));
                                }

                            } else {
                                cs.sendMessage(ColorUtils.colorize("&#D39FF0&l> &r&#A39E7CThat is not a valid block."));
                            }
                        }
                    } else if(args[0].equalsIgnoreCase("delete")) {
                        if(args.length < 2) {
                            cs.sendMessage(ColorUtils.colorize("&#D39FF0&l> &r&#A39E7CIncorrect syntax. Usage: &#F0E6AA/drops delete <block>"));
                        } else {
                            Material m = findBlock(args[1]);
                            if(m != null) {

                                if (pl.getMaterial(m) == null) {

                                    cs.sendMessage(ColorUtils.colorize("&#D39FF0&l> &r&#A39E7CThis block doesn't have a loottable."));

                                } else {
                                    pl.removeMaterial(m);
                                    cs.sendMessage(ColorUtils.colorize("&#D39FF0&l> &r&#A39E7CThis block no longer has a loottable."));
                                }


                            } else {
                                cs.sendMessage(ColorUtils.colorize("&#D39FF0&l> &r&#A39E7CCould not interpret iterations."));
                            }

                        }
                    } else if(args[0].equalsIgnoreCase("additem")) {
                        if(args.length < 3) {
                            cs.sendMessage(ColorUtils.colorize("&#D39FF0&l> &r&#A39E7CIncorrect syntax. Usage: &#F0E6AA/drops additem <block> <weight>"));
                        } else {

                            Material m = findBlock(args[1]);
                            if(m != null) {

                                if(StringUtils.isNumeric(args[2])) {

                                    if(!player.getInventory().getItemInMainHand().getType().isAir()) {
                                        Double weight = Double.parseDouble(args[2]);

                                        if(pl.getMaterial(m) == null) {

                                            cs.sendMessage(ColorUtils.colorize("&#D39FF0&l> &r&#A39E7CThis block does not have custom drops."));

                                        } else {

                                            BlockDrops items = pl.getMaterial(m);


//                                        cs.sendMessage(ColorUtils.colorize("&#D39FF0&l> &r&#A39E7CLength:" + items.size()));
                                            if(items.hasItem(player.getInventory().getItemInMainHand())) {
                                                cs.sendMessage(ColorUtils.colorize("&#D39FF0&l> &r&#A39E7CThis item is already part of this loottable."));
                                            } else {
                                                items.addDrop(player.getInventory().getItemInMainHand(), weight);
                                                cs.sendMessage(ColorUtils.colorize("&#D39FF0&l> &r&#A39E7CDrop successfully added!"));
                                            }


//                                    cs.sendMessage(ColorUtils.colorize("&#D39FF0&l> &r&#A39E7CThis block already has custom drops."));
                                        }
                                    } else {
                                        cs.sendMessage(ColorUtils.colorize("&#D39FF0&l> &r&#A39E7CYou are not holding an item."));
                                    }



                                } else {
                                    cs.sendMessage(ColorUtils.colorize("&#D39FF0&l> &r&#A39E7CCould not interpret iterations."));
                                }

                            } else {
                                cs.sendMessage(ColorUtils.colorize("&#D39FF0&l> &r&#A39E7CThat is not a valid block."));
                            }

                        }
                    } else if(args[0].equalsIgnoreCase("removeitem")) {
                        if(args.length < 2) {
                            cs.sendMessage(ColorUtils.colorize( "&#D39FF0&l> &r&#A39E7CIncorrect syntax. Usage: &#F0E6AA/drops removeitem <block>"));
                        } else {
                            Material m = findBlock(args[1]);
                            if(m != null) {

                                if(!player.getInventory().getItemInMainHand().getType().isAir()) {

                                    ConfigurationSection config = pl.getBlocksConfig().getConfigurationSection("loottable.blocks." + m.name().toLowerCase());
                                    if(pl.getMaterial(m) == null) {

                                        cs.sendMessage(ColorUtils.colorize("&#D39FF0&l> &r&#A39E7CThis block does not have custom drops."));

                                    } else {

                                        BlockDrops items = pl.getMaterial(m);


//                                        cs.sendMessage(ColorUtils.colorize("&#D39FF0&l> &r&#A39E7CLength:" + items.size()));
                                        if(items.hasItem(player.getInventory().getItemInMainHand())) {
                                            cs.sendMessage(ColorUtils.colorize("&#D39FF0&l> &r&#A39E7CThis item has been removed from the loottable."));
                                            items.removeDrop(player.getInventory().getItemInMainHand());
                                        } else {
                                            cs.sendMessage(ColorUtils.colorize("&#D39FF0&l> &r&#A39E7CThis item is not part of that loottable."));
                                        }


//                                    cs.sendMessage(ColorUtils.colorize("&#D39FF0&l> &r&#A39E7CThis block already has custom drops."));
                                    }
                                } else {
                                    cs.sendMessage(ColorUtils.colorize("&#D39FF0&l> &r&#A39E7CYou are not holding an item."));
                                }


                            } else {
                                cs.sendMessage(ColorUtils.colorize("&#D39FF0&l> &r&#A39E7CThat is not a valid block."));
                            }
                        }
                    } else if(args[0].equalsIgnoreCase("setiterations")) {
                        if(args.length < 3) {
                            cs.sendMessage(ColorUtils.colorize("&#D39FF0&l> &r&#A39E7CIncorrect syntax. Usage: &#F0E6AA/drops setiterations <block> <iterations>"));
                        } else {
                            Material m = findBlock(args[1]);
                            if(m != null) {

                                if(StringUtils.isNumeric(args[2])) {

                                    if(player.getInventory().getItemInMainHand().getType() != Material.AIR) {
                                        int iterations = Integer.parseInt(args[2]);

                                        if(pl.getMaterial(m) == null) {

                                            cs.sendMessage(ColorUtils.colorize("&#D39FF0&l> &r&#A39E7CThis block does not have custom drops."));

                                        } else {

                                            BlockDrops items = pl.getMaterial(m);

                                            items.setIterations(iterations);

                                            cs.sendMessage(ColorUtils.colorize("&#D39FF0&l> &r&#A39E7CLoot iterations updated."));

//                                    cs.sendMessage(ColorUtils.colorize("&#D39FF0&l> &r&#A39E7CThis block already has custom drops."));
                                        }
                                    } else {
                                        cs.sendMessage(ColorUtils.colorize("&#D39FF0&l> &r&#A39E7CYou are not holding an item."));
                                    }



                                } else {
                                    cs.sendMessage(ColorUtils.colorize("&#D39FF0&l> &r&#A39E7CCould not interpret iterations."));
                                }

                            } else {
                                cs.sendMessage(ColorUtils.colorize("&#D39FF0&l> &r&#A39E7CThat is not a valid block."));
                            }
                        }
                    } else if(args[0].equalsIgnoreCase("give")) {
                        if(args.length < 2) {
                            cs.sendMessage(ColorUtils.colorize("&#D39FF0&l> &r&#A39E7CIncorrect syntax. Usage: &#F0E6AA/drops give <block>"));
                        } else {
                            Material m = findBlock(args[1]);
                            if(m != null) {


                                if(pl.getMaterial(m) == null) {

                                    cs.sendMessage(ColorUtils.colorize("&#D39FF0&l> &r&#A39E7CThis block does not have custom drops."));

                                } else {

                                    BlockDrops items = pl.getMaterial(m);

                                    for(ItemStack i : items.getItems()) {
                                        player.getInventory().addItem(i);
                                    }

                                    cs.sendMessage(ColorUtils.colorize("&#D39FF0&l> &r&#A39E7CDrop items given."));

//                                    cs.sendMessage(ColorUtils.colorize("&#D39FF0&l> &r&#A39E7CThis block already has custom drops."));
                                }


                            } else {
                                cs.sendMessage(ColorUtils.colorize("&#D39FF0&l> &r&#A39E7CThat is not a valid block."));
                            }
                        }
                    } else if(args[0].equalsIgnoreCase("test")) {
                        if(args.length < 2) {
                            cs.sendMessage(ColorUtils.colorize("&#D39FF0&l> &r&#A39E7CIncorrect syntax. Usage: &#F0E6AA/drops test <block>"));
                        } else {
                            Material m = findBlock(args[1]);
                            if(m != null) {


                                if(pl.getMaterial(m) == null) {

                                    cs.sendMessage(ColorUtils.colorize("&#D39FF0&l> &r&#A39E7CThis block does not have custom drops."));

                                } else {

                                    BlockDrops items = pl.getMaterial(m);
                                    ArrayList<ItemStack> give = items.getDrops();

                                    for(ItemStack i : give) {
                                        player.getInventory().addItem(i);
                                    }

                                    cs.sendMessage(ColorUtils.colorize("&#D39FF0&l> &r&#A39E7CTested item drops."));

//                                    cs.sendMessage(ColorUtils.colorize("&#D39FF0&l> &r&#A39E7CThis block already has custom drops."));
                                }


                            } else {
                                cs.sendMessage(ColorUtils.colorize("&#D39FF0&l> &r&#A39E7CThat is not a valid block."));
                            }
                        }
                    } else {
                        cs.sendMessage(ColorUtils.colorize( "&#D39FF0&l> &r&#A39E7CIncorrect syntax. Usage: &#F0E6AA/drops <new/delete/additem/removeitem/setiterations/give/test>"));
                    }
                } else {
                    cs.sendMessage(ColorUtils.colorize( "&#D39FF0&l> &r&#A39E7CIncorrect syntax. Usage: &#F0E6AA/drops <new/delete/additem/removeitem/setiterations/give/test>"));
                }
            } else {
                cs.sendMessage(ColorUtils.colorize("&#D39FF0&l> &r&#A39E7CYou cannot use this plugin via console."));
            }


        } else {
            cs.sendMessage(ColorUtils.colorize("&#D39FF0&l> &r&#A39E7CYou don't have permission."));
        }
        return false;
    }

    public Material findBlock(String s) {
        return Material.matchMaterial(s);
    }
}
