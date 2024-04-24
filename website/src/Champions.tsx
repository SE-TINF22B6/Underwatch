import { ThemeProvider } from "@emotion/react";
import { theme2 } from "./theme";
import { AppBar, Paper, Toolbar, Typography } from "@mui/material";
import {Link} from "react-router-dom";
import NavigationMenu from "./NavigationMenu";
import { useState, useEffect } from "react";
import toast from 'react-hot-toast';

interface ScoreData {
    playerName: string;
    score: number;
    coins: number;
    kills: number;
    damageDealt: number;
    dps: number;
    timestamp: string;
    game_time: number;
}

export default function Champions() {
    const [apiData, setApiData] = useState<ScoreData[]>([]);
    const [dataLoaded, setDataLoaded] = useState<boolean>(false);
    async function getApiData(url: string): Promise<ScoreData[]> {
        try {
            const response = await fetch(url);
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            const data = await response.json();
            return data._embedded.scores;
        } catch (error) {
            console.error('Beim Laden der API-Daten ist folgender Fehler aufgetreten:', error);
            toast.error("The data could not be loaded, unfortunately!", {
                style: {
                    backgroundColor: theme2.palette.primary.main,
                    border: '3px solid #F36437',
                    color: theme2.palette.primary.contrastText,
                }
            });
            throw error;
        }
    }
    useEffect(() => {
        getApiData('https://underwatch.freemine.de/api/scores?page=0&size=3&sort=score%2Cdesc')
        .then((scores) => {
            setApiData(scores);
            setDataLoaded(true);
        })
        .catch((error) => {
            console.error('Fehler beim Abrufen der API-Daten:', error);
            toast.error("Something gone wrong displaying the data, unfortunately!", {
                style: {
                    backgroundColor: theme2.palette.primary.main,
                    border: '3px solid #F36437',
                    color: theme2.palette.primary.contrastText,
                }
            });
        });
    }, []);



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
                            <Typography variant="h6" align="center" color={theme2.palette.primary.contrastText}>{dataLoaded? apiData[1].playerName: ""}</Typography>
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
                        <Typography variant="h6" align="center" color={theme2.palette.primary.contrastText}>{dataLoaded? apiData[0].playerName: ""}</Typography>
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
                        <Typography variant="h6" align="center" color={theme2.palette.primary.contrastText}>{dataLoaded? apiData[2].playerName: ""}</Typography>
                        </div>
                    </div>
                </div>
            </Paper>
        </div>
        </ThemeProvider>
    );
}