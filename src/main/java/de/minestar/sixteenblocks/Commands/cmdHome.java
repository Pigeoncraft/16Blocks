package de.minestar.sixteenblocks.Commands;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import de.minestar.minestarlibrary.commands.AbstractExtendedCommand;
import de.minestar.sixteenblocks.Core.Core;
import de.minestar.sixteenblocks.Core.TextUtils;
import de.minestar.sixteenblocks.Manager.AreaManager;
import de.minestar.sixteenblocks.Manager.SkinArea;

public class cmdHome extends AbstractExtendedCommand {

    private AreaManager areaManager;

    public cmdHome(String syntax, String arguments, String node, AreaManager areaManager) {
        super(Core.NAME, syntax, arguments, node);
        this.areaManager = areaManager;
        this.description = "Gives you the current area";
    }

    @Override
    public void execute(String[] arguments, Player player) {
        if (arguments.length == 0) {
            // TELEPORT TO HOME AREA
            SkinArea thisArea = this.areaManager.getPlayerArea(player);
            if (thisArea == null) {
                TextUtils.sendError(player, "You do not have an area yet.");
                TextUtils.sendLine(player, ChatColor.GRAY, "Take one with '/start' or '/starthere'.");
                return;
            }
            player.teleport(thisArea.getZoneXZ().getSpawnLocation());
            TextUtils.sendInfo(player, "Teleporting you to your area...");
        } else {
            // TELEPORT TO ANOTHER AREA
            SkinArea thisArea = this.areaManager.getPlayerArea(arguments[0]);
            if (thisArea == null) {
                TextUtils.sendError(player, "No area found for player '" + arguments[0] + "'.");
                return;
            }
            player.teleport(thisArea.getZoneXZ().getSpawnLocation());
            TextUtils.sendInfo(player, "Teleporting you to '" + thisArea.getAreaOwner() + "s' area...");
        }
    }
}
