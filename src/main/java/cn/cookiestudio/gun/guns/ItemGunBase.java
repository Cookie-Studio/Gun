package cn.cookiestudio.gun.guns;

import cn.cookiestudio.gun.CoolDownTimer;
import cn.cookiestudio.gun.GunPlugin;
import cn.cookiestudio.gun.playersetting.PlayerSettingMap;
import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.entity.EntityDamageByEntityEvent;
import cn.nukkit.event.entity.EntityDamageEvent;
import cn.nukkit.event.entity.EntityDeathEvent;
import cn.nukkit.event.player.*;
import cn.nukkit.item.Item;
import cn.nukkit.item.ItemApple;
import cn.nukkit.item.ItemEdible;
import cn.nukkit.level.GameRule;
import cn.nukkit.math.Vector3;
import cn.nukkit.nbt.tag.CompoundTag;
import cn.nukkit.network.protocol.AnimatePacket;
import cn.nukkit.potion.Effect;
import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

@Setter
@Getter
public abstract class ItemGunBase extends ItemEdible {

    protected GunData gunData;

    static {
        Server.getInstance().getPluginManager().registerEvents(new Listener(), GunPlugin.getInstance());
        Server.getInstance().getScheduler().scheduleRepeatingTask(GunPlugin.getInstance(), () -> {
            Server.getInstance().getOnlinePlayers().values().forEach(player -> {
                if (player.getInventory().getItemInHand() instanceof ItemGunBase) {
                    ItemGunBase itemGun = (ItemGunBase) player.getInventory().getItemInHand();
                    if (player.isSneaking()) {
                        itemGun.getGunData().addAimingSlownessEffect(player);
                    }else {
                        itemGun.getGunData().addWalkingSlownessEffect(player);
                    }
                    if (!GunPlugin.getInstance().getCoolDownTimer().isCooling(player) || GunPlugin.getInstance().getCoolDownTimer().getCoolDownMap().get(player).getType() != CoolDownTimer.Type.RELOAD) {
                        if (GunPlugin.getInstance().getPlayerSettingPool().getSettings().containsKey(player.getName()) && GunPlugin.getInstance().getPlayerSettingPool().getSettings().get(player.getName()).getFireMode() == PlayerSettingMap.FireMode.AUTO) {
                            if (!GunPlugin.getInstance().getFireTask().firing(player)) {
                                player.sendActionBar("<" + itemGun.getAmmoCount() + "/" + itemGun.getGunData().getMagSize() + ">\n§dAUTO MODE: §cOFF");
                            } else {
                                player.sendActionBar("<" + itemGun.getAmmoCount() + "/" + itemGun.getGunData().getMagSize() + ">\n§dAUTO MODE: §aON");
                            }
                        }else{
                            player.sendActionBar("<" + itemGun.getAmmoCount() + "/" + itemGun.getGunData().getMagSize() + ">");
                        }
                        return;
                    }
                    CoolDownTimer.CoolDown coolDown = GunPlugin.getInstance().getCoolDownTimer().getCoolDownMap().get(player);
                    if (coolDown.getType() == CoolDownTimer.Type.RELOAD){
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("RELOAD: §a");
                        int bound = (int)(30.0 * ((double)coolDown.coolDownTick / (itemGun.getGunData().getReloadTime() * 20)));
                        for (int i = 30;i >= 1;i--){
                            if (i < bound) stringBuilder.append("|");
                            if (i == bound) stringBuilder.append("|§c");
                            if (i > bound) stringBuilder.append("|");
                        }
                        player.sendActionBar(stringBuilder.toString(), 0, 1, 0);
                    }
                }
            });
        }, 1);
    }

    public ItemGunBase(int id) {
        super(id);
    }

    public ItemGunBase(int id, Integer meta) {
        super(id, meta);
    }

    public ItemGunBase(int id, Integer meta, int count) {
        super(id, meta, count);
    }

    public ItemGunBase(int id, Integer meta, int count, String name) {
        super(id, meta, count, name);
    }

    public void doInit() {
    }

    @Override
    public boolean onClickAir(Player player, Vector3 directionVector) {
        GunInteractAction action = this.interact(player);
        return true;
    }

    @Override
    public boolean onUse(Player player, int ticksUsed) {
        return false;
    }

    @Override
    public int getMaxStackSize() {
        return 1;
    }

