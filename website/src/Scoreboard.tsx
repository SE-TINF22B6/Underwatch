import React, {useEffect, useState} from 'react';
import './Scoreboard.css';
import {Link} from 'react-router-dom';
import {
    AppBar,
    Toolbar,
    Table,
    TableBody,
    TableCell,
    TableContainer,
    TableHead,
    TableRow,
    Paper,
    Button,
    Slider,
    ThemeProvider,
    Typography,
    InputLabel,
    FormControl,
    OutlinedInput,
    TableSortLabel,
} from '@mui/material';
import {theme2} from './theme';
import NavigationMenu from './NavigationMenu';
import {LocalizationProvider} from '@mui/x-date-pickers';
import {AdapterDayjs} from '@mui/x-date-pickers/AdapterDayjs';
import {DateRange, DateRangePicker} from '@mui/x-date-pickers-pro';
import dayjs, {Dayjs} from "dayjs";

const apiDataJSON = `
[
    {
        "id": 1,
        "playerName": "ndeangelo0",
        "score": 529,
        "coins": 9384,
        "kills": 49,
        "damageDealt": 5980,
        "dps": 932,
        "timestamp": "2023-08-18T22:00:00.000+00:00",
        "game_time": 2981
    },
    {
        "id": 2,
        "playerName": "lclaeskens1",
        "score": 989,
        "coins": 6449,
        "kills": 96,
        "damageDealt": 3949,
        "dps": 659,
        "timestamp": "2023-03-05T23:00:00.000+00:00",
        "game_time": 2121
    },
    {
        "id": 3,
        "playerName": "owilkisson2",
        "score": 839,
        "coins": 3371,
        "kills": 60,
        "damageDealt": 8608,
        "dps": 80,
        "timestamp": "2023-01-11T23:00:00.000+00:00",
        "game_time": 2116
    },
    {
        "id": 4,
        "playerName": "bcastanyer3",
        "score": 546,
        "coins": 8889,
        "kills": 63,
        "damageDealt": 115,
        "dps": 870,
        "timestamp": "2023-07-24T22:00:00.000+00:00",
        "game_time": 581
    },
    {
        "id": 5,
        "playerName": "bhughson4",
        "score": 840,
        "coins": 108,
        "kills": 35,
        "damageDealt": 3672,
        "dps": 33,
        "timestamp": "2023-03-26T23:00:00.000+00:00",
        "game_time": 2416
    },
    {
        "id": 6,
        "playerName": "efernao5",
        "score": 823,
        "coins": 1743,
        "kills": 25,
        "damageDealt": 8491,
        "dps": 492,
        "timestamp": "2023-07-12T22:00:00.000+00:00",
        "game_time": 1433
    },
    {
        "id": 7,
        "playerName": "lollivierre6",
        "score": 157,
        "coins": 9680,
        "kills": 61,
        "damageDealt": 5601,
        "dps": 242,
        "timestamp": "2023-05-11T22:00:00.000+00:00",
        "game_time": 3162
    },
    {
        "id": 8,
        "playerName": "lplayhill7",
        "score": 999,
        "coins": 5876,
        "kills": 51,
        "damageDealt": 9087,
        "dps": 694,
        "timestamp": "2023-06-26T22:00:00.000+00:00",
        "game_time": 1084
    },
    {
        "id": 9,
        "playerName": "bciciura8",
        "score": 324,
        "coins": 4965,
        "kills": 97,
        "damageDealt": 1983,
        "dps": 564,
        "timestamp": "2023-05-29T22:00:00.000+00:00",
        "game_time": 279
    },
    {
        "id": 10,
        "playerName": "ckippling9",
        "score": 895,
        "coins": 2333,
        "kills": 8,
        "damageDealt": 7853,
        "dps": 345,
        "timestamp": "2023-02-28T23:00:00.000+00:00",
        "game_time": 1502
    }
]`;

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

// ---------- CookieObject ----------
interface CookieObject {
    username: string;
    startDate: Date | null ;
    endDate: Date | null;
    dateRange: DateRange<Dayjs>;
    minScore: number;
    isFilterOpen: boolean;
}
let cookieObject : CookieObject = {
    username: "",
    startDate: null,
    endDate: null,
    dateRange: [null, null],
    minScore: 0,
    isFilterOpen: false,
}

