package com.github.jenya705.pancake.event.armorequip;

import com.github.jenya705.pancake.event.armorequip.BukkitArmorEquipEvent.EquipMethod;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Player;
import org.bukkit.event.Event.Result;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.*;
import org.bukkit.event.inventory.InventoryType.SlotType;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

/**
 * @author Arnah
 * @since Jul 30, 2015
 */
public class ArmorListener implements Listener{

	// Pancake Start
	/*
	private final List<String> blockedMaterials;

	public ArmorListener(List<String> blockedMaterials){
		this.blockedMaterials = blockedMaterials;
	}
	*/
	// Pancake End
	//Event Priority is highest because other plugins might cancel the events before we check.

	@EventHandler(priority =  EventPriority.HIGHEST, ignoreCancelled = true)
	public final void inventoryClick(final InventoryClickEvent e){
		boolean shift = false; // Pancake
		boolean numberkey = false;
		if(e.isCancelled()) return;
		if(e.getAction() == InventoryAction.NOTHING) return;// Why does this get called if nothing happens??
		if(e.getClick().equals(ClickType.SHIFT_LEFT) || e.getClick().equals(ClickType.SHIFT_RIGHT)){
			shift = true;
		}
		if(e.getClick().equals(ClickType.NUMBER_KEY)){
			numberkey = true;
		}
		if(e.getSlotType() != SlotType.ARMOR && e.getSlotType() != SlotType.QUICKBAR && e.getSlotType() != SlotType.CONTAINER) return;
		if(e.getClickedInventory() != null && !e.getClickedInventory().getType().equals(InventoryType.PLAYER)) return;
		if (!e.getInventory().getType().equals(InventoryType.CRAFTING) && !e.getInventory().getType().equals(InventoryType.PLAYER)) return;
		if(!(e.getWhoClicked() instanceof Player)) return;
		ArmorType newArmorType = ArmorType.matchType(shift ? e.getCurrentItem() : e.getCursor());
		if(!shift && newArmorType != null && e.getRawSlot() != newArmorType.getSlot()){
			// Used for drag and drop checking to make sure you aren't trying to place a helmet in the boots slot.
			return;
		}
		if(shift){
			newArmorType = ArmorType.matchType(e.getCurrentItem());
			if(newArmorType != null){
				boolean equipping = true;
				if(e.getRawSlot() == newArmorType.getSlot()){
					equipping = false;
				}
				if(newArmorType.equals(ArmorType.HELMET) && (equipping ? isAirOrNull(e.getWhoClicked().getInventory().getHelmet()) : !isAirOrNull(e.getWhoClicked().getInventory().getHelmet())) || newArmorType.equals(ArmorType.CHESTPLATE) && (equipping ? isAirOrNull(e.getWhoClicked().getInventory().getChestplate()) : !isAirOrNull(e.getWhoClicked().getInventory().getChestplate())) || newArmorType.equals(ArmorType.LEGGINGS) && (equipping ? isAirOrNull(e.getWhoClicked().getInventory().getLeggings()) : !isAirOrNull(e.getWhoClicked().getInventory().getLeggings())) || newArmorType.equals(ArmorType.BOOTS) && (equipping ? isAirOrNull(e.getWhoClicked().getInventory().getBoots()) : !isAirOrNull(e.getWhoClicked().getInventory().getBoots()))){
					BukkitArmorEquipEvent armorEquipEvent = new BukkitArmorEquipEvent((Player) e.getWhoClicked(), EquipMethod.SHIFT_CLICK, newArmorType, equipping ? null : e.getCurrentItem(), equipping ? e.getCurrentItem() : null);
					Bukkit.getServer().getPluginManager().callEvent(armorEquipEvent);
					if(armorEquipEvent.isCancelled()){
						e.setCancelled(true);
					}
				}
			}
		}else{
			ItemStack newArmorPiece = e.getCursor();
			ItemStack oldArmorPiece = e.getCurrentItem();
			if(numberkey){
				if(e.getClickedInventory().getType().equals(InventoryType.PLAYER)){// Prevents shit in the 2by2 crafting
					// e.getClickedInventory() == The players inventory
					// e.getHotBarButton() == key people are pressing to equip or unequip the item to or from.
					// e.getRawSlot() == The slot the item is going to.
					// e.getSlot() == Armor slot, can't use e.getRawSlot() as that gives a hotbar slot ;-;
					ItemStack hotbarItem = e.getClickedInventory().getItem(e.getHotbarButton());
					if(!isAirOrNull(hotbarItem)){// Equipping
						newArmorType = ArmorType.matchType(hotbarItem);
						newArmorPiece = hotbarItem;
						oldArmorPiece = e.getClickedInventory().getItem(e.getSlot());
					}else{// Unequipping
						newArmorType = ArmorType.matchType(!isAirOrNull(e.getCurrentItem()) ? e.getCurrentItem() : e.getCursor());
					}
				}
			}else{
				if(isAirOrNull(e.getCursor()) && !isAirOrNull(e.getCurrentItem())){// unequip with no new item going into the slot.
					newArmorType = ArmorType.matchType(e.getCurrentItem());
				}
				// e.getCurrentItem() == Unequip
				// e.getCursor() == Equip
				// newArmorType = ArmorType.matchType(!isAirOrNull(e.getCurrentItem()) ? e.getCurrentItem() : e.getCursor());
			}
			if(newArmorType != null && e.getRawSlot() == newArmorType.getSlot()){
				EquipMethod method = EquipMethod.PICK_DROP;
				if(e.getAction().equals(InventoryAction.HOTBAR_SWAP) || numberkey) method = EquipMethod.HOTBAR_SWAP;
				BukkitArmorEquipEvent armorEquipEvent = new BukkitArmorEquipEvent((Player) e.getWhoClicked(), method, newArmorType, oldArmorPiece, newArmorPiece);
				Bukkit.getServer().getPluginManager().callEvent(armorEquipEvent);
				if(armorEquipEvent.isCancelled()){
					e.setCancelled(true);
				}
			}
		}
	}
	
