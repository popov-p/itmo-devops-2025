/**
 * @jest-environment jsdom
 */

import { render, screen, fireEvent, cleanup } from '@testing-library/react';
import LogTable from '../LogTable/LogTable';
import { RowsProvider } from '../LogTable/LogContexts';
import { SelectedRowsProvider } from '../LogTable/LogContexts';
import { test, describe, it, expect, vi, beforeEach } from 'vitest';

beforeEach(() => {
    cleanup();
});

test('рендерит таблицу', () => {
    render(
      <RowsProvider>
        <SelectedRowsProvider>
          <LogTable />
        </SelectedRowsProvider>
      </RowsProvider>
    );
  });

// Мокаем данные
const mockRows = [
    { id: 1, employeeName: 'John Doe', logMessage: 'Test log 1', timestamp: '2025-04-25' },
    { id: 2, employeeName: 'Jane Smith', logMessage: 'Test log 2', timestamp: '2025-04-26' }
];

describe('handleSelectAllClick', () => {
    it('should deselect all rows when all rows are selected and checkbox is unchecked', () => {
        const selectMultipleRowsMock = vi.fn();
        const { rerender } = render(
            <RowsProvider value={{ rows: mockRows }}>
                <SelectedRowsProvider value={{ selectedRows: [1, 2], selectMultipleRows: selectMultipleRowsMock }}>
                    <LogTable />
                </SelectedRowsProvider>
            </RowsProvider>
        );

        const selectAllCheckbox = screen.getByLabelText('select all logs');

        fireEvent.click(selectAllCheckbox);


        rerender(
            <RowsProvider value={{ rows: mockRows }}>
                <SelectedRowsProvider value={{ selectedRows: [], selectMultipleRows: selectMultipleRowsMock }}>
                    <LogTable />
                </SelectedRowsProvider>
            </RowsProvider>
        );
    });


    it('should select all rows when no rows are selected and checkbox is checked', () => {
        const selectMultipleRowsMock = vi.fn();
        const { rerender } = render(
            <RowsProvider value={{ rows: mockRows }}>
                <SelectedRowsProvider value={{ selectedRows: [], selectMultipleRows: selectMultipleRowsMock }}>
                    <LogTable />
                </SelectedRowsProvider>
            </RowsProvider>
        );

        const selectAllCheckbox = screen.getByLabelText('select all logs');

        fireEvent.click(selectAllCheckbox);

        rerender(
            <RowsProvider value={{ rows: mockRows }}>
                <SelectedRowsProvider value={{ selectedRows: [1, 2], selectMultipleRows: selectMultipleRowsMock }}>
                    <LogTable />
                </SelectedRowsProvider>
            </RowsProvider>
        );
    });


    describe('handleSelectAllClick - when no rows are selected', () => {
        it('should select all rows when checkbox is checked and no rows are selected', () => {
            const selectMultipleRowsMock = vi.fn();
    
            render(
                <RowsProvider value={{ rows: mockRows }}>
                    <SelectedRowsProvider value={{ selectedRows: [], selectMultipleRows: selectMultipleRowsMock }}>
                        <LogTable />
                    </SelectedRowsProvider>
                </RowsProvider>
            );
    
            const selectAllCheckbox = screen.getByLabelText('select all logs');
    
            fireEvent.click(selectAllCheckbox);
        });
    });
});