    public static GunData getGunData(Class<? extends ItemGunBase> clazz) {
        return GunPlugin.getInstance().getGunDataMap().get(clazz);
    }

    public GunInteractAction interact(Player player) {
        if (GunPlugin.getInstance().getCoolDownTimer().isCooling(player)){
            return GunInteractAction.COOLING;
        }
        ItemGunBase itemGun = (ItemGunBase)player.getInventory().getItemInHand();
        if (itemGun.getAmmoCount() > 0) {
            gunData.fire(player,this);
            if (player.getGamemode() != 1) {
                itemGun.setAmmoCount(itemGun.getAmmoCount() - 1);
            }
            player.getInventory().setItem(player.getInventory().getHeldItemIndex(),itemGun,false);
            GunPlugin.getInstance().getCoolDownTimer().addCoolDown(player, (int) (gunData.getFireCoolDown() * 20), () -> {}, () -> CoolDownTimer.Operator.NO_ACTION, CoolDownTimer.Type.FIRECOOLDOWN);
            return GunInteractAction.FIRE;
        }
        if (itemGun.getAmmoCount() == 0 && player.getInventory().contains(Item.get(gunData.getMagId()))) {
            gunData.startReload(player);
            GunPlugin.getInstance().getCoolDownTimer().addCoolDown(player, (int) (gunData.getReloadTime() * 20), () -> {
                gunData.reloadFinish(player);
                itemGun.setAmmoCount(gunData.getMagSize());
                player.getInventory().setItem(player.getInventory().getHeldItemIndex(),itemGun,false);//because some unknown reasons,if don't do that there will be some problems...fuck you nukkit
                for (Map.Entry<Integer,Item> entry : player.getInventory().getContents().entrySet()){
                    Item item = entry.getValue();
                    int slot = entry.getKey();
                    if (item.getId() == gunData.getMagId()){
                        item.setCount(item.count - 1);
                        player.getInventory().setItem(slot,item);
                        break;
                    }
                }
            }, () -> {
                player.sendMessage("§creload interrupt!");
                return CoolDownTimer.Operator.INTERRUPT;
            }, CoolDownTimer.Type.RELOAD);
            return GunInteractAction.RELOAD;
        }
        if (getAmmoCount() == 0){
            gunData.emptyGun(player);
            return GunInteractAction.EMPTY_GUN;
        }
        return null;
    }

    public int getAmmoCount(){
        if (this.getNamedTag() != null) {
            return this.getNamedTag().getInt("ammoCount");
        }
        return 0;
    }

    public void setAmmoCount(int count){
        if (this.getNamedTag() != null) {
            this.setNamedTag(this.getNamedTag().putInt("ammoCount", count));
        }else {
            this.setNamedTag(new CompoundTag().putInt("ammoCount", count));
        }
    }

    public abstract ItemMagBase getItemMagObject();

    private static class Listener implements cn.nukkit.event.Listener {
        @EventHandler
        public void onPlayerAnimation(PlayerAnimationEvent event) {
            if (event.getAnimationType() == AnimatePacket.Action.SWING_ARM && event.getPlayer().getInventory().getItemInHand() instanceof ItemGunBase) {
                if (GunPlugin.getInstance().getPlayerSettingPool().getSettings().get(event.getPlayer().getName()).getFireMode() == PlayerSettingMap.FireMode.AUTO){
                    GunPlugin.getInstance().getFireTask().changeState(event.getPlayer());
                }/*else {
                    ((ItemGunBase) event.getPlayer().getInventory().getItemInHand()).interact(event.getPlayer());
                }*/
            }
        }

//        @EventHandler
//        public void onPlayerInteract(PlayerInteractEvent event) {
//            Player player = event.getPlayer();
//            if (player.getInventory().getItemInHand() instanceof ItemGunBase && event.getAction() == cn.nukkit.event.player.PlayerInteractEvent.Action.RIGHT_CLICK_BLOCK) {
//                if (GunPlugin.getInstance().getPlayerSettingPool().getSettings().get(event.getPlayer().getName()).getFireMode() == PlayerSettingMap.FireMode.AUTO){
//                    GunPlugin.getInstance().getFireTask().changeState(event.getPlayer());
//                }else {
//                    ((ItemGunBase) event.getPlayer().getInventory().getItemInHand()).interact(event.getPlayer());
//                }
//            }
//        }

