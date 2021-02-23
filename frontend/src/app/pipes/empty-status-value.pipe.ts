import {Pipe, PipeTransform} from '@angular/core';

@Pipe({
  name: 'nullValueSave'
})
export class NullValueSavePipe implements PipeTransform {

  constructor() {}


  transform(value: any): any {
    return value === null ? '-' : value;
  }

}
