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
} from '@mui/material';
import { theme2 } from './theme';
import VideoBackground from './VideoBackground';


let data = [
  { name: 'TheDestroyer', date: '2023-11-08', score: '137' },
  { name: 'TheGamerPro', date: '2023-11-06', score: '98' },
  { name: 'TheDestroyer', date: '2023-11-04', score: '241' },
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
            {/*<p className="sc-heading">Scoreboard</p>*/}
          </Toolbar>
        </AppBar>

        <div style={{display: 'flex'}}>
          <TableContainer component={Paper} sx={{ maxWidth: `calc(100% - 40px)`, maxHeight:'800px', margin: '20px', backgroundColor: theme2.palette.primary.main}}>
          <Table sx={{ minWidth: 350, size: 'small', color: theme2.palette.primary.contrastText}} aria-label="simple table">
          <TableHead style={{color: theme2.palette.primary.contrastText}}>
              <TableRow>
              <TableCell style={{color: theme2.palette.primary.contrastText, fontWeight: 'bold'}}>Username</TableCell>
              <TableCell align="right" style={{color: theme2.palette.primary.contrastText, fontWeight: 'bold'}}>
                  Date
              </TableCell>
              <TableCell align="right" style={{color: theme2.palette.primary.contrastText, fontWeight: 'bold'}}>
                  Scores
              </TableCell>
              </TableRow>
          </TableHead>

          <TableBody>
              {data.map((row) => (
              <TableRow
                  key={row.date}
                  sx={{ '&:last-child td, &:last-child th': { border: 0 } }}
              >
                  <TableCell style={{color: theme2.palette.primary.contrastText}}>{row.name}</TableCell>
                  <TableCell align="right" style={{color: theme2.palette.primary.contrastText}}>
                  {row.date}
                  </TableCell>
                  <TableCell align="right" style={{color: theme2.palette.primary.contrastText}}>
                  {row.score}
                  </TableCell>
              </TableRow>
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
                  <InputLabel htmlFor="component-outlined" style={{color:theme2.palette.primary.contrastText}}>Username</InputLabel>
                  <OutlinedInput
                    label="Username"
                    autoComplete='new-password'
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
                  <InputLabel htmlFor="component-outlined" style={{color:theme2.palette.primary.contrastText}}>Date</InputLabel>
                  <OutlinedInput
                    label="Date"
                    autoComplete='new-password'
                    style={{color: theme2.palette.primary.contrastText}}
                    />
                </FormControl>
                <Typography variant='body1' style={{color:theme2.palette.primary.contrastText}}>min.score</Typography>
                <div style={{padding:'10px'}}>
                    <Slider aria-label="min-score" style={{color: theme2.palette.primary.contrastText}}/>
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
