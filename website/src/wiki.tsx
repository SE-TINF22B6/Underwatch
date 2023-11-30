import React from 'react';
import { getWeapons, getEnemies } from './data/base';
import { ThemeProvider } from "@emotion/react";
import { theme2 } from "./theme";
import VideoBackground from "./VideoBackground";
import { AppBar, Toolbar, Typography } from "@mui/material";
import {Link} from "react-router-dom";
import NavigationMenu from "./NavigationMenu";
import './wiki.css';

function Wiki() {
    const weapons = getWeapons();
    const enemies = getEnemies();

    return (
        <ThemeProvider theme={theme2}>
        <div className="Wiki">
            <VideoBackground/>
            <AppBar position="static" className="appBar">
                <Toolbar variant="dense">
                    <Link to="/" className="link">
                        <img src="../Logo.png" alt="Underwatch-logo" className="logo" />
                    </Link>
                    <Typography variant='h1' style={{margin: 'auto', fontSize: '35px', fontWeight: 'bold'}}>Wiki</Typography>
                    <NavigationMenu/>
                </Toolbar>
            </AppBar>
        </div>

        <div className="wrapper">
            <div className="left">
                <h2>Enemys</h2>
                <table>
                    <thead>
                    <tr>
                        <th>Name</th>
                        <th>Health</th>
                        <th>Damage</th>
                    </tr>
                    </thead>
                    <tbody>
                    {enemies.map((enemy, index) => (
                        <tr key={index}>
                            <td>{enemy.name}</td>
                            <td>{enemy.health}</td>
                            <td>{enemy.damage}</td>
                        </tr>
                    ))}
                    </tbody>
                </table>
            </div>
            <div className="right">
                <h2>Weapons</h2>
                <table>
                    <thead>
                    <tr>
                        <th>Name</th>
                        <th>Damage</th>
                        <th>Type</th>
                    </tr>
                    </thead>
                    <tbody>
                    {weapons.map((weapon, index) => (
                        <tr key={index}>
                            <td>{weapon.name}</td>
                            <td>{weapon.damage}</td>
                            <td>{weapon.type}</td>
                        </tr>
                    ))}
                    </tbody>
                </table>
            </div>
        </div>


        </ThemeProvider>

    );
}

export default Wiki;