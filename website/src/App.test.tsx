import { fireEvent, screen, render } from'@testing-library/react';
import App from './App';
import {MemoryRouter, Router} from 'react-router-dom';
import { createMemoryHistory } from 'history';
import '@testing-library/jest-dom';

test('Component App (landing page) has Typography "Underwatch"', () => {
    render(<MemoryRouter><App/></MemoryRouter>);


    const typographyElement = screen.getByText('Underwatch');
    expect(typographyElement).toBeInTheDocument();
});

test('Play-Now-Button directs to the download-page', async () => {
    const history = createMemoryHistory();
    history.push = jest.fn();
    render(
        <Router location={history.location} navigator={history}>
            <App/>
        </Router>);
    const button = screen.getByText('Play Now');
    fireEvent.click(button);
    expect(history.push).toHaveBeenCalledWith({
        "hash": "",
        "pathname": "/download/",
        "search": "",
        },
        undefined,
        {"preventScrollReset": undefined,
        "relative": undefined,
        "replace": false,
        "state": undefined,
        "unstable_viewTransition": undefined,});
})