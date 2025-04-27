/**
 * @jest-environment jsdom
 */

import { render } from '@testing-library/react';
import { test } from 'vitest';
import Main from '../Main';

test('рендерит Main без ошибок', () => {
  render(<Main />);
});
