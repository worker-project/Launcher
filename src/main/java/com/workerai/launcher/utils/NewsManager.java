package com.workerai.launcher.utils;

import java.util.ArrayList;
import java.util.List;

public class NewsManager {
    private static final List<News> newsList = new ArrayList<>();

    public static void initNews() {
        newsList.add(new News(
                "WorkerAI News",
                "https://noideaindustry.com/Minecraft",
                "Looking to test our available modules?",
                "Have a look at our free trials on our website!",
                ResourceManager.getMinecraftIcon()));

        newsList.add(new News(
                "WorkerClient News",
                "https://noideaindustry.com/Mojang",
                "Want to control our modules via discord?",
                "Check our plans to get access to this feature.",
                ResourceManager.getMinecraftIcon()));

        newsList.add(new News(
                "WorkerAI News",
                "https://noideaindustry.com/Microsoft",
                "Becoming one of our partner?",
                "Apply on our website and get free advantages.",
                ResourceManager.getMinecraftIcon()));
    }

    public static News getNewsList(int index) {
        return newsList.get(index);
    }

    static class News {
        private final String description;
        private final String subDescription;
        private final String url;
        private final String name;
        private final String preview;

        News(String name, String url, String description, String subDescription, String preview) {
            if (description.length() > 48 || subDescription.length() > 46) {
                throw new RuntimeException("Description or Sub Description too long (max 46 chars...)" + "\n ###actual desc = " + description.length() + "\n ###actual subDesc = " + subDescription.length());
            }

            this.name = name;
            this.description = description;
            this.subDescription = subDescription;
            this.url = url;
            this.preview = preview;
        }

        String getDescription() {
            return this.description;
        }

        String getSubDescription() {
            return subDescription;
        }

        String getUrl() {
            return this.url;
        }

        String getName() {
            return this.name;
        }

        String getPreview() {
            return this.preview;
        }
    }
}