const Scoreboard = () => {
    // ---------- getApiData ----------
    const [apiData, setApiData] = useState<ScoreData[]>([JSON.parse('{"id": 0,"playerName": "","score": 0,"coins": 0,"kills": 0,"damageDealt": 0,"dps": 0,"timestamp": "","game_time": 0}')]);

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
            throw error;
        }
    }

    // ---------- CookieHandling ----------
    useEffect(() => {
        const cookie = document.cookie;
        const match = cookie.match(/(?:^|;\s*)(filterData=)([^;]+)/);
        if (match) {
            try {
                cookieObject = JSON.parse(decodeURIComponent(match[2]));
            }
            catch (error) {
                console.error("Es kam beim Parsen des JSON-Strings des Cookeis zu folgendem Fehler:", error);
            }
        }
        if (cookieObject.username) {
            setInputValue(cookieObject.username);
        }
        if (cookieObject.startDate && cookieObject.endDate) {
            setScoreStartDate(new Date(cookieObject.startDate));
            setScoreEndDate(new Date(cookieObject.endDate));
            setScoreDate([dayjs(new Date(cookieObject.startDate)), dayjs(new Date(cookieObject.endDate))]);
        } else if (cookieObject.startDate) {
            setScoreStartDate(cookieObject.startDate);
        } else if (cookieObject.endDate) {
            setScoreEndDate(cookieObject.endDate);
        }
        if (cookieObject.minScore) {
            setMinScore(cookieObject.minScore);
        }
        if (cookieObject.isFilterOpen) {
            setShowSecondElement(true);
            setFilterButtonText("Hide Filter");
        }
        getApiData("https://underwatch.freemine.de/api/scores")
        .then((scores) => {
            console.log('API-Daten:', scores);
            setApiData(scores);
        })
        .catch((error) => {
            console.error('Fehler beim Abrufen der API-Daten:', error);
            JSON.parse(apiDataJSON);
        });
    }, []);

    function resetFilter(): void {
        setInputValue('');
        setScoreStartDate(new Date(0));
        setScoreEndDate(new Date());
        setScoreDate([null, null]);
        setMinScore(0);
        document.cookie = `filterData=; expires=Thu, 01 Jan 1970 00:00:00 UTC;`;
        document.cookie = `filterData=; expires=Thu, 01 Jan 1970 00:00:00 UTC; path=/;`;
    }

    // ---------- Sortierung ----------
    const [order, setOrder] = useState<'asc' | 'desc' | undefined>('desc');
    const [orderBy, setOrderBy] = useState<'score' | 'playerName' | 'timestamp' | ''>('score');
    const sortedData = [...apiData].sort((a, b) => {
        if (orderBy === '') {
            return 1;
        } else if (orderBy === 'timestamp') {
            let dateA = new Date(a.timestamp);
            let dateB = new Date(b.timestamp);
            if (order === 'asc') {
                return dateA > dateB ? 1 : -1;
            } else {
                return dateA < dateB ? 1 : -1;
            }
        } else {
            if (order === 'asc') {
                return a[orderBy] > b[orderBy] ? 1 : -1;
            } else {
                return a[orderBy] < b[orderBy] ? 1 : -1;
            }
        }
    });

    function handleSort(sortParam: string): void {
        if (orderBy === sortParam) {
            if (order === 'desc') {
                setOrder('asc');
            } else {
                setOrderBy('');
            }
        } else {
            setOrder('desc')
            setOrderBy(sortParam as 'score' | 'playerName' | 'timestamp' | '');
        }
    }

    // ---------- Filtermaske ----------
    const [showFilterMask, setShowSecondElement] = useState(false);
    const [filterButtonText, setFilterButtonText] = useState('Show Filter');
    const clickFilterButton = () => {
        if (showFilterMask) {
            setShowSecondElement(false);
            setFilterButtonText("Show Filter");
            cookieObject.isFilterOpen = false;
            document.cookie = `filterData=${JSON.stringify(cookieObject)}`;    
        } else {
            setShowSecondElement(true);
            setFilterButtonText("Hide Filter");
            cookieObject.isFilterOpen = true;
            document.cookie = `filterData=${JSON.stringify(cookieObject)}`;
        }
    };

    // ---------- Username ----------
    const [inputValue, setInputValue] = useState('');
    const handleInputChange = (event: { target: { value: React.SetStateAction<string>; }; }) => {
        setInputValue(event.target.value);
        cookieObject.username = event.target.value.toString();
        document.cookie = `filterData=${JSON.stringify(cookieObject)}`;
    }

    // ---------- Date ----------
    const [scoreStartDate, setScoreStartDate] = useState<Date>(new Date(0));
    const [scoreEndDate, setScoreEndDate] = useState<Date>(new Date());
    const [scoreDate, setScoreDate] = useState<DateRange<Dayjs>>([null, null]);

    function handleDateInput(dateData: DateRange<Dayjs>) {
        setScoreDate(dateData);
        cookieObject.dateRange = dateData;
        document.cookie = `filterData=${JSON.stringify(cookieObject)}`;
        if (dateData[0]) {
            let startDate = dateData[0]?.toDate();
            let startDateObject = new Date(startDate.toISOString());
            setScoreStartDate(startDateObject);
            cookieObject.startDate = startDateObject;
            document.cookie = `filterData=${JSON.stringify(cookieObject)}`;
        }
        if (dateData[1]) {
            let endDate = dateData[1]?.toDate();
            let endDateObject = new Date(endDate.toISOString());
            endDateObject.setDate(endDateObject.getDate() + 1);
            setScoreEndDate(endDateObject);
            cookieObject.endDate = endDateObject;
            document.cookie = `filterData=${JSON.stringify(cookieObject)}`;
        }
    }

    // ---------- minScore ----------
    const [minScore, setMinScore] = useState(0);
    function handleMinScoreChange(minScore:number) {
        setMinScore(minScore);
        cookieObject.minScore = minScore;
        document.cookie = `filterData=${JSON.stringify(cookieObject)}`;
    }
    const maxScore = apiData.reduce((max: number, obj: { score: number; }) => (obj.score > max ? obj.score : max), apiData[0].score);

    return (
        <ThemeProvider theme={theme2}>
            <div className="Scoreboard">
                <AppBar position="static" className="appBar">
                    <Toolbar variant="dense">
                        <Link to="/" className="link">
                            <img src="../Logo.png" alt="Underwatch-logo" className="logo"/>
                        </Link>
                        <Typography variant='h1' style={{
                            margin: 'auto',
                            fontSize: '35px',
                            fontWeight: 'bold'
                        }}>Scoreboard</Typography>
                        <NavigationMenu/>
                    </Toolbar>
                </AppBar>

                <div style={{display: 'flex'}}>
                    <TableContainer component={Paper} sx={{
                        maxWidth: `calc(100% - 40px)`,
                        maxHeight: '800px',
                        margin: '20px',
                        backgroundColor: theme2.palette.primary.main
                    }}>
                        <Table sx={{minWidth: 350, size: 'small', color: theme2.palette.primary.contrastText}}
                               aria-label="simple table">
                            <TableHead style={{color: theme2.palette.primary.contrastText}}>
                                <TableRow>
                                    <TableCell style={{color: theme2.palette.primary.contrastText, fontWeight: 'bold'}}>
                                        <TableSortLabel
                                            active={orderBy === 'playerName'}
                                            direction={orderBy === 'playerName' ? order : 'desc'}
                                            onClick={() => handleSort('playerName')}
                                            sx={{
                                                '&.MuiTableSortLabel-root .MuiTableSortLabel-icon': {
                                                    color: theme2.palette.primary.contrastText
                                                },
                                            }}
                                            style={{color: theme2.palette.primary.contrastText}}
                                        >
                                            Username
                                        </TableSortLabel>
                                    </TableCell>
                                    <TableCell align="right"
                                               style={{color: theme2.palette.primary.contrastText, fontWeight: 'bold'}}>
                                        <TableSortLabel
                                            active={orderBy === 'timestamp'}
                                            direction={orderBy === 'timestamp' ? order : 'desc'}
                                            onClick={() => handleSort('timestamp')}
                                            sx={{
                                                '&.MuiTableSortLabel-root .MuiTableSortLabel-icon': {
                                                    color: theme2.palette.primary.contrastText
                                                },
                                            }}
                                            style={{color: theme2.palette.primary.contrastText}}
                                        >
                                            Date
                                        </TableSortLabel>
                                    </TableCell>
                                    <TableCell align="right"
                                               style={{color: theme2.palette.primary.contrastText, fontWeight: 'bold'}}>
                                        <TableSortLabel
                                            active={orderBy === 'score'}
                                            direction={orderBy === 'score' ? order : 'asc'}
                                            onClick={() => handleSort('score')}
                                            sx={{
                                                '&.MuiTableSortLabel-root .MuiTableSortLabel-icon': {
                                                    color: theme2.palette.primary.contrastText
                                                },
                                            }}
                                            style={{color: theme2.palette.primary.contrastText}}
                                        >
                                            Scores
                                        </TableSortLabel>
                                    </TableCell>
                                </TableRow>
                            </TableHead>

                            <TableBody>
                                {sortedData.map((row, id) => (
                                    (
                                        (inputValue === "" || row.playerName.toLowerCase().includes(inputValue.toLowerCase())) &&
                                        (row.score >= minScore) &&
                                        (new Date(row.timestamp) >= scoreStartDate && new Date(row.timestamp) <= scoreEndDate)
                                    ) && (
                                        <TableRow key={id}
                                                  sx={{'&:last-child td, &:last-child th': {border: 0}}}
                                        >
                                            <TableCell style={{color: theme2.palette.primary.contrastText}}>
                                                {row.playerName}
                                            </TableCell>
                                            <TableCell align="right"
                                                       style={{color: theme2.palette.primary.contrastText}}>
                                                {new Date(row.timestamp).toLocaleString('de-DE', {timeZone: 'Europe/Berlin'}).split(',')[0]}
                                            </TableCell>
                                            <TableCell align="right"
                                                       style={{color: theme2.palette.primary.contrastText}}>
                                                {row.score}
                                            </TableCell>
                                        </TableRow>
                                    )
                                ))}
                            </TableBody>
                        </Table>
                    </TableContainer>

                    {showFilterMask && (
                        <Paper elevation={1} sx={{
                            margin: '20px 20px 0 0',
                            padding: '10px',
                            backgroundColor: '#3d253b',
                            maxWidth: '30%'
                        }}>
                            <Typography variant='h6' style={{
                                color: theme2.palette.primary.contrastText,
                                fontWeight: 'bold'
                            }}>Filter</Typography>
                            <FormControl
                                sx={{
                                    '& .MuiOutlinedInput-root': {
                                        '& fieldset': {borderColor: theme2.palette.primary.contrastText},
                                        '&:hover fieldset': {borderColor: theme2.palette.primary.contrastText},
                                        '&.Mui-focused fieldset': {borderColor: theme2.palette.primary.contrastText}
                                    },
                                    width: '100%'
                                }}>
                                <InputLabel htmlFor="component-outlined"
                                            style={{color: theme2.palette.primary.contrastText}}>
                                    Username
                                </InputLabel>
                                <OutlinedInput
                                    id="usernameInput"
                                    label="Username"
                                    value={inputValue}
                                    onChange={handleInputChange}
                                    autoComplete='off'
                                    style={{color: theme2.palette.primary.contrastText}}
                                />
                            </FormControl>
                            <LocalizationProvider dateAdapter={AdapterDayjs}>
                                <DateRangePicker
                                    localeText={{start: 'from', end: 'till'}}
                                    onChange={(newValue) => handleDateInput(newValue)}
                                    value={scoreDate}
                                    sx={{
                                        color: theme2.palette.primary.contrastText,
                                        paddingTop: '10px',
                                        '& .MuiFormLabel-root': {
                                            color: theme2.palette.primary.contrastText
                                        },
                                        '& .MuiFormLabel-root:active': {
                                            color: theme2.palette.primary.contrastText
                                        },
                                        '& .MuiFormLabel-root:selected': {
                                            color: theme2.palette.primary.contrastText
                                        },
                                        '& .MuiOutlinedInput-root': {
                                            color: theme2.palette.primary.contrastText,
                                            '& fieldset': {borderColor: theme2.palette.primary.contrastText},
                                            '&:hover fieldset': {borderColor: theme2.palette.primary.contrastText},
                                            '&.Mui-focused fieldset': {borderColor: theme2.palette.primary.contrastText}
                                        }
                                    }}
                                />
                            </LocalizationProvider>
                            <Typography variant='body1'
                                style={{color: theme2.palette.primary.contrastText}}>
                                min.score
                            </Typography>
                            <div style={{padding: '10px'}}>
                                <Slider
                                    aria-label="min-score"
                                    min={0}
                                    max={maxScore}
                                    value={minScore}
                                    onChange={(event, value) => handleMinScoreChange(value as number)}
                                    valueLabelDisplay='auto'
                                    style={{color: theme2.palette.primary.contrastText}}
                                />
                            </div>
                            <div style={{display: 'flex', justifyContent: 'center'}}>
                            <Button variant="contained" onClick={resetFilter} style={{fontWeight: 'bold', border: '1px solid #F36437', marginTop: "50px", width: "100%"}}>
                                Reset Filter
                            </Button>
                            </div>
                        </Paper>
                    )}

                </div>
                <div style={{display: 'flex', justifyContent: 'center'}}>
                    <Button variant="contained" onClick={clickFilterButton} style={{fontWeight: 'bold'}}>
                        {filterButtonText}
                    </Button>
                </div>
            </div>
        </ThemeProvider>
    );
}

export default Scoreboard;
