import { ThemeProvider } from "@emotion/react";
import { theme2 } from "./theme";
import { AppBar, Toolbar, Typography } from "@mui/material";
import {Link} from "react-router-dom";
import NavigationMenu from "./NavigationMenu";

export default function Champions() {
    return(
        <ThemeProvider theme={theme2}>
        <div className="Scoreboard">
            <AppBar position="static" className="appBar">
                <Toolbar variant="dense">
                    <Link to="/" className="link">
                        <img src="../Logo.png" alt="Underwatch-logo" className="logo" />
                    </Link>
                    <Typography variant='h1' style={{margin: 'auto', fontSize: '35px', fontWeight: 'bold'}}>Champions</Typography>
                    <NavigationMenu/>
                </Toolbar>
            </AppBar>

        </div>
        </ThemeProvider>
    );
}