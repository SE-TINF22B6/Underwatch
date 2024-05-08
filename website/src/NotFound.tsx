import { Typography } from '@mui/material';
import React from 'react';
import { Link } from 'react-router-dom';
import { theme2 } from './theme';

function NotFound() {
  return (
    <div className="NotFound" style={{
        color: theme2.palette.primary.contrastText,
        paddingTop: "50px",
        display: "flex",
        flexFlow:"column",
        justifyContent: "center"
        }}>
        <Typography variant="h1" align="center" style={{fontSize:"300px"}}>404</Typography>
        <Typography variant='body1' align="center" style={{width:"80%", alignSelf:"center"}}>
            Die von Ihnen angeforderte Seite konnte leider nicht gefunden werden.
            Bitte überprüfen Sie die URL auf Tippfehler oder kehren sie
            <Link to="/"> hier </Link>
            zur Startseite zurück.
        </Typography>
    </div>
  );
}

export default NotFound;
