/**
 * @jest-environment jsdom
 */

import { render, screen, fireEvent } from '@testing-library/react';
import { expect, test, vi } from 'vitest';
import LogFormDialog from '../LogTable/LogFormDialog.jsx';
import { RowsProvider } from '../LogTable/LogContexts.jsx';


test('рендерит диалог и тестируется нажатие отмены и отправить', async () => {
    const closeLogFormDialog = vi.fn();

    render(
        <RowsProvider>
            <LogFormDialog
                opened={true}
                idToEdit={null}
                closeLogFormDialog={closeLogFormDialog}
                employeeName=""
                logMessage=""
            />
        </RowsProvider>
    );

    expect(screen.getByText('Добавить запись')).toBeTruthy();

    const employeeNameField = screen.getByLabelText('ФИО');
    const logMessageField = screen.getByLabelText('Текст');

    expect(employeeNameField).toBeTruthy();
    expect(logMessageField).toBeTruthy();

    const cancelButton = screen.getByText('Отмена');
    const submitButton = screen.getByText('Отправить');

    expect(cancelButton).toBeTruthy();
    expect(submitButton).toBeTruthy();

    fireEvent.click(cancelButton);
    expect(closeLogFormDialog).toHaveBeenCalled();


    fireEvent.click(submitButton);
    expect(closeLogFormDialog).toHaveBeenCalled();
});