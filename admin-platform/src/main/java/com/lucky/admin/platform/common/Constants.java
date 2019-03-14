package com.lucky.admin.platform.common;

public class Constants {
    public static enum ProcessStatus {
        APPLICATION("0", "申请中"),APPROVAL("1", "审批中"),COMPLETED("2", "已完成");

        private ProcessStatus(String value, String name) {
            this.value = value;
            this.name = name;
        }

        private final String value;
        private final String name;

        public String getValue() {
            return value;
        }
        public String getName() {
            return name;
        }

    }
}
