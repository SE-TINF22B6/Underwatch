import React from 'react';
import WeaponList from './components/WeaponList.js';
import EnemyList from './components/EnemyList.js';
import { getWeapons, getEnemies } from './data/base';

function Wiki() {
    const weapons = getWeapons();
    const enemies = getEnemies();

    return (
        <div>
            <WeaponList weapons={weapons} />
            <EnemyList enemies={enemies} />
        </div>
    );
}

export default Wiki;