package com.dezzzl.Util;

public enum PersonRole {

        SUPPLIER("Поставщик"),
        CLIENT("Клиент");

        private final String roleName;

        PersonRole(String roleName) {
            this.roleName = roleName;
        }

        public String getRoleName() {
            return roleName;
        }
    }
