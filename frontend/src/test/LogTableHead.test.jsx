/**
 * @jest-environment jsdom
 */

import '@testing-library/jest-dom/vitest';
import { render, screen, cleanup } from '@testing-library/react';
import LogTableHead from '../LogTable/LogTableHead.jsx';
import { describe, it, expect, vi, beforeEach } from 'vitest';


describe('LogTableHead', () => {

    beforeEach(() => {
        cleanup();
    });

    it("рендерит все заголовки корректно", () => {
        render(<LogTableHead numSelected={0} rowCount={5} onSelectAllClick={vi.fn()} />);

        expect(screen.getByRole("checkbox")).toBeInTheDocument();

        expect(screen.getByText("ФИО Сотрудника")).toBeInTheDocument();
        expect(screen.getByText("Внёс изменения")).toBeInTheDocument();
        expect(screen.getByText("Дата и время")).toBeInTheDocument();
    });

    it('рендерит чекбокс. все строки выделены', () => {
        const onSelectAllClick = vi.fn();
        render(<LogTableHead numSelected={3} rowCount={3} onSelectAllClick={onSelectAllClick} />);

        const checkbox = screen.getByRole('checkbox');
        expect(checkbox).toBeChecked();
    });

    it('рендерит чекбокс. не все строки выделены ', () => {
        const onSelectAllClick = vi.fn();
        render(<LogTableHead numSelected={2} rowCount={4} onSelectAllClick={onSelectAllClick} />);

        const checkbox = screen.getByRole('checkbox');
        expect(checkbox).not.toBeChecked();
    });

});