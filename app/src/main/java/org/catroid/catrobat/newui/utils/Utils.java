package org.catroid.catrobat.newui.utils;


import org.catroid.catrobat.newui.data.LookInfo;
import org.catroid.catrobat.newui.data.SoundInfo;
import org.catroid.catrobat.newui.io.PathInfoDirectory;
import org.catroid.catrobat.newui.io.StorageHandler;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public final class Utils {

    public static PathInfoDirectory getImageDirectory() {
        StorageHandler.setupDirectoryStructure();
        return new PathInfoDirectory(StorageHandler.ROOT_DIRECTORY, StorageHandler.IMAGE_FOLDER);
    }

    public static PathInfoDirectory getSoundDirectory() {
        StorageHandler.setupDirectoryStructure();
        return new PathInfoDirectory(StorageHandler.ROOT_DIRECTORY, StorageHandler.SOUND_FOLDER);
    }

    public static String getUniqueLookName(String name, List<LookInfo> scope) {
        Set<String> nameSet = new HashSet<>();
        for (LookInfo item : scope) {
            nameSet.add(item.getName());
        }

        return getUniqueName(name, nameSet);
    }

    public static String getUniqueSoundName(String name, List<SoundInfo> scope) {
        Set<String> nameSet = new HashSet<>();
        for (SoundInfo item : scope) {
            nameSet.add(item.getName());
        }

        return getUniqueName(name, nameSet);
    }

    private static String getUniqueName(String name, Set<String> nameSet) {
        String newName = name;
        int suffix = 1;
        while (nameSet.contains(newName)) {
            newName = name.concat(" ").concat(Integer.toString(suffix));
            suffix++;
        }
        return newName;
    }
}
