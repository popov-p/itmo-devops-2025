/**
 * @jest-environment jsdom
 */


import { render, screen } from '@testing-library/react';
import { RowsProvider, useRows } from '../LogTable/LogContexts';
import { SelectedRowsProvider, useSelectedRows } from '../LogTable/LogContexts';
import { describe, it, test, expect, beforeEach } from 'vitest';
import { cleanup, fireEvent } from '@testing-library/react';

import React from 'react';

const TestComponentRows = () => {
  const { rows, setRows } = useRows();

  return (
    <div>
      <div data-testid="rows">{JSON.stringify(rows)}</div>
      <button onClick={() => setRows(['row1', 'row2'])}>Update Rows</button>
    </div>
  );
};

describe('RowsContext', () => {
  test('провайдер рендерит детей без ошибок', () => {
    render(
      <RowsProvider>
        <div>Child</div>
      </RowsProvider>
    );
  });

  test('useRows возвращает rows и setRows', () => {
    render(
      <RowsProvider>
        <TestComponentRows />
      </RowsProvider>
    );

    expect(screen.getByTestId('rows').textContent).toBe('[]');

    screen.getByText('Update Rows').click();
  });
});


const TestComponentSelectedRows = () => {
    const { selectedRows, selectRow, selectMultipleRows } = useSelectedRows();
  
    return (
      <div>
        <div data-testid="selected">{selectedRows.join(',')}</div>
        <button onClick={(e) => selectRow(e, 1)}>Select 1</button>
        <button onClick={(e) => selectRow(e, 2)}>Select 2</button>
        <button onClick={(e) => selectRow(e, 3)}>Select 3</button>
        <button onClick={() => selectMultipleRows([4, 5])}>Select multiple [4,5]</button>
        <button onClick={() => selectMultipleRows([])}>Select multiple []</button>
      </div>
    );
  };
  
  beforeEach(() => {
    cleanup();
  });

  describe('SelectedRowsContext', () => {
    test('добавляет элемент в selectedRows', () => {
      const { getByTestId, getByText } = render(
        <SelectedRowsProvider>
          <TestComponentSelectedRows />
        </SelectedRowsProvider>
      );
  
      expect(getByTestId('selected').textContent).toBe('');
  
      fireEvent.click(getByText('Select 1'));
      expect(getByTestId('selected').textContent).toBe('1');
  
      fireEvent.click(getByText('Select 2'));
      expect(getByTestId('selected').textContent).toBe('1,2');
    });
  
    test('удаляет элемент из selectedRows при повторном клике', () => {
        const { getByTestId, getByRole } = render(
          <SelectedRowsProvider>
            <TestComponentSelectedRows />
          </SelectedRowsProvider>
        );
      
        const select1Button = getByRole('button', { name: 'Select 1' });
      
        fireEvent.click(select1Button);
        fireEvent.click(select1Button);
        expect(getByTestId('selected').textContent).toBe('');
      });
      
      test('удаляет элемент если он первый в списке (selectedIndex === 0)', () => {
        const { getByTestId, getByText } = render(
          <SelectedRowsProvider>
            <TestComponentSelectedRows />
          </SelectedRowsProvider>
        );
    
        fireEvent.click(getByText('Select 1'));
        fireEvent.click(getByText('Select 1'));
        expect(getByTestId('selected').textContent).toBe('');
      });

      test('удаляет элемент если он последний в списке (selectedIndex === selectedRows.length - 1)', () => {
        const { getByTestId, getByText } = render(
          <SelectedRowsProvider>
            <TestComponentSelectedRows />
          </SelectedRowsProvider>
        );
    
        fireEvent.click(getByText('Select 1'));
        fireEvent.click(getByText('Select 2'));
        fireEvent.click(getByText('Select 2'));
        expect(getByTestId('selected').textContent).toBe('1');
      });


      test('удаляет элемент если он в середине списка (selectedIndex > 0)', () => {
        const { getByTestId, getByText } = render(
          <SelectedRowsProvider>
            <TestComponentSelectedRows />
          </SelectedRowsProvider>
        );
    
        fireEvent.click(getByText('Select 1'));
        fireEvent.click(getByText('Select 2'));
        fireEvent.click(getByText('Select 3'));
        fireEvent.click(getByText('Select 2'));
        expect(getByTestId('selected').textContent).toBe('1,3');
      });
    
      it('вызов selectMultipleRows добавляет элементы', () => {
        const { getByTestId, getByText } = render(
          <SelectedRowsProvider>
            <TestComponentSelectedRows />
          </SelectedRowsProvider>
        );
    
        fireEvent.click(getByText('Select multiple [4,5]'));
        expect(getByTestId('selected').textContent).toContain('4');
        expect(getByTestId('selected').textContent).toContain('5');
      
        fireEvent.click(getByText('Select multiple []'));
        expect(getByTestId('selected').textContent).toBe('');
      });

  });

  