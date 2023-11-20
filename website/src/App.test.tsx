import { screen } from'@testing-library/react';
import { render } from '@testing-library/react';
import App from './App';
import {MemoryRouter} from 'react-router-dom'
import '@testing-library/jest-dom'

test('Component App (landing page) has Typography "Underwatch"', () => {
    render(<MemoryRouter><App/></MemoryRouter>);


    const typographyElement = screen.getByText('Underwatch');
    expect(typographyElement).toBeInTheDocument();
})