/**
 * @jest-environment jsdom
 */

import { render, screen, fireEvent, cleanup, waitFor } from '@testing-library/react';
import { expect, test, vi, beforeEach } from 'vitest';
import LogFormDialog from '../LogTable/LogFormDialog.jsx';
import { RowsProvider} from '../LogTable/LogContexts';
import { SelectedRowsProvider } from '../LogTable/LogContexts';

import axios from 'axios';  
vi.mock('axios');

vi.mock('../LogTable/LogContexts', async (importOriginal) => {
    const actual = await importOriginal();
    return {
      ...actual,
      useRows: vi.fn(() => ({ setRows: vi.fn() })),
      SelectedRowsProvider: ({ children }) => <div>{children}</div>,
    };
  });

beforeEach(() => {
    cleanup();
});

  
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

test('Создание новой записи', async () => {
    const mockCloseDialog = vi.fn();
    const mockSetRows = vi.fn();
  
    axios.post.mockResolvedValue({ data: { id: 1, employeeName: 'John Doe', logMessage: 'Test message' } });
    axios.get.mockResolvedValue({ data: [{ id: 1, employeeName: 'John Doe', logMessage: 'Test message' }] });
  
    render(
      <SelectedRowsProvider>
        <LogFormDialog opened={true} closeLogFormDialog={mockCloseDialog} setRows={mockSetRows} />
      </SelectedRowsProvider>
    );
  
    fireEvent.change(screen.getByLabelText(/ФИО/i), { target: { value: 'John Doe' } });
    fireEvent.change(screen.getByLabelText(/Текст/i), { target: { value: 'Test message' } });

    fireEvent.click(screen.getByText(/Отправить/i));
  
    await waitFor(() => {
      expect(axios.post).toHaveBeenCalledWith(expect.any(String), {
        employeeName: 'John Doe',
        logMessage: 'Test message',
      });
  
      expect(axios.get).toHaveBeenCalledWith(expect.any(String));
    });
  
    expect(mockCloseDialog).toHaveBeenCalledTimes(2);
  });


  test('обновление записи с idToEdit', async () => {
    const mockCloseDialog = vi.fn();
    const mockSetRows = vi.fn();
    const idToEdit = 1;
    const data = { employeeName: 'Jane Doe', logMessage: 'Updated message' };
  
    axios.put.mockResolvedValue({ data: { id: 1, employeeName: 'Jane Doe', logMessage: 'Updated message' } });
  
    render(
      <LogFormDialog
        opened={true}
        closeLogFormDialog={mockCloseDialog}
        setRows={mockSetRows}
        idToEdit={idToEdit}
        employeeName="John Doe"
        logMessage="Old message"
      />
    );
  
    fireEvent.change(screen.getByLabelText(/ФИО/i), { target: { value: 'Jane Doe' } });
    fireEvent.change(screen.getByLabelText(/Текст/i), { target: { value: 'Updated message' } });
  
    fireEvent.click(screen.getByText(/Отправить/i));
  
    await waitFor(() => {
      expect(axios.put).toHaveBeenCalledWith(
        expect.any(String),
        { employeeName: 'Jane Doe', logMessage: 'Updated message' }
      );
    });
  
    expect(mockCloseDialog).toHaveBeenCalledTimes(2);  
  });