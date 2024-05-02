import { ThemeProvider } from "@emotion/react";
import { theme2 } from "./theme";
import { AppBar, Paper, Toolbar, Typography } from "@mui/material";
import {Link} from "react-router-dom";
import NavigationMenu from "./NavigationMenu";
import { useState, useEffect } from "react";
import toast from 'react-hot-toast';


export default function Download() {
    return(
        <ThemeProvider theme={theme2}>
        <div className="DownloadPage">
            <AppBar position="static" className="appBar">
                <Toolbar variant="dense">
                    <Link to="/" className="link">
                        <img src="../Logo.png" alt="Underwatch-logo" className="logo" />
                    </Link>
                    <Typography variant='h1' style={{margin: 'auto', fontSize: '35px', fontWeight: 'bold'}}>Download</Typography>
                    <NavigationMenu/>
                </Toolbar>
            </AppBar>

            <Paper style={{
                width: "90%",
                height: "200px",
                backgroundColor: theme2.palette.primary.main,
                margin: "auto",
                marginTop: "20px",
                paddingTop: "100px",
            }}>
                <Typography variant="body1" align="center" style={{
                    color:theme2.palette.primary.contrastText,
                }}>
                    Unfortunally, the download of our game is not  yet available.
                </Typography>
            </Paper>
        </div>
        </ThemeProvider>
    );
}