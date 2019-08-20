package org.scau.internshipsystem.common.util;

import org.scau.internshipsystem.system.entity.Menu;

import java.util.*;

public class MenuTreeUtil {
    public static List<Menu> makeTree(List<Menu> menus) {
        List<Menu> menuTrees = null;
        if (menus != null) {
            Map<Integer, Menu> map = new HashMap<>();
            menus.forEach(menu -> {
                map.put(menu.getId(), menu);
            });

            menuTrees = new ArrayList<>();
            List<Menu> menuTrees1 = menuTrees;
            map.forEach((id, menuTree) -> {
                if (map.containsKey(menuTree.getPid())) {
                    if (map.get(menuTree.getPid()).getChildren() == null) {
                        List<Menu> children = new ArrayList<>();
                        children.add(menuTree);
                        map.get(menuTree.getPid()).setChildren(children);
                    } else {
                        map.get(menuTree.getPid()).getChildren().add(menuTree);
                    }
                } else {
                    menuTrees1.add(menuTree);
                }
            });

            Comparator menuComparator = new Comparator<Menu>() {
                @Override
                public int compare(Menu o1, Menu o2) {
                    return o1.getOrder().compareTo(o2.getOrder());
                }
            };
            sortTree(menuTrees, menuComparator);
        }
        return menuTrees;
    }

    public static void sortTree(List<Menu> menuTrees, Comparator menuComparator) {
        menuTrees.sort(menuComparator);
        menuTrees.forEach(menuTree -> {
            if (menuTree.getChildren() != null)
                sortTree(menuTree.getChildren(), menuComparator);
        });
    }
}
