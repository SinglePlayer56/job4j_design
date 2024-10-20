package ru.job4j.question;

import java.util.*;

public class Analize {

    public static Info diff(Set<User> previous, Set<User> current) {
        Info info = new Info(0, 0, 0);
        Map<Integer, User> previousUsers = new HashMap<>();
        previous.forEach(user -> previousUsers.put(user.getId(), user));
        for (User user : current) {
            User prevUser = previousUsers.remove(user.getId());
            if (Objects.isNull(prevUser)) {
                info.setAdded(info.getAdded() + 1);
            } else if (!user.equals(prevUser)) {
                info.setChanged(info.getChanged() + 1);
            }
        }
        info.setDeleted(previousUsers.size());
        return info;
    }

}