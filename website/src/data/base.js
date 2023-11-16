
//test data
const weapons = [
    { id: 1, name: 'Sword', damage: 10 },
    { id: 2, name: 'Raser', damage: 15 },
    { id: 3, name: 'Magic Staff', damage: 20 },
];

const enemies = [
    { id: 1, name: 'Goblin', health: 20 },
    { id: 2, name: 'Orc', health: 30 },
    { id: 3, name: 'Dragon', health: 100 },
];
export const getWeapons = () => weapons;
export const getEnemies = () => enemies;