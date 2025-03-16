/**
 * @jest-environment jsdom
 */

import '@testing-library/jest-dom/vitest';
import {describe, it, expect, beforeEach } from 'vitest';
import {cleanup, render, screen, waitFor, fireEvent } from "@testing-library/react";

import { RowsProvider, SelectedRowsProvider } from "../LogTable/LogContexts.jsx";
import LogTableToolbar from "../LogTable/LogTableToolbar.jsx";

describe('LogTableToolbar', () => {

    beforeEach(() => {
        cleanup();
    });

    it('should render the "Add" button when no rows are selected', async () => {
        render(
            <RowsProvider>
                <SelectedRowsProvider>
                    <LogTableToolbar numSelected={0} />
                </SelectedRowsProvider>
            </RowsProvider>
        );

        const addButton = await screen.findByRole('button', { name: /add/i });
        expect(addButton).toBeInTheDocument();

        fireEvent.click(addButton);

        await waitFor(() => {
            const dialogTitle = screen.getByText(/Добавить запись/i);
            expect(dialogTitle).toBeInTheDocument();
        });
    });

});