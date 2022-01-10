import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { BuildingCreateFormComponent } from './building-create-form.component';

describe('BuildingCreateFormComponent', () => {
  let component: BuildingCreateFormComponent;
  let fixture: ComponentFixture<BuildingCreateFormComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ BuildingCreateFormComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BuildingCreateFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
