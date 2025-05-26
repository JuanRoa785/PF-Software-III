import { Component, OnInit } from '@angular/core';
import { CommonModule, NgClass } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { TokenService } from '../../token.service';
import { AppService } from '../../app.service';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { ModalDireccionComponent } from '../../components/modal-direccion/modal-direccion.component';
import { ModalVerifComponent } from '../../components/modal-verif/modal-verif.component';
import { ModalPerfilComponent } from '../../components/modal-perfil/modal-perfil.component';

@Component({
  selector: 'app-perfil',
  standalone: true,
  imports: [ 
    NgClass,
    CommonModule, 
    FormsModule
  ],
  templateUrl: './perfil.component.html',
  styleUrl: './perfil.component.css'
})
export class PerfilComponent  implements OnInit{
  constructor(
    private tokenService: TokenService,
    private router: Router,
    private modalService:NgbModal,
    private appService: AppService
  ) { }

  ngOnInit(): void {
    this.getUserData();
  }

  isPasswordVisible: boolean = false;
  isRepeatPasswordVisible:boolean = false;

  togglePasswordsVisibility(firstPassword:boolean) {
    if (firstPassword) {
      this.isPasswordVisible = !this.isPasswordVisible;
    } else {
      this.isRepeatPasswordVisible = !this.isRepeatPasswordVisible;
    }
  }

  user: any = {
    "id": 0,
    "nombres": "N.A",
    "apellidos": "",
    "email": "N.A",
    "telefono": "N.A",
    "cuenta_activa": true,
    "tipoUsuario": {
      "id": 0,
      "nombre": "string",
      "descripcion": "string"
    }
  }

  direccion: any = {
    "id": 0,
    "pais": "N.A",
    "region": "N.A",
    "ciudad": "N.A",
    "codigo_postal": "N.A",
    "direccion": "N.A",
    "idUsuario": 0,
    "nombreUsuario": "N.A"
  };

  getUserData(){
    this.tokenService.getUser().subscribe(
      (response) => {
        this.user = response;
        this.direccion.idUsuario = this.user.id
        this.getDireccion();
      },
      (error) => {
        console.error('Error obteniendo la info del usuario:', error);
      }
    );
  }

  getDireccion(){
    this.appService.getDireccion(this.user.id).subscribe(
      (response: any[]) => {
        this.direccion = response[0];
        //console.log(response[0]);
      },
      (error) => {
        console.error('Error obteniendo la dirección del usuario:', error);
      }
    );
  }

  openModalDireccion() {
    const modalRef = this.modalService.open(
      ModalDireccionComponent, {
        backdrop: 'static',
        centered: true,
      }
    );
    
    //Generamos una copia de this.direccion para que sean "independientes"
    modalRef.componentInstance.address = JSON.parse(JSON.stringify(this.direccion));
    
    modalRef.closed.subscribe(() => {
      this.getDireccion();
    });
  }

  eliminarDireccion() {
    if (this.direccion.id == 0) {
      return;
    }

    this.appService.eliminarDireccion(this.direccion).subscribe(
      (response) => {
        //console.log(response);
        window.location.href = '/cliente/perfil'; //recargar la pagina
      },
      (error) => {
        console.error('Error eliminando la dirección del usuario:', error);
      }
    );
  }

  verifEliminarDireccion() {
    const modalRef = this.modalService.open(
      ModalVerifComponent, {
        backdrop: 'static',
        centered: true,
      }
    );

    const infoModal = {
      titulo: '¿Está Seguro? ',
      mensaje: '¿De verdad quiere eliminar su dirección?<br><br>Tendrá que anexar una nueva para poder comprar cualquier producto'
    };

    modalRef.componentInstance.modal = infoModal;
    modalRef.componentInstance.tareaARealizar = () => this.eliminarDireccion();
  }

  openModalPerfil() {
    const modalRef = this.modalService.open(
      ModalPerfilComponent, {
        backdrop: 'static',
        centered: true,
      }
    );
    
    //Generamos una copia de this.user para que sean "independientes"
    modalRef.componentInstance.usuario = JSON.parse(JSON.stringify(this.user));
    
    modalRef.closed.subscribe(() => {
      this.getUserData();
    });
  }

  updPassword:string = '';
  updRepeatPassword:string = '';

  strError:string = '';

  logout() {
    this.tokenService.clearToken();
    window.location.href = '/';
  }

  actualizarContrasena() {
    this.strError = ''; 

    if (this.updPassword.trim().length < 8 || this.updRepeatPassword.trim().length < 8) {
      this.strError = '* Las contraseñas deben tener minimo 8 caracteres *'; 
      return;
    }

    if (this.updPassword.trim() != this.updRepeatPassword.trim()) {
      this.strError = '* Las contraseñas no coinciden *'; 
      return;
    }

    this.appService.actualizarContrasena(this.user, this.updPassword.trim()).subscribe(
      (response) => {
        //console.log(response);

        //Abrimos un modal que nos redirigirá al inicio de sesión
        const modalRef = this.modalService.open(
          ModalVerifComponent, {
          backdrop: 'static',
          centered: true,
        }
        );

        const infoModal = {
          titulo: 'Actualización Exitosa',
          mensaje: 'Ya que ha actualizado su contraseña, por favor inicie sesión nuevamente',
          notificacion: true
        };

        modalRef.componentInstance.modal = infoModal;
        modalRef.componentInstance.tareaARealizar = () => this.logout();
      },
      (error) => {
        console.error('Error actualizando la contraseña del usuario:', error);
      }
    );
  }
}
