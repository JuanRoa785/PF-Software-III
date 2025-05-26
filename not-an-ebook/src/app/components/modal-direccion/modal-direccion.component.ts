import { CommonModule } from '@angular/common';
import { Component, Input } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { FormsModule } from '@angular/forms';
import { AppService } from '../../app.service';

@Component({
  selector: 'app-modal-direccion',
  standalone: true,
  imports: [
    CommonModule, 
    FormsModule
  ],
  templateUrl: './modal-direccion.component.html',
  styleUrl: './modal-direccion.component.css'
})
export class ModalDireccionComponent {

  constructor(
    public activeModal: NgbActiveModal, 
    private appService: AppService
  ) {}

  @Input() address!: {
    id: number,
    pais: string,
    region: string,
    ciudad: string,
    codigo_postal: string,
    direccion: string,
    idUsuario: number,
    nombreUsuario: string
  }
        
  /*Estructura POST y UPDATE:
    direccion: {
      pais: string,
      region: string,
      ciudad: string,
      codigo_postal: string,
      direccion: string,
      usuario: {
        id: number
      }
    }
  */

  strError:String = "";

  actualizarDireccion() {
    this.strError = "";

    if (this.address.pais == '' || this.address.pais == 'N.A') {
      this.strError = "* El pais ingresado es invalido *";
      return;
    }

    if (this.address.region == '' || this.address.region == 'N.A') {
      this.strError = "* La región ingresada es invalida *";
      return;
    }

    if (this.address.ciudad == '' || this.address.ciudad == 'N.A') {
      this.strError = "* La ciudad ingresada es invalida *";
      return;
    }

    if (this.address.codigo_postal == '' || this.address.codigo_postal == 'N.A') {
      this.strError = "* El codigo postal ingresado es invalido *";
      return;
    }

    if (this.address.direccion == '' || this.address.direccion == 'N.A') {
      this.strError = "* La dirección ingresada es invalida *";
      return;
    }

    this.appService.actualizarDireccion(this.address).subscribe(
      (response) => {
        //console.log(response);
        this.closeModal();
      },
      (error) => {
        console.error('Error actualizando la dirección del usuario:', error);
      }
    );
  }

  closeModal() {
    this.activeModal.close();
  }
}
