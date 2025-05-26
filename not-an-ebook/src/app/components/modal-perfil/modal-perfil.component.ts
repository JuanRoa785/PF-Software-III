import { Component, Input } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { FormsModule } from '@angular/forms';
import { AppService } from '../../app.service';
import { CommonModule } from '@angular/common';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { ModalVerifComponent } from '../modal-verif/modal-verif.component';
import { TokenService } from '../../token.service';

@Component({
  selector: 'app-modal-perfil',
  standalone: true,
  imports: [
    CommonModule,
    FormsModule
  ],
  templateUrl: './modal-perfil.component.html',
  styleUrl: './modal-perfil.component.css'
})
export class ModalPerfilComponent {
  constructor(
    public activeModal: NgbActiveModal, 
    private appService: AppService,
    private modalService:NgbModal,
    private tokenService:TokenService
  ) {}

  @Input() usuario!: {
    id: number,
    nombres: string,
    apellidos: string,
    email: string,
    telefono: string,
    cuenta_activa: true,
    tipoUsuario: {
      id: 0,
      nombre: string,
      descripcion: string
    }
  }

  strError:string = "";

  verifEmail():boolean {
    if (this.usuario) {
      const regex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
      return regex.test(this.usuario.email);
    } else {
      return false;
    }
  }

  logout() {
    this.tokenService.clearToken();
    window.location.href = '/';
  }

  actualizarPerfil() {
    this.strError = ''
    this.usuario.telefono = this.usuario.telefono.replace(/\D/g, ''); //Dejamos solo números

    if (this.usuario.nombres == '' || this.usuario.apellidos == '') {
      this.strError = '* Nombre o Apellido Invalido *'
      return;
    }

    if (!this.verifEmail()) {
      this.strError = '* Correo Electrónico Invalido *'
      return;
    }

    if (this.usuario.telefono.length != 10) {
      this.strError = '* El teléfono debe tener 10 digitos *'
      return;
    }

    this.appService.actualizarPerfil(this.usuario).subscribe(
      (response) => {
        //console.log(response);

        //Abrimos un modal que nos redirigirá al inicio de sesión
        if (this.tokenService.getUserEmail()?.sub != this.usuario.email) {
          const modalRef = this.modalService.open(
            ModalVerifComponent, {
            backdrop: 'static',
            centered: true,
          }
          );

          const infoModal = {
            titulo: 'Actualización Exitosa',
            mensaje: 'Ya que ha actualizado su correo electrónico, por favor inicie sesión nuevamente',
            notificacion: true
          };

          modalRef.componentInstance.modal = infoModal;
          modalRef.componentInstance.tareaARealizar = () => this.logout();
        } else {
          this.closeModal();
        }
      },
      (error) => {
        console.error('Error actualizando el perfil del usuario:', error);
      }
    );
  }

  closeModal() {
    this.activeModal.close();
  }
}
