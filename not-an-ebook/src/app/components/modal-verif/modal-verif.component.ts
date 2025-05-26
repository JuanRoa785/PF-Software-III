import { Component,Input } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-modal-verif',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './modal-verif.component.html',
  styleUrl: './modal-verif.component.css'
})
export class ModalVerifComponent {
  constructor(
    public activeModal: NgbActiveModal,
  ) { }
  
  @Input() modal!: {
    titulo: string,
    mensaje: string,
    notificacion: boolean
  }

  @Input() tareaARealizar!: () => void;

  closeModal() {
    this.activeModal.close()
  }

  ejecutarTarea() {
    if (this.tareaARealizar) {
      this.tareaARealizar();
    }
    this.closeModal();
  }
}
