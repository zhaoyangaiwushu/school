package com.luftraveler.enumerations;

/**
 * 季节枚举类
 */
public enum SeasonType implements Info {
    SPRING(1, "春天") {
        @Override
        public String show() {
            return "季节编码:" + getSeasonCode() + "-" + "季节名称:" + getSeasonName();
        }
    },
    SUMMER(2, "夏天") {
        @Override
        public String show() {
            return "季节编码:" + getSeasonCode() + "-" + "季节名称:" + getSeasonName();
        }
    },
    AUTUMN(3, "秋天") {
        @Override
        public String show() {
            return "季节编码:" + getSeasonCode() + "-" + "季节名称:" + getSeasonName();
        }
    },
    WINTER(4, "冬天") {
        @Override
        public String show() {
            return "季节编码:" + getSeasonCode() + "-" + "季节名称:" + getSeasonName();
        }
    };
    private final Integer seasonCode;
    private final String seasonName;

    SeasonType(Integer seasonCode, String seasonName) {
        this.seasonCode = seasonCode;
        this.seasonName = seasonName;
    }

    public String getSeasonName() {
        return seasonName;
    }

    public Integer getSeasonCode() {
        return seasonCode;
    }
}

interface Info {
    String show();
}

class SeasonEnumTets1 {
    public static void main(String[] args) {
        //遍历所有枚举对象并调用show方法
        for (SeasonType value : SeasonType.values()) {
            System.out.println(value.show());
        }
    }
}
