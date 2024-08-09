package ru.job4j.generics;

public class Role extends Base {
    private final String roleType;

    public Role(String id, String roleType) {
        super(id);
        this.roleType = roleType;
    }

    public String getRoleType() {
        return roleType;
    }
}
