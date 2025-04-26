/**
 * @jest-environment jsdom
 */


import { render, screen } from '@testing-library/react';
import { RowsProvider, useRows } from '../LogTable/LogContexts';
import { describe, test, expect } from 'vitest';

import React from 'react';

const TestComponent = () => {
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
        <TestComponent />
      </RowsProvider>
    );
    
    expect(screen.getByTestId('rows').textContent).toBe('[]');

    screen.getByText('Update Rows').click();
  });
});
