import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ModalVerifComponent } from './modal-verif.component';

describe('ModalVerifComponent', () => {
  let component: ModalVerifComponent;
  let fixture: ComponentFixture<ModalVerifComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ModalVerifComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ModalVerifComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
