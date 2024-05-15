import React from 'react';
import { render, fireEvent, screen } from '@testing-library/react';
import NavigationMenu from './NavigationMenu';
import { MemoryRouter } from 'react-router-dom';
import '@testing-library/jest-dom'

test('renders NavigationMenu component', () => {
    render(<MemoryRouter><NavigationMenu/></MemoryRouter>);
  const menuButton = screen.getByTestId('navigation-menu-button');
  expect(menuButton).toBeInTheDocument();
});

test('opens menu when button is clicked', () => {
  render(<MemoryRouter><NavigationMenu/></MemoryRouter>);
  const menuButton = screen.getByTestId('navigation-menu-button');
  fireEvent.click(menuButton);
  const basicMenu = screen.getByText('Landing Page');
  expect(basicMenu).toBeInTheDocument();
});