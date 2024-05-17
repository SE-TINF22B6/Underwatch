import React from 'react';
import { ThemeProvider } from "@emotion/react";
import { theme2 } from "./theme";
import { AppBar, Toolbar, Typography } from "@mui/material";
import {Link} from "react-router-dom";
import NavigationMenu from "./NavigationMenu";
import WikiRegisters from './WikiRegisters';
import WikiCardBox from './WikiCardBox';
import { getEnemies } from './data/base';

function WikiMobs() {
    return (
        <ThemeProvider theme={theme2}>
        <div className="wiki">
            <AppBar position="static" className="appBar">
                <Toolbar variant="dense">
                    <Link to="/" className="link">
                        <img src="/Logo.png" alt="Underwatch-logo" className="logo" />
                    </Link>
                    <Typography variant='h1' style={{margin: 'auto', fontSize: '35px', fontWeight: 'bold'}}>Wiki:Mobs</Typography>
                    <NavigationMenu/>
                </Toolbar>
            </AppBar>
        </div>

        <WikiCardBox getData={getEnemies}/>
        <WikiRegisters tabIdents={[1,2,1]}/>
        
        </ThemeProvider>
    );
}

export default WikiMobs;