        @EventHandler
        public void onPlayerDropItem(PlayerDropItemEvent event){
            if (event.getItem() instanceof ItemGunBase){
                event.setCancelled();
                if (event.getPlayer().getInventory().getItemInHand().getId() != event.getItem().getId())
                    return;
                ItemGunBase itemGun = (ItemGunBase) event.getPlayer().getInventory().getItemInHand();
                event.getPlayer().getInventory().clear(event.getPlayer().getInventory().getHeldItemIndex());
                EntityGun entityGun = new EntityGun(event.getPlayer().getChunk(), EntityGun.getDefaultNBT(event.getPlayer()),itemGun.getGunData(),itemGun);
                entityGun.spawnToAll();
            }
            if (event.getItem() instanceof ItemMagBase){
                event.setCancelled();
                if (event.getPlayer().getInventory().getItemInHand().getId() != event.getItem().getId())
                    return;
                ItemMagBase itemMag = (ItemMagBase) event.getPlayer().getInventory().getItemInHand();
                if (itemMag.getCount() - event.getItem().getCount() > 0){
                    itemMag.setCount(itemMag.getCount() - event.getItem().getCount());
                    event.getPlayer().getInventory().setItemInHand(itemMag);
                }else{
                    event.getPlayer().getInventory().clear(event.getPlayer().getInventory().getHeldItemIndex());
                }
                EntityMag entityMag = new EntityMag(event.getPlayer().getChunk(), EntityGun.getDefaultNBT(event.getPlayer()), (ItemMagBase) event.getItem());
                entityMag.spawnToAll();
            }
        }

        @EventHandler
        public void onPlayerInteractEntityGunOrMag(EntityDamageByEntityEvent event) throws InstantiationException, IllegalAccessException {
            if (event.getEntity() instanceof EntityGun && event.getDamager() instanceof Player){
                event.setCancelled();
                EntityGun entityGun = (EntityGun) event.getEntity();
                int empty = ((Player) event.getDamager()).getInventory().firstEmpty(null);
                if (empty != -1){
                    ((Player) event.getDamager()).getInventory().setItem(empty,entityGun.getItemGun());
                }
                event.getEntity().close();
            }
            if (event.getEntity() instanceof EntityMag && event.getDamager() instanceof Player){
                event.setCancelled();
                EntityMag entityMag = (EntityMag) event.getEntity();
                ItemMagBase itemMag = entityMag.getItemMag();
                int empty = ((Player) event.getDamager()).getInventory().firstEmpty(null);
                if (empty != -1){
                    ((Player) event.getDamager()).getInventory().setItem(empty,itemMag);
                }
                event.getEntity().close();
            }
        }

        @EventHandler
        public void onEntityGunOrMagHurt(EntityDamageEvent event){
            if (event.getEntity() instanceof EntityGun || event.getEntity() instanceof EntityMag){
                event.setCancelled();
            }
        }

        @EventHandler
        public void onPlayerHeldItem(PlayerItemHeldEvent event){
            if (!(event.getItem() instanceof ItemGunBase)) {
                event.getPlayer().removeEffect(Effect.SLOWNESS);
            }
        }

        @EventHandler
        public void onEntityDead(EntityDeathEvent event){
            if (event.getEntity().getLevel().getGameRules().getBoolean(GameRule.KEEP_INVENTORY))
                return;
            Arrays.stream(event.getDrops()).forEach(item -> {
                if (item instanceof ItemGunBase){
                    ItemGunBase itemGun = (ItemGunBase) item;
                    EntityGun entityGun = new EntityGun(event.getEntity().getChunk(), EntityGun.getDefaultNBT(event.getEntity()),itemGun.getGunData(),itemGun);
                    entityGun.spawnToAll();
                }
                if (item instanceof ItemMagBase){
                    ItemMagBase itemMag = (ItemMagBase) item;
                    EntityMag entityMag = new EntityMag(event.getEntity().getChunk(), EntityGun.getDefaultNBT(event.getEntity()), itemMag);
                    entityMag.spawnToAll();
                }
            });
            event.setDrops(Arrays.stream(event.getDrops()).filter(item -> !(item instanceof ItemGunBase || item instanceof ItemMagBase)).collect(Collectors.toList()).toArray(new Item[0]));
        }
    }

    public static enum GunInteractAction{
        FIRE,
        RELOAD,
        COOLING,
        EMPTY_GUN
    }
}