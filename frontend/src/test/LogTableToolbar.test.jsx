/**
 * @jest-environment jsdom
 */

import '@testing-library/jest-dom/vitest';
import {describe, it, expect, beforeEach, vi } from 'vitest';
import {cleanup, render, screen, waitFor, fireEvent } from "@testing-library/react";

import { RowsProvider, SelectedRowsProvider } from "../LogTable/LogContexts.jsx";
import LogTableToolbar from "../LogTable/LogTableToolbar.jsx";
import LogTable from "../LogTable/LogTable.jsx";

import axios from 'axios';

vi.mock('axios');

const mockRows = [
    { id: 1, employeeName: 'John Doe', logMessage: 'Test log 1', timestamp: '2025-04-25' },
    { id: 2, employeeName: 'Jane Smith', logMessage: 'Test log 2', timestamp: '2025-04-26' }
];

describe('LogTableToolbar', () => {

    beforeEach(() => {
        cleanup();
    });

    // it('should render the "Add" button when no rows are selected', async () => {
    //     render(
    //         <RowsProvider>
    //             <SelectedRowsProvider>
    //                 <LogTableToolbar numSelected={0} />
    //             </SelectedRowsProvider>
    //         </RowsProvider>
    //     );

    //     const addButton = await screen.findByRole('button', { name: /add/i });
    //     expect(addButton).toBeInTheDocument();

    //     fireEvent.click(addButton);

    //     await waitFor(() => {
    //         const dialogTitle = screen.getByText(/Добавить запись/i);
    //         expect(dialogTitle).toBeInTheDocument();
    //     });
    // });

    // it('should handle edit click and open dialog with correct data', async () => {
    //     const rows = [
    //       { id: 1, employeeName: 'John Doe', logMessage: 'Test log 1', timestamp: '2025-04-25' },
    //       { id: 2, employeeName: 'Jane Smith', logMessage: 'Test log 2', timestamp: '2025-04-26' }
    //     ];
    
    //     const selectedRows = [1];
    //     const selectMultipleRows = vi.fn();
    
    //     axios.get.mockResolvedValue({
    //       data: { employeeName: 'John Doe', logMessage: 'Test log 1' }
    //     });
    
    //     render(
    //       <RowsProvider value={{ rows }}>
    //         <SelectedRowsProvider value={{ selectedRows, selectMultipleRows }}>
    //           <LogTableToolbar numSelected={selectedRows.length} />
    //         </SelectedRowsProvider>
    //       </RowsProvider>
    //     );
    // });

    // it('should display the correct content when no rows are selected (numSelected === 0)', () => {
    //     render(
    //         <RowsProvider value={{ rows: [] }}>
    //             <SelectedRowsProvider value={{ selectedRows: [], selectMultipleRows: vi.fn() }}>
    //                 <LogTableToolbar numSelected={0} />
    //             </SelectedRowsProvider>
    //         </RowsProvider>
    //     );
    // });

    // it('should display the correct content when one row is selected (numSelected === 1)', () => {
    //     const rows = [
    //         { id: 1, employeeName: 'John Doe', logMessage: 'Test log 1', timestamp: '2025-04-25' }
    //     ];

    //     render(
    //         <RowsProvider value={{ rows }}>
    //             <SelectedRowsProvider value={{ selectedRows: [1], selectMultipleRows: vi.fn() }}>
    //                 <LogTableToolbar numSelected={1} />
    //             </SelectedRowsProvider>
    //         </RowsProvider>
    //     );
    // });


    // it('should display the correct content when multiple rows are selected (numSelected > 1)', () => {
    //     const rows = [
    //         { id: 1, employeeName: 'John Doe', logMessage: 'Test log 1', timestamp: '2025-04-25' },
    //         { id: 2, employeeName: 'Jane Smith', logMessage: 'Test log 2', timestamp: '2025-04-26' }
    //     ];

    //     render(
    //         <RowsProvider value={{ rows }}>
    //             <SelectedRowsProvider value={{ selectedRows: [1, 2], selectMultipleRows: vi.fn() }}>
    //                 <LogTableToolbar numSelected={2} />
    //             </SelectedRowsProvider>
    //         </RowsProvider>
    //     );
    // });

    // it('should delete selected rows and update the state', async () => {
    //     const selectMultipleRowsMock = vi.fn();
    //     const setRowsMock = vi.fn();
    
    //     axios.delete.mockResolvedValueOnce({ status: 200 });
    
    //     render(
    //         <RowsProvider value={{ rows: mockRows, setRows: setRowsMock }}>
    //             <SelectedRowsProvider value={{ selectedRows: [1, 2], selectMultipleRows: selectMultipleRowsMock }}>
    //                 <LogTable />
    //                 <LogTableToolbar numSelected={2} />
    //             </SelectedRowsProvider>
    //         </RowsProvider>
    //     );
    
    //     const selectAllCheckbox = screen.getByLabelText('select all logs');
    //     fireEvent.click(selectAllCheckbox);
    
    //     const deleteButton = screen.getByTestId('delete-button');
    //     fireEvent.click(deleteButton);
    
    // });

});