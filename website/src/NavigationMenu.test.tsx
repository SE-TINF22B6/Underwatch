import React from 'react';
import { render, fireEvent } from '@testing-library/react';
import NavigationMenu from './NavigationMenu';

test('renders NavigationMenu component', () => {
  const { getByText } = render(<NavigationMenu />);
  const menuButton = getByText(/MenuIcon/i);
  expect(menuButton).toBeInTheDocument();
});


test('opens menu when button is clicked', () => {
  const { getByLabelText, getByText } = render(<NavigationMenu />);
  const menuButton = getByLabelText('basic-button');
  fireEvent.click(menuButton);
  const basicMenu = getByText('Landing Page');
  expect(basicMenu).toBeInTheDocument();
});