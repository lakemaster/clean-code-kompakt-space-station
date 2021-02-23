import { NullValueSavePipe } from './empty-status-value.pipe';

describe('EmptyStatusValuePipe', () => {
  it('create an instance', () => {
    const pipe = new NullValueSavePipe();
    expect(pipe).toBeTruthy();
  });
});
