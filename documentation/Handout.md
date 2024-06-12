# Handout Team Underwatch

Ein Projekt von Benjamin Bies, Jeremias Wolfs, Jakob Fassunge und Martin Schmidt.

## Statistics of efforts

![Statistiken](assets/Screenshot%202024-06-06%20at%2014.43.29.png)

Oben aufgelistet sind die Arbeitstunden, aufgeschlüsselt nach Teammitglied.

- JAWolfs = Jeremias
- iscodeminister = Ting
- BenjaminB109 = Benjamin
- LPkkjHD = Martin
- dolerich-hirnfiedler = Jakob

## Highlights of our Demo

![Level1](assets/Level1.png)

![Combat](assets/Combat1.png)

![Dungeon](assets/Dungeon1.png)

![GameOver](assets/GameOver.png)

![Download Page](assets/DownloadPage.png)

![Webpage Mobs](assets/MobsWiki.png)

![Monthly Champions](assets/CurrentMonthChampions.png)

## Highlights of our Project

- **Box2D**: We use a third party, open-source library which takes care of handling the physics simulation

- **Pathfinding**: We implemented the logic for the A*-Pathfinding algorithm based on tiled layers in Java.
![A*-Algorithm](assets/a_star.png)

- **CI/CD**: We use GitHub Actions to build our project into docker containers and deploy those with a horizontal autoscaler into a kubernetes cluster.
 ![deployment](assets/DeploymentView.png)

### Artstyle

We decided on two different tilesets for the art style. There should be maps in the dungeon and in the outside world. We used a dark TileSet for the maps in the dungeon and one with rock-like textures for the outside world.

The enemies are skeletons(![skelettHut.png](assets/skelettHut.png)) and orcs(![babo.png](assets/babo.png)), but we fight with modern weapons.(![m4.png](assets/m4.png))
![map2.png](assets/map2.png)
![map1.png](assets/map1.png)
