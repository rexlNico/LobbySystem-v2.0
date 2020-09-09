package de.rexlnico.lobbysystem.methodes.inventory;

import de.rexlnico.lobbysystem.main.Main;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

public class AnimatedInventory implements Listener {

    private Inventory template;
    private InventoryAnimation animation;
    private long speed;
    private HashMap<Integer, ClickEvent> clickEvents;
    private Sound sound = null;

    public AnimatedInventory(Inventory template, InventoryAnimation animation, long speed) {
        this.template = template;
        this.animation = animation;
        this.speed = speed;
        clickEvents = new HashMap<Integer, ClickEvent>();
        Main.getPlugin().addListener(this);
    }

    public AnimatedInventory(InventoryAnimation animation, int size, String title, long speed) {
        this.template = Bukkit.createInventory(null, size, title);
        this.animation = animation;
        this.speed = speed;
        clickEvents = new HashMap<Integer, ClickEvent>();
        Main.getPlugin().addListener(this);
    }

    public AnimatedInventory setSound(Sound sound) {
        this.sound = sound;
        return this;
    }

    public void setTemplate(Inventory template) {
        this.template = template;
    }

    public void setAnimation(InventoryAnimation animation) {
        this.animation = animation;
    }

    public void setSpeed(long speed) {
        this.speed = speed;
    }

    public void setTitle(String title) {
        Inventory newTemp = Bukkit.createInventory(null, template.getSize(), title);
        newTemp.setContents(template.getContents());
        template = newTemp;
    }

    public void clearTemplateSlot(int slot) {
        template.setItem(slot, null);
    }

    public int getSize() {
        return template.getSize();
    }

    public void clearTemplate() {
        template.clear();
    }

    public void setTemplateSlot(int slot, ItemStack itemStack) {
        template.setItem(slot, itemStack);
    }

    public String getTitle() {
        return template.getTitle();
    }

    public Inventory getTemplate() {
        return template;
    }

    public InventoryAnimation getAnimation() {
        return animation;
    }

    public long getSpeed() {
        return speed;
    }

    public void openInventory(Player player) {
        animation.animateInventory(player, template, speed, sound);
    }

    public void openInventory(Player player, long speed) {
        animation.animateInventory(player, template, speed, sound);
    }

    public void openInventory(InventoryAnimation animation, Player player) {
        animation.animateInventory(player, template, speed, sound);
    }

    public void openInventory(InventoryAnimation animation, Player player, long speed) {
        animation.animateInventory(player, template, speed, sound);
    }

    public void clearClickEvents() {
        clickEvents.clear();
    }

    public void addClickEvent(int slot, ClickEvent clickEvent) {
        clickEvents.remove(slot);
        clickEvents.put(slot, clickEvent);
    }

    public void removeClickEvent(int slot) {
        clickEvents.remove(slot);
    }

    @EventHandler
    private void onClickEvent(InventoryClickEvent e) {
        if (e.getClickedInventory().getTitle() != null && e.getClickedInventory().getTitle().equals(template.getTitle())) {
            e.setCancelled(true);
            if (clickEvents.containsKey(e.getSlot())) {
                ClickEvent clickEvent = clickEvents.get(e.getSlot());
                clickEvent.inventoryClickEvent = e;
                clickEvent.player = (Player) e.getWhoClicked();
                clickEvent.run();
            }
        }
    }

}
