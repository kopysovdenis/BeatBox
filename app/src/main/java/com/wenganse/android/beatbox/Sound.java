package com.wenganse.android.beatbox;

/**
 * Created by Plus on 06.05.2017.
 */
//Класс для хранения всей информации.
public class Sound {
    private String mAssetPath;
    private String mName;
    private Integer mSoundId;

    public Sound(String assetPath){
        mAssetPath = assetPath;
        //Отделение имени файла
        String[] components = assetPath.split("/");
        String filename = components[components.length - 1];
        //удаление лишнего расширения в имени.
        mName = filename.replace(".wav", "");
    }

    public String getName() {
        return mName;
    }

    public String getAssetPath() {
        return mAssetPath;
    }

    public Integer getSoundId() {
        return mSoundId;
    }

    public void setSoundId(Integer soundId) {
        mSoundId = soundId;
    }
}
