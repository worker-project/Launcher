package com.workerai.launcher.utils;

public class NewsManager {
    public static class News {
        private final String description;
        private final String subDescription;
        private final String url;
        private final String name;
        private final String preview;

        public News(String name, String url, String description, String subDescription, String preview) {
            if (description.length() > 48 || subDescription.length() > 46) {
                throw new RuntimeException("Description or Sub Description too long (max 46 chars...)" + "\n ###actual desc = " + description.length() + "\n ###actual subDesc = " + subDescription.length());
            }

            this.name = name;
            this.description = description;
            this.subDescription = subDescription;
            this.url = url;
            this.preview = preview;
        }

        public String getDescription() {
            return this.description;
        }

        public String getSubDescription() {
            return subDescription;
        }

        public String getUrl() {
            return this.url;
        }

        public String getName() {
            return this.name;
        }

        public String getPreview() {
            return this.preview;
        }
    }
}
