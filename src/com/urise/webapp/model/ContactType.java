package com.urise.webapp.model;

public enum ContactType {
    PHONENUMBER("Тел."),
    SKYPE("Skype") {
        @Override
        public String toHtml0(String value) {
            return "<a href='skype:" + value + "'>" + value + "</a>";
        }
    },
    EMAIL("Почта") {
        @Override
        public String toHtml0(String value) {
            return "<a href='mailto:" + value + "'>" + value + "</a>";
        }
    },
    LINKEDIN("Профиль LinkedIn") {
        @Override
        public String toHtml0(String value) {
            return "<a href='" + value + "'>" + value + "</a>";
        }
    },
    GITHUB("Профиль GitHub") {
        @Override
        public String toHtml0(String value) {
            return "<a href='" + value + "'>" + value + "</a>";
        }
    },

    STACKOVERFLOW("Профиль StackOverflow") {
        @Override
        public String toHtml0(String value) {
            return "<a href='" + value + "'>" + value + "</a>";
        }
    },
    HOMEPAGE("Домашняя страница") {
        @Override
        public String toHtml0(String value) {
            return "<a href='" + value + "'>" + value + "</a>";
        }
    };

    private String title;

    ContactType(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    protected String toHtml0(String value) {
        return value;
    }

    public String toHtml(String value) {
        return (value == null ? "" : toHtml0(value));
    }
}
