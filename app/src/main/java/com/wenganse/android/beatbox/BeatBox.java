package com.wenganse.android.beatbox;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.AudioManager;
import android.media.SoundPool;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Plus on 06.05.2017.
 */

public class BeatBox {
    //Получение информации об активах
    private static final String TAG ="BeatBox";
    private static final String SOUNDS_FOLDER="sample_sounds";
    //максимальное количество воиспроизводимых звуков одновременно
    public static final int MAX_SOUNDS = 5;

    private List<Sound> mSounds = new ArrayList<>();
    //Константа для констурктора Asset
    private AssetManager mAssets;
    private SoundPool mSoundPool;

    //конструктор, который получает Context, извлекает AssetManager и сохраняет на будущее.
    public BeatBox(Context context){
        mAssets = context.getAssets();
        //Является устаревшим конструктором
        //Из-за того, что в API 16 не поддерживается новый, используем его.
        mSoundPool = new SoundPool(MAX_SOUNDS, AudioManager.STREAM_MUSIC, 0);
        //Для получения списка доступных активов используется list(String).
        loadSounds();
    }
    public void play(Sound sound){
        Integer soundId = sound.getSoundId();
        if (soundId == null){
            return;
        }
        mSoundPool.play(soundId, 1.0f, 1.0f, 1, 0, 1.0f);
    }
    //Освобождение SoundPool от ресурсов после завершения работы.
    public void release(){
        mSoundPool.release();
    }
    public List<Sound> getSounds() {
        return mSounds;
    }
    //Получение списка активов.
    private void loadSounds (){
        String [] soundNames;

        try{
            //list(String) возвращает список имен файлов содержащихся в заданной папке.
            soundNames = mAssets.list( SOUNDS_FOLDER );
            Log.i(TAG, "Found " + soundNames.length + " sounds");
        }catch (IOException ioe){
            Log.e( TAG, " Could not list assets ", ioe);
            return;
        }
        //Строение списка объектов.
        for (String filename : soundNames){
            try {
            String assetPath = SOUNDS_FOLDER + "/" + filename;
            Sound sound = new Sound(assetPath);
                //загрузка всех звуков
                load(sound);
            mSounds.add(sound);
            }catch (IOException ioe){
                Log.e(TAG, "Could not load sound " + filename, ioe);
            }
        }
    }

    private void load(Sound sound) throws IOException{
        //загружает файл для дальнейшего воспроизведения
        AssetFileDescriptor afd = mAssets.openFd(sound.getAssetPath());
        //возвращает идентификатор типа int, который сохраняется в только что определенном поле mSoundId
        int soundId = mSoundPool.load(afd, 1);
        sound.setSoundId(soundId);
    }


}