	@EventHandler(priority =  EventPriority.HIGHEST)
	public void playerInteractEvent(PlayerInteractEvent e){
		if(e.useItemInHand().equals(Result.DENY) ||
				e.getAction() == Action.PHYSICAL ||
				(e.getAction() != Action.RIGHT_CLICK_AIR && e.getAction() != Action.RIGHT_CLICK_BLOCK)
		) return;
			Player player = e.getPlayer();
			if(!e.useInteractedBlock().equals(Result.DENY) // Pancake - if optimization
					&& e.getClickedBlock() != null && e.getAction() == Action.RIGHT_CLICK_BLOCK && !player.isSneaking()){// Having both of these checks is useless, might as well do it though.
					// Some blocks have actions when you right click them which stops the client from equipping the armor in hand.
				Material mat = e.getClickedBlock().getType();
				// Pancake Start
				/*
				for(String s : blockedMaterials){
					if(mat.name().equalsIgnoreCase(s)) return;
				}
				*/
				if (mat.isInteractable()) {
					switch (mat){
						case PISTON, PISTON_HEAD, MOVING_PISTON, STICKY_PISTON:
							return;
						default:
							break;
					}
				}
				// Pancake End

			}
			ArmorType newArmorType = ArmorType.matchType(e.getItem());
			if(newArmorType != null && (
							newArmorType.equals(ArmorType.HELMET) && isAirOrNull(e.getPlayer().getInventory().getHelmet()) ||
							newArmorType.equals(ArmorType.CHESTPLATE) && isAirOrNull(e.getPlayer().getInventory().getChestplate()) ||
							newArmorType.equals(ArmorType.LEGGINGS) && isAirOrNull(e.getPlayer().getInventory().getLeggings()) ||
							newArmorType.equals(ArmorType.BOOTS) && isAirOrNull(e.getPlayer().getInventory().getBoots()))) {
				BukkitArmorEquipEvent armorEquipEvent = new BukkitArmorEquipEvent(e.getPlayer(), EquipMethod.HOTBAR, ArmorType.matchType(e.getItem()), null, e.getItem());
				Bukkit.getServer().getPluginManager().callEvent(armorEquipEvent);
				if(armorEquipEvent.isCancelled()){
					e.setCancelled(true);
					player.updateInventory();
				}
			}

	}
	
	@EventHandler(priority =  EventPriority.HIGHEST, ignoreCancelled = true)
	public void inventoryDrag(InventoryDragEvent event){
		ArmorType type = ArmorType.matchType(event.getOldCursor());
		if(event.getRawSlots().isEmpty()) return;// Idk if this will ever happen
		if(type != null && type.getSlot() == event.getRawSlots().stream().findFirst().orElse(0)){
			BukkitArmorEquipEvent armorEquipEvent = new BukkitArmorEquipEvent((Player) event.getWhoClicked(), EquipMethod.DRAG, type, null, event.getOldCursor());
			Bukkit.getServer().getPluginManager().callEvent(armorEquipEvent);
			if(armorEquipEvent.isCancelled()){
				event.setResult(Result.DENY);
				event.setCancelled(true);
			}
		}
	}

	@EventHandler
	public void itemBreakEvent(PlayerItemBreakEvent e){
		ArmorType type = ArmorType.matchType(e.getBrokenItem());
		if(type != null){
			Player p = e.getPlayer();
			BukkitArmorEquipEvent armorEquipEvent = new BukkitArmorEquipEvent(p, EquipMethod.BROKE, type, e.getBrokenItem(), null);
			Bukkit.getServer().getPluginManager().callEvent(armorEquipEvent);
			if(armorEquipEvent.isCancelled()){
				ItemStack i = e.getBrokenItem().clone();
				i.setAmount(1);
				// Pancake Start
				ItemMeta itemMeta = i.getItemMeta();
				if (itemMeta instanceof Damageable) ((Damageable) itemMeta).damage(((Damageable) itemMeta).getLastDamageCause().getFinalDamage() - 1);
				i.setItemMeta(itemMeta);
				//i.setDurability((short) (i.getDurability() - 1));
				// Pancake End
				if(type.equals(ArmorType.HELMET)){
					p.getInventory().setHelmet(i);
				}else if(type.equals(ArmorType.CHESTPLATE)){
					p.getInventory().setChestplate(i);
				}else if(type.equals(ArmorType.LEGGINGS)){
					p.getInventory().setLeggings(i);
				}else if(type.equals(ArmorType.BOOTS)){
					p.getInventory().setBoots(i);
				}
			}
		}
	}

	@EventHandler
	public void playerDeathEvent(PlayerDeathEvent e){
		Player p = e.getEntity();
		if(e.getKeepInventory()) return;
		for(ItemStack i : p.getInventory().getArmorContents()){
			if(!isAirOrNull(i)){
				Bukkit.getServer().getPluginManager().callEvent(new BukkitArmorEquipEvent(p, EquipMethod.DEATH, ArmorType.matchType(i), i, null));
				// No way to cancel a death event.
			}
		}
	}

	/**
	 * A utility method to support versions that use null or air ItemStacks.
	 */
	public static boolean isAirOrNull(ItemStack item){
		return item == null || item.getType().equals(Material.AIR);
	}
}
