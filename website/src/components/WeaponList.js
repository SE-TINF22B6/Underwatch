import React from 'react';

const WeaponList = ({weapons}) => {
    return (
        <div>
            <h2>Weapon List</h2>
            <ul>
                {weapons.map((weapon) => (
                    <li key={weapon.id}>{weapon.name}</li>
                ))}
            </ul>
        </div>
    );
};

export default WeaponList;

