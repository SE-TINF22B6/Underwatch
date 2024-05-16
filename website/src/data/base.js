
//test data
const weapons = [
    { id: 1, name: 'AK', damage: 50, firerate: 0.7, range: 20, type: 'gun', picturePath: "/AK.png" },
    { id: 2, name: 'M4', damage: 40, firerate: 0.5, range: 16, type: 'gun', picturePath: "/M4.png" },
];

const enemies = [
    { id: 1, name: 'Snarg (orc1)', health: 150, damage: 20, picturePath: "/Orc1.png" },
    { id: 2, name: 'Grommok (orc2)', health: 300, damage: 30, picturePath: "/Orc2.png" },
    { id: 3, name: 'Grakor (orc3)', health: 450, damage: 40, picturePath: "/Orc3.png" },
    { id: 4, name: 'Durgosh (orc4)', health: 600, damage: 50, picturePath: "/Orc4.png" },
    { id: 5, name: 'Morglak (orc5)', health: 750, damage: 60, picturePath: "/Orc5.png" },
    { id: 6, name: 'OrcBabo', health: 1000, damage: 70, picturePath: "/OrcBabo.png" },
    { id: 7, name: 'Skeleton', health: 300, damage: 50, picturePath: "/Skeleton.png" },
    { id: 8, name: 'SkeletonHad', health: 800, damage: 80, picturePath: "/SkeletonHad.png" }
];
export const getWeapons = () => weapons;
export const getEnemies = () => enemies;