import { ThemeProvider } from "@emotion/react";
import { theme2 } from "./theme";
import { AppBar, Button, Paper, ToggleButton, ToggleButtonGroup, Toolbar, Typography } from "@mui/material";
import {Link} from "react-router-dom";
import NavigationMenu from "./NavigationMenu";
import { useState, useEffect } from "react";
import toast from 'react-hot-toast';
import { start } from "repl";
import { ConnectingAirportsOutlined } from "@mui/icons-material";

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
    const [currentMonthData, setCurrentMonthData] = useState<ScoreData[]>([]);
    const [monthDataLoaded, setMonthDataLoaded] = useState<boolean>(false);
    const [dataLoaded, setDataLoaded] = useState<boolean>(false);
    const [champions, setChampions] = useState('overall');

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
            //console.log(scores);
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

    useEffect(() => {
        getApiData(`https://underwatch.freemine.de/api/scores/search/filterQuery?score=0&timeStampA=${calcDates()[0]}&timeStampB=${calcDates()[1]}&page=0&size=3&sort=score%2Cdesc`)
        .then((scores) => {
            setCurrentMonthData(scores);
            setMonthDataLoaded(true);
            console.log("Startdatum: " + calcDates()[0]);
            console.log("Enddate: " + calcDates()[1]);
            console.log(scores);
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


    const handleChange = (
        event: React.MouseEvent<HTMLElement>,
        newChampions: string,
      ) => {
            //if (newChampions !== null){
                setChampions(newChampions);
        };


    function calcDates(): string[] {
        const currentDate = new Date();
        const currentMonth = currentDate.getMonth()+1;
        const startdate = currentDate.getFullYear().toString() + "-0" + currentMonth.toString() + "-01%2000%3A00%3A00";
        const enddate = currentDate.getFullYear().toString() + "-0" + currentMonth.toString() + "-31%2023%3A59%3A59";
        //console.log([startdate, enddate]);
        return [startdate, enddate];
    }

    return(
        <ThemeProvider theme={theme2}>
        <div className="Scoreboard">
            <AppBar position="static" className="appBar">
                <Toolbar variant="dense">
                    <Link to="/" className="link">
                        <img src="/Logo.png" alt="Underwatch-logo" className="logo" />
                    </Link>
                    <Typography variant='h1' style={{margin: 'auto', fontSize: '35px', fontWeight: 'bold'}}>Champions</Typography>
                    <NavigationMenu/>
                </Toolbar>
            </AppBar>

            <div className="ChampionsWrapper" style={{
                margin: "50px auto 0 auto",
                width: "80%",
            }}>

                <Paper style={{
                    backgroundColor: theme2.palette.primary.main,
                    padding: "30px 5% 30px 5%",
                    borderRadius: "5px 5px 5px 0px",
                    border: "1px solid #362034"
                }}>
                    { champions === 'overall' &&
                        <Typography variant="h3" align="center" color={theme2.palette.primary.contrastText}>Best Players overall</Typography>
                    }
                    { champions === 'current month' &&
                        <Typography variant="h3" align="center" color={theme2.palette.primary.contrastText}>Best Players of current month</Typography>
                    }
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
                                { champions === 'overall' &&
                                    <Typography variant="h6" align="center" color={theme2.palette.primary.contrastText}>{dataLoaded? apiData[1].playerName: ""}</Typography>
                                }
                                { champions === 'current month' &&
                                    <Typography variant="h6" align="center" color={theme2.palette.primary.contrastText}>{dataLoaded? currentMonthData[1].playerName: ""}</Typography>
                                }
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
                            { champions === 'overall' &&
                                <Typography variant="h6" align="center" color={theme2.palette.primary.contrastText}>{dataLoaded? apiData[0].playerName: ""}</Typography>
                            }
                            { champions === 'current month' &&
                               <Typography variant="h6" align="center" color={theme2.palette.primary.contrastText}>{dataLoaded? currentMonthData[0].playerName: ""}</Typography>
                            }
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
                            { champions === 'overall' &&
                                <Typography variant="h6" align="center" color={theme2.palette.primary.contrastText}>{dataLoaded? apiData[2].playerName: ""}</Typography>
                            }
                            { champions === 'current month' &&
                                <Typography variant="h6" align="center" color={theme2.palette.primary.contrastText}>{dataLoaded? currentMonthData[2].playerName: ""}</Typography>
                            }
                            </div>
                        </div>
                    </div>
                </Paper>              
                <ToggleButtonGroup aria-label="champions choosing" value={{champions}} exclusive onChange={handleChange} style={{
                    backgroundColor: theme2.palette.primary.main,
                    color: theme2.palette.primary.contrastText,
                    borderRadius: "0px 0px 5px 5px",
                }}>
                    <ToggleButton value="overall" aria-label="overall" style={{color: theme2.palette.primary.contrastText, borderRadius: "0px 0px 0px 5px",}}>
                            Overall
                    </ToggleButton>
                    <ToggleButton value="current month" aria-label="current month" style={{color: theme2.palette.primary.contrastText, borderRadius: "0px 0px 5px 0px",}}>
                            current month
                    </ToggleButton>
                </ToggleButtonGroup>
            </div>
        </div>
        </ThemeProvider>
    );
}