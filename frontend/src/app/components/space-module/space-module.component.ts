import {Component, Input, OnInit} from '@angular/core';

@Component({
  selector: 'ccss-space-module',
  templateUrl: './space-module.component.html',
  styleUrls: ['./space-module.component.scss']
})
export class SpaceModuleComponent implements OnInit {

  @Input()
  moduleName: string;

  @Input()
  airConsumption: string;

  @Input()
  power: string;

  @Input()
  weight: string;

  @Input()
  message: string;

  constructor() { }

  ngOnInit() {
  }

}
