package de.dhbw.tinf22b6.ai;

import com.badlogic.gdx.ai.fsm.State;
import com.badlogic.gdx.ai.msg.Telegram;
import de.dhbw.tinf22b6.gameobject.Enemy;

public enum EnemyFSMState implements State<Enemy> {
    WAALK() {
        @Override
        public void update(Enemy enemy) {
            super.update(enemy);
        }

        @Override
        public void enter(Enemy enemy) {
            // TODO Auto-generated method stub
            super.enter(enemy);
        }
    },
    DO_NOTHING() {};

    @Override
    public void enter(Enemy enemy) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'enter'");
    }

    @Override
    public void update(Enemy enemy) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    public void exit(Enemy enemy) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'exit'");
    }

    @Override
    public boolean onMessage(Enemy enemy, Telegram telegram) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'onMessage'");
    }
}
