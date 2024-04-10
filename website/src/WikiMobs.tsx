import React from 'react';
import { getWeapons, getEnemies } from './data/base';
import { ThemeProvider } from "@emotion/react";
import { theme2 } from "./theme";
import { AppBar, Toolbar, Typography } from "@mui/material";
import {Link} from "react-router-dom";
import NavigationMenu from "./NavigationMenu";
import WikiRegisters from './WikiRegisters';
import VideoBackground from './VideoBackground';

function WikiMobs() {
    const weapons = getWeapons();
    const enemies = getEnemies();
    

    return (
        <ThemeProvider theme={theme2}>
        <div className="wiki">
            <AppBar position="static" className="appBar">
                <Toolbar variant="dense">
                    <Link to="/" className="link">
                        <img src="../../Logo.png" alt="Underwatch-logo" className="logo" />
                    </Link>
                    <Typography variant='h1' style={{margin: 'auto', fontSize: '35px', fontWeight: 'bold'}}>Wiki:Mobs</Typography>
                    <NavigationMenu/>
                </Toolbar>
            </AppBar>
        </div>

        <WikiRegisters tabIdents={[1,2,1]}/>


        </ThemeProvider>

    );
}

export default WikiMobs;