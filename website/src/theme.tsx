import { createTheme } from "@mui/material";

export const theme1 = createTheme({
    palette: {
        primary: {
        //    main: '#25131a'
            main: '#F36437',
            contrastText: '#3d253b'
        },
        secondary: {
            main: '#3d253b',
            contrastText: '#fff'
        },
        background: {
            default: '#6e4a48'
        }
    }

});

export const theme2 = createTheme({
    palette: {
        primary: {
        //    main: '#25131a'
            main: '#3d253b',
            dark: '#2B1B2B',
            contrastText: '#F36437'
        },
        secondary: {
            main: '#3d253b',
            contrastText: '#fff'
        },
        background: {
            default: '#6e4a48'
        }
    }

});