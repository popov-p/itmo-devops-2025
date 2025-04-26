/**
 * @jest-environment jsdom
 */

import { render, screen } from '@testing-library/react';
import { expect, test } from 'vitest';
import Main from '../Main';

test('рендерит Main без ошибок', () => {
  render(<Main />);
});
