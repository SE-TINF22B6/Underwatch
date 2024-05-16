import { fireEvent, screen, render, waitFor } from'@testing-library/react';
import {MemoryRouter, Router} from 'react-router-dom';
import { createMemoryHistory } from 'history';
import '@testing-library/jest-dom';
import Download from './Download';


test('Latest-Button exists', () => {
    render(
        <MemoryRouter>
            <Download/>
        </MemoryRouter>
    );
    const latestButton = screen.getByText('Bring me there');
    expect(latestButton).toBeInTheDocument;
})


test('Download-Button exists', () => {
    render(
        <MemoryRouter>
            <Download/>
        </MemoryRouter>
    );
    const downloadButton = screen.getByText('Give it to me');
    expect(downloadButton).toBeInTheDocument;
})


test('Latest-Button has correct URL', () => {
    const history = createMemoryHistory();
    history.push = jest.fn();
    render(
        <Router location={history.location} navigator={history}>
            <Download/>
        </Router>);
    const latestButton = screen.getByText('Bring me there');
    expect(latestButton).toHaveAttribute('href',"https://github.com/SE-TINF22B6/Underwatch/releases/latest/");
})


test('Download-Button has correct URl', () => {
    const history = createMemoryHistory();
    history.push = jest.fn();
    render(
        <Router location={history.location} navigator={history}>
            <Download/>
        </Router>);
    const downloadButton = screen.getByText('Give it to me');
    fireEvent.click(downloadButton);
    expect(downloadButton).toHaveAttribute('href',"https://github.com/SE-TINF22B6/Underwatch/releases/download/v0.0.2-alpha/desktop-1.0.jar");
})