
//test data
const weapons = [
    { id: 1, name: 'Sword', damage: 10, type: 'melee' },
    { id: 2, name: 'Raser', damage: 15, type: 'long' },
    { id: 3, name: 'Magic Staff', damage: 20, type: 'long' },
    { id: 4, name: 'Pistol', damage: 10, type: 'gun' },
    { id: 5, name: 'Rifle', damage: 15, type: 'gun' },
    { id: 6, name: 'Sniper', damage: 30, type: 'gun' },
];

const enemies = [
    { id: 1, name: 'Goblin', health: 20, damage: 2 },
    { id: 2, name: 'Orc', health: 30, damage: 5 },
    { id: 3, name: 'Dragon', health: 100, damage: 20 },
    { id: 4, name: 'Skeletton', health: 20, damage: 2 },
    { id: 5, name: 'Bomber', health: 10, damage: 50 },
    { id: 6, name: 'Boss', health: 500, damage: 35 },
    { id: 7, name: 'TestMob', health: "-", damage: "-" },
    { id: 7, name: 'TestMob', health: "-", damage: "-" },
    { id: 7, name: 'TestMob', health: "-", damage: "-" },
    { id: 7, name: 'TestMob', health: "-", damage: "-" },
    { id: 7, name: 'TestMob', health: "-", damage: "-" },
    { id: 7, name: 'TestMob', health: "-", damage: "-" },
    { id: 7, name: 'TestMob', health: "-", damage: "-" },
    { id: 7, name: 'TestMob', health: "-", damage: "-" },
   /* { id: 7, name: 'TestMob', health: "-", damage: "-" },*/
];
export const getWeapons = () => weapons;
export const getEnemies = () => enemies;