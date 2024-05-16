import { fireEvent, screen, render, waitFor } from'@testing-library/react';
import {MemoryRouter, Router} from 'react-router-dom';
import { createMemoryHistory } from 'history';
import '@testing-library/jest-dom';
import WikiRegisters from './WikiRegisters';

test('All three Registers exist', () => {
    render(<MemoryRouter><WikiRegisters tabIdents={[3,3,3]}/></MemoryRouter>);
    const weaponsRegister = screen.getByText('Weapons');
    const mobsRegister = screen.getByText('Mobs');
    const tippsRegister = screen.getByText("Game Law");
    expect(weaponsRegister).toBeInTheDocument();
    expect(mobsRegister).toBeInTheDocument();
    expect(tippsRegister).toBeInTheDocument();
});


test('Weapon-Register leads to the correct underpage', () => {
    const history = createMemoryHistory();
    history.push = jest.fn();
    render(
        <Router location={history.location} navigator={history}>
            <WikiRegisters tabIdents={[3,3,3]}/>
        </Router>);
    const weaponsRegister = screen.getByText('Weapons');
    fireEvent.click(weaponsRegister);
    expect(history.push).toHaveBeenCalledWith({
        "hash": "",
        "pathname": "/wiki/weapons/",
        "search": "",
        },
        undefined,
        {"preventScrollReset": undefined,
        "relative": undefined,
        "replace": false,
        "state": undefined,
        "unstable_viewTransition": undefined,
    });
})


test('mouse trigger hover on weaponsRegister', () => {
    render(<MemoryRouter><WikiRegisters tabIdents={[1,1,1]}/></MemoryRouter>);
    const weaponsRegister = screen.getByText('Weapons');
    const weaponParent = weaponsRegister.parentElement?.parentElement?.parentElement;
    expect(weaponParent).toHaveStyle({ height: '50px' });
    fireEvent.mouseOver(weaponsRegister);
    expect(weaponParent).toHaveStyle({ height: '200px' });
})