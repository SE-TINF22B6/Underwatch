import React from 'react';
import { screen, render, fireEvent } from '@testing-library/react';
import NavigationMenu from './NavigationMenu';
import { MemoryRouter } from 'react-router-dom';
import { Navigation } from '@mui/icons-material';

test('renders NavigationMenu component', () => {
  const { getByTestId, getByText } = render(<MemoryRouter><NavigationMenu/></MemoryRouter>);
  const menuButton = screen.getByTestId('navigation-menu-button');
  expect(menuButton).toBeInTheDocument();
});


test('opens menu when button is clicked', () => {
    const { getByTestId, getByText } = render(<MemoryRouter><NavigationMenu/></MemoryRouter>);
    const menuButton = screen.getByTestId('navigation-menu-button');
    fireEvent.click(menuButton);
    const basicMenu = screen.getByText('Landing Page');
    expect(basicMenu).toBeInTheDocument();
  });