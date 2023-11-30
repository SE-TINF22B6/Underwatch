
//test data
const weapons = [
    { id: 1, name: 'Sword', damage: 10, type: 'melee' },
    { id: 2, name: 'Raser', damage: 15, type: 'long' },
    { id: 3, name: 'Magic Staff', damage: 20, type: 'long' },
];

const enemies = [
    { id: 1, name: 'Goblin', health: 20, damage: 2 },
    { id: 2, name: 'Orc', health: 30, damage: 5 },
    { id: 3, name: 'Dragon', health: 100, damage: 20 },
];
export const getWeapons = () => weapons;
export const getEnemies = () => enemies;