package cn.cookiestudio.gun.command;

import cn.cookiestudio.easy4form.window.BFormWindowCustom;
import cn.cookiestudio.easy4form.window.BFormWindowSimple;
import cn.cookiestudio.gun.GunPlugin;
import cn.cookiestudio.gun.guns.GunData;
import cn.cookiestudio.gun.playersetting.PlayerSettingMap;
import cn.nukkit.Player;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.ConsoleCommandSender;
import cn.nukkit.command.data.CommandParameter;
import cn.nukkit.form.element.*;
import cn.nukkit.form.response.FormResponseCustom;
import cn.nukkit.form.response.FormResponseSimple;

import java.util.ArrayList;
import java.util.List;

public class GunCommand extends Command {
    public GunCommand(String name) {
        super(name, "Gun Plugin Command");
        this.setPermission("gun.command");
        this.commandParameters.clear();
        this.commandParameters.put("default", new CommandParameter[]{
                CommandParameter.newEnum("opt", new String[]{"data", "setting"})
        });
    }

    @Override
    public boolean execute(CommandSender commandSender, String s, String[] strings) {
        if (commandSender instanceof ConsoleCommandSender) {
            commandSender.sendMessage("此命令无法在控制台使用！");
            return true;
        }
        if (strings.length == 0) {
            return true;
        }
        Player player = (Player) commandSender;
        if (strings[0].equals("data")) {
            if (!commandSender.isOp()) {
                commandSender.sendMessage("你没有足够的权限使用此命令！");
                return true;
            }
            BFormWindowSimple.Builder simpleFormBuilder = BFormWindowSimple.getBuilder();
            simpleFormBuilder.setTitle("选择你需要修改参数的枪械:");
            GunPlugin.getInstance().getGunDataMap().values().forEach(gunData -> {
                simpleFormBuilder.addButton(new ElementButton(gunData.getGunName(), new ElementButtonImageData("path", "textures/items/book_writable")));
            });
            simpleFormBuilder.setResponseAction(e -> {
                if (e.getResponse() == null)
                    return;
                FormResponseSimple responseSimple = (FormResponseSimple) e.getResponse();
                String gunName = responseSimple.getClickedButton().getText();
                Class gunClass = GunPlugin.getInstance().getStringClassMap().get(gunName);
                GunData gunData = GunPlugin.getInstance().getGunDataMap().get(gunClass);
                BFormWindowCustom.Builder customFormBuilder = BFormWindowCustom.getBuilder();
                customFormBuilder.setTitle(gunName);
                customFormBuilder.addElements(new ElementInput("弹夹容量", "magSize", String.valueOf(gunData.getMagSize())));//0
                customFormBuilder.addElements(new ElementInput("开火冷却", "fireCoolDown", String.valueOf(gunData.getFireCoolDown())));//1
                customFormBuilder.addElements(new ElementInput("换弹时间", "reloadTime", String.valueOf(gunData.getReloadTime())));//2
                customFormBuilder.addElements(new ElementInput("站立时缓慢等级", "slownessLevel", String.valueOf(gunData.getSlownessLevel())));//3
                customFormBuilder.addElements(new ElementInput("潜行时缓慢等级", "slownessLevelAim", String.valueOf(gunData.getSlownessLevelAim())));//4
                customFormBuilder.addElements(new ElementInput("开火视角摇晃程度", "fireSwingIntensity", String.valueOf(gunData.getFireSwingIntensity())));//5
                customFormBuilder.addElements(new ElementInput("伤害", "hitDamage", String.valueOf(gunData.getHitDamage())));//6
                customFormBuilder.addElements(new ElementInput("范围", "range", String.valueOf(gunData.getRange())));//7
                customFormBuilder.addElements(new ElementInput("弹道粒子效果", "particle", gunData.getParticle()));//8
                customFormBuilder.addElements(new ElementInput("弹夹名称", "magName", gunData.getMagName()));//9
                customFormBuilder.addElements(new ElementInput("后坐力", "recoil", String.valueOf(gunData.getRecoil())));//10
                customFormBuilder.addElements(new ElementInput("开火视角摇晃时间", "fireSwingDuration", String.valueOf(gunData.getFireSwingDuration())));//11

                customFormBuilder.setResponseAction(e1 -> {
                    if (e1.getResponse() == null)
                        return;
                    FormResponseCustom responseCustom = (FormResponseCustom) e1.getResponse();
                    gunData.setMagSize(Integer.parseInt(responseCustom.getInputResponse(0)));
                    gunData.setFireCoolDown(Double.parseDouble(responseCustom.getInputResponse(1)));
                    gunData.setReloadTime(Double.parseDouble(responseCustom.getInputResponse(2)));
                    gunData.setSlownessLevel(Integer.parseInt(responseCustom.getInputResponse(3)));
                    gunData.setSlownessLevelAim(Integer.parseInt(responseCustom.getInputResponse(4)));
                    gunData.setFireSwingIntensity(Double.parseDouble(responseCustom.getInputResponse(5)));
                    gunData.setHitDamage(Double.parseDouble(responseCustom.getInputResponse(6)));
                    gunData.setRange(Double.parseDouble(responseCustom.getInputResponse(7)));
                    gunData.setParticle(responseCustom.getInputResponse(8));
                    gunData.setMagName(responseCustom.getInputResponse(9));
                    gunData.setRecoil(Double.parseDouble(responseCustom.getInputResponse(10)));
                    gunData.setFireSwingDuration(Double.parseDouble(responseCustom.getInputResponse(11)));

                    GunPlugin.getInstance().saveGunData(gunData);
                    player.sendMessage("§aSucceed!");
                });

                customFormBuilder.build().sendToPlayer(player);
            });
            simpleFormBuilder.build().sendToPlayer(player);
            return true;
        }
        if (strings[0].equals("setting")) {
            BFormWindowCustom custom = new BFormWindowCustom("Settings");
            PlayerSettingMap settings = GunPlugin.getInstance().getPlayerSettingPool().getPlayerSetting(player.getName());
            List<String> list = new ArrayList<>();
            list.add(PlayerSettingMap.FireMode.AUTO.name());
            list.add(PlayerSettingMap.FireMode.MANUAL.name());
            custom.addElement(new ElementDropdown("开火模式:", list, settings.getFireMode().ordinal()));
            custom.addElement(new ElementToggle("打开弹道粒子:", settings.isOpenTrajectoryParticle()));
            custom.addElement(new ElementToggle("打开开火烟雾:", settings.isOpenMuzzleParticle()));
            custom.setResponseAction((e) -> {
                if (e.getResponse() == null)
                    return;
                FormResponseCustom response = (FormResponseCustom) e.getResponse();
                if (response.getDropdownResponse(0).getElementContent().equals(PlayerSettingMap.FireMode.AUTO.name())) {
                    settings.setFireMode(PlayerSettingMap.FireMode.AUTO);
                } else {
                    settings.setFireMode(PlayerSettingMap.FireMode.MANUAL);
                }
                settings.setOpenTrajectoryParticle(response.getToggleResponse(1));
                settings.setOpenMuzzleParticle(response.getToggleResponse(2));
                GunPlugin.getInstance().getPlayerSettingPool().write(player.getName(), settings);
                player.sendMessage("§aSucceed!");
            });
            custom.sendToPlayer(player);
            return true;
        }
        return true;
    }
}
