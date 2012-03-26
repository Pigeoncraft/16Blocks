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

package de.minestar.sixteenblocks.Units;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import de.minestar.minestarlibrary.utils.ConsoleUtils;
import de.minestar.sixteenblocks.Core.Core;

public class ChatFilter {

    private List<String> whiteWords, blackWords;

    public ChatFilter(File dataFolder) {
        loadLists(dataFolder);
    }

    private void loadLists(File dataFolder) {
        try {
            BufferedReader bReader = null;
            String line = "";

            // Read white list
            this.whiteWords = new ArrayList<String>();
            File whiteListFile = new File(dataFolder, "whitelist.txt");
            if (!whiteListFile.exists())
                ConsoleUtils.printInfo(Core.NAME, whiteListFile + " not found! No Whitelist for ChatFilter created!");
            else {
                // Every line is seperated in the result with an
                // | (logical operator in regex)
                bReader = new BufferedReader(new FileReader(whiteListFile));
                while ((line = bReader.readLine()) != null) {
                    line = line.toLowerCase().trim();
                    if (line.equalsIgnoreCase(""))
                        continue;
                    if (line.contains("."))
                        this.whiteWords.add(line);
                    else
                        this.whiteWords.add(" " + line + " ");
                }
            }
            // Read black list
            File blackListFile = new File(dataFolder, "blacklist.txt");
            this.blackWords = new ArrayList<String>();
            if (!blackListFile.exists())
                ConsoleUtils.printInfo(Core.NAME, blackListFile + " not found! No Blacklist for ChatFilter created!");
            else {
                bReader = new BufferedReader(new FileReader(blackListFile));
                while ((line = bReader.readLine()) != null) {
                    line = line.toLowerCase().trim();
                    if (line.equalsIgnoreCase(""))
                        continue;
                    if (line.contains("."))
                        this.blackWords.add(line);
                    else
                        this.blackWords.add(" " + line + " ");
                }
            }
            ConsoleUtils.printInfo(Core.NAME, "White and Blacklist for ChatFilter loaded!");
        } catch (Exception e) {
            ConsoleUtils.printException(e, Core.NAME, "Can' read filter files!");
        }
    }

    public boolean acceptMessage(final String message) {
        // When on white list or NOT on black list -> is ok
        for (int i = 0; i < this.whiteWords.size(); i++) {
            if (message.contains(this.whiteWords.get(i)))
                return true;
        }

        for (int i = 0; i < this.blackWords.size(); i++) {
            if (message.contains(this.blackWords.get(i)))
                return false;
        }
        return true;
    }
}
