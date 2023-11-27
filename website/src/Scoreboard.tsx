import React, { useState } from 'react';
import './Scoreboard.css';
import { Link } from 'react-router-dom';
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
import { theme2 } from './theme';
import VideoBackground from './VideoBackground';
import NavigationMenu from './NavigationMenu';


type dataTypeDeklaration = {
  username: string;
  date: string;
  score: string;
  [key: string]: string;
};

let data: dataTypeDeklaration[] = [
  { username: 'TheDestroyer', date: '2023-11-08', score: '137' },
  { username: 'TheGamerPro', date: '2023-12-06', score: '98' },
  { username: 'TheKillerXx', date: '2023-11-04', score: '241' },
  { username: 'ProGamerXx', date: '2023-11-24', score: '798' },
  { username: 'ZeKillerXx', date: '2023-11-23', score: '684' },
];




const Scoreboard = () => {
    const [showFilterMask, setShowSecondElement] = useState(false);
    const [filterButtonText, setFilterButtonText] = useState('Filter');
    const clickFilterButton = () => {
        if (showFilterMask === true){
            setShowSecondElement(false);
            setFilterButtonText("Filter")
        }
        else{
            setShowSecondElement(true);
            setFilterButtonText("Don't  Filter")
        }
    };

    const [inputValue, setInputValue] = useState('');
    const handleInputChange = (event: { target: { value: React.SetStateAction<string>; }; }) => {
      setInputValue(event.target.value);
    }
    const [minScore, setMinScore] = useState(0);
    const handleSlide = (event: Event, newValue: number | number[]) => {
      setMinScore(newValue as number);
    }

    const [order, setOrder] = useState<'asc' | 'desc' | undefined>('desc');
    const [orderBy, setOrderBy] = useState('score');
    const sortedData = [...data].sort((a, b) => {
      if(orderBy === 'score'){
        if (order === 'asc') {
          return parseInt(a[orderBy]) > parseInt(b[orderBy]) ? 1 : -1;
        } else {
          return parseInt(a[orderBy]) < parseInt(b[orderBy]) ? 1 : -1;
        }
      }
      else{
        if (order === 'asc') {
          return a[orderBy] > b[orderBy] ? 1 : -1;
        } else {
          return a[orderBy] < b[orderBy] ? 1 : -1;
        }
      }

    });
    

  function handleSort(sortParam: string): void {
    if (orderBy === sortParam){
      if(order === 'desc'){
        setOrder('asc');
      }
      else{
        setOrderBy('');
      }
    }
    else{
      setOrder('desc')
      setOrderBy(sortParam);
    }
  }

  const maxScore = data.reduce((max, obj) => (parseInt(obj.score) > max ? parseInt(obj.score) : max), parseInt(data[0].score))

  return (
    <ThemeProvider theme={theme2}>
      <div className="Scoreboard">
        <VideoBackground/>
        <AppBar position="static" className="appBar">
          <Toolbar variant="dense">
            <Link to="/" className="link">
              <img src="../Logo.png" alt="Underwatch-logo" className="logo" />
            </Link>
            <Typography variant='h1' style={{margin: 'auto', fontSize: '35px', fontWeight: 'bold'}}>Scoreboard</Typography>
            <NavigationMenu/>
          </Toolbar>
        </AppBar>

        <div style={{display: 'flex'}}>
          <TableContainer component={Paper} sx={{ maxWidth: `calc(100% - 40px)`, maxHeight:'800px', margin: '20px', backgroundColor: theme2.palette.primary.main}}>
          <Table sx={{ minWidth: 350, size: 'small', color: theme2.palette.primary.contrastText}} aria-label="simple table">
          <TableHead style={{color: theme2.palette.primary.contrastText}}>
              <TableRow>
              <TableCell               style={{color: theme2.palette.primary.contrastText, fontWeight: 'bold'}}>
                <TableSortLabel
                  active={orderBy === 'username'}
                  direction={orderBy === 'username' ? order: 'desc'}
                  onClick={()=>handleSort('username')}
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
              <TableCell align="right" style={{color: theme2.palette.primary.contrastText, fontWeight: 'bold'}}>
                <TableSortLabel
                  active={orderBy === 'date'}
                  direction={orderBy === 'date' ? order: 'desc'}
                  onClick={()=>handleSort('date')}
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
              <TableCell align="right" style={{color: theme2.palette.primary.contrastText, fontWeight: 'bold'}}>
                <TableSortLabel
                  active={orderBy === 'score'}
                  direction={orderBy === 'score' ? order : 'asc'}
                  onClick={()=>handleSort('score')}
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
                (
                    ((row.username.toLowerCase().includes(inputValue.toLowerCase())) || inputValue === "") && 
                    (parseInt(row.score) >= minScore)
                ) && (
                  <TableRow
                    sx={{ '&:last-child td, &:last-child th': { border: 0 } }}
                  >
                    <TableCell style={{color: theme2.palette.primary.contrastText}}>
                      {row.username}
                    </TableCell>
                    <TableCell align="right" style={{color: theme2.palette.primary.contrastText}}>
                      {row.date}
                    </TableCell>
                    <TableCell align="right" style={{color: theme2.palette.primary.contrastText}}>
                      {row.score}
                    </TableCell>
                  </TableRow>
                )
              ))}
          </TableBody>
          </Table>
          </TableContainer>

          {showFilterMask && (
              <Paper elevation={1} sx={{margin: '20px 20px 0 0', padding:'10px', backgroundColor: '#3d253b', maxWidth: '30%'}}>
                <Typography variant='h6' style={{color: theme2.palette.primary.contrastText, fontWeight: 'bold'}}>Filter</Typography>
                <FormControl 
                  sx={{'& .MuiOutlinedInput-root':{
                        '& fieldset': {borderColor: theme2.palette.primary.contrastText}, 
                        '&:hover fieldset':{borderColor: theme2.palette.primary.contrastText}, 
                        '&.Mui-focused fieldset':{borderColor: theme2.palette.primary.contrastText}
                       }
                      }}>
                  <InputLabel htmlFor="component-outlined" style={{color:theme2.palette.primary.contrastText}}>
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
                <FormControl 
                  sx={{'& .MuiOutlinedInput-root':{
                        '& fieldset': {borderColor: theme2.palette.primary.contrastText}, 
                        '&:hover fieldset':{borderColor: theme2.palette.primary.contrastText}, 
                        '&.Mui-focused fieldset':{borderColor: theme2.palette.primary.contrastText}
                       }
                      }}>
                  <InputLabel htmlFor="component-outlined" style={{color:theme2.palette.primary.contrastText}}>
                    Date
                  </InputLabel>
                  <OutlinedInput
                    label="Date"
                    autoComplete='new-password'
                    style={{color: theme2.palette.primary.contrastText}}
                    />
                </FormControl>
                <Typography variant='body1' style={{color:theme2.palette.primary.contrastText}}>min.score</Typography>
                <div style={{padding:'10px'}}>
                    <Slider 
                      aria-label="min-score"
                      min={0}
                      max={maxScore}
                      value={minScore}
                      onChange={handleSlide}
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
