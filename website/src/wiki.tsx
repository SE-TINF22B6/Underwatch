import React from 'react';
import WeaponList from './components/WeaponList.js';
import EnemyList from './components/EnemyList.js';
import { getWeapons, getEnemies } from './data/base';
import { ThemeProvider } from "@emotion/react";
import { theme2 } from "./theme";
import VideoBackground from "./VideoBackground";
import { AppBar, Toolbar, Typography } from "@mui/material";
import {Link} from "react-router-dom";
import NavigationMenu from "./NavigationMenu";

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
            <WeaponList weapons={weapons} />
            <EnemyList enemies={enemies} />
        </div>
        </ThemeProvider>

    );
}

export default Wiki;