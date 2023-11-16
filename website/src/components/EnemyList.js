import React from 'react';

const EnemyList = ({enemies}) => {
    return (
        <div>
            <h2>Enemy List</h2>
            <ul>
                {enemies.map((enemy) => (
                    <li key={enemy.id}>{enemy.name}</li>
                ))}
            </ul>
        </div>
    );
};

export default EnemyList;