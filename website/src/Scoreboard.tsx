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
  createTheme,
  Button,
  TextField,
  Slider,
} from '@mui/material';

const theme = createTheme();

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
    <div className="Scoreboard">
      <AppBar position="static" className="appBar">
        <Toolbar variant="dense" className="toolBar">
          <Link to="/" className="link">
            <img src="../Logo.png" alt="Underwatch-logo" className="logo" />
          </Link>
          <p className="sc-heading">Scoreboard</p>
        </Toolbar>
      </AppBar>

      <div style={{display: 'flex'}}>
        <TableContainer component={Paper} sx={{ maxWidth: `calc(100% - 40px)`, maxHeight:'800px', margin: '20px' }}>
        <Table sx={{ minWidth: 350, size: 'small'}} aria-label="simple table">
        <TableHead>
            <TableRow>
            <TableCell >Username</TableCell>
            <TableCell  align="right">
                Date
            </TableCell>
            <TableCell  align="right">
                Score
            </TableCell>
            </TableRow>
        </TableHead>

        <TableBody>
            {data.map((row) => (
            <TableRow
                key={row.date}
                sx={{ '&:last-child td, &:last-child th': { border: 0 } }}
            >
                <TableCell>{row.name}</TableCell>
                <TableCell  align="right">
                {row.date}
                </TableCell>
                <TableCell align="right">
                {row.score}
                </TableCell>
            </TableRow>
            ))}
        </TableBody>
        </Table>
        </TableContainer>

        {showFilterMask && (
            <Paper elevation={1} sx={{margin: '20px 20px 0 0', padding:'10px'}}>
                <b>Filter</b><br/>
                Username<br/>
                <TextField variant="outlined" multiline={false} rows={1}/>
                Date<br/>
                <TextField variant="outlined" multiline={false} rows={1}/>
                min.score<br/>
                <div style={{padding:'10px'}}>
                    <Slider aria-label="min-score" />
                </div>
            </Paper>
        )}

      </div>
      <div style={{display: 'flex', justifyContent: 'center'}}>
        <Button variant="contained" onClick={clickFilterButton}>
                {filterButtonText}
        </Button>
      </div>
    </div>
  );
}

export default Scoreboard;
