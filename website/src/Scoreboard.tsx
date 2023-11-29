import React, {useState} from 'react';
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
import VideoBackground from './VideoBackground';
import NavigationMenu from './NavigationMenu';
import {LocalizationProvider} from '@mui/x-date-pickers';
import {AdapterDayjs} from '@mui/x-date-pickers/AdapterDayjs';
import {DateRange, DateRangePicker} from '@mui/x-date-pickers-pro';

let data = [
    {username: 'TheDestroyer', date: '2023-11-08', score: 137},
    {username: 'TheGamerPro', date: '2023-12-06', score: 98},
    {username: 'TheKillerXx', date: '2023-11-04', score: 241},
    {username: 'ProGamerXx', date: '2023-11-24', score: 6541},
];

let apiData = [
    {
        id: 1,
        playername: "ndeangelo0",
        score: 529,
        coins: 9384,
        kills: 49,
        damagedealt: 5980,
        dps: 932,
        timestamp: "2022-08-18T22:00:00.000+00:00",
        game_time: 2981
    },
    {
        id: 2,
        playername: "lclaeskens1",
        score: 989,
        coins: 6449,
        kills: 96,
        damagedealt: 3949,
        dps: 659,
        timestamp: "2022-03-05T23:00:00.000+00:00",
        game_time: 2121
    },
    {
        id: 3,
        playername: "owilkisson2",
        score: 839,
        coins: 3371,
        kills: 60,
        damagedealt: 8608,
        dps: 80,
        timestamp: "2022-01-11T23:00:00.000+00:00",
        game_time: 2116
    },
    {
        id: 4,
        playername: "bcastanyer3",
        score: 546,
        coins: 8889,
        kills: 63,
        damagedealt: 115,
        dps: 870,
        timestamp: "2022-07-24T22:00:00.000+00:00",
        game_time: 581
    },
    {
        id: 5,
        playername: "bhughson4",
        score: 840,
        coins: 108,
        kills: 35,
        damagedealt: 3672,
        dps: 33,
        timestamp: "2022-03-26T23:00:00.000+00:00",
        game_time: 2416
    },
    {
        id: 6,
        playername: "efernao5",
        score: 823,
        coins: 1743,
        kills: 25,
        damagedealt: 8491,
        dps: 492,
        timestamp: "2022-07-12T22:00:00.000+00:00",
        game_time: 1433
    },
    {
        id: 7,
        playername: "lollivierre6",
        score: 157,
        coins: 9680,
        kills: 61,
        damagedealt: 5601,
        dps: 242,
        timestamp: "2022-05-11T22:00:00.000+00:00",
        game_time: 3162
    },
    {
        id: 8,
        playername: "lplayhill7",
        score: 999,
        coins: 5876,
        kills: 51,
        damagedealt: 9087,
        dps: 694,
        timestamp: "2022-06-26T22:00:00.000+00:00",
        game_time: 1084
    },
    {
        id: 9,
        playername: "bciciura8",
        score: 324,
        coins: 4965,
        kills: 97,
        damagedealt: 1983,
        dps: 564,
        timestamp: "2022-05-29T22:00:00.000+00:00",
        game_time: 279
    },
    {
        id: 10,
        playername: "ckippling9",
        score: 895,
        coins: 2333,
        kills: 8,
        damagedealt: 7853,
        dps: 345,
        timestamp: "2022-02-28T23:00:00.000+00:00",
        game_time: 1502
    }
]


const Scoreboard = () => {
    // ---------- Sortierung ----------
    const [order, setOrder] = useState<'asc' | 'desc' | undefined>('desc');
    const [orderBy, setOrderBy] = useState<'score' | 'playername' | 'timestamp' | ''>('score');
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
            setOrderBy(sortParam as 'score' | 'playername' | 'timestamp' | '');
        }
    }

    // ---------- Filtermaske ----------
    const [showFilterMask, setShowSecondElement] = useState(false);
    const [filterButtonText, setFilterButtonText] = useState('Filter');
    const clickFilterButton = () => {
        if (showFilterMask) {
            setShowSecondElement(false);
            setFilterButtonText("Filter")
        } else {
            setShowSecondElement(true);
            setFilterButtonText("Don't  Filter")
        }
    };

    // ---------- Username ----------
    const [inputValue, setInputValue] = useState('');
    const handleInputChange = (event: { target: { value: React.SetStateAction<string>; }; }) => {
        setInputValue(event.target.value);
    }

    // ---------- Date ----------
    const [scoreStartDate, setScoreStartDate] = useState(new Date());
    const [scoreEndDate, setScoreEndDate] = useState(new Date());

    function handleDateInput(dateData: DateRange<unknown>) {
        if (dateData[0]) {
            let startDate = dateData[0] as Date;
            let startDateObject = new Date(startDate.toISOString());
            console.log(startDate);
            console.log(startDateObject);
            console.log(startDateObject.toLocaleString('de-DE', {timeZone: 'Europe/Berlin'}))
            setScoreStartDate(startDateObject);
        }
        if (dateData[1]) {
            let endDate = dateData[1] as Date;
            let endDateObject = new Date(endDate.toISOString());
            setScoreEndDate(endDateObject);
            //console.log(endDateObject.toISOString().split("T")[0]);
        }
    }

    const [score, setScore] = useState(0);
    const maxScore = data.reduce((max, obj) => (obj.score > max ? obj.score : max), data[0].score);

    function handleSlide() {

    }

    return (
        <ThemeProvider theme={theme2}>
            <div className="Scoreboard">
                <VideoBackground/>
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
                                            active={orderBy === 'playername'}
                                            direction={orderBy === 'playername' ? order : 'desc'}
                                            onClick={() => handleSort('playername')}
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
                                {sortedData.map((row) => (
                                    (inputValue === "" || row.playername.toLowerCase().includes(inputValue.toLowerCase())) && (
                                        <TableRow key={row.id}
                                                  sx={{'&:last-child td, &:last-child th': {border: 0}}}
                                        >
                                            <TableCell style={{color: theme2.palette.primary.contrastText}}>
                                                {row.playername}
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
                                        style={{color: theme2.palette.primary.contrastText}}>min.score</Typography>
                            <div style={{padding: '10px'}}>
                                <Slider
                                    aria-label="min-score"
                                    min={0}
                                    max={maxScore}
                                    value={score}
                                    onChange={(event, value) => setScore(value as number)}
                                    valueLabelDisplay='auto'
                                    style={{color: theme2.palette.primary.contrastText}}
                                />
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
