export class GridMap {
  private _tileSize: number;
  private _rows: number;
  private _cols: number;
  private _array: number[][];

  // Note: All these values should probably come from backend
  constructor(tileSize: number, rows: number, cols: number) {
    this._tileSize = tileSize;
    this._rows = rows;
    this._cols = cols;

    this._array = [
      [1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1],
      [1,0,0,0,0,0,1,0,0,0,0,0,0,1,0,0,0,0,0,1],
      [1,0,0,0,0,0,1,0,0,0,0,0,0,1,0,0,0,0,0,1],
      [1,0,0,0,0,0,1,1,1,0,0,0,0,1,0,0,0,0,0,1],
      [1,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,1],
      [1,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,1],
      [1,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,1],
      [1,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,1],
      [1,0,0,1,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,1],
      [1,0,0,0,0,0,0,0,0,1,1,1,1,1,0,0,0,0,0,1],
      [1,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,1],
      [1,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,1],
      [1,0,0,1,1,1,1,1,1,1,0,0,0,0,0,0,0,0,0,1],
      [1,0,0,0,0,0,0,0,0,0,0,0,0,1,1,1,0,0,0,1],
      [1,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,1],
      [1,1,1,1,1,0,0,0,0,0,0,0,0,1,0,0,0,0,0,1],
      [1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1],
      [1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1],
      [1,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1],
      [1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1]
    ];
  }

  get map(): number[][] {
    return this._array;
  }

  set map(value: number[][]) {
    this._array = value;
  }

  get cols(): number {
    return this._cols;
  }

  get rows(): number {
    return this._rows;
  }

  get tileSize(): number {
    return this._tileSize;
  }

  get height(): number {
    return this.tileSize * this.rows;
  }

  get width(): number {
    return this.tileSize * this.cols;
  }

}