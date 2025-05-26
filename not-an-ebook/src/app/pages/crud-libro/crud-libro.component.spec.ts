import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CrudLibroComponent } from './crud-libro.component';

describe('CrudLibroComponent', () => {
  let component: CrudLibroComponent;
  let fixture: ComponentFixture<CrudLibroComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CrudLibroComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CrudLibroComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
