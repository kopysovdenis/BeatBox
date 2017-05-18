package com.wenganse.android.beatbox;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.List;

/**
 * Created by Plus on 06.05.2017.
 */

public class BeatBoxFragment extends Fragment {

    //Екземпляр битбокс
    private BeatBox mBeatBox;


    public static BeatBoxFragment newInstance(){
        return new BeatBoxFragment();
    }

    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        //сохранение между поворотами экрана и беспрерывный аудиопоток.
        setRetainInstance(true);
        mBeatBox = new BeatBox( getActivity() );
    }

    //связка с fragment_beat_box.xml  и создание сетки.
    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate( R.layout.fragment_baet_box, container, false );

        //создание сетки, в каждой строке 3 элемента.
        RecyclerView recyclerView = (RecyclerView)view.findViewById( R.id.fragment_beat_box_recycler_wiew );
        //Подключение сетки
        recyclerView.setLayoutManager( new GridLayoutManager( getActivity(), 3 ) );
        //Подключение адаптера
        recyclerView.setAdapter(new SoundAdapter(mBeatBox.getSounds()));

        return view;
    }
    //освобождение BeatBox после завершения работы.
    @Override
    public void onDestroy() {
        super.onDestroy();
        mBeatBox.release();
    }

    //Виджет ViewHolder  связанный с list_item_sound.xml
    private class SoundHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private Button mButton;
        private Sound mSound;

        public SoundHolder(LayoutInflater inflater, ViewGroup container) {
            super(inflater.inflate(R.layout.list_item_sound, container, false));
            mButton = (Button)itemView.findViewById( R.id.list_item_sound_button);
            mButton.setOnClickListener(this);
        }
        // Связка с объектом Sound
        public void bindSound (Sound sound){
            mSound = sound;
            mButton.setText(mSound.getName());
        }
        @Override
        public void onClick(View v) {
            mBeatBox.play(mSound);
        }
    }

    //Adapter для Holder
    private class SoundAdapter extends RecyclerView.Adapter<SoundHolder>{
         //Связыване адаптера со списком sound.
        private List<Sound> mSounds;

        public SoundAdapter(List<Sound> sounds){
            mSounds = sounds;
        }
        @Override
        public SoundHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from( getActivity());
            return new SoundHolder( inflater, parent );
        }

        @Override
        public void onBindViewHolder(SoundHolder soundHolder, int position) {
            Sound sound = mSounds.get(position);
            soundHolder.bindSound(sound);
        }

        @Override
        public int getItemCount() {
            return mSounds.size();
        }
    }
}
