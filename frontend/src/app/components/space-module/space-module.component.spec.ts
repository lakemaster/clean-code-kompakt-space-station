import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SpaceModuleComponent } from './space-module.component';

describe('SpaceModuleComponent', () => {
  let component: SpaceModuleComponent;
  let fixture: ComponentFixture<SpaceModuleComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SpaceModuleComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SpaceModuleComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
