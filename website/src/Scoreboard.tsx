import React from 'react';
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
  ThemeProvider,
  createTheme,
} from '@mui/material';
import { makeStyles } from '@mui/styles';

const theme = createTheme();

let data = [
  { name: 'TheDestroyer', date: '2023-11-08', score: '137' },
  { name: 'TheGamerPro', date: '2023-11-06', score: '98' },
  { name: 'TheDestroyer', date: '2023-11-04', score: '241' },
];

const useStyles = makeStyles({
  cell: {
    width: '33.33%', // Teile den verfügbaren Platz gleichmäßig auf drei Spalten auf
  },
});



const Scoreboard = () => {
  const classes = useStyles();

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

      <ThemeProvider theme={theme}>
        <TableContainer component={Paper} sx={{ width: 1 / 2 }}>
          <Table sx={{ minWidth: 650, size: 'small' }} aria-label="simple table">
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
      </ThemeProvider>
    </div>
  );
}

export default Scoreboard;
