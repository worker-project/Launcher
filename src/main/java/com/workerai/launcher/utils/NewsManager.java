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

    record News(String name, String url, String description, String subDescription, String preview) {
        News {
            if (description.length() > 48 || subDescription.length() > 46) {
                throw new RuntimeException("Description or Sub Description too long (max 46 chars...)" + "\n ###actual desc = " + description.length() + "\n ###actual subDesc = " + subDescription.length());
            }
        }
    }
}
