import React from 'react';
import { ThemeProvider } from "@emotion/react";
import { theme2 } from "./theme";
import { AppBar, Paper, Toolbar, Typography } from "@mui/material";
import {Link} from "react-router-dom";
import NavigationMenu from "./NavigationMenu";
import WikiRegisters from './WikiRegisters';

function WikiLore() {

    

    return (
        <ThemeProvider theme={theme2}>
        <div className="wiki">
            <AppBar position="static" className="appBar">
                <Toolbar variant="dense">
                    <Link to="/" className="link">
                        <img src="../../Logo.png" alt="Underwatch-logo" className="logo" />
                    </Link>
                    <Typography variant='h1' style={{margin: 'auto', fontSize: '35px', fontWeight: 'bold'}}>Wiki:Lore</Typography>
                    <NavigationMenu/>
                </Toolbar>
            </AppBar>
        </div>

        <Paper style={{
                width: "80%",
                maxHeight: "500px",
                backgroundColor: theme2.palette.primary.main,
                margin: "auto",
                marginTop: "20px",
                paddingTop: "50px",
                paddingBottom: "50px",
                paddingLeft: "5%",
                paddingRight: "5%",
                color: theme2.palette.primary.contrastText,
                overflowX: "hidden",
                overflowY: "auto",
            }}>
                <Typography variant="body1">
                    In the dark depths of a <b>forgotten dungeon</b>, once built by mighty wizards, lies a secret that blurs the boundaries between past and present. This dungeon, once a place of magic and mysteries, has now been engulfed by the shadows of oblivion. Yet the secret it holds is far more powerful than time itself.
                        <br/><br/>
                    Many centuries ago, kings and queens ruled over these lands, employing the art of magic to protect and expand their realms. One of these kings, <b>King Alaric</b>, was renowned for his unwavering love for his people and his deep knowledge of the arcane arts. He commissioned the finest architects and wizards of his time to create a dungeon that would serve as both a shield for his kingdom and a prison for the realm's most dangerous foes.
                    The dungeon was built and fortified with the most powerful magic barriers to thwart any escape attempts by the prisoners. However, the dark forces lurking in the depths of the dungeon proved to be more potent than expected. An ancient darkness, existing long before the time of kings, awakened from its slumber and permeated the walls of the dungeon.
                    Over time, the dungeon fell into oblivion, and its name was struck from the chronicles. Yet the sinister power trapped within it patiently awaited the moment of its release.
                        <br/><br/>
                    Centuries later, in an era dominated by technology, adventurers rediscovered the forgotten dungeon. Drawn by tales of immense wealth and forbidden secrets, they bravely ventured into its depths.
                    But what they found was more than they ever expected. Instead of encountering abandoned corridors, they stumbled upon a sinister alliance of <b>orcs and skeletons</b>, corrupted by the dark magic of the dungeon. Yet the most unusual aspect was that the adventurers could not rely on swords and magic to defend themselves. Instead, they found modern <b>firearms</b> in the dusty halls of the dungeon.
                    Armed with these, the adventurers now battle the evil that reigns over the dungeon. As they fight their way through the corridors and gloomy chambers, they push forward, with the monsters growing stronger with each step.
                    Yet the deeper they delve into the dark depths, the more apparent it becomes that the true danger lies not only in the depths of the dungeon but also in the dark shadows of the past itself.
                </Typography>
            </Paper>

        <WikiRegisters tabIdents={[1,1,2]}/>


        </ThemeProvider>

    );
}

export default WikiLore;