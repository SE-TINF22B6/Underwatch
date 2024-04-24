import { ThemeProvider } from "@emotion/react";
import { theme2 } from "./theme";
import { AppBar, Paper, Toolbar, Typography } from "@mui/material";
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

            <Paper style={{
                backgroundColor: theme2.palette.primary.main,
                margin: "50px auto 0 auto",
                width: "80%",
                padding: "30px 5% 30px 5%",
            }}>
                <Typography variant="h3" align="center" color={theme2.palette.primary.contrastText}>Best Players overall</Typography>
                <div className="podium" style={{
                    width: "100%",
                    height: "250px",
                    display: "flex",
                }}>
                    <div className="secondPlace" style={{flex: "0 0 32%", display: "flex", flexFlow: "column-reverse"}}>
                        <div className="podiumBox" style={{
                            width: "100%",
                            height: "35%",
                            backgroundColor: theme2.palette.primary.dark,
                            color: theme2.palette.primary.contrastText,
                            display: "flex",
                            alignItems: "center",
                            justifyContent: "center",
                            borderRadius: "5px 0 0 0",
                        }}>
                            <Typography variant="h2">2</Typography>
                        </div>
                        <div className="nameBox" style={{width: "100%", height:"30px"}}>
                            <Typography variant="h6" align="center" color={theme2.palette.primary.contrastText}>Name bal bla la l asdf lb alsd </Typography>
                        </div>
                    </div>
                    <div className="firstPlace" style={{flex: "1", display: "flex", flexFlow: "column-reverse"}}>
                        <div className="podiumBox" style={{
                            width: "100%",
                            height: "55%",
                            backgroundColor: theme2.palette.primary.dark,
                            color: theme2.palette.primary.contrastText,
                            display: "flex",
                            alignItems: "center",
                            justifyContent: "center",
                            borderRadius: "5px 5px 0 0",
                        }}>
                            <Typography variant="h1">1</Typography>
                        </div>
                        <div className="nameBox" style={{width: "100%", height:"30px"}}>
                        <Typography variant="h6" align="center" color={theme2.palette.primary.contrastText}>Name bal bla la l asdf lb alsd </Typography>
                        </div>
                    </div>
                    <div  className="thirdPlace" style={{flex: "0 0 32%", display: "flex", flexFlow: "column-reverse"}}>
                        <div className="podiumBox" style={{
                            width: "100%",
                            height: "25%",
                            backgroundColor: theme2.palette.primary.dark,
                            color: theme2.palette.primary.contrastText,
                            display: "flex",
                            alignItems: "center",
                            justifyContent: "center",
                            borderRadius: "0 5px 0 0",
                        }}>
                            <Typography variant="h3">3</Typography>
                        </div>
                        <div className="nameBox" style={{width: "100%", height:"30px"}}>
                        <Typography variant="h6" align="center" color={theme2.palette.primary.contrastText}>Name bal bla la l asdf lb alsd </Typography>
                        </div>
                    </div>
                </div>
            </Paper>
        </div>
        </ThemeProvider>
    );
}