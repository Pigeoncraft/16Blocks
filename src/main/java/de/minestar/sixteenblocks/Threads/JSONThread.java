/*
 * Copyright (C) 2012 MineStar.de 
 * 
 * This file is part of SixteenBlocks.
 * 
 * SixteenBlocks is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, version 3 of the License.
 * 
 * SixteenBlocks is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with SixteenBlocks.  If not, see <http://www.gnu.org/licenses/>.
 */

package de.minestar.sixteenblocks.Threads;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.TimerTask;

import org.bukkit.Bukkit;
import org.json.simple.JSONObject;

import de.minestar.minestarlibrary.utils.ConsoleUtils;
import de.minestar.sixteenblocks.Core.Core;
import de.minestar.sixteenblocks.Core.Settings;
import de.minestar.sixteenblocks.Manager.AreaManager;

public class JSONThread extends TimerTask {

    private File JSONFile = new File(Settings.getJSONPath());
    private AreaManager aManager;

    public JSONThread(AreaManager aManager) {
        this.aManager = aManager;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void run() {
        // Create sync thread to use notthreadsafe methods
        Bukkit.getScheduler().scheduleSyncDelayedTask(Core.getInstance(), new Runnable() {

            @Override
            public void run() {
                BufferedWriter bWriter = null;
                try {
                    JSONObject json = new JSONObject();
                    json.put("ConnectedUsers", Bukkit.getOnlinePlayers().length);
                    json.put("Skins", aManager.getUsedAreaCount());
                    json.put("Slots", Core.getAllowedMaxPlayer());
                    bWriter = new BufferedWriter(new FileWriter(JSONFile));
                    bWriter.write(json.toJSONString());
                    bWriter.flush();
                    bWriter.close();
                } catch (Exception e) {
                    ConsoleUtils.printException(e, Core.NAME, "Can't save JSON");
                } finally {
                    try {
                        if (bWriter != null)
                            bWriter.close();
                    } catch (Exception e2) {
                        // IGNORE SECOND EXCEPTION
                    }
                }
            }
        });

    }
}
