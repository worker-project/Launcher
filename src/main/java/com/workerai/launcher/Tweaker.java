package com.workerai.launcher;

import fr.theshark34.openlauncherlib.minecraft.GameInfos;
import fr.theshark34.openlauncherlib.minecraft.GameTweak;

public class Tweaker {
    public static final GameTweak WorkerAI = new GameTweak()
    {
        @Override
        public String getName()
        {
            return "WorkerAI Tweaker";
        }

        @Override
        public String getTweakClass(GameInfos infos) {
            return "com.workerai.client.tweak.Tweak";
        }
    };
}
