import { ThemeProvider } from "@emotion/react";
import { theme2 } from "./theme";
import { AppBar, Button, Paper, Toolbar, Typography } from "@mui/material";
import {Link} from "react-router-dom";
import NavigationMenu from "./NavigationMenu";


export default function Download() {
    return(
        <ThemeProvider theme={theme2}>
        <div className="DownloadPage">
            <AppBar position="static" className="appBar">
                <Toolbar variant="dense">
                    <Link to="/" className="link">
                        <img src="../Logo.png" alt="Underwatch-logo" className="logo" />
                    </Link>
                    <Typography variant='h1' style={{margin: 'auto', fontSize: '35px', fontWeight: 'bold'}}>Download</Typography>
                    <NavigationMenu/>
                </Toolbar>
            </AppBar>

            <Paper style={{
                width: "80%",
                height: "200px",
                backgroundColor: theme2.palette.primary.main,
                margin: "auto",
                marginTop: "20px",
                paddingTop: "50px",
                paddingLeft: "5%",
                paddingRight: "5%",
            }}>
                <Typography variant="body1" align="center" style={{
                    color:theme2.palette.primary.contrastText,
                }}>
                    Altough the game is not yet finished, you can download it via the pages below.
                </Typography>
                <table style={{marginTop:"20px", borderCollapse: "separate", borderSpacing:"0 10px"}}>
                    <tbody>
                        <tr>
                            <td>
                                <Typography variant="body1" align="left" style={{
                                    color:theme2.palette.primary.contrastText,
                                }}>
                                    GitHub Latest Release:
                                </Typography>
                            </td>
                            <td>
                                <Button variant="contained" component="a" href="https://github.com/SE-TINF22B6/Underwatch/releases/latest/" style={{
                                    fontWeight: 'bold', 
                                    border: '1px solid #F36437', 
                                    marginLeft: "10px", 
                                    width: "100%"
                                }}>
                                    Bring me there
                                </Button>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <Typography variant="body1" align="left" style={{
                                    color:theme2.palette.primary.contrastText,
                                }}>
                                    v0.0.2-alpha Fast Downlaod:
                                </Typography>
                            </td>
                            <td>
                                <Button variant="contained" component="a" href="https://github.com/SE-TINF22B6/Underwatch/releases/download/v0.0.2-alpha/desktop-1.0.jar" style={{
                                    fontWeight: 'bold', 
                                    border: '1px solid #F36437', 
                                    marginLeft: "10px", 
                                    width: "100%"
                                }}>
                                    Give it to me
                                </Button>
                            </td>
                        </tr>
                    </tbody>
                </table>
            </Paper>
        </div>
        </ThemeProvider>
    );
}