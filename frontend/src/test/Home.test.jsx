/**
 * @jest-environment jsdom
 */

import { render, screen } from '@testing-library/react';
import { expect, test } from 'vitest';
import Home from '../Home';
import { RowsProvider, SelectedRowsProvider } from '../LogTable/LogContexts';

test('рендерит компонент Home и проверяет элементы', () => {
    render(
        <RowsProvider>
            <SelectedRowsProvider>
                <Home />
            </SelectedRowsProvider>
        </RowsProvider>
    );

    expect(screen.getByText('Учёт изменений состояния серверной')).toBeTruthy();
});